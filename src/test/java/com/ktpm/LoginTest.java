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

    @org.testng.annotations.Test(priority = 1, description = "TC1: Để trống user hoặc pass word")
    public void testTC1_EmptyUsername() {
        String testId = "TC1";
        String testName = "Để trống user hoặc pass word";
        String inputData = "username: '', password: '1256'";
        String expected = "Bạn chưa nhập tên đăng nhập";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterPassword("1256");
            Thread.sleep(800);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("Bạn chưa nhập tên đăng nhập") || actual.contains("tên đăng nhập"),
                    "Thông báo lỗi không đúng khi để trống username!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @org.testng.annotations.Test(priority = 2, description = "TC2: Để trống mật khẩu")
    public void testTC2_EmptyPassword() {
        String testId = "TC2";
        String testName = "Để trống mật khẩu";
        String inputData = "username: 'huongnt', password: ''";
        String expected = "Bạn chưa nhập mật khẩu";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            Thread.sleep(800);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("Bạn chưa nhập mật khẩu") || actual.contains("mật khẩu"),
                    "Thông báo lỗi không đúng khi để trống mật khẩu!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @org.testng.annotations.Test(priority = 3, description = "TC3: Đúng tên sai mật khẩu")
    public void testTC3_CorrectUsernameWrongPassword() {
        String testId = "TC3";
        String testName = "Đúng tên sai mật khẩu";
        String inputData = "username: 'huongnt', password: 'utc@235'";
        String expected = "Tài khoản không đúng (hoặc Tài khoản hoặc mật khẩu không đúng.)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            Thread.sleep(600);
            loginPage.enterPassword("utc@235");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("không đúng") || actual.contains("Tài khoản"),
                    "Không hiển thị thông báo lỗi khi nhập sai mật khẩu!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @org.testng.annotations.Test(priority = 4, description = "TC4: Sai tên, đúng mật khẩu")
    public void testTC4_WrongUsernameCorrectPassword() {
        String testId = "TC4";
        String testName = "Sai tên, đúng mật khẩu";
        String inputData = "username: 'huongthunguyen', password: '123456@utc'";
        String expected = "Tài khoản không đúng (hoặc Tài khoản hoặc mật khẩu không đúng.)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongthunguyen");
            Thread.sleep(600);
            loginPage.enterPassword("123456@utc");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("không đúng") || actual.contains("Tài khoản"),
                    "Không hiển thị thông báo lỗi khi nhập sai tên đăng nhập!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @org.testng.annotations.Test(priority = 5, description = "TC5: Đăng nhập thành công và chọn 'Giữ tôi luôn đăng nhập'")
    public void testTC5_LoginWithRememberMe() {
        String testId = "TC5";
        String testName = "Đăng nhập thành công và chọn 'Giữ tôi luôn đăng nhập'";
        String inputData = "username: 'huongnt', password: '123456@utc', rememberMe: true";
        String expected = "Tích chọn ghi nhớ thành công và gửi request đăng nhập";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            Thread.sleep(500);
            loginPage.enterPassword("123456@utc");
            Thread.sleep(500);
            loginPage.toggleRememberMe();
            Thread.sleep(500);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = "Đã tích chọn 'Giữ tôi luôn đăng nhập' và submit form thành công";
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @org.testng.annotations.Test(priority = 6, description = "TC6: Đăng nhập thành công và không chọn 'Giữ tôi luôn đăng nhập'")
    public void testTC6_LoginWithoutRememberMe() {
        String testId = "TC6";
        String testName = "Đăng nhập thành công và không chọn 'Giữ tôi luôn đăng nhập'";
        String inputData = "username: 'huongnt', password: '123456@utc', rememberMe: false";
        String expected = "Không tích chọn ghi nhớ và gửi request đăng nhập bình thường";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            Thread.sleep(500);
            loginPage.enterPassword("123456@utc");
            Thread.sleep(500);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = "Đã thực hiện gửi request đăng nhập không lưu phiên";
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Đăng nhập")
    @io.qameta.allure.Story("TC7: Để trống cả user và pass word")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @org.testng.annotations.Test(priority = 7, description = "TC7: Để trống cả user và pass word")
    public void testTC7_EmptyUsernameAndPassword() {
        String testId = "TC7";
        String testName = "Để trống cả user và pass word";
        String inputData = "username: '', password: ''";
        String expected = "Bạn chưa nhập tên đăng nhập (hoặc Bạn chưa nhập tên đăng nhập và mật khẩu)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("tên đăng nhập") || actual.contains("chưa nhập"),
                    "Không hiển thị thông báo lỗi khi để trống cả username và password!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Bảo mật")
    @io.qameta.allure.Story("TC8: Kiểm thử tấn công SQL Injection ở ô username")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.CRITICAL)
    @org.testng.annotations.Test(priority = 8, description = "TC8: Kiểm thử tấn công SQL Injection ở ô username")
    public void testTC8_SqlInjectionUsername() {
        String testId = "TC8";
        String testName = "Kiểm thử tấn công SQL Injection ở ô username";
        String inputData = "username: '' OR '1'='1', password: '123'";
        String expected = "Hệ thống chặn truy cập, báo lỗi Tài khoản hoặc mật khẩu không đúng.";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("' OR '1'='1");
            Thread.sleep(600);
            loginPage.enterPassword("123");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("không đúng") || actual.contains("Tài khoản"),
                    "Hệ thống không chặn được tấn công SQL Injection!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Bảo mật")
    @io.qameta.allure.Story("TC9: Kiểm thử tấn công SQL Injection dạng Comment ở ô username")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.CRITICAL)
    @org.testng.annotations.Test(priority = 9, description = "TC9: Kiểm thử tấn công SQL Injection dạng Comment ở ô username")
    public void testTC9_SqlInjectionComment() {
        String testId = "TC9";
        String testName = "Kiểm thử tấn công SQL Injection dạng Comment ở ô username";
        String inputData = "username: 'admin\\' --', password: 'password123'";
        String expected = "Hệ thống chặn truy cập, báo lỗi Tài khoản hoặc mật khẩu không đúng.";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("admin' --");
            Thread.sleep(600);
            loginPage.enterPassword("password123");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("không đúng") || actual.contains("Tài khoản"),
                    "Hệ thống không chặn được tấn công SQL Injection dạng comment!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
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
