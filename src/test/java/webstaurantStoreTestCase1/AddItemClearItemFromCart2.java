package webstaurantStoreTestCase1;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.RespositoryParser;
import utilities.ExcelUtilities;

//This configuration is using .properties file for object storage and apache poi with excel for the test run data

public class AddItemClearItemFromCart2 {
	
	@Test
	public void TestScenario() throws InterruptedException, IOException 
	{
		ExcelUtilities excelUtils = new ExcelUtilities();
		String excelFilePath = "src/test/resources/AddItemClearitemFromCartData.xlsx";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		//Get the object repository
		RespositoryParser parser = new RespositoryParser("src/test/resources/ObjectRepo.properties");
		//Get the test run data
		excelUtils.setExcelFile(excelFilePath,"Sheet1");
		//Open WebstaurantStore site
		//driver.get("https://webstaurantstore.com/");
		driver.get(excelUtils.getCellData(1,0));
		driver.manage().window().maximize();
		//Search for product
		WebElement SearchStoreEditBox = driver.findElement(parser.getobjectLocator("SearchStoreEditBox"));
		SearchStoreEditBox.sendKeys(excelUtils.getCellData(1,1));
		WebElement SearchStoreButton = driver.findElement(parser.getobjectLocator("SearchStoreButton"));
		SearchStoreButton.click();
		System.out.println("Verify that each product has the word 'Table' in its title");
		List<WebElement> ItemDescription = driver.findElements(parser.getobjectLocator("ProductItemDescriptionsList")); 
  		Iterator<WebElement> ItemDescriptionIterator = ItemDescription.iterator();
		int counter = 0;
		while(ItemDescriptionIterator.hasNext()) 
		{
			if (ItemDescriptionIterator.next().getText().contains(excelUtils.getCellData(1,2))) 
			{
				counter = counter + 1;
		    	System.out.println("PASS: Table was found in the title for item number " + counter);
			}
			else 
			{	
				counter = counter + 1;
				System.out.println("FAIL: Table was not found in the title for item number " + counter);
			}
		}
		//Get the description of the last product item in the list to compare against in the cart
		WebElement LastItemDescription = driver.findElement(parser.getobjectLocator("LastProductItemDescription"));
		String ItemDescriptionGoingToCartTextContent = LastItemDescription.getAttribute("textContent");
		//Add the last product item in the list to the cart
		WebElement LastAddToCartButton = driver.findElement(parser.getobjectLocator("LastAddToCartButton"));
		LastAddToCartButton.click();
		//Wait for the add to cart popup to disappear and scroll page up to top
		Thread.sleep(12000);
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		System.out.println("Verify that there is one item displayed in the cart area at the top of the search results page");
		WebElement ItemsInCart = driver.findElement(parser.getobjectLocator("CartItemCount"));
		String CartCount = ItemsInCart.getAttribute("textContent");
		if (CartCount.equals(excelUtils.getCellData(1,3))) 
		{
			System.out.println("PASS: The cart has one item in it");
		}
		else
		{
			System.out.println("FAIL: One item in the cart was expected but there were " + CartCount + " items");
		}
		//Navigate to the cart
		WebElement ViewCart = driver.findElement(parser.getobjectLocator("ViewCartButton"));
		ViewCart.click();
		System.out.println("Verify the item displayed in the cart is the same that was selected from the search results");
		WebElement ItemDescriptionInCart = driver.findElement(parser.getobjectLocator("ItemInCartDescription"));
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
		WebElement RemoveItemFromCart = driver.findElement(parser.getobjectLocator("RemoveItemFromCart"));
		RemoveItemFromCart.click();
		Thread.sleep(2000);
		driver.navigate().refresh();
		System.out.println("Verify item has been removed from the cart");
		WebElement CartIsEmptyMessage = driver.findElement(parser.getobjectLocator("CartIsEmptyMessage"));
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
