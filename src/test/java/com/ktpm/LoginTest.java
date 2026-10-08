package com.ktpm;

import com.ktpm.model.TestCaseResult;
import com.ktpm.pages.LoginPage;
import com.ktpm.utils.ExcelExporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lớp kiểm thử tự động trang Đăng nhập Văn phòng điện tử Trường ĐH Giao Thông Vận Tải (UTC)
 * URL: https://vanphongdientu.utc.edu.vn/Login
 * Tự động ghi nhận kết quả và xuất ra file Excel KetQuaKiemThu_Login.xlsx
 */
public class LoginTest {
    protected WebDriver driver;
    protected LoginPage loginPage;

    // Danh sách lưu trữ kết quả của tất cả các test case để xuất file Excel
    protected static final List<TestCaseResult> testResults = Collections.synchronizedList(new ArrayList<>());
    protected static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.silentOutput", "true");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void generateExcelReport() {
        ExcelExporter.exportResultsToExcel(testResults, "KetQuaKiemThu_Login.xlsx");
    }
}
