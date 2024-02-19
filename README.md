
# Phone Shop

Đây là phần mềm dành cho khách hàng muốn đặt mua điện thoại của cửa hàng PhoneShop

## Mô tả chức năng chính: 

- Khách hàng có thể xem, tra cứu thông tin, đặt hàng, phản hồi cho PhoneShop.
- Khách hàng có thể đăng nhập bằng Google, Facebook .
- Khách hàng có thể thanh toán trực tuyến bằng Paypal Sanbox khi đặt hàng.
- Quản lý có thể quản lý thông tin khách hàng, quản lý đơn hàng được đặt, quản lý sản phẩm, thống kê.
- Ngoài ra website còn tích hợp chức năng tư vấn trực tiếp trên website và gửi mail về cho khách hàng.
## Tác giả

- [@LeDonChung](https://www.github.com/LeDonChung)


## Công nghệ sử dụng

**Client:** Html, CSS, Bootstrap 4, Thymeleaf

**Server:** Spring boot, Spring Data JPA, Hibernate, Spring Mail, Spring Security6, Spring Web, MYSQL,...


## Khởi chạy

Clone the project

```bash
  git clone https://github.com/LeDonChung/PhoneShop.git
```

Cài đặt IntelliJ IDEA
```bash
  https://www.jetbrains.com/idea/download/
```

Cấu hình CSDL
```bash
  Start server MYSQL và chạy file database-v1.sql trong workbench.
```

Cấu hình File application.properties
```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/PhoneShop
  spring.datasource.username=<username>
  spring.datasource.password=<password>
```

Khởi động
```bash
  Chạy hai application là AdminApplication và CustomerApplication
```


## Hỗ trợ

For support, email ledonchung12a2@gmail.com
