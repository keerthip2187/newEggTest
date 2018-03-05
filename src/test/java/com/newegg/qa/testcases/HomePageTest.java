package com.newegg.qa.testcases;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.newegg.qa.base.TestBase;
import com.newegg.qa.data.Data;
import com.newegg.qa.pages.HomePage;
import com.newegg.qa.pages.LoginPage;
import com.newegg.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	HomePage homePage;
	LoginPage loginPage;
	TestUtil testUtil;
	String sheetName ="Sheet1";
	private String actualPrice;
	public ArrayList<Data> listDetails= new ArrayList<Data>();;
	
	public HomePageTest(){
		super();
	}
	
	@BeforeClass
	public void setUp(){
		initialization();
		testUtil = new TestUtil();
		homePage = new HomePage();
		loginPage = new LoginPage();
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));	
	}
	
	@DataProvider
	public Object[][] getTestExcelData(){
		Object data[][]= TestUtil.getTestData(sheetName);
		return data;
	}
	
	@Test(priority=1,dataProvider="getTestExcelData") 
	public void searchItem(String searchTerm,String item, String price) throws InterruptedException{
		
		Data data = new Data(item, price);
		listDetails.add(data);
		actualPrice= homePage.searchAndAddtoCart(searchTerm,item);
		
		//check for correct product added to the cart
		Assert.assertTrue(item.contains(homePage.containsElement(item)));
		
		//check for the price
		Assert.assertEquals(Float.parseFloat(actualPrice.replace('$',' ')), Float.parseFloat(price),
				"Actual price is " +actualPrice +" expected price is "+price);
	}
	
	@Test(priority=2)
	public void editCart() throws InterruptedException{
		
		Float expectedTotal = 0.0f;
		Data data = listDetails.get(0);
		homePage.deleteItemFromCart(data.getItem());
		
		for(int i=1; i<listDetails.size();i ++){
			expectedTotal = expectedTotal+ Float.parseFloat(listDetails.get(i).getPrice());
		}
		
		String price =homePage.getGrandTotal().replace('$', ' ');
		Float actualGrandTotal = Float.parseFloat(price);
		
		Assert.assertEquals(actualGrandTotal, expectedTotal," Expected grand total is "+expectedTotal);
		
	}
	
	@Test(priority=3)
	public void errorMessageValidation(){
		
		String[] arr= {"SFirstName","SLastName","SAddress1","SCity","SZip","ShippingPhone"};
		homePage.secureCheckout();
		homePage.continueToBilling();
		for(int i=0;i<arr.length;i++){
			Assert.assertTrue(homePage.isErrorTextDisplayed(arr[i]),"Error message not displayed for "+ arr[i]);
		}
	}
	
	@AfterClass
	public void tearDown(){
		driver.quit();
	}
	

}
