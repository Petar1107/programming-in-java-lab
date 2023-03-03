package com.oss.lecture1.myClass;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ColorTest {
    @Test
    public void HexadecimalToDecimalTest (){
        var expectedValue = 255;
        var actualValue = Color.hexadecimalToDecimal("FF");
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void decodeTest (){
        var expectedValue = new Color (255,255,255);
        var actualValue = Color.decode("0xFFFFFF");
        assertEquals(expectedValue.getRed(),actualValue.getRed());
        assertEquals(expectedValue.getGreen(),actualValue.getGreen());
        assertEquals(expectedValue.getBlue(),actualValue.getBlue());
    }
    @Test
    public void RGBtoHSBTest (){
        var c = new Color(31, 240, 255);
        var expectedArr = new float [3];
        expectedArr[0] = 0.5111607f;
        expectedArr[1] = 0.8784314f;
        expectedArr[2] = 1.0f;
        var actualArr = new float [3];
        Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),actualArr);
        assertEquals(expectedArr[0],actualArr[0],0.0f);
        assertEquals(expectedArr[1],actualArr[1],0.0f);
        assertEquals(expectedArr[2],actualArr[2],0.0f);
    }
    @Test
    public void RGBtoHSLTest (){
        var c = new Color(31, 240, 255);
        var expectedArr = new float [3];
        expectedArr[0] = 184.01785f;
        expectedArr[1] = 100.000015f;
        expectedArr[2] = 56.078434f;
        var actualArr = new float [3];
        Color.RGBtoHSL(c.getRGB(),actualArr);
        assertEquals(expectedArr[0],actualArr[0],0.0f);
        assertEquals(expectedArr[1],actualArr[1],0.0f);
        assertEquals(expectedArr[2],actualArr[2],0.0f);

    }
    @Test
    public void RGBtoCMYTest (){
        var c = new Color(31, 240, 255);
        var expectedArr = new int [4];
        expectedArr[0] = 87;
        expectedArr[1] = 5;
        expectedArr[2] = 0;
        expectedArr[3] =0;
        var actualArr = new int [4];
        Color.RGBtoCMYK(c.getRed(),c.getGreen(),c.getBlue(),actualArr);
        assertEquals(expectedArr[0],actualArr[0]);
        assertEquals(expectedArr[1],actualArr[1]);
        assertEquals(expectedArr[2],actualArr[2]);
        assertEquals(expectedArr[3],actualArr[3]);

    }
}
