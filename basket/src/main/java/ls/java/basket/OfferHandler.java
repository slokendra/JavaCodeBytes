package ls.java.basket;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.*;;

/*A state less class to apply to find applicable offers on a shopping basket.*/
public class OfferHandler {
	private final static Logger logger = Logger.getLogger(OfferHandler.class.getName());
    /* Find valid offers on the basket and calcualte discount applicable for each offer.*/
	static public HashMap<String,Integer> findValidOffers(Basket basket)
	{
		
		HashMap<String, Integer> applicableOffersList = new HashMap<String, Integer>();
		int offerCount = 0, offerCount1=0;
		for(Entry<Product, Integer> entry: basket.productsInBasket.entrySet())
		{
			Product productInBasket = entry.getKey();
			Integer productCountInBasket = entry.getValue();
			
			
			
			/*Search for product in basket in the offers list, to check if any applicable offers.*/
			for(Offer offer: Offer.offersList)
			{
				
				if (offer.productName.equals(productInBasket.productName)
							&& (productCountInBasket >= offer.productCount))
				{
					if(offer.offerType == OfferType.DISCOUNT)
					{
						applicableOffersList.put(offer.offerDetail, (Integer) productInBasket.productPrice
																  *productCountInBasket
																  *offer.discountPercent);
					}
					else if ((offer.offerType == OfferType.FREE) || 
							 (offer.offerType == OfferType.HALFPRICE))
					{
						
						/*Search for discounted product in basket, discount is applicable
						 * if discounted product found in the basket.*/
						Product discountedProduct = null;
						Integer discountedProductCount = 0;
						
						Entry<Product, Integer> productinBasketEntry =
											basket.findPproductInBasket(offer.offerProductName);
						if (productinBasketEntry != null)
						{	
							 discountedProductCount = productinBasketEntry.getValue();
							 discountedProduct =productinBasketEntry.getKey();
						}
						if(discountedProductCount > 0)
						{	
							/*If number of products, which triggers the offers, are matching
							 * with number of products under offer. Total applicable offer count is 1.
							 * If Number of products bought are more than the products under offer,
							 * divide to find the offerCount.
							 * If offer is buy 2 cans milk get 1 loaf of bread half price. 
							 * If user has bought 3 cans of milks, offerCount would be 3/2 = 1.*/
							if(productCountInBasket == offer.productCount) offerCount = 1;
							else offerCount = productCountInBasket/offer.productCount;
							
							/*If number of products which are discounted are less
							 * than the number of discounted products under offer, then 
							 * there is no offer to apply. For ex. offer is-> buy 3 Milk, get 2 loaf
							 * of bread half price. However user has picked up only 1 loaf of bread.
							 * then offer is NOT valid. */
							if(discountedProductCount < offer.offerProductCount)	
							{
								offerCount = 0;
								logger.warning("Buy " + offer.offerProductCount + 
										discountedProduct.productName + " to avail the offer" );
							}
							/*If there are more quantity of discounted products in the basket than 
							 * the quantity under offer. Choose the lower offer_count.*/
							else if (discountedProductCount >= offer.offerProductCount)
							{
								offerCount1 = discountedProductCount/offer.offerProductCount;
								if(offerCount > offerCount1)
									offerCount = offerCount1;
							}
							if(offerCount >= 1)
							{
								logger.fine("Product under offer, offer_count :"+ offerCount);
								
							//	logger.info("discountedProductPprice :"+ discountedProduct.productPrice);
								Integer discountAmount = discountedProduct.productPrice*offerCount*offer.discountPercent;
								applicableOffersList.put(offer.offerDetail, discountAmount);	
								
							//	logger.info("discountAmount :" + discountAmount);
							}	
						}
						else
						{
							if (offer.offerType == OfferType.FREE)
								logger.warning("ALERT: free product " + offer.offerProductName +" is not included in the basket");
							else if (offer.offerType == OfferType.HALFPRICE)
								logger.info("INFORMATION: Discounted product " + offer.offerProductName +" not included in the basket" );
						}
					}			
				}
			}
		}
		
		return applicableOffersList;
	}
}
