package ls.java.basket;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
import java.util.logging.*;

/**
 * @author slokendra
 * Class shop, which initialises  list of products and their prices from 
 * file Products.csv. Calls method to load offers from Offers.csv.
 * Process the shopped product entered through command line arguments, 
 * calls method to apply offers and prints final basket total.
 */
public class Shop {

	private final static Logger logger = Logger.getLogger( Shop.class.getName());
	
	static Set<Product> productPriceList = new HashSet<Product>();
	
	/*Read file products.csv and initialise set productPriceList.
	 * Each element contains Product object. */
	static public void initialiseProductPriceList() throws IOException
	{
		/* List all .csv files in root directory, open product.csv file 
		 * and initialise products list.*/
	    try
	    {		
	    	 String directory = ".\\";
			 List<Path> fileslist = Files.walk(Paths.get(directory))
			     .filter(Files::isRegularFile).filter(line->line.toString().contains(".csv"))
			     .collect(Collectors.toList());
			
			  for(Path pt : fileslist)
			  {
				  /*Look for products.csv file*/
				  if (pt.toString().contains("products.csv"))
				  {
					Stream<String> productfile =  Files.lines(Paths.get(pt.toUri()));
					List<String> products = productfile.filter(line->!(line.contains("*")))
											.collect(Collectors.toList());  
					
					for(String productFileLine : products)
					{
						/*Extract product code, product name and price from product.csv file.*/
						Product product = new Product(Integer.parseInt(productFileLine.split(",")[0]), 
								productFileLine.split(",")[1],Integer.parseInt(productFileLine.split(",")[2]));
						productPriceList.add(product);
						logger.info(productPriceList.toString());
					}	
					
					productfile.close();	
				  }
			  } 
	    } 
	    catch (IOException ie)
	    {
	    	logger.severe("IO Exception" + ie);
	    	throw ie;
	    }
	    catch (Exception e)
	    {
	    	logger.severe("other Exception " + e);
	    	throw e;
	    	
	    }	    	
	}
	
	/*Find price of a product from global productPriceList*/
	static public Integer find_product_price(Product product)
	{
		for(Product p : productPriceList)
		{
			if(p.equals(product))
				return p.productPrice;
		}
		logger.warning("price of poduct " + product.productName + "NOT found");
		return 0;
	}
	
	/* Validate if all the product entries are valid and found in the 
	 * productPriceList.*/
	static public boolean validateAndFetchShoppedProducts(String[] args,
								HashMap<Product,Integer> shoppedProductList)
	{
		/*Search for products in the list and calculate basket total*/
		if (args.length >  0)
		{	
			//validateShoppedProductsList();
			for(String shoppedProduct : args)
		    {
				boolean vaildProduct =false;
				/*Search for shopped product's name in 
				 * product price list.*/
				for(Product product : productPriceList)
				{
					if (product.productName.equals(shoppedProduct))
					{
						vaildProduct = true;
						
						shoppedProductList.put(product, (shoppedProductList.containsKey(product) ?
												shoppedProductList.get(product) : 0) +1);
						break;
					}
				}
				if (vaildProduct == false)
				{
					logger.warning("invalid product name:" + shoppedProduct);
					return false;
				}
		      }	
		}
		else
		{
			logger.warning("Empty basket");
			return false;
		}
		return true;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
	
		Basket basket = new Basket();
		/*Map of shopped product and quantity.*/
		HashMap<Product,Integer> shoppedProductList = new HashMap<Product,Integer>();
			
		logger.setLevel(Level.WARNING);
		logger.info("testing main before");

		Shop.initialiseProductPriceList();
		
		Offer.initialiseOffersList();
		
		if( validateAndFetchShoppedProducts(args, shoppedProductList) == false)
		{
			logger.severe("Shop application terminates");
			return;	
		}
		
		/*Hold reference of shoppedPriceList in Basket object.*/
		basket.productsInBasket = shoppedProductList;
	
		basket.calculateTotal();
		
		/*find valid offers*/
	    HashMap<String, Integer> applicable_offers =   OfferHandler.findValidOffers(basket);
	
		basket.calculateFinalTotal(applicable_offers);
		
	}
}


