package com.ktpm.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model (POM) cho trang Đăng nhập Văn phòng điện tử UTC
 * URL: https://vanphongdientu.utc.edu.vn/Login
 */
public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final String PAGE_URL = "https://vanphongdientu.utc.edu.vn/Login";

    // 1. Ô nhập "Tên đăng nhập" (<input type="text" name="username"/>)
    private final By usernameInput = By.name("username");

    // 2. Ô nhập "Mật khẩu" (<input type="password" name="userpwd"/>)
    private final By passwordInput = By.name("userpwd");

    // 3. Checkbox "Giữ tôi luôn đăng nhập" (<input id="persistent" name="persistent"/>)
    private final By rememberCheckbox = By.id("persistent");
    private final By rememberLabel = By.cssSelector("label[for='persistent']");

    // 4. Nút bấm "Đăng nhập" (<input type="submit" value="Đăng nhập" class="submit_login"/>)
    private final By loginButton = By.cssSelector("input.submit_login");

    // 5. Nút "Đăng nhập bằng e-mail UTC" (<a class="button">...</a>)
    private final By googleLoginButton = By.cssSelector("a.button");

    // 6. Link "Bạn quên mật khẩu đăng nhập ?" (<div class="helps"><a href="/Login/GetPass">...</a></div>)
    private final By forgotPasswordLink = By.cssSelector("div.helps a");

    // 7. Thông báo lỗi khi đăng nhập thất bại (<div class="error">...</div>)
    private final By errorMessage = By.cssSelector("div.error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(PAGE_URL);
    }

    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        element.clear();
        element.sendKeys(password);
    }

    /**
     * Tích chọn hoặc bỏ chọn checkbox "Giữ tôi luôn đăng nhập"
     * Do trang web dùng jQuery ẩn checkbox gốc và thay bằng label, dùng JS click để đảm bảo an toàn 100%
     */
    public void toggleRememberMe() {
        try {
            WebElement label = driver.findElement(rememberLabel);
            label.click();
        } catch (Exception e) {
            WebElement checkbox = driver.findElement(rememberCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    public boolean isRememberMeChecked() {
        try {
            WebElement checkbox = driver.findElement(rememberCheckbox);
            return checkbox.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    public void clickGoogleLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(googleLoginButton)).click();
    }

    public String getErrorMessage() {
        try {
            WebElement errorElem = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return errorElem.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPasswordInputType() {
        try {
            return driver.findElement(passwordInput).getAttribute("type");
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordValue() {
        try {
            return driver.findElement(passwordInput).getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public String getUsernameValue() {
        try {
            return driver.findElement(usernameInput).getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isAlertPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.dismiss();
            return alertText != null;
        } catch (Exception e) {
            return false;
        }
    }
}
