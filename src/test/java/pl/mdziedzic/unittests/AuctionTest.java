package pl.mdziedzic.unittests;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.mdziedzic.unittests.persistence.Repository;
import pl.mdziedzic.unittests.services.RequestContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AuctionTest {
	
	private Repository repo;

	@Before
	public void setUp() {
		repo = Repository.getInstance();
		repo.beginTransaction();
	}
	
	@After
	public void tearDown() {
		repo.rollback();
	}
	
	/**
	 * Test verifying that it is possible to create an BUY NOW action
	 */
	@Test
	public void test1() {
		User user = new User("example.user");
		repo.persist(user);
		
		RequestContext context = new RequestContext(user);

		Calendar startTime = Calendar.getInstance();
		startTime.add(Calendar.DATE, 1);

		Calendar endTime = (Calendar) startTime.clone();
		endTime.add(Calendar.DATE, 7);

		CreateAuctionRequest request = CreateAuctionRequest.createBuyNowAuction("Jan Kowalski", startTime, endTime, 1000);

		Auction auction = new Auction(context, request);
		repo.persist(auction);

		assertFalse(auction.hasBids());
		assertEquals("Jan Kowalski", auction.getName());
        assertThat(auction.getName(), is(equalTo("Jan Kowalski")));
		assertEquals(startTime, auction.getStartTime());
		assertEquals(endTime, auction.getEndTime());
		assertEquals(AUCTION_TYPE.BUY_NOW, auction.getType());
		assertEquals(AUCTION_STATUS.NEW, auction.getStatus());
		assertEquals(1000, auction.getBuyNowPrice());
		assertEquals(user, auction.getOwner());
	}

	/**
	 * Test verifying that it is possible to activate an auction
	 */
	@Test
	public void test2() {
		User user = new User("example.user");
		repo.persist(user);
		
		RequestContext context = new RequestContext(user);

		Calendar startTime = Calendar.getInstance();
		startTime.add(Calendar.DATE, 1);

		Calendar endTime = (Calendar) startTime.clone();
		endTime.add(Calendar.DATE, 7);

		CreateAuctionRequest request = CreateAuctionRequest.createBuyNowAuction("Piotr Nowak", startTime, endTime, 1000);

		Auction auction = new Auction(context, request);
		repo.persist(auction);

		Calendar now = context.getNow();
		now.add(Calendar.DATE, 1);
		now.add(Calendar.SECOND, 1);

		auction.activate();

		assertNotNull(auction.getActivateDate());
		assertEquals(AUCTION_STATUS.ACTIVE, auction.getStatus());
	}
	
}
