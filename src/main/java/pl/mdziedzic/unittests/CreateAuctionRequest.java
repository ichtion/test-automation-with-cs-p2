package pl.mdziedzic.unittests;

import java.util.Calendar;

public class CreateAuctionRequest {
	
	private final String auctionName;
	private final Calendar startTime;
	private final Calendar endTime;
	private final AUCTION_TYPE type;
	private final long startingPrice;
	private final long buyNowPrice;

	private CreateAuctionRequest(String auctionName, Calendar startTime, Calendar endTime,
	 AUCTION_TYPE type, long startingPrice, long buyNowPrice) {
		this.auctionName = auctionName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
		this.startingPrice = startingPrice;
		this.buyNowPrice = buyNowPrice;
	}
	
	public static CreateAuctionRequest createBuyNowAuction(String auctionName, Calendar startTime, Calendar endTime,
	    long buyNowPrice) {

		return new CreateAuctionRequest(auctionName, startTime, endTime, AUCTION_TYPE.BUY_NOW, 0, buyNowPrice);
	}

	public static CreateAuctionRequest createBidAuction(String auctionName, Calendar startTime, Calendar endTime,
	    long startingPrice) {

		return new CreateAuctionRequest(auctionName, startTime, endTime, AUCTION_TYPE.BID, startingPrice, 0);
	}

	public String getName() {
  	return auctionName;
  }

	public Calendar getStartTime() {
  	return startTime;
  }

	public Calendar getEndTime() {
  	return endTime;
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

	@Override
  public String toString() {
	  return "CreateAuctionRequest [auctionName=" + auctionName + ", startTime=" + startTime + ", endTime=" + endTime
	      + ", type=" + type + ", startingPrice=" + startingPrice + ", buyNowPrice=" + buyNowPrice + "]";
  }

}
