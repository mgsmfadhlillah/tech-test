package com.telkomsel.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class EncrytedPasswordUtils {

    // Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static String autoGenerate(){
        String password = String.format("%03d", new Random().nextInt(999));
        return password;
    }
    public static void main(String[] args) {

        String str = "India";
        String akulaku = "123";
        System.out.println("pass " + encrytePassword(akulaku));
        System.out.println("skrg " + new Date());
        System.out.println("date " + addHoursToJavaUtilDate(new Date(), 2));
        System.out.println("auto " + autoGenerate());
        System.out.println("last char = " + str.substring(str.length() - 3));
    }
}