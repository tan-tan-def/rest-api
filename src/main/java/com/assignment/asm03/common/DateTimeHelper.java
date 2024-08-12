package com.assignment.asm03.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZoneId;

public class DateTimeHelper {

//    public static String getCurrentDateTime() {
//        // Lấy ngày và giờ hiện tại
//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//        // Định dạng ngày và giờ theo ý muốn
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        String formattedDateTime = currentDateTime.format(formatter);
//
//        return formattedDateTime;
//    }
    public static Date getCurrentDateTime() {
        // Lấy ngày và giờ hiện tại
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Chuyển đổi từ LocalDateTime sang kiểu Date
        Date date = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return date;
    }

}
