package com.oss.lecture5.utils;

public class Randomizer {
    public static int getRandomNumber() {
        int min=0;
        int max=10000;
        return (int) ((Math.random() * (max - min)) + min);
    }
}
