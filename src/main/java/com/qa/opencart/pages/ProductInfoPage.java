package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("a.thumbnail");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	
	//creating hashmap 
	private Map<String, String> productMap;    //hashmap doesn't maintain the order.. for maintain order we use linkedhashmap
                                                   //and for alphabetically order use Treemap
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		return eleUtil.doGetElementText(productHeader);
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisibe(productImages, TimeUtil.DEFAULT_TIME_OUT).size();
	    System.out.println("Images Count --->" +imagesCount);
	    return imagesCount;
	}
	
	
	//
	public Map<String, String> getProductInformation() {  //this is the example of encapsulation... calling private method from the public method
		 productMap = new HashMap<String, String>();
		// productMap = new LinkedHashMap<String, String>();
		// productMap = new TreeMap<String, String>();
		 getProductMetaData();
		 getProductPriceData();
		 System.out.println(productMap);
		 return productMap;
	}
	
	
	//Hashmap for meta data
	//Brand: Apple
    //Product Code: Product 18
    //Reward Points: 800
    //Availability: In Stock
	
	private void getProductMetaData() {
		List<WebElement>metaDataList = eleUtil.getElements(productMetaData);
	    System.out.println("product meta data count--->" +metaDataList.size());
	  //  productMap = new HashMap<String, String>();   //hashmap is the child of map so we can do top casting here
	    
	    for(WebElement e : metaDataList) {
	    	String meta = e.getText();
	    	String metaData[] =  meta.split(":");            //spliting the : from codes
	    	String metaKey = metaData[0].trim();
	    	String metaValue = metaData[1].trim();
	    	//now initializing the hash map so first we create on class level bcz we can use at any method... after this we initialize before the for loop where his  
	    	//storing value is zero so we do below and putting some string value
	        productMap.put(metaKey, metaValue);
	    	
	    }
	}
	
	//$2,000.00    //it will be at 0th position
	//Ex Tax: $2,000.00 //and it will at 1th
	private void getProductPriceData() {
		List<WebElement>metaPriceList = eleUtil.getElements(productPriceData);
	    System.out.println("product price count--->" +metaPriceList.size());
	    String price = metaPriceList.get(0).getText().trim();      //so we give here get 0th position 
	    String ExTaxPrice = metaPriceList.get(1).getText().trim();  
	    productMap.put("actualprice", price);
	    productMap.put("actualtaxprice", ExTaxPrice);
	    
	}
}
