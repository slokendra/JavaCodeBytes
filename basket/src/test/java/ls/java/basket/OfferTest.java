package ls.java.basket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.*;

import org.junit.Test;

import junit.framework.TestCase;

public class OfferTest extends TestCase {
	private final static Logger logger = Logger.getLogger(OfferTest.class.getName());
	
	@Test
	public void testFindValidOffers() {
		BasketTest bt = new BasketTest();
		Basket basket = new Basket();
		
		List<String> offers = new ArrayList<String>();
		offers.add("Offer1,1 Apples,OFF 10");	
		offers.add("Offer2,2 Soup,HP 1 Bread");
		offers.add("Offer3,2 Milk,FREE 1 Biscuit");
		
		Offer.decodeOffers(offers);
	
		logger.info("******testFindValidOffers Begins *******");
		bt.initializeProducts(basket,2/*Apples*/,1/*Bread*/,2/*Milk*/,1/*Soup*/);
		basket.calculateTotal();
		
	    HashMap<String, Integer> applicable_offers =   OfferHandler.findValidOffers(basket);
		basket.calculateFinalTotal(applicable_offers);
		assertEquals(5.85,  basket.finalTotal/100);
		logger.info("******testFindValidOffers Ends *******");
		logger.info("    ");
	}
	
	@Test
	public void testFindValidOffers1() {
		BasketTest bt = new BasketTest();
		Basket basket = new Basket();
		
		List<String> offers = new ArrayList<String>();
		offers.add("Offer1,1 Apples,OFF 10");	
		offers.add("Offer2,2 Soup,HP 1 Bread");
		offers.add("Offer3,2 Milk,FREE 1 Biscuit");
		
		Offer.decodeOffers(offers);
		logger.info("******testFindValidOffers1 Begins *******");
		bt.initializeProducts(basket,4,2,4,0);
		basket.calculateTotal();/*SubTotal £10.80*/
		
	    HashMap<String, Integer> applicable_offers =   OfferHandler.findValidOffers(basket);
		basket.calculateFinalTotal(applicable_offers);
		assertEquals(10.4,  basket.finalTotal/100);/*Final total £10.40*/
		logger.info("******testFindValidOffers1 Ends *******");
		logger.info("    ");
	}
	@Test
	public void testFindValidOffers2() {
		BasketTest bt = new BasketTest();
		Basket basket = new Basket();

		List<String> offers = new ArrayList<String>();
		offers.add("Offer1,1 Apples,OFF 10");	
		offers.add("Offer2,2 Soup,HP 1 Bread");
		offers.add("Offer3,2 Milk,FREE 1 Biscuit");
		
		Offer.decodeOffers(offers);
		logger.info("******testFindValidOffers2 Begins *******");
		bt.initializeProducts(basket,4,2,1,4);
		basket.calculateTotal();/*SubTotal £9.50*/
		
	    HashMap<String, Integer> applicable_offers =   OfferHandler.findValidOffers(basket);
		basket.calculateFinalTotal(applicable_offers);
		assertEquals(8.3,  basket.finalTotal/100);/*Final total £7.50*/
		logger.info("******testFindValidOffers2 Ends *******");
		logger.info("    ");
	}
	public void testFindValidOffers3() {
		BasketTest bt = new BasketTest();
		Basket basket = new Basket();
		
		List<String> offers = new ArrayList<String>();
		offers.add("Offer1,1 Apples,OFF 10");	
		offers.add("Offer2,2 Soup,HP 1 Bread");
		offers.add("Offer3,2 Milk,FREE 1 Biscuit");
		
		Offer.decodeOffers(offers);
		logger.info("******testFindValidOffers3 Begins *******");
		bt.initializeProducts(basket,4,0,1,4);
		basket.calculateTotal();/*SubTotal £7.90*/
		
	    HashMap<String, Integer> applicable_offers =   OfferHandler.findValidOffers(basket);
		basket.calculateFinalTotal(applicable_offers);
		assertEquals(7.50,  basket.finalTotal/100);/*Final total £7.50*/
		logger.info("******testFindValidOffers3 Ends *******");
		logger.info("    ");
	}
	@Test
	public void testDecodeOffers() {
		/*call fetch offers*/
		logger.info("******testDecodeOffers Begins *******");
		List<Offer> test_offers_list = new ArrayList<Offer>();
		test_offers_list.add(new Offer("Offer1,1 Apples,OFF 10","Apples",1,10," ",0,OfferType.DISCOUNT)); 
		test_offers_list.add(new Offer("Offer2,2 Soup,HP 1 Bread","Soup",2,50,"Bread",1,OfferType.HALFPRICE));
		test_offers_list.add(new Offer("Offer3,2 Milk,FREE 1 Biscuit","Milk",2,100,"Biscuit",1,OfferType.FREE));
		
		List<String> offers = new ArrayList<String>();
		offers.add("Offer1,1 Apples,OFF 10");	
		offers.add("Offer2,2 Soup,HP 1 Bread");
		offers.add("Offer3,2 Milk,FREE 1 Biscuit");
		
		try
		{
			Offer.offersList.clear();
			Offer.decodeOffers(offers);
			logger.info("testDecodeOffers");
			assertEquals(test_offers_list,Offer.offersList);
		}
		catch(Exception e)
		{
			System.out.println("Exception in testDecodeOffers");
		}
		logger.info("******testDecodeOffers Ends *******");
		logger.info("    ");
	}
}
