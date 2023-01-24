package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	private WebDriver driver;     //to pass the driver....  bcz of this we write the this.driver
	                              //the default value of diver is null so if we make it public it give unnecessary null pointer exception so that we make it private 
	                              //also to restrict the use outside the class 
	private JavaScriptUtil jsUtil;                             
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	//when we creaate these two methods the word driver in below method give error
	// so to overcome this driver error write upper constructor
	
	 public  WebElement getElement(By locator) {       //getElement asking for By locators
    	 WebElement ele = driver.findElement(locator);
	     if(Boolean.parseBoolean(DriverFactory.highlight)) {
	    	 jsUtil.flash(ele);
	     }
	 return ele; 
	 
	 }
    
	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}


	 public void doClick(By locator) {
		 getElement(locator).click();
	 }


	 public String doGetElementText(By locator) {
		 return getElement(locator).getText();
	 }
	 
	 
	
	public  String doGetAttributeValue(By locator, String attrName ) {
		return getElement(locator).getAttribute(attrName);
	}
	
    public  void doClickOnElement(By locators, String Linktext) {     //after writing this we don't need to define all things for every links on page  
		
		List<WebElement> Linklist = driver.findElements(locators);   
		System.out.println("total lang links : " +Linklist.size());
		

	
		for(WebElement e : Linklist) {
			String text = e.getText();
			System.out.println(text);      //print all language
		      
			  if(text.contains(Linktext)) {        //for going on any language
		    	   e.click();
		    	   break;
		       }
			
			}
	}

    
    public boolean isSingleElementExit(By locator) {
 	   int actCount = getElements(locator).size();
 	   System.out.println("actual count of element ===" +actCount);
 	      if(actCount == 1) {
 	    	  return true;
 	      }
 	      return false;
 	}
 	
 	public boolean isTwoElementExit(By locator) {
 		   int actCount = getElements(locator).size();
 		   System.out.println("actual count of element ===" +actCount);
 		      if(actCount == 2) {
 		    	  return true;
 		      }
 		      return false;
 	}   
 	
 	public  boolean isMultiElementExit(By locator) {
 		   int actCount = getElements(locator).size();
 		   System.out.println("actual count of element ===" +actCount);
 		      if(actCount>1) {
 		    	  return true;
 		      }
 		      return false;
 	}   
 	
 	public  boolean isMultiElementExit(By locator, int expElementCount) {
 		   int actCount = getElements(locator).size();
 		   System.out.println("actual count of element ===" +actCount);
 		      if(actCount == expElementCount) {
 		    	  return true;
 		      }
 		      return false;
 	}   

 	
 	public  boolean doIsDisplayed(By locator) {
 		return getElement(locator).isDisplayed();
 	}
 	
 	
 	
    
    
	//****************drop down utils-- select base drop down************//
	
	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropdownValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		
		List<WebElement> optionList = select.getOptions();
		
		for(WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);
			  if(text.equals(value)) {
				  e.click();
				  break;
			  }
		}	
	}
	
	//****************jquery drop down selecting option by choice in single, multiple...*******************************//
	
	public  void selectChoice(By locator, String... choice) {
		List<WebElement> choiceList = driver.findElements(locator); 
		boolean flag = false;
		if(!choice[0].equalsIgnoreCase("all")) {            //in this we put condition if choice[0] equal all then this condition make flase bcz of this (!)
			for(WebElement e : choiceList) {                             //and go on else part there it select all the choices              
				String text = e.getText();                              // so all selction method works this way and for multi or single selection
				for(int i=0; i<choice.length; i++) {                      // that choice[0] condition make false and (!) this make it ture and select what choices in             
					   if(text.equals(choice[i])) {                        // single or multi option               
						   flag = true;
						   e.click();                                                     
						   break;                                                        
					   }
				   }
			}	
		}
		
		else {
			//all selection logic
			try {
			for(WebElement e : choiceList) {
				 flag = true;
				e.click();                          //it gives error bcz we have 14 choices out of 44 so to handle this error we use try catch
			} 
			}	
			catch(Exception e) {
				}
			}
		if(flag == false) {
			System.out.println("choice is not available.." +choice[0]);
		}
	}
	
	
		//******************* getElements Generic method**************************//
	
	
	public  List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	//to count the element
	public  int totalElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	public List<String> getElementsTextList(By locator) {
		
     // List<WebElement> eleList = driver.findElements(locator);
		List<WebElement> eleList = getElements(locator);          //we make a generic method of getElements so we call it hear directly or mention it hear insted of above 
		
		List<String> eleTextList = new ArrayList<String>();      //the physical capacity of this list is write now 0 , and virtual capacity is 10...
		
		for(WebElement e : eleList) {
			String text = e.getText();
			//System.out.println(text);
			eleTextList.add(text);               //this method hear to written to add the element if we want to add more...
		
		//so what if tomorrow the size of footer will change like 21 to 25 links are available than what to do so.. we have to store in dynamic array
			//so we have to create one array list
		
		}
	    return eleTextList;
	}
	
	
 
	//***************************Action class***********************************//
	
	public void selectSubMenu(String htmltag, String parentMenu, String childMenu) throws InterruptedException {
		
		By parentMenuLocator = By.xpath("//"+htmltag+"[text()= '"+parentMenu+"']");
		By childMenuLocator = By.xpath("//"+htmltag+"[text()='"+childMenu+"']");
		//WebElement addons = driver.findElement(By.xpath("//div[text()='Add-ons']"));   //instead of this line we write above line for parentMeanu
		
		WebElement parentMenuElement = getElement(parentMenuLocator);
		Actions act = new Actions(driver); 
		
		act.moveToElement(parentMenuElement).build().perform();
	    
		Thread.sleep(2000);
	
	   // driver.findElement(By.xpath("//div[text()='Student Discount']")).click();     //we create a locator of this above this is for submenu/childmenu
		
	    doClick(childMenuLocator);
	
	}
	
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}
	
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}
	
	//*********************** wait Util explicit ***********************//
	
	//for title:
    public String waitForTitleContains(String titleFractionValue, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleContains(titleFractionValue))){
			return driver.getTitle();
		}
		else {
			System.out.println("expected title is not visible..");
			return null;
		}
	}
	
    public String waitForTitleIs(String titleValue, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleIs(titleValue))){
			return driver.getTitle();
		}
		else {
			System.out.println("expected title is not visible..");
			return null;
		}
	}
    
    //for url
    
    public  String waitForUrlContains(String urlFractionValue, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.urlContains(urlFractionValue))){
			return driver.getCurrentUrl();
		}
		else {
			System.out.println("expected url is not visible..");
			return null;
		}
	}
	
    public String waitForUrlIs(String urlValue, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.urlToBe(urlValue))){
			return driver.getCurrentUrl();
		}
		else {
			System.out.println("expected url is not visible..");
			return null;
		}
	}
    
    //for alerts:
    //fluent wait
    public Alert waitForAlertPresentAndSwitch(int timeOut, int intervalTime){
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		          .withTimeout(Duration.ofSeconds(timeOut))
		          .pollingEvery(Duration.ofSeconds(intervalTime))
		          .ignoring(NoAlertPresentException.class)
		          .withMessage("Alert not found on the page...");
     	return wait.until(ExpectedConditions.alertIsPresent());   //here importing alert class and this statement waiting for alert and switching to alert also
	}
    
    //web driver wait
    public Alert waitForAlertPresentAndSwitch(int timeOut){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
     	return wait.until(ExpectedConditions.alertIsPresent());   //here importing alert class and this statement waiting for alert and switching to alert also
	}
	
	public String getAlertText(int timeOut) {
		return waitForAlertPresentAndSwitch(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForAlertPresentAndSwitch(timeOut).accept();
	}
	
	public void dismissAlert(int timeOut) {
		waitForAlertPresentAndSwitch(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {
		waitForAlertPresentAndSwitch(timeOut).sendKeys(value);
	}

	
	public void waitForFramePresentAndSwitch(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
     	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFramePresentAndSwitch(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
     	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFramePresentAndSwitch(WebElement frameElement, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
     	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	public void waitForFramePresentAndSwitch(String framenameOrId, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
     	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framenameOrId));
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does notnecessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * default interval time = 500ms
	 * @return
	 */
	public List<WebElement> waitForElementsVisibe(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @param intervalTime 
	 * @return
	 */
	public List<WebElement> waitForElementsVisibe(By locator, int timeout, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, int timeout, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(intervalTime));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
    public WebElement waitForElementToBeVisibleWithFluentWait(By locator, int timeOut, int intervalTime) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		          .withTimeout(Duration.ofSeconds(timeOut))
		          .pollingEvery(Duration.ofSeconds(intervalTime))
		          .ignoring(ElementNotInteractableException.class)
		          .ignoring(NoSuchElementException.class)
		          .ignoring(StaleElementReferenceException.class)
		          .withMessage("Element not found on the page...");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}
    
	//WebDriverWait is child of FluentWait
    
    public WebElement waitForElementToBeVisible(By locator, int timeOut, int intervalTime) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
	    wait.ignoring(NoSuchElementException.class)
	        .ignoring(ElementNotInteractableException.class)
	        .withMessage("Element not found on the page...");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }
	
	//***********************customWait********************************************//
    
    public WebElement retryingElement(By locator, int timeOut) {
		
		WebElement element = null;
		int attempts = 0;
		
		while(attempts < timeOut) {
			try {
			    element = getElement(locator);  //if element is not available throws exception noSuchElement	
			    System.out.println("element is found in attempt: " + attempts);
			    break;
			}
			catch(NoSuchElementException e){
				System.out.println("element is not found in attempt :"+ attempts + " for " + locator );
			    //after giving thread.sleep we didn't add throws declaration we surround it by try catch block and this thread.sleep is using here bcz wait for 1 sec
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
				
				TimeUtil.applyWait(500);  //timeUtil is a class that we create for methods and applyWait is one of those methods
			}
			
			attempts++;  //attempt = 0 < timeout come inside try catch block to check condition whether the element is found or not if not thn come into
			             //catch block thn wait for 1 sec in thread.sleep thn attempts++ now attempt = 1.. and so on....
		}
		
		if(element == null) { //if element is null we have to throw the exception from FrameWorkException class.. so we create object here
			System.out.println("element is not found.. tried for :" + timeOut + " secs " + "with the interval of 500 millisecs");
			throw new FrameworkException("TimeOutException");
		}
		
		return element;
	}
	
    public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
		
		WebElement element = null;
		int attempts = 0;
		
		while(attempts < timeOut) {
			try {
			    element = getElement(locator);  //if element is not available throws exception noSuchElement	
			    System.out.println("element is found in attempt: " + attempts);
			    break;
			}
			catch(NoSuchElementException e){
				System.out.println("element is not found in attempt :"+ attempts + " for " + locator );
			    //after giving thread.sleep we didn't add throws declaration we surround it by try catch block and this thread.sleep is using here bcz wait for 1 sec
//				try {
//					Thread.sleep(intervalTime*1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
				
				TimeUtil.applyWait(intervalTime);  // instead of thread.sleep put this line of code
			}
			
			attempts++;  //attempt = 0 < timeout come inside try catch block to check condition whether the element is found or not if not thn come into
			             //catch block thn wait for 1 sec in thread.sleep thn attempts++ now attempt = 1.. and so on....
		}
		
		if(element == null) { //if element is null we have to throw the exception from FrameWorkException class.. so we create object here
			System.out.println("element is not found.. tried for :" + timeOut + " secs " + "with the interval of "+intervalTime+" millisecs");
			throw new FrameworkException("TimeOutException");
		}
		
		return element;
	}
	

    
    public void waitForPageLoad(int timeOut) {

		long endTime = System.currentTimeMillis() + timeOut;

		while (System.currentTimeMillis() < endTime) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String pageState = js.executeScript("return document.readyState").toString();
			if (pageState.equals("complete")) {
				System.out.println("page DOM is fully loaded now.....");
				break;
			}
			
		}

	}
}
