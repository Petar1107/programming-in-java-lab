package com.oss.lecture1;
import java.awt.Color;
public class ColorConverter {
    public static void main(String[] args) {

        String hexColor = "0x1FF0FF";

        Color c = Color.decode(hexColor);

        float[] hsbCode = new float[3];

        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbCode);
        System.out.println("Zadatak1!");
        System.out.println("Boja u HEX formatu: 0x" +
                Integer.toHexString(c.getRGB() & 0x00FFFFFF));
        System.out.println("Boja u RGB formatu: " + c.getRed() + ", " +
                c.getGreen() + ", " + c.getBlue());
        System.out.println("Boja u HSB formatu: " + hsbCode[0] * 360 + "°, " +
                hsbCode[1] * 100 + "%, " + hsbCode[2] * 100 + "%");
    }
}
