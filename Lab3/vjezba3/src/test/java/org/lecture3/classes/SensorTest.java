package org.lecture3.classes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SensorTest {
    @Test
    public void SensorToJsonTest (){
        var expectedValue = "{\"sensorPurpose\":null,\"dataType\":null,\"factor\":0,\"lowerValueLimit\":0.0,\"upperValueLimit\":0.0,\"measuringUnit\":null,\"value\":0.0}";
        var actualValue = Sensor.sensorToJsonConverter(new Sensor());
        assertEquals(expectedValue,actualValue);
    }

    @Test
    public void randomValueTest (){
        var mockedSensor = new Sensor();
        var actualSensor = new Sensor();
        actualSensor.randomValue();
        assertEquals(mockedSensor.getValue(),actualSensor.getValue(),0);

    }
}
