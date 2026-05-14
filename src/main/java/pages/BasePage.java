package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By loader = By.cssSelector(".loader, .spinner, .loading");
    private final By modal = By.cssSelector(".custom-modal");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void waitForPageReady() {
        waitForLoaderToDisappear();
        waitUntilModalGone();
    }
    
 // ✅ Wrapper for single element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    // ✅ Wrapper for multiple elements
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        } catch (Exception ignored) {}
    }

    protected void waitUntilModalGone() {
        try {
            List<WebElement> modals = driver.findElements(modal);
            for (WebElement m : modals) {
                if (m.isDisplayed()) {
                    List<WebElement> ok = m.findElements(By.xpath(".//button[text()='OK']"));
                    if (!ok.isEmpty()) {
                        ok.get(0).click();
                    }
                    wait.until(ExpectedConditions.invisibilityOf(m));
                }
            }
        } catch (Exception ignored) {}
    }
    
    // ✅ Wrapper for typing text
    public void type(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected WebElement waitForVisibility(By locator) {
        waitForPageReady();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        waitForPageReady();
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        WebElement el = waitForClickable(locator);
        scrollToElement(el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement el = waitForClickable(locator);
        new Select(el).selectByVisibleText(text);
    }
    
    public void selectDropdownByText(By locator, String value) {

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(dropdown);

        boolean found = false;

        for (WebElement option : select.getOptions()) {
            if (option.getText().trim().equalsIgnoreCase(value)) {
                select.selectByVisibleText(option.getText().trim());
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("❌ Option NOT found: " + value);
        }

        System.out.println("✅ Selected: " + value);
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitForUrlContains(String value) {
        wait.until(ExpectedConditions.urlContains(value));
    }

    protected void scrollToElement(WebElement el) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
    
 // BasePage.java

    public void navigateUsingMenu(By menuLocator, By subMenuLocator, String urlKeyword) {

        waitForPageReady();

        // STEP 1: Expand menu (JS for reliability)
        WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(menuLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);

        // STEP 2: Get submenu anchor
        WebElement subMenu = wait.until(ExpectedConditions.presenceOfElementLocated(subMenuLocator));

        String href = subMenu.getAttribute("href");
        System.out.println("Navigation URL: " + href);

        // STEP 3: Direct navigation (MOST STABLE)
        driver.get(href);

        // STEP 4: Wait for page
        wait.until(ExpectedConditions.urlContains(urlKeyword));
        waitForPageReady();
    }
    
    private By successPopup = By.id("popup_message");
    private By okButton     = By.id("popup_ok");
    
    public void handleSuccessPopup() {

   	    // Wait for popup visible
   	    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));

   	    // Print message
   	    System.out.println("✅ Popup Message: " + popup.getText());

   	    // Click OK button
   	    WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
   	    okBtn.click();

   	    // Wait for popup to disappear
   	    wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));

   	    System.out.println("✅ Popup handled successfully");
   	}
    
    
    protected void clearAndType(WebElement element, String value) {
        try {
            element.click();
            element.clear();

            // Extra safety
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);

            element.sendKeys(value);

        } catch (Exception e) {
            // JS fallback
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value='';", element);
            element.sendKeys(value);
        }
    }
    
    protected void selectDropdownByVisibleText(By locator, String visibleText) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                dropdown
        );

        Select select = new Select(dropdown);

        boolean optionFound = false;

        String expectedText = visibleText
                .trim()
                .replaceAll("\\s+", " ");

        for (WebElement option : select.getOptions()) {

            String actualText = option.getText()
                    .trim()
                    .replaceAll("\\s+", " ");

            System.out.println("Dropdown Option => " + actualText);

            // Exact match
            if (actualText.equalsIgnoreCase(expectedText)) {

                try {
                    option.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();",
                            option
                    );
                }

                optionFound = true;

                System.out.println("Selected Option => " + actualText);
                break;
            }
        }

        // Partial match fallback
        if (!optionFound) {

            for (WebElement option : select.getOptions()) {

                String actualText = option.getText()
                        .trim()
                        .replaceAll("\\s+", " ");

                if (actualText.toLowerCase()
                        .contains(expectedText.toLowerCase())) {

                    try {
                        option.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].click();",
                                option
                        );
                    }

                    optionFound = true;

                    System.out.println("Selected By Partial Match => " + actualText);
                    break;
                }
            }
        }

        if (!optionFound) {

            System.out.println("===== AVAILABLE OPTIONS =====");

            for (WebElement option : select.getOptions()) {
                System.out.println(option.getText());
            }

            throw new NoSuchElementException(
                    "Dropdown option not found: " + visibleText
            );
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                dropdown
        );
    }
    
}