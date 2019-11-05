package pages;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class BasePage {


	final WebDriver driver;
	public BasePage(WebDriver driver) {
		this.driver=driver;
		//Page decoration [initialize the elements on this page]
		//PageFactory.initElements(driver,this);


	}

	

	@FindBy(how= How.ID, using="twotabsearchtextbox")
	private WebElement txtSearchItem;
	
	@FindBy(how= How.CSS, using="input[value='Go']")
	private WebElement btnSearch;
	

	@FindBy(how= How.CSS, using="div[data-component-type='sp-sponsored-result']")
	private List<WebElement> lstSearchedItemsList;

	@FindBy(how= How.XPATH, using="//div[@data-component-type='sp-sponsored-result']//span[@class='a-badge-label-inner a-text-ellipsis']")
	private WebElement lblBestSellerItem;
	
	

	

	public void searchItem(String itemType) {
		txtSearchItem.clear();
		txtSearchItem.sendKeys(itemType);
		btnSearch.click();
		waitForVisibilityOfListElements(lstSearchedItemsList);
	}
	
	public String openBestSellerItem() {
		JavascriptExecutor js =  (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", lblBestSellerItem);
		System.out.println(lblBestSellerItem.getText());
		String bestSeller = lblBestSellerItem.getText();
		lblBestSellerItem.click();
		return  bestSeller;
	}

	public void waitForVisibilityOfElement(final WebElement element) {
		/*wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOf(element));*/

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(2))
				.ignoring(TimeoutException.class)
				.ignoring(NoSuchElementException.class)
				.ignoring(IndexOutOfBoundsException.class)
				.ignoring(ElementNotVisibleException.class)
				.ignoring(StaleElementReferenceException.class);
		Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver arg0) {
				//System.out.println("Waiting for element to be visible");
				if (element.isDisplayed()) {
					System.out.println("Expected Element is found.");
					return element;
				}
				return null;
			}
		};
		wait.until(function);


	}
	public void waitForVisibilityOfListElements(final List<WebElement> elements) {
		/*wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));*/

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(2))
				.ignoring(TimeoutException.class)
				.ignoring(NoSuchElementException.class)
				.ignoring(IndexOutOfBoundsException.class)
				.ignoring(ElementNotVisibleException.class)
				.ignoring(StaleElementReferenceException.class);

		Function < WebDriver, Boolean > function = new Function < WebDriver, Boolean > () {
			public Boolean apply(WebDriver arg0) {
				//System.out.println("Waiting for list elements to be visible");
				if(elements!=null) {
					elements.get(0).isDisplayed();
					System.out.println("Expected Elements list found.");
					return true;
				}
				return false;
			}
		};
		wait.until(function);
	}
	public void waitForTextToBePresentInElement(final WebElement element,final String text) {
		/*wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));*/

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(2))
				.ignoring(TimeoutException.class)
				.ignoring(NoSuchElementException.class)
				.ignoring(IndexOutOfBoundsException.class)
				.ignoring(ElementNotVisibleException.class)
				.ignoring(StaleElementReferenceException.class);

		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {
				System.out.println("Waiting for text to be present in elemment");
				String actualText = element.getText();
				if (actualText.equalsIgnoreCase(text)) {
					System.out.println("Text is  present the element :" + actualText);
					return true;
				}
				return false;
			}
		};

		wait.until(function);
	}


}
