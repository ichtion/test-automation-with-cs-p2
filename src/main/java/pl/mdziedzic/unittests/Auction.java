package pl.mdziedzic.unittests;

import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import pl.mdziedzic.unittests.exception.InvalidObjectStatusException;
import pl.mdziedzic.unittests.exception.InvalidObjectTypeException;
import pl.mdziedzic.unittests.exception.InvalidUserException;
import pl.mdziedzic.unittests.exception.NotSufficientFoundsException;
import pl.mdziedzic.unittests.exception.OperationNotAllowedException;
import pl.mdziedzic.unittests.services.HISTORY_EVENT;
import pl.mdziedzic.unittests.services.MAIL_TEMPLATE;
import pl.mdziedzic.unittests.services.MailProvider;
import pl.mdziedzic.unittests.services.RequestContext;

import com.google.common.collect.Sets;

@Entity
@Table(name = "t_auctions")
@Access(AccessType.FIELD)
public class Auction {

	@Transient
	private RequestContext context;

	@Id
	@GeneratedValue
	@Column(name = "auction_id")
	private long id;

	@Column(name = "name", nullable=false)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable=false)
	private Calendar startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", nullable=false)
	private Calendar endTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "activate_date")
	private Calendar activateDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancel_date")
	private Calendar cancelDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable=false)
	private AUCTION_STATUS status;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable=false)
	private AUCTION_TYPE type;

	@Column(name = "starting_price")
	private long startingPrice;

	@Column(name = "buy_now_price")
	private long buyNowPrice;

	@OneToOne
	private User owner;

	@OneToMany
	@OrderBy("bidDate")
	private Set<Bid> bids = Sets.newTreeSet();

	@SuppressWarnings("unused")
	private Auction() {
	}

	public Auction(RequestContext context, CreateAuctionRequest request) {
		this.context = context;

		mustBeFromFuture(request.getStartTime());
		mustBeFromFuture(request.getEndTime());

		if (request.getEndTime().before(request.getStartTime())) {
			throw new OperationNotAllowedException("End time must be after current time and start time");
		}

		this.name = request.getName();
		this.startTime = request.getStartTime();
		this.endTime = request.getEndTime();
		this.type = request.getType();
		this.startingPrice = request.getStartingPrice();
		this.buyNowPrice = request.getBuyNowPrice();

		this.owner = context.getUser();
		this.status = AUCTION_STATUS.NEW;

		context.getHistoryProvider().saveHistory(HISTORY_EVENT.AUCTION_CREATED);
	}

	public void activate() {
		mustBeStatus(AUCTION_STATUS.NEW);
		mustBeFromPast(startTime);

		status = AUCTION_STATUS.ACTIVE;
		activateDate = context.getNow();

		context.getHistoryProvider().saveHistory(HISTORY_EVENT.AUCTION_ACTIVATED);
	}

	public void bid(long price) {
		mustBeType(AUCTION_TYPE.BID);

		if (startingPrice > price) {
			throw new NotSufficientFoundsException("Current bid price must be heigher than starting price");
		}

		if (hasBids()) {

			Bid lastBid = getBids().last();
			if (lastBid != null && lastBid.getPrice() > price) {
				throw new NotSufficientFoundsException("Current bid price must be heigher than last bid price");
			}
		}

		addBid(price, HISTORY_EVENT.BID_ADDED);
	}

	public void buyNow() {
		mustBeType(AUCTION_TYPE.BUY_NOW);

		addBid(buyNowPrice, HISTORY_EVENT.BOUGHT_NOW);

		finish();
	}

	private void addBid(long price, HISTORY_EVENT historyEvent) {

		mustBeStatus(AUCTION_STATUS.ACTIVE);
		mustNotBeOwner();

		Bid bid = new Bid(this, price, context.getUser(), context.getNow());
		bids.add(bid);

		context.getHistoryProvider().saveHistory(historyEvent);
	}

	public void finish() {
		mustBeStatus(AUCTION_STATUS.ACTIVE);

		if (isType(AUCTION_TYPE.BID) && endTime.before(context.getNow())) {
			throw new OperationNotAllowedException("Auction has to be after its finish time");
		}

		if (hasBids()) {
			notifyBuyer();

			context.getHistoryProvider().saveHistory(HISTORY_EVENT.AUCTION_FINISHED);
		}

		notifySeller();

		status = AUCTION_STATUS.FINISHED;
	}

	private void notifyBuyer() {
		context.getMailProvider().sendMail(MAIL_TEMPLATE.YOU_BOUGHT_AUCTION);
	}

	private void notifySeller() {
		MailProvider mailProvider = context.getMailProvider();

		if (hasBids()) {
			mailProvider.sendMail(MAIL_TEMPLATE.YOUR_AUCTION_IS_SOLD);
		} else {
			mailProvider.sendMail(MAIL_TEMPLATE.YOUR_AUCTION_WAS_NOT_SOLD);
		}
	}

	public boolean isType(AUCTION_TYPE expectedType) {
		return type.equals(expectedType);
	}

	public boolean hasBids() {
		return bids.size() != 0;
	}

	private void mustBeFromFuture(Calendar time) {
		if (time.before(context.getNow())) {
			throw new OperationNotAllowedException("Given time must be from future");
		}
	}

	private void mustBeFromPast(Calendar time) {
		if (time.after(context.getNow())) {
			throw new OperationNotAllowedException("Given time must be from past");
		}
	}

	private void mustBeStatus(AUCTION_STATUS... exptectedStatus) {
		if (isStatusIn(exptectedStatus) == false) {
			throw new InvalidObjectStatusException(String.format("Current auction status is %s, expected %s", status,
			    exptectedStatus));
		}
	}

	private boolean isStatusIn(AUCTION_STATUS... exptectedStatus) { 
		return ArrayUtils.contains(exptectedStatus, status);
	}

	private void mustBeType(AUCTION_TYPE expectedType) {
		if (!isType(expectedType)) {
			throw new InvalidObjectTypeException(String.format("Current auction type is %s, expected %s", type, expectedType));
		}
	}

	private void mustNotBeOwner() {
		if (isCalledByOwner()) {
			throw new InvalidUserException("Operation not allowed for auction owner");
		}
	}

	private boolean isCalledByOwner() {
		return owner.equals(context.getUser());
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public Calendar getActivateDate() {
		return activateDate;
	}

	public Calendar getCancelDate() {
		return cancelDate;
	}

	public AUCTION_STATUS getStatus() {
		return status;
	}

	public AUCTION_TYPE getType() {
		return type;
	}

	public long getStartingPrice() {
		return startingPrice;
	}

	public long getBuyNowPrice() {
		return buyNowPrice;
	}

	public User getOwner() {
		return owner;
	}

	public TreeSet<Bid> getBids() {
		return (TreeSet<Bid>) bids;
	}

	@Override
	public String toString() {
		return "Auction [auctionName=" + name + ", status=" + status + ", type=" + type + "]";
	}

}
