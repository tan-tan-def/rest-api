package com.assignment.asm03.model;

import com.assignment.asm03.annotation.checkEmailDuplicate.EmailNotDuplicate;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorIsAddedByAdmin {

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
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotNull(message = "Vui lòng điền địa chỉ")
    private String address;

    @NotNull(message = "Vui lòng điền mật khẩu")
    private String password;


    @NotNull(message = "Vui lòng điền trường này")
    private String generalIntroduction;

    @NotNull(message = "Vui lòng điền trường này")
    private String educationalBackground;

    @NotNull(message = "Vui lòng điền trường này")
    private String achievements;

    @NotNull(message = "Vui lòng điền trường này")
    @Column(name="specialization_id")
    @Min(value = 1,message = "Vui lòng nhập số nguyên lớn hơn 1")
    @Max(value = 10,message = "Vui lòng nhập số nhỏ hơn 10")
    private int specializationId;

    @Column(name="hospital_id")
    @NotNull(message = "Vui lòng điền trường này")
    @Min(value = 1,message = "Vui lòng nhập số nguyên lớn hơn 1")
    @Max(value = 10,message = "Vui lòng nhập số nhỏ hơn 10")
    private int hospitalId;
}
