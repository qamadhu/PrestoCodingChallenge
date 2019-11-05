package testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.BasePage;
import pages.CartPage;


public class verifyAddProductToCart extends TestBase{

	BasePage basePage;
	CartPage cartPage;
	
	@BeforeClass(alwaysRun=true)
	public void initiateObjects() throws Exception{
		basePage=PageFactory.initElements(driver, BasePage.class);
		cartPage=PageFactory.initElements(driver, CartPage.class);
		softAssert = new SoftAssert();
	}

	@Test
	public void verifyFlightsSearch() {
		basePage.searchItem("Headphones");
		String bestSellerTxt = basePage.openBestSellerItem();
		softAssert.assertEquals("Best Seller", bestSellerTxt);
		
		softAssert.assertTrue(cartPage.isAddCartButtonDisplayed());
		cartPage.addProductToCart();
	}
}
