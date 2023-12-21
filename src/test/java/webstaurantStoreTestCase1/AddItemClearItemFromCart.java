package webstaurantStoreTestCase1;

import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import utilities.DataUtilities;
import pages.CartPage;

public class AddItemClearItemFromCart {
	@Test(dataProviderClass = DataUtilities.class, dataProvider = "dataProvider")
	public void TestScenario(HashMap<String, String> hashMap) throws InterruptedException 
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		//Open WebstaurantStore site
		driver.get(hashMap.get("SiteURL"));
		driver.manage().window().maximize();
		HomePage HomePage = new HomePage(driver);
		CartPage CartPage = new CartPage(driver);
		//Search for product
		HomePage.EnterProductSearchCriteria(hashMap.get("ProductSearchCriteria"));
		HomePage.ClickSearchStoreButton();
		System.out.println("Verify that each product has the word 'Table' in its title");
		List<WebElement> ItemDescription = HomePage.GetProductItemDescriptionsList();
		Iterator<WebElement> ItemDescriptionIterator = ItemDescription.iterator();
		int counter = 0;
		while(ItemDescriptionIterator.hasNext()) {
			if (ItemDescriptionIterator.next().getText().contains(hashMap.get("ProductDescriptionSearchCriteria"))) {
				counter = counter + 1;
		    	System.out.println("PASS: Table was found in the title for item number " + counter);
			}
			else 
			{	counter = counter + 1;
				System.out.println("FAIL: Table was not found in the title for item number " + counter);
			}
		}
		//Get the description of the last product item in the list to compare against in the cart
		WebElement LastItemDescription = HomePage.GetLastProductItemDescription();
		String ItemDescriptionGoingToCartTextContent = LastItemDescription.getAttribute("textContent");
		//Add the last product item in the list to the cart
		HomePage.AddLastItemToCart();
		//Wait for the add to cart popup to disappear and scroll page up to top
		Thread.sleep(12000);
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		System.out.println("Verify that there is one item displayed in the cart area at the top of the search results page");
		WebElement ItemsInCart = HomePage.GetCartItemCount();
		String CartCount = ItemsInCart.getAttribute("textContent");
		if (CartCount.equals(hashMap.get("ExpectedNumberOfItemsInCart"))) 
		{
			System.out.println("PASS: The cart has one item in it");
		}
		else
		{
			System.out.println("FAIL: One item in the cart was expected but there were " + CartCount + " items");
		}
		//Navigate to the cart
		HomePage.ViewCart();
		System.out.println("Verify the item displayed in the cart is the same that was selected from the search results");
		WebElement ItemDescriptionInCart = CartPage.GetItemInCartDescription();
		String ItemDescriptionInCartTextContent = ItemDescriptionInCart.getAttribute("textContent");
		if(ItemDescriptionInCart.getText().contains(ItemDescriptionGoingToCartTextContent)) 
		{
			System.out.println("PASS: The item selected from the search results is displaying in the cart");
		}
		else 
		{
			System.out.println("FAIL: The item selected from the search results is not displayed in the cart. " + ItemDescriptionGoingToCartTextContent + " was expected but " + ItemDescriptionInCartTextContent + " was displayed");
		}
		//Remove item from cart
		CartPage.RemoveItemFromCart();
		driver.navigate().refresh();
		System.out.println("Verify item has been removed from the cart");
		WebElement CartIsEmptyMessage = CartPage.GetCartIsEmptyMessage(); 
		if(CartIsEmptyMessage.isDisplayed()) 
		{
			System.out.println("PASS: The item has been removed from the cart");	
		}
		else 
		{
			System.out.println("FAIL: The itme has not been removed from the cart");
		}
		driver.quit();
	}
}
