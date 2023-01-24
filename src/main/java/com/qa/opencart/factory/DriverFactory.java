package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
	
	//this driver is supply driver to everywhere and it is responsible for initialize the driver..
	public WebDriver driver;
	public Properties prop ;
	public OptionsManager optionsManager;
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); 
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser").trim() ;
		System.out.println("Browser name is :" +browserName);
		
		highlight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equalsIgnoreCase("chrome")) {
		//	driver = new ChromeDriver(optionsManager.getChromeOptions());      //this will make execution and frame work very stable
           	tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));	
		}
		
		else if(browserName.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if(browserName.equalsIgnoreCase("safari")) {
		//	driver = new SafariDriver();
		   tlDriver.set(new SafariDriver());
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();
			tlDriver.set(new EdgeDriver());
		}
		else {
			System.out.println("please pass the right browser name.." +browserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
	    
		return getDriver();
	}
	
	
	public synchronized static WebDriver getDriver() {//making static so that we use anywhere anytime
		return tlDriver.get();
	}
	
	

	
	public Properties initiProp() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/test/resorces/config/config.properties");//so this FileInputStream is make the connection with config.properties file   
			                     //and this path is copied from config.properties right click--> properties--> path()copy from src/test and pasted
			                     // here and put a dot(.) before it and it ask for try-catch block
			prop.load(ip);  //load all the properties of ip into prop.... it also ask for try-catch block so put in it.. and below catch is for this line
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	    return prop;	
	}

	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}  //so for interview TakesScreenshot interface available for to take the screenshot and the method is getScreenshotAs
	
	
}

//listener:  
