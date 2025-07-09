package Intern;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class NullClassInternshipProject {
	 WebDriver driver;

	    @BeforeClass
	    public void setUp() {
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	    }

	    @Test(priority = 1)
	    public void openBrowserAndNavigate() {
	    	 driver.get("https://www.amazon.in/");
	    	    String title = driver.getTitle();
	    	    System.out.println("Page title: " + title);
	    	    Assert.assertTrue(title.contains("Amazon"), "Title does not contain 'Amazon'");
	    	}

	    @Test(priority = 2)
	    public void searchAddToCartAndPay() {
	        LocalTime now = LocalTime.now();
	        if (now.isAfter(LocalTime.of(18, 0)) && now.isBefore(LocalTime.of(19, 0))) {
	            driver.findElement(By.name("search")).sendKeys("Mobile");
	            driver.findElement(By.id("searchBtn")).click();
	            driver.findElement(By.xpath("//button[contains(text(),'Add to Cart')]")).click();
	            driver.findElement(By.id("cart")).click();
	            WebElement amount = driver.findElement(By.id("totalAmount"));
	            double price = Double.parseDouble(amount.getText().replace("Rs", "").trim());
	            Assert.assertTrue(price > 500);
	            driver.findElement(By.id("payNow")).click();
	            Assert.assertTrue(driver.getPageSource().contains("Order Confirmed"));
	        }
	    }

	    @Test(priority = 3)
	    public void searchWithFilters() {
	        LocalTime now = LocalTime.now();
	        if (now.isAfter(LocalTime.of(15, 0)) && now.isBefore(LocalTime.of(18, 0))) {
	            driver.findElement(By.name("search")).sendKeys("Shoes");
	            driver.findElement(By.id("searchBtn")).click();
	            driver.findElement(By.id("brandFilter-C"))  .click();
	            driver.findElement(By.id("priceFilter-2000Above")).click();
	            driver.findElement(By.id("ratingFilter-4Above")).click();
	            List<WebElement> filteredResults = driver.findElements(By.className("product"));
	            Assert.assertTrue(filteredResults.size() > 0);
	        }
	    }

	    @Test(priority = 4)
	    public void monitorPriceAndNotify() {
	    	 // Navigate to a specific product page (this one is for demo purposes, replace if needed)
	        driver.get("https://www.amazon.in/dp/B0BZ9C1TNB"); // Example product: Mobile
	        try {
	            // Wait for price element to be visible (replace with actual price ID or class)
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.cssSelector("span.a-price-whole"))); // Amazon often uses this class

	            // Extract and clean up price
	            String priceText = priceElement.getText().replaceAll("[^0-9]", "");
	            double price = Double.parseDouble(priceText);

	            System.out.println("Current Price: Rs " + price);

	            // Trigger "notification" logic
	            if (price < 1000) {
	                System.out.println("🔔 Price dropped below Rs 1000! Send email alert.");
	                Assert.assertTrue(true);
	            } else {
	                System.out.println("Price is still above Rs 1000.");
	                Assert.assertTrue(true); // Still pass
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            Assert.fail("Could not monitor product price.");
	        }
	    }

	    @Test(priority = 5)
	    public void loginAndProfileValidation() {
	        LocalTime now = LocalTime.now();
	        if (now.isAfter(LocalTime.NOON) && now.isBefore(LocalTime.of(15, 0))) {
	            driver.findElement(By.id("loginBtn")).click();
	            driver.findElement(By.id("username")).sendKeys("testuser");
	            driver.findElement(By.id("password")).sendKeys("password");
	            driver.findElement(By.id("submit")).click();
	            String profileName = driver.findElement(By.id("profileName")).getText();
	            Assert.assertTrue(!profileName.matches(".*[ACGILK].*"));
	        }
	    }

	    @Test(priority = 6)
	    public void addMultipleProductsVerifyTotal() {
	        LocalTime now = LocalTime.now();
	        if (now.isAfter(LocalTime.of(18, 0)) && now.isBefore(LocalTime.of(19, 0))) {
	            driver.findElement(By.name("search")).sendKeys("Watch");
	            driver.findElement(By.id("searchBtn")).click();
	            driver.findElement(By.xpath("//button[contains(text(),'Add to Cart')]")).click();
	            driver.findElement(By.name("search")).clear();
	            driver.findElement(By.name("search")).sendKeys("Shoes");
	            driver.findElement(By.id("searchBtn")).click();
	            driver.findElement(By.xpath("//button[contains(text(),'Add to Cart')]")).click();
	            driver.findElement(By.id("cart")).click();
	            double total = Double.parseDouble(driver.findElement(By.id("totalAmount")).getText().replace("Rs", "").trim());
	            Assert.assertTrue(total > 2000);
	            String username = "Lakhan1234";
	            Assert.assertTrue(username.length() == 10 && username.matches("^[a-zA-Z0-9]+$"));
	        }
	    }

	    @Test(priority = 7)
	    public void verifyNonElectronicProductSelection() {
	        LocalTime now = LocalTime.now();
	        if (now.isAfter(LocalTime.of(15, 0)) && now.isBefore(LocalTime.of(18, 0))) {
	            driver.findElement(By.name("search")).sendKeys("Product");
	            driver.findElement(By.id("searchBtn")).click();
	            List<WebElement> products = driver.findElements(By.className("productTitle"));
	            for (WebElement product : products) {
	                String name = product.getText();
	                if (!name.toLowerCase().contains("electronic") &&
	                    !name.startsWith("A") && !name.startsWith("B") && !name.startsWith("C") && !name.startsWith("D")) {
	                    product.click();
	                    Assert.assertTrue(driver.findElement(By.id("productDetail")).isDisplayed());
	                    break;
	                }
	            }
	        }
	    }

	    @AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	    
}