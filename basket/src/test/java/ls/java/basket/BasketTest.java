package ls.java.basket;

import java.util.logging.*;

import org.junit.Test;

import junit.framework.TestCase;


public class BasketTest extends TestCase {

	private final static Logger logger = Logger.getLogger(BasketTest.class.getName());
    
	public void initializeProducts(Basket basket, int a_count, int b_count, int m_count, int s_count)
	{
		//System.out.println("all versions of log4j Logger: " + logger.getAllAppenders(). ); // getClass().getClassLoader().getResources("org/apache/log4j/Logger.class") );
		Product a = new Product(111,"Apples",100);
		Product b = new Product(222,"Bread",80);
		Product m = new Product(333,"Milk",130);
		Product s = new Product(444,"Soup",65);
		
		if (a_count>0) basket.productsInBasket.put(a, a_count);
		if (b_count>0) basket.productsInBasket.put(b, b_count);
		if (m_count>0) basket.productsInBasket.put(m, m_count);
		if (s_count>0) basket.productsInBasket.put(s, s_count);
	}
	@Test
	public void testCalculate_total() {
		Basket basket = new Basket();
		initializeProducts(basket,1,1,1,1);
		basket.calculateTotal();
		logger.info("basket.basketTotal : " + basket.basketTotal/100);
		assertEquals(3.75, basket.basketTotal/100,0.00);	
	}

	@Test
	public void testCalculate_total1() {
		Basket basket = new Basket();
		initializeProducts(basket,2,1,2,1);
		basket.calculateTotal();
		logger.info("basket.basketTotal : " + basket.basketTotal/100);
		//System.out.println("basket.apples.count : " + basket.product_list.get(0).count);
	    assertEquals(6.05, basket.basketTotal/100,0.001);
		
	}
	
}
