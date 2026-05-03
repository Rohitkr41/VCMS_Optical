package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ======================
    // LOCATORS
    // ======================

    private By usernameField = By.id("Login_txtUserName");
    private By passwordField = By.id("Login_txtPassword");
//    private By captchaField = By.cssSelector("input[placeholder='Captcha']");
    private By loginButton = By.id("Login_btnLogin");

    // ======================
    // LOGIN METHOD
    // ======================

    public void login(String username, String password) {

        // Wait until page fully loads
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

        enterText(usernameField, username);
        enterText(passwordField, password);

        System.out.println("Enter CAPTCHA manually...");

        // Wait until user enters captcha
       
        safeClick(loginButton);

    
    }

    // ======================
    // STABLE TEXT ENTRY
    // ======================

    private void enterText(By locator, String text) {

        int attempts = 0;

        while (attempts < 3) {

            try {

                WebElement element = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(locator));

                wait.until(ExpectedConditions.elementToBeClickable(element));
                

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                element.clear();
                element.sendKeys(text);

                return;

            } catch (StaleElementReferenceException e) {

                attempts++;

                if (attempts == 3) {
                    throw new RuntimeException("Unable to enter text into element: " + locator);
                }
            }
        }
    }

    // ======================
    // STABLE CLICK
    // ======================

    private void safeClick(By locator) {

        int attempts = 0;

        while (attempts < 3) {

            try {

                WebElement element = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(locator));

                wait.until(ExpectedConditions.elementToBeClickable(element));

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                try {
                    element.click();
                } catch (Exception e) {
                    // JS fallback click
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", element);
                }

                return;

            } catch (StaleElementReferenceException e) {

                attempts++;

                if (attempts == 3) {
                    throw new RuntimeException("Unable to click element: " + locator);
                }
            }
        }
    }
}