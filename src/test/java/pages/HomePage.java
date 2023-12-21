package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver=driver;
	}
	
	By SearchStoreEditBox = By.cssSelector("[data-testid=\"searchval\"]");
	By SearchStoreButton = By.cssSelector("button[value=\"Search\"]");
	By LastProductItemDescription = By.xpath("//*[@id=\"ProductBoxContainer\"][last()]/div[1]/a/span");
	By LastAddToCartButton = By.xpath("//*[@id=\"ProductBoxContainer\"][last()]/div[4]/form/div/div/input[2]");
	By CartItemCount = By.id("cartItemCountSpan");
	By ViewCartButton = By.cssSelector("[data-testid=\"cart-button\"]");
	By ProductItemDescriptionsList = By.cssSelector("[data-testid=\"itemDescription\"]");
	
	//Method to enter search criteria
	public void EnterProductSearchCriteria(String criteria) {
		driver.findElement(SearchStoreEditBox).sendKeys(criteria);
	}
	
	//Method to click the search button
	public void ClickSearchStoreButton() {
		driver.findElement(SearchStoreButton).click();
	}
	
	//Method to get the last product item description
	public WebElement GetLastProductItemDescription() {
		return driver.findElement(LastProductItemDescription);
	}
	
	//Method to add the last product item in the list to the cart
	public void AddLastItemToCart() {
		driver.findElement(LastAddToCartButton).click();
	}
	
	//Method to get the cart item count
	public WebElement GetCartItemCount() {
		return driver.findElement(CartItemCount);
	}
	
	//Method to click the add to cart button
	public void ViewCart() {
		driver.findElement(ViewCartButton).click();
	}
	
	//Method to get the list of product descriptions
	public List<WebElement> GetProductItemDescriptionsList() {
		return (List<WebElement>) driver.findElements(ProductItemDescriptionsList);
	}
	
}