package com.oss.lecture1.myClass;
public class Color {
    private int red;
    private int green;
    private int blue;
    private int value;
    public int getRed(){
        return red;
    }
    public int getGreen(){
        return green;
    }
    public int getBlue(){
        return blue;
    }
    public int getRGB() {
        return value;
    }

    Color(int red, int green, int blue){
       this.red=red;
       this.green=green;
       this.blue=blue;
       value = ((red & 0xFF) << 16) | ((green & 0xFF) << 8)  | ((blue & 0xFF) << 0);
    }

    public static int hexadecimalToDecimal(String hexVal)
    {
        int len = hexVal.length();
        int base = 1;
        int dec_val = 0;

        for (int i = len - 1; i >= 0; i--) {

            if (hexVal.charAt(i) >= '0' && hexVal.charAt(i) <= '9') {
                dec_val += (hexVal.charAt(i) - 48) * base;
                base = base * 16;
            }

            else if (hexVal.charAt(i) >= 'A' && hexVal.charAt(i) <= 'F') {
                dec_val += (hexVal.charAt(i) - 55) * base;
                base = base * 16;
            }
        }
        return dec_val;
    }

    public static float[] RGBtoHSL(int rgb, float[] hsl) {
        float r = ((0x00ff0000 & rgb) >> 16) / 255.f;
        float g = ((0x0000ff00 & rgb) >> 8) / 255.f;
        float b = ((0x000000ff & rgb)) / 255.f;
        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float c = max - min;

        float h_ = 0.f;
        if (c == 0) {
            h_ = 0;
        } else if (max == r) {
            h_ = (float)(g-b) / c;
            if (h_ < 0) h_ += 6.f;
        } else if (max == g) {
            h_ = (float)(b-r) / c + 2.f;
        } else if (max == b) {
            h_ = (float)(r-g) / c + 4.f;
        }
        float h = 60.f * h_;

        float l = (max + min) * 0.5f;

        float s;
        if (c == 0) {
            s = 0.f;
        } else {
            s = c / (1 - Math.abs(2.f * l - 1.f));
        }

        hsl[0] = h;
        hsl[1] = s*100;
        hsl[2] = l*100;
        return hsl;
    }
     public static int[] RGBtoCMYK(int r, int g, int b, int [] cmyk) {
        double percentageR = r / 255.0 * 100;
        double percentageG = g / 255.0 * 100;
        double percentageB = b / 255.0 * 100;

        double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);

        if (k == 100) {
            cmyk[0]=0;
            cmyk[1]=0;
            cmyk[2]=0;
            cmyk[3]=100;
            return cmyk;
        }

        int c = (int)((100 - percentageR - k) / (100 - k) * 100);
        int m = (int)((100 - percentageG - k) / (100 - k) * 100);
        int y = (int)((100 - percentageB - k) / (100 - k) * 100);
        cmyk[0]=c;
        cmyk[1]=m;
        cmyk[2]=y;
        cmyk[3]=(int)k;
        return cmyk;
    }
    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = (r > g) ? r : g;
        if (b > cmax) cmax = b;
        int cmin = (r < g) ? r : g;
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }
    public static Color decode(String str){
        str = str.toUpperCase();
        var code = str.split("X")[1];
        var r= code.substring(0, 2);
        var g= code.substring(2, 4);
        var b= code.substring(4, 6);
        return new Color(Color.hexadecimalToDecimal(r),Color.hexadecimalToDecimal(g),Color.hexadecimalToDecimal(b));
    }

    public static void main(String[] args) {

        String hexColor = "0x1FF0FF";
        Color c = Color.decode(hexColor);

        float[] hsbCode = new float[3];
        int [] cmykCode = new int[4];
        float[] hslCode = new float[3];

        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbCode);
        Color.RGBtoCMYK(c.getRed(), c.getGreen(), c.getBlue(), cmykCode);
        Color.RGBtoHSL(c.getRGB(),hslCode);

        System.out.println("Zadatak2!");
        System.out.println("Boja u HEX formatu: 0x" +
                Integer.toHexString(c.getRGB() & 0x00FFFFFF));
        System.out.println("Boja u RGB formatu: " + c.getRed() + ", " +
                c.getGreen() + ", " + c.getBlue());
        System.out.println("Boja u HSB formatu: " + hsbCode[0] * 360 + "°, " +
                hsbCode[1] * 100 + "%, " + hsbCode[2] * 100 + "%");
        System.out.println("Boja u CMYK formatu: " + cmykCode[0] + ", " +
                cmykCode[1] + ", " + cmykCode[2] + ", " + cmykCode[3]);
        System.out.println("Boja u HSL formatu: " + hslCode[0] + "°, " +
                hslCode[1]  + "%, " + hslCode[2]  + "%");
    }
}
