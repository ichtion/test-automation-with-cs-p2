package pl.mdziedzic.unittests;

import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_bids")
@Access(AccessType.FIELD)
public class Bid implements Comparable<Bid> {

	@Id
	@GeneratedValue
	@Column(name = "bid_id")
	private long id;

	@Column(name = "price")
	private long price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bid_date", nullable = false)
	private Calendar bidDate;

	@OneToOne
	private User buyer;

	@ManyToOne
	private Auction auction;

	@SuppressWarnings("unused")
	private Bid() {
	}

	public Bid(Auction auction, long price, User buyer, Calendar bidDate) {
		this.price = price;
		this.buyer = buyer;
		this.bidDate = bidDate;
		this.auction = auction;
	}

	public long getId() {
		return id;
	}

	public long getPrice() {
		return price;
	}

	public Calendar getBidDate() {
		return bidDate;
	}

	public User getBuyer() {
		return buyer;
	}

	public Auction getAuction() {
		return auction;
	}

	@Override
	public int compareTo(Bid other) {
		if (other == this) {
			return 0;
		}

		return bidDate.compareTo(other.bidDate);
	}

}
