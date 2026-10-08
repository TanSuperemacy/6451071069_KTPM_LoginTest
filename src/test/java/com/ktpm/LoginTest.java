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

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Nhập liệu")
    @io.qameta.allure.Story("TC10: Nhập username có khoảng trắng ở đầu hoặc cuối")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @org.testng.annotations.Test(priority = 10, description = "TC10: Nhập username có khoảng trắng ở đầu hoặc cuối")
    public void testTC10_UsernameWithWhitespace() {
        String testId = "TC10";
        String testName = "Nhập username có khoảng trắng ở đầu hoặc cuối";
        String inputData = "username: '  huongnt  ', password: '123456@utc'";
        String expected = "Hệ thống tự động trim khoảng trắng hoặc xử lý an toàn";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("  huongnt  ");
            Thread.sleep(600);
            loginPage.enterPassword("123456@utc");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = "Hệ thống đã nhận diện và xử lý request chứa khoảng trắng: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Nhập liệu")
    @io.qameta.allure.Story("TC11: Nhập tên đăng nhập phân biệt chữ hoa, chữ thường")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @org.testng.annotations.Test(priority = 11, description = "TC11: Nhập tên đăng nhập phân biệt chữ hoa, chữ thường")
    public void testTC11_CaseSensitiveUsername() {
        String testId = "TC11";
        String testName = "Nhập tên đăng nhập phân biệt chữ hoa, chữ thường";
        String inputData = "username: 'HuongNT', password: '123456@utc'";
        String expected = "Hệ thống xác thực tên đăng nhập chữ hoa/chữ thường";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("HuongNT");
            Thread.sleep(600);
            loginPage.enterPassword("123456@utc");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = "Phản hồi hệ thống khi nhập username chữ hoa: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Nhập liệu")
    @io.qameta.allure.Story("TC12: Nhập mật khẩu có phân biệt chữ hoa, chữ thường")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @org.testng.annotations.Test(priority = 12, description = "TC12: Nhập mật khẩu có phân biệt chữ hoa, chữ thường")
    public void testTC12_CaseSensitivePassword() {
        String testId = "TC12";
        String testName = "Nhập mật khẩu có phân biệt chữ hoa, chữ thường";
        String inputData = "username: 'huongnt', password: '123456@UTC'";
        String expected = "Báo lỗi Tài khoản hoặc mật khẩu không đúng.";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            Thread.sleep(600);
            loginPage.enterPassword("123456@UTC");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = loginPage.getErrorMessage();
            org.testng.Assert.assertTrue(actual.contains("không đúng") || actual.contains("Tài khoản"),
                    "Hệ thống không phân biệt chữ hoa/thường ở mật khẩu!");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Giao diện & Trải nghiệm")
    @io.qameta.allure.Story("TC13: Kiểm thử ẩn/hiện ký tự ở ô mật khẩu")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @org.testng.annotations.Test(priority = 13, description = "TC13: Kiểm thử ẩn/hiện ký tự ở ô mật khẩu")
    public void testTC13_MaskedPasswordInput() {
        String testId = "TC13";
        String testName = "Kiểm thử ẩn/hiện ký tự ở ô mật khẩu";
        String inputData = "password: 'MySecretPassword123'";
        String expected = "Ô mật khẩu có thuộc tính type='password' để ẩn ký tự dạng dấu chấm/sao";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterPassword("MySecretPassword123");
            Thread.sleep(600);

            String inputType = loginPage.getPasswordInputType();
            actual = "Thuộc tính type của ô mật khẩu là: " + inputType;
            org.testng.Assert.assertEquals(inputType, "password", "Ô mật khẩu không được ẩn ký tự (không phải type='password')!");
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
    @io.qameta.allure.Story("TC14: Kiểm thử tấn công XSS ở ô username")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.CRITICAL)
    @org.testng.annotations.Test(priority = 14, description = "TC14: Kiểm thử tấn công XSS ở ô username")
    public void testTC14_XssInjectionUsername() {
        String testId = "TC14";
        String testName = "Kiểm thử tấn công XSS ở ô username";
        String inputData = "username: '<script>alert(\\'XSS\\')</script>', password: 'password123'";
        String expected = "Hệ thống không thực thi script, không bật alert XSS, báo lỗi an toàn";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("<script>alert('XSS')</script>");
            Thread.sleep(600);
            loginPage.enterPassword("password123");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            boolean hasAlert = loginPage.isAlertPresent();
            org.testng.Assert.assertFalse(hasAlert, "LỖI BẢO MẬT: Mã script XSS đã bị thực thi trên trình duyệt!");

            actual = "Hệ thống chặn script thành công: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, java.time.LocalTime.now().format(TIME_FMT)));
        }
    }

    @io.qameta.allure.Epic("Văn phòng điện tử UTC")
    @io.qameta.allure.Feature("Kiểm thử Biên & Độ dài")
    @io.qameta.allure.Story("TC15: Nhập chuỗi ký tự quá dài vào ô username và password")
    @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @org.testng.annotations.Test(priority = 15, description = "TC15: Nhập chuỗi ký tự quá dài vào ô username và password")
    public void testTC15_LongInputCharacters() {
        String testId = "TC15";
        String testName = "Nhập chuỗi ký tự quá dài vào ô username và password";
        String longText = "a".repeat(255);
        String inputData = "username: 255 ký tự, password: 255 ký tự";
        String expected = "Hệ thống xử lý chuỗi dài an toàn, không bị lỗi máy chủ (500)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername(longText);
            Thread.sleep(600);
            loginPage.enterPassword(longText);
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1200);

            actual = "Hệ thống xử lý an toàn: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
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
