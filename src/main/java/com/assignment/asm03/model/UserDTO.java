package com.assignment.asm03.model;

import com.assignment.asm03.annotation.checkEmailDuplicate.EmailNotDuplicate;
import com.assignment.asm03.annotation.passwordMatches.PasswordMatches;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches(message = "Vui lòng nhập lại mật khẩu trùng khớp với mật khẩu ban đầu")
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull(message = "Vui lòng điền họ và tên")
    private String name;

    @NotNull(message = "Vui lòng điền giới tính")
    private String gender;

    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Vui lòng nhập email đúng định dạng")
    @NotNull(message = "Vui lòng điền email")
    @EmailNotDuplicate(message = "Email bị trùng lặp, Vui lòng chọn email khác")
    private String email;

    @NotNull(message = "Vui lòng điền số điện thoại")
//    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotNull(message = "Vui lòng điền địa chỉ")
    private String address;

    @NotNull(message = "Vui lòng điền mật khẩu")
    private String password;

    @NotNull(message = "Vui lòng điền trường này")
    private String confirmPassword;

}
