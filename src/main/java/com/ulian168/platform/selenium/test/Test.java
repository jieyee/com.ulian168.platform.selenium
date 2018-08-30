/**
 * 
 */
package com.ulian168.platform.selenium.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author liuming
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		test126();
		/*WebDriver driver = null;
		String browser = "chrome";
		String URL = "http://www.baidu.com";
		if (browser.equalsIgnoreCase("firefox")) 
		   {
			 System.out.println(" Executing on FireFox");
			 driver = new FirefoxDriver();
			 driver.get(URL);
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 driver.manage().window().maximize();		
		   } 
		   else if (browser.equalsIgnoreCase("chrome")) 
		   {
			 System.out.println(" Executing on CHROME");
			 System.setProperty("webdriver.chrome.driver", "D:\\dev\\tools\\chromedriver.exe");
			 driver = new ChromeDriver();
			 driver.get(URL);
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 driver.manage().window().maximize();	
			 
			 driver.findElement(By.id("kw")).sendKeys("彭杰");
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 driver.findElement(By.id("su")).click();
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 driver.findElement(By.xpath("//*[@id=\"1\"]/h3/a")).click();
			 
		   } 
		   else if (browser.equalsIgnoreCase("ie")) 
		   {
			 System.out.println("Executing on IE");
			 System.setProperty("webdriver.ie.driver", "D:IEDriverServer.exe");
			 driver = new InternetExplorerDriver();
			 driver.get(URL);
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 driver.manage().window().maximize();		
		   }
		   else 
		   {
		      throw new IllegalArgumentException("The Browser Type is Undefined");
		   }*/
		
		
	   /* //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	    //Launch website
		driver.navigate().to("http://www.calculator.net/");
		
		//Maximize the browser
		driver.manage().window().maximize();

	    // Click on Math Calculators
		driver.findElement(By.xpath(".//*[@id='menu']/div[3]/a")).click();
	  
	    // Click on Percent Calculators
		driver.findElement(By.xpath(".//*[@id='menu']/div[4]/div[3]/a")).click();

		// Enter value 10 in the first number of the percent Calculator
	    driver.findElement(By.id("cpar1")).sendKeys("10");

	    // Enter value 50 in the second number of the percent Calculator
	    driver.findElement(By.id("cpar2")).sendKeys("50");
	    
	    // Click Calculate Button
	    driver.findElement(By.xpath(".//*[@id='content']/table/tbody/tr/td[2]/input")).click();

	    // Get the Result Text based on its xpath
	    String result = driver.findElement(By.xpath(".//*[@id='content']/p[2]/span/font/b")).getText();
	    
		//Print a Log In message to the screen
	    System.out.println(" The Result is " + result);*/
	    
		//Close the Browser.
	    //driver.close();    

	}
	
	/**
	 * 126测试.
	 * @throws InterruptedException 
	 */
	public static void test126() throws InterruptedException {
		 String URL = "http://192.168.0.126:8090/";
		 System.setProperty("webdriver.chrome.driver", "D:\\dev\\tools\\chromedriver.exe");
		 WebDriver driver = new ChromeDriver();
		 driver.get(URL);
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 driver.manage().window().maximize();	
		 driver.findElement(By.id("tx_login")).click();
		 
		 Thread.sleep(2000);
		 //输入用户名密码
		 driver.findElement(By.id("txt_loginphone")).sendKeys("13555555555");
		 driver.findElement(By.id("pwd")).sendKeys("123456");
		 //登录
		 driver.findElement(By.xpath("//*[@id=\"login_box\"]/div/div[2]/div/input")).click();
		 Thread.sleep(2000);
		 //跳转我的电子合同
		 driver.findElement(By.xpath("//*[@id=\"jy\"]/img")).click();
		 Thread.sleep(2000);
		 
		 //跳转到新建合同
		 driver.navigate().to("http://192.168.0.126:8090/contract/uploadContract.html");
		 
		 //选择合同类型
		 driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[1]/input")).click();
		 driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[1]/ul/li[1]")).click();
		 Thread.sleep(3000);
		 //上传合同文件  用java来实现文件读取功 
		 //File file = new File("D:\\temp\\trans.pdf");
         //driver.get(file.getAbsolutePath());
         driver.findElement(By.name("upmoban")).sendKeys("D:\\temp\\trans.pdf");
         //Thread.sleep(3000);
         //合同名称
         driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[3]/input")).sendKeys("自动化测试一");
         
         //甲方乙方
         driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[4]/input")).click();
		 driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[4]/ul/li[1]")).click();
		 
         driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[5]/input")).click();
		 driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[5]/ul/li[4]")).click();
		 Thread.sleep(3000);
         //创建合同
		 driver.findElement(By.xpath("//*[@id=\"app\"]/div[4]/div/div[2]/div[1]/div[10]/div")).click();
		 
		 //判断创建是否成功
		 ////*[@id="app"]/div[3]/div/div/p[1]
		 
		 
		 Thread.sleep(10000);
		 driver.close();
	}

}
