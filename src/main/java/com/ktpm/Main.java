package com.ktpm;

import com.ktpm.model.TestCaseResult;
import com.ktpm.pages.LoginPage;
import com.ktpm.utils.ExcelExporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * File Main: Bật trực tiếp trình duyệt Chrome, thực hiện kiểm thử tự động
 * và xuất báo cáo kết quả ra file Excel KetQuaKiemThu_Login.xlsx
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  BẮT ĐẦU CHẠY AUTOMATION TEST VÀ XUẤT BÁO CÁO EXCEL");
        System.out.println("==========================================================");

        System.setProperty("webdriver.chrome.silentOutput", "true");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);
        List<TestCaseResult> results = new ArrayList<>();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            LoginPage loginPage = new LoginPage(driver);

            // --- TC01: Mở trang ---
            System.out.println("\n[TC01] Đang mở trang web...");
            loginPage.open();
            Thread.sleep(1500);
            String title = loginPage.getPageTitle();
            results.add(new TestCaseResult(
                    "TC01", "Kiểm tra mở trang đăng nhập UTC",
                    "URL: " + LoginPage.PAGE_URL,
                    "Mở trang thành công, tiêu đề trang hợp lệ",
                    "Tiêu đề: " + title,
                    (title != null && !title.isEmpty()) ? "PASS" : "FAIL",
                    LocalTime.now().format(timeFmt)
            ));
            System.out.println("-> Hoàn thành TC01: " + title);

            // --- TC02: Để trống username ---
            System.out.println("\n[TC02] Kiểm thử để trống tên đăng nhập...");
            loginPage.open();
            loginPage.enterPassword("matkhautest123");
            Thread.sleep(800);
            loginPage.clickLogin();
            Thread.sleep(1500);
            String err02 = loginPage.getErrorMessage();
            boolean pass02 = err02.contains("chưa nhập tên đăng nhập") || err02.contains("tên đăng nhập");
            results.add(new TestCaseResult(
                    "TC02", "Kiểm thử để trống tên đăng nhập",
                    "username: '', password: 'matkhautest123'",
                    "Hiển thị thông báo: Bạn chưa nhập tên đăng nhập",
                    err02,
                    pass02 ? "PASS" : "FAIL",
                    LocalTime.now().format(timeFmt)
            ));
            System.out.println("-> Hoàn thành TC02: " + err02);

            // --- TC03: Để trống password ---
            System.out.println("\n[TC03] Kiểm thử để trống mật khẩu...");
            loginPage.open();
            loginPage.enterUsername("6451071069");
            Thread.sleep(800);
            loginPage.clickLogin();
            Thread.sleep(1500);
            String err03 = loginPage.getErrorMessage();
            boolean pass03 = err03.contains("chưa nhập mật khẩu") || err03.contains("mật khẩu");
            results.add(new TestCaseResult(
                    "TC03", "Kiểm thử để trống mật khẩu",
                    "username: '6451071069', password: ''",
                    "Hiển thị thông báo: Bạn chưa nhập mật khẩu",
                    err03,
                    pass03 ? "PASS" : "FAIL",
                    LocalTime.now().format(timeFmt)
            ));
            System.out.println("-> Hoàn thành TC03: " + err03);

            // --- TC04: Nhập sai tài khoản/mật khẩu ---
            System.out.println("\n[TC04] Kiểm thử đăng nhập sai tài khoản...");
            loginPage.open();
            loginPage.enterUsername("sinhvien_utc_sai");
            Thread.sleep(600);
            loginPage.enterPassword("matkhausai999");
            Thread.sleep(600);
            loginPage.clickLogin();
            Thread.sleep(1500);
            String err04 = loginPage.getErrorMessage();
            boolean pass04 = err04.contains("không đúng") || err04.contains("Tài khoản hoặc mật khẩu");
            results.add(new TestCaseResult(
                    "TC04", "Kiểm thử đăng nhập sai tài khoản hoặc mật khẩu",
                    "username: 'sinhvien_utc_sai', password: 'matkhausai999'",
                    "Hiển thị thông báo: Tài khoản hoặc mật khẩu không đúng.",
                    err04,
                    pass04 ? "PASS" : "FAIL",
                    LocalTime.now().format(timeFmt)
            ));
            System.out.println("-> Hoàn thành TC04: " + err04);

            // --- Xuất file Excel ---
            System.out.println("\n[XUẤT EXCEL] Đang tạo file báo cáo Excel...");
            ExcelExporter.exportResultsToExcel(results, "KetQuaKiemThu_Login.xlsx");

            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("Đã đóng trình duyệt.");
        }
    }
}
