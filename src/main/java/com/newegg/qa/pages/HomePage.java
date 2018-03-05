package com.newegg.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.newegg.qa.util.CommonUtils;

public class HomePage extends CommonUtils {
	@FindBy(id = "haQuickSearchTextbox")
	WebElement searchTextbox;

	@FindBy(id = "SrchInDesc_top")
	WebElement searchInDescTextBox;
	
	@FindBy(css=".search-bar-btn")
	WebElement searchBarButton;
	
	@FindBy(id="btn_InnerSearch")
	WebElement innerSearchButton;
	
	@FindBy(css=".items-view.is-grid .item-container:nth-child(1) .item-title")
	WebElement itemInfoLink;
	
	@FindBy(css="#landingpage-price .price-current")
	WebElement productPrice;
	
	@FindBy(css=".btn.btn-primary.btn-wide")
	WebElement addToCartButton;
	
	@FindBy(xpath=".//table[2]/tbody/tr/td[1]/div/div/a")
	List<WebElement> cartItems;
	
	@FindBy(css=".state-two.selectedAlert-state #removeFromCart")
	WebElement deleteButton;
	
	@FindBy(css=".grand-total .amount")
	WebElement grandTotal;
	
	@FindBy(xpath="//*[@id='custom']/div/div[3]/div/div/div/button[1]")
	WebElement cancelPopup;
	
	@FindBy(xpath=".//*[@id='bodyArea']/div[10]/div[1]/div[2]/div/a[2]")
	WebElement checkout;
	
	@FindBy(xpath=".//*[@id='orderSummaryPanelAndPayment']/div/div[4]/div/div/a")
	WebElement continueBilling;
	
	
	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public void secureCheckout(){
		click(checkout);
	}
	
	public void continueToBilling(){
		click(continueBilling);
	}
	
	public boolean isErrorTextDisplayed(String fieldname){
		return driver.findElement(By.cssSelector("label[class='error'][for='"+fieldname+"']")).isDisplayed();
	}
	
	public String getGrandTotal(){
		return getElementText(grandTotal);
	}
	
	private void deleteItem(){
		click(deleteButton);
	}
	
	public String containsElement(String str){
		String elementText = driver.findElement(By.xpath("//a[contains(text(), str)]")).getText();
		return elementText;
	}
	
	
	public void deleteItemFromCart(String str) throws InterruptedException{
		
		driver.get(driver.getCurrentUrl());
		sleep(6000);
		WebElement chkItem = driver.findElement(By.xpath("//img[starts-with(@alt,'"+str+"')]//parent::a//parent::div//parent::td//input"));
		
		Thread.sleep(6000);
		if(chkItem!=null){
			click(chkItem);
			deleteItem();
			//TODO verify element deleted successfully
		}
		else{
			System.out.println("delete item not visible");
		}
	}
	
	public void searchProduct (String searchItem) {
		Actions actions = new Actions(driver);
		actions.moveToElement(searchTextbox);
		actions.click();
		actions.sendKeys(searchItem);
		actions.build().perform();
	}
	
	public void buttonClick(WebElement button){
		button.click();
	}
	
	public String searchAndAddtoCart(String searchItem, String item) throws InterruptedException {
		driver.get(prop.getProperty("url"));
		searchProduct(searchItem);
		click(searchBarButton);
		sendKeys(searchInDescTextBox, item);
		click(innerSearchButton);
		sleep(2000);
		click(itemInfoLink);
		String price=productPrice.getText();
		click(addToCartButton);
		
		if(item.equals("INSTEON Thermostat (2441TH)")){
			cancelPopup.click();
			//Thread.sleep(5000);
		}
		
		return price;
	}
	
	
	
}
