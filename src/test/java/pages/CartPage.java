package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage 
{
	WebDriver driver;
	public CartPage(WebDriver driver) 
	{
		this.driver=driver;
	}

	By ItemInCartDescription = By.cssSelector(".details");
	By RemoveItemFromCart = By.cssSelector(".deleteCartItemButton");
	By CartIsEmptyMessage = By.cssSelector(".empty-cart__text");

	//Method to get the description of the item in the cart
	public WebElement GetItemInCartDescription() 
	{
		return driver.findElement(ItemInCartDescription);
	}
		
	//Method to remove item from the cart
	public void RemoveItemFromCart() 
	{
		driver.findElement(RemoveItemFromCart).click();
	}
		
	//Method to get the the cart is empty message
	public WebElement GetCartIsEmptyMessage() 
	{
		return driver.findElement(CartIsEmptyMessage);
	}

}
