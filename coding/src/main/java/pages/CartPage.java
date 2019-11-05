package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CartPage extends BasePage {
	
	
	    public CartPage(WebDriver driver) {
	    	super(driver);
	       

	    }
	    
	    @FindBy(id = "add-to-cart-button")
	    private WebElement btnAddCart;

	   
	    public boolean isAddCartButtonDisplayed() {
	    	return btnAddCart.isDisplayed();
	    }
	    public void addProductToCart() {
	    	btnAddCart.click();
	    }
	    
	   

	    
}
