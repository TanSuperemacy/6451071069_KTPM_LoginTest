# 6451071069_KTPM_LoginTest

Dự án kiểm thử tự động tính năng **Đăng nhập (Login)** cho hệ thống **Văn phòng điện tử Trường ĐH Giao Thông Vận Tải (UTC)** sử dụng **Java**, **Selenium WebDriver**, **TestNG** và tự động xuất báo cáo kết quả ra file **Excel (.xlsx)** bằng **Apache POI**.

---

## 🌐 Trang web kiểm thử
- **Hệ thống:** Văn phòng điện tử UTC
- **URL:** `https://vanphongdientu.utc.edu.vn/Login`

---

## 🛠️ Công nghệ sử dụng
- **Ngôn ngữ:** Java 21 (LTS)
- **Công cụ build:** Maven (tích hợp sẵn Maven Wrapper `mvnw`)
- **Kiểm thử tự động:** Selenium WebDriver (v4.27.0)
- **Testing Framework:** TestNG (v7.10.2)
- **Xuất báo cáo Excel:** Apache POI (v5.3.0)
- **Design Pattern:** Page Object Model (POM)

---

## 📁 Cấu trúc thư mục dự án

```text
6451071069_KTPM_LoginTest/
│
├── .mvn/                             # Cấu hình Maven Wrapper
├── mvnw / mvnw.cmd                   # Script chạy Maven độc lập (không cần cài trước mvn)
├── pom.xml                           # Cấu hình thư viện (Selenium, TestNG, Apache POI)
├── KetQuaKiemThu_Login.xlsx          # File báo cáo Excel được tự động tạo sau khi chạy test
├── README.md                         # Hướng dẫn chi tiết
│
├── src/
│   ├── main/
│   │   └── java/com/ktpm/
│   │       ├── Main.java             # Chạy trực tiếp để xem Chrome tự thao tác và xuất Excel
│   │       ├── model/
│   │       │   └── TestCaseResult.java # Model lưu trữ kết quả kiểm thử
│   │       ├── pages/
│   │           └── LoginPage.java    # Page Object Model cho trang Login UTC
│   │       └── utils/
│   │           └── ExcelExporter.java # Tiện ích định dạng và xuất báo cáo Excel (.xlsx)
│   │
│   └── test/
│       ├── java/com/ktpm/
│       │   └── LoginTest.java        # Bộ kịch bản TestNG (tự động xuất Excel sau khi test xong)
│       └── resources/
│           └── testng.xml            # Cấu hình Suite TestNG
```

---

## 🧪 Các kịch bản kiểm thử (Test Cases)

| Mã TC | Tên Kịch Bản | Dữ Liệu Đầu Vào | Kết Quả Mong Đợi | Trạng Thái |
| :---: | :--- | :--- | :--- | :---: |
| **TC01** | Kiểm tra mở trang đăng nhập UTC | URL: `vanphongdientu.utc.edu.vn/Login` | Mở trang thành công, tiêu đề hợp lệ | **PASS** |
| **TC02** | Kiểm thử để trống tên đăng nhập | `username: ""` <br> `password: "matkhautest123"` | Báo lỗi: *"Bạn chưa nhập tên đăng nhập"* | **PASS** |
| **TC03** | Kiểm thử để trống mật khẩu | `username: "6451071069"` <br> `password: ""` | Báo lỗi: *"Bạn chưa nhập mật khẩu"* | **PASS** |
| **TC04** | Kiểm thử đăng nhập sai tài khoản hoặc mật khẩu | `username: "sinhvien_utc_sai"` <br> `password: "matkhausai999"` | Báo lỗi: *"Tài khoản hoặc mật khẩu không đúng."* | **PASS** |

---

## 📊 File báo cáo Excel kết quả (`KetQuaKiemThu_Login.xlsx`)
File Excel được thiết kế chuẩn báo cáo môn học KTPM:
- **Tiêu đề báo cáo:** BÁO CÁO KẾT QUẢ KIỂM THỬ TỰ ĐỘNG - LOGIN TEST
- **Thông tin:** Tên hệ thống, Mã sinh viên `6451071069`, Ngày giờ thực hiện
- **Bảng chi tiết:** STT/Mã TC, Tên kịch bản, Dữ liệu đầu vào, Kết quả mong đợi, Kết quả thực tế, Trạng thái (được tô màu xanh lá **PASS** hoặc màu đỏ **FAIL**), Thời gian chạy.
- **Thống kê:** Tổng số Test Case, số ca PASS, số ca FAIL, tỷ lệ đạt (%).

---

## 🚀 Cách bấm chạy để xuất file Excel

### Cách 1: Chạy trực tiếp từ file `Main.java`
- Mở file [`Main.java`](file:///c:/Users/HP/Documents/Bai%20tap%20va%201%20so%20file%20j%20do/ktpm/logintest/src/main/java/com/ktpm/Main.java).
- Bấm nút **Run ▶️** (hình tam giác màu xanh).
- Chrome sẽ tự bật lên chạy đủ 4 kịch bản và tự tạo file [`KetQuaKiemThu_Login.xlsx`](file:///c:/Users/HP/Documents/Bai%20tap%20va%201%20so%20file%20j%20do/ktpm/logintest/KetQuaKiemThu_Login.xlsx) ngay tại thư mục gốc.

### Cách 2: Chạy từ Terminal
```powershell
.\mvnw.cmd test
```
hoặc:
```powershell
.\mvnw.cmd compile exec:java
```
