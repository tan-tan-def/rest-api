package com.assignment.asm03.common;

import java.util.Random;

public class RandomNumber {
    public static int randomNumber(int max){
        Random random = new Random();
        int number = random.nextInt(max)+1;
        return number;
    }
}
