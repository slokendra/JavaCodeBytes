package ls.java.basket;

import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.*;


public class Basket {
	private final static Logger logger = Logger.getLogger(Basket.class.getName());

    Map<Product,Integer> productsInBasket;
	double basketTotal;
	double finalTotal;
	Basket()
	{
		productsInBasket = new HashMap<Product,Integer>();	
	}
	/*Method to search for a product in basket by its name.*/
	 public Entry<Product, Integer> findPproductInBasket(String p_name)
	{
		for(Entry<Product, Integer> entry: this.productsInBasket.entrySet())
		{
			if(entry.getKey().productName.equals(p_name))
			{
				/*return reference of the product and quantity in basket.*/
				logger.fine("discounted product " + p_name + " found in the basket");
				return entry;
			}
		}
		return null;
	}
	 /*Method to print the offer details in required format.*/
	 public void formatOfferToDisplay(HashMap<String, Integer> applicable_offers)
	 {
		 for(Entry<String, Integer> entry: applicable_offers.entrySet())
		 {
			 String offerDetails = entry.getKey();
			 
			 if (offerDetails.contains("OFF"))
			 {
				 System.out.println(" " + offerDetails.split(",")[0] + " " + offerDetails.split(",")[1].split(" ")[1]
						 + " "  +  offerDetails.split(",")[2].split(" ")[1] + "% " + offerDetails.split(",")[2].split(" ")[0]
						  		+ " discount: -" + entry.getValue()/100 + "p");
					 
			 }
			 else if(offerDetails.contains("FREE"))
			{
				 System.out.println(" " + offerDetails.split(",")[0] + " Buy " +
						 offerDetails.split(",")[1] 
						  +  " FREE " +   offerDetails.split(",")[2].substring(4) 
						  		+ " discount: -" + entry.getValue()/100 + "p");
				 
			}
		    else if(offerDetails.contains("HP"))
			{
				 System.out.println(" " + offerDetails.split(",")[0] + " Buy " +
						 offerDetails.split(",")[1] 
						  +  " HALF PRICE " +   offerDetails.split(",")[2].substring(2) 
						  		+ " discount: -" + entry.getValue()/100 + "p");
				 
			}
		 }
		 
	 }
	 
	 /*Calculate total price of all the products in the basket.*/
	void calculateTotal()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		for(Entry<Product, Integer> entry: this.productsInBasket.entrySet())
		{
			logger.fine("In Basket-> ProductPrice = " 
								+ entry.getKey().productPrice + "p" + 
								" quantity = " + entry.getValue());
			basketTotal =  basketTotal + entry.getKey().productPrice * entry.getValue(); 
		}
		/*convert from pence to pounds.*/
		System.out.println("Basket SubTotal: " + formatter.format((double) basketTotal/100));
	}
	
	/*Calculate final total after considering the discounts.*/
	void calculateFinalTotal(HashMap<String, Integer> applicable_offers)
	{
		this.finalTotal = this.basketTotal;
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		for(Entry<String, Integer> entry: applicable_offers.entrySet())
		{
		//	System.out.println(" " + entry.getKey() + " discount: -" + entry.getValue()/100 + "p");
			/*Calculate final amount by subtracting discount amount. */
			this.finalTotal = this.finalTotal - entry.getValue()/100;
		}
		if (applicable_offers.size() == 0) 
		{
			System.out.println("(No offers available)");
		}
		else
		{
			formatOfferToDisplay(applicable_offers);
		}
		 
		System.out.println("Basket Total :" + formatter.format((double) this.finalTotal/100));
		
	}


}
