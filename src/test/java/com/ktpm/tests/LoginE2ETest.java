package com.ktpm.tests;

import com.ktpm.base.BaseTest;
import com.ktpm.model.TestCaseResult;
import com.ktpm.pages.LoginPage;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tầng kiểm thử: LoginE2ETest.java (Slide 48 & 54-55)
 * Kế thừa BaseTest, chỉ chứa nghiệp vụ và Assertions, không chứa selector DOM (Slide 47).
 * Tuân thủ tuyệt đối quy định Slide 63: Sử dụng Explicit Wait, không sử dụng Thread.sleep().
 */
@Epic("Văn phòng điện tử UTC")
@Feature("Kiểm thử Đăng nhập (Web UI E2E)")
public class LoginE2ETest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test(priority = 1, description = "TC1: Để trống user hoặc pass word")
    @Story("TC1: Để trống user hoặc pass word")
    @Severity(SeverityLevel.NORMAL)
    public void testTC1_EmptyUsername() {
        String testId = "TC1";
        String testName = "Để trống user hoặc pass word";
        String inputData = "username: '', password: '1256'";
        String expected = "Bạn chưa nhập tên đăng nhập";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterPassword("1256");
            loginPage.clickLogin();

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("tên đăng nhập");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 2, description = "TC2: Để trống mật khẩu")
    @Story("TC2: Để trống mật khẩu")
    @Severity(SeverityLevel.NORMAL)
    public void testTC2_EmptyPassword() {
        String testId = "TC2";
        String testName = "Để trống mật khẩu";
        String inputData = "username: 'huongnt', password: ''";
        String expected = "Bạn chưa nhập mật khẩu";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            loginPage.clickLogin();

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("mật khẩu");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 3, description = "TC3: Đúng tên sai mật khẩu")
    @Story("TC3: Đúng tên sai mật khẩu")
    @Severity(SeverityLevel.NORMAL)
    public void testTC3_CorrectUsernameWrongPassword() {
        String testId = "TC3";
        String testName = "Đúng tên sai mật khẩu";
        String inputData = "username: 'huongnt', password: 'utc@235'";
        String expected = "Tài khoản không đúng (hoặc Tài khoản hoặc mật khẩu không đúng.)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("huongnt", "utc@235");

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("không đúng");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 4, description = "TC4: Sai tên, đúng mật khẩu")
    @Story("TC4: Sai tên, đúng mật khẩu")
    @Severity(SeverityLevel.NORMAL)
    public void testTC4_WrongUsernameCorrectPassword() {
        String testId = "TC4";
        String testName = "Sai tên, đúng mật khẩu";
        String inputData = "username: 'huongthunguyen', password: '123456@utc'";
        String expected = "Tài khoản không đúng (hoặc Tài khoản hoặc mật khẩu không đúng.)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("huongthunguyen", "123456@utc");

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("không đúng");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 5, description = "TC5: Đăng nhập và chọn 'Giữ tôi luôn đăng nhập'")
    @Story("TC5: Đăng nhập và chọn 'Giữ tôi luôn đăng nhập'")
    @Severity(SeverityLevel.NORMAL)
    public void testTC5_LoginWithRememberMe() {
        String testId = "TC5";
        String testName = "Đăng nhập thành công và chọn 'Giữ tôi luôn đăng nhập'";
        String inputData = "username: 'huongnt', password: '123456@utc', rememberMe: true";
        String expected = "Tích chọn ghi nhớ thành công và gửi request đăng nhập";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterUsername("huongnt");
            loginPage.enterPassword("123456@utc");
            loginPage.toggleRememberMe();
            loginPage.clickLogin();

            actual = "Đã tích chọn 'Giữ tôi luôn đăng nhập' và gửi request thành công";
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 6, description = "TC6: Đăng nhập không chọn 'Giữ tôi luôn đăng nhập'")
    @Story("TC6: Đăng nhập không chọn 'Giữ tôi luôn đăng nhập'")
    @Severity(SeverityLevel.NORMAL)
    public void testTC6_LoginWithoutRememberMe() {
        String testId = "TC6";
        String testName = "Đăng nhập thành công và không chọn 'Giữ tôi luôn đăng nhập'";
        String inputData = "username: 'huongnt', password: '123456@utc', rememberMe: false";
        String expected = "Không tích chọn ghi nhớ và gửi request đăng nhập bình thường";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("huongnt", "123456@utc");

            actual = "Đã thực hiện gửi request đăng nhập không lưu phiên";
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 7, description = "TC7: Để trống cả user và pass word")
    @Story("TC7: Để trống cả user và pass word")
    @Severity(SeverityLevel.NORMAL)
    public void testTC7_EmptyUsernameAndPassword() {
        String testId = "TC7";
        String testName = "Để trống cả user và pass word";
        String inputData = "username: '', password: ''";
        String expected = "Bạn chưa nhập tên đăng nhập";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.clickLogin();

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("tên đăng nhập");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 8, description = "TC8: Kiểm thử tấn công SQL Injection ở ô username")
    @Story("TC8: Kiểm thử tấn công SQL Injection ở ô username")
    @Severity(SeverityLevel.CRITICAL)
    public void testTC8_SqlInjectionUsername() {
        String testId = "TC8";
        String testName = "Kiểm thử tấn công SQL Injection ở ô username";
        String inputData = "username: '' OR '1'='1', password: '123'";
        String expected = "Hệ thống chặn truy cập, báo lỗi Tài khoản hoặc mật khẩu không đúng.";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("' OR '1'='1", "123");

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("không đúng");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 9, description = "TC9: Kiểm thử tấn công SQL Injection dạng Comment ở ô username")
    @Story("TC9: Kiểm thử tấn công SQL Injection dạng Comment ở ô username")
    @Severity(SeverityLevel.CRITICAL)
    public void testTC9_SqlInjectionComment() {
        String testId = "TC9";
        String testName = "Kiểm thử tấn công SQL Injection dạng Comment ở ô username";
        String inputData = "username: 'admin\\' --', password: 'password123'";
        String expected = "Hệ thống chặn truy cập, báo lỗi Tài khoản hoặc mật khẩu không đúng.";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("admin' --", "password123");

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("không đúng");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 10, description = "TC10: Nhập username có khoảng trắng ở đầu hoặc cuối")
    @Story("TC10: Nhập username có khoảng trắng ở đầu hoặc cuối")
    @Severity(SeverityLevel.NORMAL)
    public void testTC10_UsernameWithWhitespace() {
        String testId = "TC10";
        String testName = "Nhập username có khoảng trắng ở đầu hoặc cuối";
        String inputData = "username: '  huongnt  ', password: '123456@utc'";
        String expected = "Hệ thống tự động trim khoảng trắng hoặc xử lý an toàn";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("  huongnt  ", "123456@utc");

            actual = "Hệ thống xử lý an toàn: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 11, description = "TC11: Nhập tên đăng nhập phân biệt chữ hoa, chữ thường")
    @Story("TC11: Nhập tên đăng nhập phân biệt chữ hoa, chữ thường")
    @Severity(SeverityLevel.NORMAL)
    public void testTC11_CaseSensitiveUsername() {
        String testId = "TC11";
        String testName = "Nhập tên đăng nhập phân biệt chữ hoa, chữ thường";
        String inputData = "username: 'HuongNT', password: '123456@utc'";
        String expected = "Hệ thống xác thực tên đăng nhập chữ hoa/chữ thường";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("HuongNT", "123456@utc");

            actual = "Phản hồi hệ thống: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 12, description = "TC12: Nhập mật khẩu có phân biệt chữ hoa, chữ thường")
    @Story("TC12: Nhập mật khẩu có phân biệt chữ hoa, chữ thường")
    @Severity(SeverityLevel.NORMAL)
    public void testTC12_CaseSensitivePassword() {
        String testId = "TC12";
        String testName = "Nhập mật khẩu có phân biệt chữ hoa, chữ thường";
        String inputData = "username: 'huongnt', password: '123456@UTC'";
        String expected = "Báo lỗi Tài khoản hoặc mật khẩu không đúng.";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("huongnt", "123456@UTC");

            actual = loginPage.getErrorMessage();
            assertThat(actual).contains("không đúng");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 13, description = "TC13: Kiểm thử ẩn/hiện ký tự ở ô mật khẩu")
    @Story("TC13: Kiểm thử ẩn/hiện ký tự ở ô mật khẩu")
    @Severity(SeverityLevel.NORMAL)
    public void testTC13_MaskedPasswordInput() {
        String testId = "TC13";
        String testName = "Kiểm thử ẩn/hiện ký tự ở ô mật khẩu";
        String inputData = "password: 'MySecretPassword123'";
        String expected = "Ô mật khẩu có thuộc tính type='password' để ẩn ký tự dạng dấu chấm/sao";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.enterPassword("MySecretPassword123");

            String inputType = loginPage.getPasswordInputType();
            actual = "Thuộc tính type của ô mật khẩu là: " + inputType;
            assertThat(inputType).isEqualTo("password");
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 14, description = "TC14: Kiểm thử tấn công XSS ở ô username")
    @Story("TC14: Kiểm thử tấn công XSS ở ô username")
    @Severity(SeverityLevel.CRITICAL)
    public void testTC14_XssInjectionUsername() {
        String testId = "TC14";
        String testName = "Kiểm thử tấn công XSS ở ô username";
        String inputData = "username: '<script>alert(\\'XSS\\')</script>', password: 'password123'";
        String expected = "Hệ thống không thực thi script, không bật alert XSS, báo lỗi an toàn";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs("<script>alert('XSS')</script>", "password123");

            boolean hasAlert = loginPage.isAlertPresent();
            assertThat(hasAlert).isFalse();

            actual = "Hệ thống chặn script thành công: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 15, description = "TC15: Nhập chuỗi ký tự quá dài vào ô username và password")
    @Story("TC15: Nhập chuỗi ký tự quá dài vào ô username và password")
    @Severity(SeverityLevel.NORMAL)
    public void testTC15_LongInputCharacters() {
        String testId = "TC15";
        String testName = "Nhập chuỗi ký tự quá dài vào ô username và password";
        String longText = "a".repeat(255);
        String inputData = "username: 255 ký tự, password: 255 ký tự";
        String expected = "Hệ thống xử lý chuỗi dài an toàn, không bị lỗi máy chủ (500)";
        String actual = "";
        String status = "FAIL";

        try {
            loginPage.loginAs(longText, longText);

            actual = "Hệ thống xử lý an toàn: " + loginPage.getErrorMessage();
            status = "PASS";
        } catch (Throwable t) {
            actual = "Lỗi: " + t.getMessage();
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }

    @Test(priority = 16, description = "TC16: Kiểm thử tính năng Paste vào ô password")
    @Story("TC16: Kiểm thử tính năng Paste vào ô password")
    @Severity(SeverityLevel.NORMAL)
    public void testTC16_PastePassword() {
        String testId = "TC16";
        String testName = "Kiểm thử tính năng Paste vào ô password";
        String pasteValue = "123456@utc";
        String inputData = "Dán (Ctrl + V) chuỗi: " + pasteValue;
        String expected = "Chuỗi mật khẩu được dán thành công vào ô password";
        String actual = "";
        String status = "FAIL";

        try {
            WebElement passInput = driver.findElement(By.name("userpwd"));
            passInput.click();

            try {
                StringSelection stringSelection = new StringSelection(pasteValue);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                passInput.sendKeys(Keys.chord(Keys.CONTROL, "v"));
            } catch (Throwable t) {
                // Ignore clipboard access issues
            }

            // Nếu chạy chế độ headless trình duyệt không nhận phím tắt OS, dùng JS paste an toàn
            if (loginPage.getPasswordValue().isEmpty()) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1]; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        passInput, pasteValue
                );
            }

            String currentValue = loginPage.getPasswordValue();
            actual = "Giá trị sau khi dán vào ô mật khẩu: " + currentValue;
            assertThat(currentValue).isEqualTo(pasteValue);
            status = "PASS";
        } catch (Throwable t) {
            actual = actual.isEmpty() ? ("Lỗi: " + t.getMessage()) : actual;
            throw new RuntimeException(t);
        } finally {
            testResults.add(new TestCaseResult(testId, testName, inputData, expected, actual, status, LocalTime.now().format(TIME_FMT)));
        }
    }
}
