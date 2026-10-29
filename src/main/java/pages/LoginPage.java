
package pages;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By usernameField =
            By.id("Login_txtUserName");

    private final By passwordField =
            By.id("Login_txtPassword");

    private final By loginButton =
            By.id("Login_btnLogin");

    public void login(String username, String password) {

    wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField))
            .sendKeys(username);

    wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField))
            .sendKeys(password);

    System.out.println("Username and password entered.");

    // Wait until the manually entered CAPTCHA has a value.

    WebElement button = wait.until(
            ExpectedConditions.elementToBeClickable(loginButton));

    System.out.println("Login button enabled: " + button.isEnabled());

    button.click();

    System.out.println("Login click command executed.");

    // Diagnostic only: confirm whether the login page remains.
    try {
        wait.until(driver ->
                driver.findElements(usernameField).isEmpty());

        System.out.println("Login form disappeared; check dashboard.");
    } catch (TimeoutException e) {
        System.out.println(
                "Login form is still present. Check the visible error message, "
                + "CAPTCHA validation and button HTML.");
    }
}

    private void clickLoginButton() {

        WebElement button = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", button);

        System.out.println("Login button tag: " + button.getTagName());
        System.out.println("Login button displayed: " + button.isDisplayed());
        System.out.println("Login button enabled: " + button.isEnabled());
        System.out.println("Login button HTML: "
                + button.getAttribute("outerHTML"));

        if (!button.isEnabled()) {
            throw new RuntimeException(
                    "Login button is disabled. Check CAPTCHA and required fields.");
        }

        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            button.click();
            System.out.println("Native login button click executed.");

        } catch (StaleElementReferenceException e) {
            // Re-locate the element if the page refreshed it.
            button = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(loginButton));
            clickWithJavaScript(button);

        } catch (WebDriverException e) {
            System.out.println("Native click failed: " + e.getMessage());

            button = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(loginButton));
            clickWithJavaScript(button);
        }
    }

    private void clickWithJavaScript(WebElement button) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", button);

        System.out.println("JavaScript login button click executed.");
    }
}


//Old code before 29-10-2026

//package pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//public class LoginPage extends BasePage {
//
//    public LoginPage(WebDriver driver) {
//        super(driver);
//    }
//
//    // ======================
//    // LOCATORS
//    // ======================
//
//    private By usernameField = By.id("Login_txtUserName");
//    private By passwordField = By.id("Login_txtPassword");
////    private By captchaField = By.cssSelector("input[placeholder='Captcha']");
//    private By loginButton = By.id("Login_btnLogin");
//
//    // ======================
//    // LOGIN METHOD
//    // ======================
//
//    public void login(String username, String password) {
//    	
//    	  System.out.println("Current URL: " + driver.getCurrentUrl());
//    	    System.out.println("Page Title: " + driver.getTitle());
//    	    System.out.println("Page Source contains username: "
//    	            + driver.getPageSource().contains("Login_txtUserName"));
//
//        // Wait until page fully loads
//        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
//
//        enterText(usernameField, username);
//        enterText(passwordField, password);
//
//        System.out.println("Enter CAPTCHA manually...");
//
//        // Wait until user enters captcha
//       
//        safeClick(loginButton);
//
//    
//    }
//
//    // ======================
//    // STABLE TEXT ENTRY
//    // ======================
//
//    private void enterText(By locator, String text) {
//
//        int attempts = 0;
//
//        while (attempts < 3) {
//
//            try {
//
//                WebElement element = wait.until(
//                        ExpectedConditions.visibilityOfElementLocated(locator));
//
//                wait.until(ExpectedConditions.elementToBeClickable(element));
//                
//
//                ((JavascriptExecutor) driver)
//                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
//
//                element.clear();
//                element.sendKeys(text);
//
//                return;
//
//            } catch (StaleElementReferenceException e) {
//
//                attempts++;
//
//                if (attempts == 3) {
//                    throw new RuntimeException("Unable to enter text into element: " + locator);
//                }
//            }
//        }
//    }
//
//    // ======================
//    // STABLE CLICK
//    // ======================
//
//    private void safeClick(By locator) {
//
//        int attempts = 0;
//
//        while (attempts < 3) {
//
//            try {
//
//                WebElement element = wait.until(
//                        ExpectedConditions.visibilityOfElementLocated(locator));
//
//                wait.until(ExpectedConditions.elementToBeClickable(element));
//
//                ((JavascriptExecutor) driver)
//                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
//
//                try {
//                    element.click();
//                } catch (Exception e) {
//                    // JS fallback click
//                    ((JavascriptExecutor) driver)
//                            .executeScript("arguments[0].click();", element);
//                }
//
//                return;
//
//            } catch (StaleElementReferenceException e) {
//
//                attempts++;
//
//                if (attempts == 3) {
//                    throw new RuntimeException("Unable to click element: " + locator);
//                }
//            }
//        }
//    }
//    
//    
//}
