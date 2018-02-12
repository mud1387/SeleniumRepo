package com.facebook.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class configReader {

	Properties pro;
	
	public configReader() {
		File src=new File("./src/main/java/com/facebook/config/config.properties");
		try {
			FileInputStream fis= new FileInputStream(src);
			pro= new Properties();
			pro.load(fis);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getURL(){
		
		String url= pro.getProperty("url");
		return url;
	}
	
	public String getBrowser(){
		
		String browser=pro.getProperty("Browser");
		return browser;
	}
	
	public String getChrome(){
		
		String chromeDriver=pro.getProperty("ChromeDriver");
		return chromeDriver;
	}
	
	public String getIE(){
		
		String IEDriver=pro.getProperty("IEDriver");
		return IEDriver;
	}
	
	public String getFireFox(){
		
		String fireFoxDriver =pro.getProperty("FirefoxDriver");
		return fireFoxDriver;
	}
	
	public String getExcel(){
		
		String Excel =pro.getProperty("Excel");
		return Excel;
	}
}
