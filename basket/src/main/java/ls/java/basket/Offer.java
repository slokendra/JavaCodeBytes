package ls.java.basket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.logging.*;

enum OfferType{
	 NONE,
	DISCOUNT,
	FREE,
	HALFPRICE
}
final public class Offer {
	
	private final static Logger logger = Logger.getLogger( Offer.class.getName());
	
	/*Offer code*/
	 final String offerDetail;
	
	/*Name of the product, which triggers the offer.*/
	 final String productName;
	
	/*Number of products, which trigger the offer.*/
	 final Integer productCount;
	
	/*discount percentage*/
	 final Integer discountPercent;
	
	/*Name of the product which is either discounted or free.*/
	 final String offerProductName;
	
	/*Number of product, which is discounted or free.*/
	 final Integer offerProductCount;
	
	/*Type of the offer.*/
	 final OfferType offerType;
	
	/*List of offers initialised from Offers.csv file*/
	 final static List<Offer> offersList = new ArrayList<Offer>();	
	
	/*Constructor*/
	Offer(String offerDetail, String productName, Integer productCount,
			Integer discountPercent, String offerProductName,
			Integer offerProductCount, OfferType offerType)
	{
		this.offerDetail = offerDetail;
		this.productName = productName;
		this.productCount = productCount;
		this.discountPercent = discountPercent;
		this.offerProductName= offerProductName;
		this.offerProductCount = offerProductCount;
		this.offerType = offerType;		
		
	}
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Offer)) {
	        return false;
	    }

	    Offer that = (Offer) other;

	    // Custom equality check here.
	    return (this.productName.equals(that.productName )
	        && this.productCount.equals(that.productCount)
	        && (this.discountPercent == that.discountPercent)
	        && this.offerProductName.equals(that.offerProductName)
	        && this.offerProductCount.equals(that.offerProductCount)
	        && this.offerType.equals(that.offerType));
	}
	
	@Override
	public int hashCode() {
	    int hashCode = 1;

	    hashCode = hashCode * 37 + this.productName.hashCode();
	    hashCode = hashCode * 37 + this.productCount.hashCode();
	    hashCode = hashCode * 37 + this.discountPercent.hashCode();
	    hashCode = hashCode * 37 + this.offerProductName.hashCode();
	    hashCode = hashCode * 37 + this.offerProductCount.hashCode();
	    hashCode = hashCode * 37 + this.offerType.hashCode();
	    
	    return hashCode;
	}

	/*Depending on the type of offer, fill the respective fields in offersList*/
	final static public void decodeOffers(List<String> offers) throws IllegalFormatException
	{
		try
		{
		
			for(String p : offers)
			{
			//	Offer offer = new Offer();
				int productCount,discountPercent,offerProductCount;
				String offerProductName;
				OfferType offerType;
				
				String offerDetail = p;
				String productName = p.split(",")[1].split(" ")[1];
				
				String OfferType_string = (p.split(",")[2]).split(" ")[0]; 
				logger.fine("offerDetail:" + p);
				switch(OfferType_string)
				{
					/*this case denotes discount on a certain product.*/
					case "OFF":
						productCount = Integer.parseInt((p.split(",")[1]).split(" ")[0]);
						discountPercent = Integer.parseInt((p.split(",")[2]).split(" ")[1]);
						offerProductName = " ";
						offerProductCount = 0;
						offerType = OfferType.DISCOUNT;
						break;
					/*this case denotes, a product is sold free when bought with certain product.*/
					case "FREE":
					    productCount = Integer.parseInt(p.split(",")[1].split(" ")[0]);
						discountPercent = 100;/*100% discount = Free*/
						offerProductName = (p.split(",")[2]).split(" ")[2];
						offerProductCount = Integer.parseInt(p.split(",")[2].split(" ")[1]);
						offerType = OfferType.FREE;
						break;
					/*this case denotes, a product is half priced when bought with certain product.*/
					case "HP":
						productCount = Integer.parseInt(p.split(",")[1].split(" ")[0]);
						discountPercent = 50;
						offerProductName = (p.split(",")[2]).split(" ")[2];
						offerProductCount = Integer.parseInt(p.split(",")[2].split(" ")[1]);;
						offerType = OfferType.HALFPRICE;
						break;
					default:
						productCount = 0; /*Default count for discounted product.*/
						discountPercent = 0;
						offerProductName = " ";
						offerProductCount = 0;
						offerType= OfferType.NONE;
						logger.warning("unrecognized offer type : " + OfferType_string);		
				}	
				if (offerType != OfferType.NONE)
				{
					Offer offer = new Offer(offerDetail, productName,productCount,discountPercent,
						offerProductName,offerProductCount,offerType);
					offersList.add(offer);
				}
			}		
		}
		catch(IllegalFormatException ie)
		{
			logger.warning("Illegal format exception while decoding offers.csv " + ie);	
		}
		catch(Exception e)
		{
			logger.warning("Exception while decoding offers.csv " + e);	
		}
			
	}
	
	/*Method to read offers from offers.csv file and update in offersList*/
	final static public void initialiseOffersList() throws IOException
	{

		/* Open offers file and fetch products list.*/
	    try
	    {		 
	    	logger.setLevel(Level.WARNING);
	    	logger.info("initialiseOffersList called");
	    	 String directory = ".\\";
			 List<Path> fileslist = Files.walk(Paths.get(directory))
			     .filter(Files::isRegularFile).filter(line->line.toString().contains(".csv"))
			     .collect(Collectors.toList());
			   
			  for(Path pt : fileslist)
			  {
				  if (pt.toString().contains("offers.csv"))
				  {
					Stream<String> productfile =  Files.lines(Paths.get(pt.toUri()));
					List<String> offers = productfile.filter(line->!(line.contains("*")))
											.collect(Collectors.toList());  

					decodeOffers(offers);
					
					productfile.close();					
				  }
			  } 
	    } 
	    catch (IOException ie)
	    {
	    	 logger.severe ("IO Exception in initialiseOffersList" + ie);
	    	
	    }
	    catch (Exception e)
	    {
	    	 logger.severe("other Exception in initialiseOffersList " + e);
	    	
	    }	    		
	}
	
}

