package com.ktpm.base;

import com.ktpm.model.TestCaseResult;
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
 * Tầng cơ sở: BaseTest.java (Slide 10, 48 & 60)
 * Quản lý vòng đời WebDriver: mở trình duyệt trước mỗi test và đóng dọn dẹp sau mỗi test.
 * Hỗ trợ chế độ --headless theo chuẩn CI (Slide 60 & 63).
 */
public abstract class BaseTest {
    protected WebDriver driver;

    // Danh sách lưu kết quả 16 Test Cases để tự động xuất file Excel
    protected static final List<TestCaseResult> testResults = Collections.synchronizedList(new ArrayList<>());
    protected static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.silentOutput", "true");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Cấu hình chế độ --headless khi chạy dòng lệnh hoặc CI (Slide 60 & 63)
        if (Boolean.getBoolean("headless") || "true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }

        // Selenium Manager tự động tải ChromeDriver tương thích
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // QUAN TRỌNG: đóng trình duyệt và tắt tiến trình (Slide 10)
        }
    }

    @AfterSuite
    public void exportReport() {
        ExcelExporter.exportResultsToExcel(testResults, "KetQuaKiemThu_Login.xlsx");
    }
}
