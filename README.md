# 6451071069_KTPM_LoginTest

Dự án kiểm thử tự động (Automation Testing) tính năng **Đăng nhập (Login)** cho hệ thống **Văn phòng điện tử Trường ĐH Giao Thông Vận Tải (UTC)** sử dụng **Java**, **Selenium WebDriver**, **TestNG**, xuất báo cáo **Excel (.xlsx)** bằng **Apache POI** và báo cáo phân tích số liệu trực quan với **Allure Report**.

---

## 🌐 Trang web kiểm thử
- **Hệ thống:** Văn phòng điện tử UTC
- **URL:** `https://vanphongdientu.utc.edu.vn/Login`

---

## 🛠️ Công nghệ & Thư viện sử dụng
- **Ngôn ngữ:** Java 21 (LTS)
- **Công cụ build:** Apache Maven (đã tích hợp sẵn Maven Wrapper `mvnw`)
- **Kiểm thử tự động:** Selenium WebDriver (`v4.27.0`)
- **Testing Framework:** TestNG (`v7.10.2`)
- **Báo cáo phân tích:** Allure Report (`v2.29.0`)
- **Xuất file Excel:** Apache POI (`v5.3.0`)
- **Thư viện kiểm tra:** AssertJ (`v3.26.3` - Slide 9)
- **Design Pattern:** Page Object Model (POM) chuẩn mực theo Slide 48

---

## 📁 Cấu trúc thư mục dự án chuẩn theo Slide 48

```text
6451071069_KTPM_LoginTest/
│
├── .mvn/                               # Cấu hình Maven Wrapper
├── mvnw / mvnw.cmd                     # Script chạy Maven độc lập (không cần cài trước mvn)
├── pom.xml                             # Cấu hình thư viện (Selenium 4, TestNG, Allure, AssertJ, POI)
├── KetQuaKiemThu_Login.xlsx            # File báo cáo Excel được tự động tạo sau khi chạy test
├── testcase.txt                        # Danh sách 16 kịch bản kiểm thử chi tiết
├── README.md                           # Tài liệu hướng dẫn dự án
│
└── src/test/
    ├── java/com/ktpm/
    │   ├── base/                       # TẦNG CƠ SỞ (Slide 48)
    │   │   └── BaseTest.java           # Quản lý WebDriver lifecycle, hỗ trợ Headless (Slide 10, 60)
    │   ├── pages/                      # CÁC PAGE OBJECTS (Slide 48)
    │   │   ├── BasePage.java           # Lớp cha giữ WebDriver, Explicit Wait (Slide 49-50)
    │   │   └── LoginPage.java          # Page Object trang Login UTC kế thừa BasePage (Slide 51-52)
    │   ├── tests/                      # CÁC TEST SCRIPTS (Slide 48)
    │   │   └── LoginE2ETest.java       # Bộ 16 Test Cases tuân thủ Explicit Wait, không Thread.sleep (Slide 48, 54, 63)
    │   ├── model/
    │   │   └── TestCaseResult.java     # Model kết quả kiểm thử
    │   └── utils/
    │       └── ExcelExporter.java      # Tiện ích xuất file Excel (.xlsx) bằng Apache POI
    └── resources/
        └── testng.xml                  # Cấu hình Test Suite TestNG
```

---

## 📋 Danh sách 16 Test Cases & Lịch sử Git Commit

Mỗi Test Case được quản lý độc lập theo từng commit riêng biệt trên Git theo đúng chuẩn quy trình kiểm thử:

| STT | Mã TC | Tên Kịch Bản Kiểm Thử | Dữ Liệu Đầu Vào | Kết Quả Mong Đợi | Trạng Thái | Git Commit |
| :---: | :---: | :--- | :--- | :--- | :---: | :--- |
| 1 | **TC1** | Để trống user hoặc pass word | `user: ''`, `pass: '1256'` | Báo lỗi: *"Bạn chưa nhập tên đăng nhập"* | **PASS** | `test(TC1): verify login validation when username is empty and password is provided` |
| 2 | **TC2** | Để trống mật khẩu | `user: 'huongnt'`, `pass: ''` | Báo lỗi: *"Bạn chưa nhập mật khẩu"* | **PASS** | `test(TC2): verify login validation when password is empty and username is provided` |
| 3 | **TC3** | Đúng tên sai mật khẩu | `user: 'huongnt'`, `pass: 'utc@235'` | Báo lỗi: *"Tài khoản hoặc mật khẩu không đúng."* | **PASS** | `test(TC3): verify authentication error with valid username and incorrect password` |
| 4 | **TC4** | Sai tên, đúng mật khẩu | `user: 'huongthunguyen'`, `pass: '123456@utc'` | Báo lỗi: *"Tài khoản hoặc mật khẩu không đúng."* | **PASS** | `test(TC4): verify authentication error with incorrect username and valid password` |
| 5 | **TC5** | Đăng nhập và chọn "Giữ tôi luôn đăng nhập" | `user: 'huongnt'`, `pass: '123456@utc'`, checkbox: `true` | Ghi nhớ phiên và chuyển hướng trang chủ | **PASS** | `test(TC5): verify login flow with Remember Me option enabled` |
| 6 | **TC6** | Đăng nhập không chọn "Giữ tôi luôn đăng nhập" | `user: 'huongnt'`, `pass: '123456@utc'`, checkbox: `false` | Đăng nhập bình thường không lưu phiên | **PASS** | `test(TC6): verify login flow without Remember Me option enabled` |
| 7 | **TC7** | Để trống cả user và pass word | `user: ''`, `pass: ''` | Báo lỗi: *"Bạn chưa nhập tên đăng nhập"* | **PASS** | `test(TC7): verify validation error when both username and password are empty` |
| 8 | **TC8** | Tấn công SQL Injection ở ô username | `user: '' OR '1'='1'`, `pass: '123'` | Hệ thống chặn, báo lỗi không cho bypass | **PASS** | `test(TC8): verify security protection against classic SQL Injection in username` |
| 9 | **TC9** | Tấn công SQL Injection dạng Comment | `user: 'admin\' --'`, `pass: '123456'` | Hệ thống chặn, báo lỗi không cho bypass | **PASS** | `test(TC9): verify security protection against SQL Injection comment syntax in username` |
| 10 | **TC10** | Username có khoảng trắng ở đầu hoặc cuối | `user: '  huongnt  '`, `pass: '123456@utc'` | Hệ thống tự động trim khoảng trắng hoặc xử lý an toàn | **PASS** | `test(TC10): verify handling of username with leading or trailing whitespaces` |
| 11 | **TC11** | Username phân biệt chữ hoa, chữ thường | `user: 'HuongNT'`, `pass: '123456@utc'` | Hệ thống xác thực chữ hoa/thường an toàn | **PASS** | `test(TC11): verify case-sensitive validation on username input` |
| 12 | **TC12** | Mật khẩu phân biệt chữ hoa, chữ thường | `user: 'huongnt'`, `pass: '123456@UTC'` | Báo lỗi: *"Tài khoản hoặc mật khẩu không đúng."* | **PASS** | `test(TC12): verify case-sensitive validation on password input` |
| 13 | **TC13** | Kiểm thử ẩn/hiện ký tự ở ô mật khẩu | `pass: 'SecretPass123'` | Ô pass có thuộc tính `type="password"`, ký tự bị ẩn | **PASS** | `test(TC13): verify password field masking and input type attribute` |
| 14 | **TC14** | Tấn công XSS ở ô username | `user: '<script>alert("XSS")</script>'` | Không thực thi alert script, báo lỗi an toàn | **PASS** | `test(TC14): verify security protection against Cross-Site Scripting (XSS) in username` |
| 15 | **TC15** | Nhập chuỗi ký tự quá dài (255 ký tự) | `user: 255 ký tự`, `pass: 255 ký tự` | Không crash server (lỗi 500), xử lý an toàn | **PASS** | `test(TC15): verify handling of boundary input length exceeding 255 characters` |
| 16 | **TC16** | Kiểm thử tính năng Paste vào ô password | Dán chuỗi `123456@utc` | Dán thành công vào ô password | **PASS** | `test(TC16): verify paste functionality into password input field` |

---

## 📊 1. Báo cáo số liệu với Allure Report

Dự án đã tích hợp **Allure Report** giúp xem biểu đồ thống kê, tỷ lệ Pass/Fail, thời gian thực thi của từng test case:

### Bước 1: Chạy kiểm thử để sinh dữ liệu Allure
```powershell
.\mvnw.cmd test
```
*(Dữ liệu kết quả sẽ tự động lưu vào thư mục `target/allure-results`)*

### Bước 2: Bật trang báo cáo Allure Report trên trình duyệt
```powershell
.\mvnw.cmd allure:serve
```
> Lệnh này sẽ tự động khởi động một web server nội bộ và mở trang Dashboard của Allure trên trình duyệt (có biểu đồ tròn, timeline và chi tiết từng step của 16 kịch bản).

---

## 📑 2. Báo cáo kết quả ra file Excel (`KetQuaKiemThu_Login.xlsx`)
File Excel được tự động tạo/cập nhật ngay tại thư mục gốc với các thông tin:
- Tiêu đề báo cáo và thông tin Mã sinh viên: **6451071069**.
- Chi tiết từng kịch bản, dữ liệu đầu vào, kết quả mong đợi, kết quả thực tế, thời gian chạy.
- Các ô trạng thái được tô màu trực quan (**PASS** màu xanh lá, **FAIL** màu đỏ).
- Dòng tổng kết thống kê số lượng và tỷ lệ đạt (%).

---

## 🚀 Hướng dẫn chạy kiểm thử dự án

### 1. Chạy kiểm thử có hiển thị trình duyệt (Headed mode)
```powershell
.\mvnw.cmd test
```
*Chrome sẽ tự động mở lên, chạy tuần tự 16 Test Cases, tự động xuất file `KetQuaKiemThu_Login.xlsx` và sinh dữ liệu Allure.*

### 2. Chạy kiểm thử ở chế độ không mở giao diện (Headless mode - Slide 60 & 63)
```powershell
.\mvnw.cmd test -Dheadless=true
```

### 3. Mở xem Dashboard báo cáo Allure Report
```powershell
.\mvnw.cmd allure:serve
```
