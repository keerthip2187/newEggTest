package com.newegg.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.newegg.qa.base.TestBase;
import com.newegg.qa.pages.LoginPage;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	
	
	public LoginPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPage();	
	}

	@Test(priority=1)
	public void loginTest(){
	  loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	  Assert.assertTrue(loginPage.verifyAccountLink());
	}
	
	
	@AfterMethod
	public void tearDown(){
		loginPage.logOut();
		driver.quit();
	}
	
	
	
}
