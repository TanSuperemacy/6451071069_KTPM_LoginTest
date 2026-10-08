package com.ktpm.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Cài đặt Page Object: LoginPage.java (Slide 51 & 52)
 * Kế thừa BasePage, đóng gói toàn bộ Locators private,
 * sử dụng các hàm click(), type(), getText() kế thừa từ BasePage.
 */
public class LoginPage extends BasePage {
    public static final String URL = "https://vanphongdientu.utc.edu.vn/Login";

    // 1. Locators khai báo private chuẩn mực (Slide 47 & 51)
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("userpwd");
    private final By loginButton = By.cssSelector("input.submit_login");

    // Locators bổ sung theo Slide 19, 20 & 38
    private final By rememberRealBox = By.id("persistent");
    private final By rememberFakeBox = By.cssSelector("label.check");
    private final By errorMessage = By.cssSelector("div.error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 2. Methods nghiệp vụ (Slide 52)
    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    public void loginAs(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("/Login");
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    /**
     * Xử lý checkbox bẫy bị ẩn trên trang UTC (Slide 38)
     */
    public void toggleRememberMe() {
        WebElement checkbox = driver.findElement(rememberRealBox);
        if (!checkbox.isSelected()) {
            click(rememberFakeBox);
        }
    }

    public boolean isRememberMeChecked() {
        return driver.findElement(rememberRealBox).isSelected();
    }

    public String getErrorMessage() {
        try {
            return getText(errorMessage).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordInputType() {
        return driver.findElement(passwordField).getAttribute("type");
    }

    public String getPasswordValue() {
        return driver.findElement(passwordField).getAttribute("value");
    }

    public String getPageTitle() {
        return driver.getTitle();
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
