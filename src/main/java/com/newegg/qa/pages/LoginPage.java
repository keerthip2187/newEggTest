package com.newegg.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.newegg.qa.util.CommonUtils;

public class LoginPage extends CommonUtils{
	
	@FindBy(css="#usaSite a")
	WebElement loginLink;
	
	@FindBy(id="UserName")
	WebElement usernameTxtBox;
	
	@FindBy(id="UserPwd")
	WebElement pwdTxtBox;
	
	@FindBy(id="submit")
	WebElement submitButton;
	
	@FindBy(css="#usaSite a ins ")
	WebElement myAccountLink;
	
	@FindBy(css="#usaSite div ul li:nth-child(4) a")
	WebElement logOutLink;
	
	
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	public void login(String userName, String pwd){
		click(loginLink);
		sendKeys(usernameTxtBox,userName);
		sendKeys(pwdTxtBox,pwd);
		click(submitButton);    	
	}
	
	public void logOut(){
		Actions actions = new Actions(driver);
		actions.moveToElement(myAccountLink);
		actions.build().perform();
		logOutLink.click();
	}
	
	public boolean verifyAccountLink(){
		return myAccountLink.isDisplayed();
	}

}
