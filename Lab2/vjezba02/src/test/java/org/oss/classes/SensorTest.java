package org.oss.classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SensorTest {
    @Test
    public void SensorToJsonTest (){
        var expectedValue = "{\"sensorPurpose\":null,\"dataType\":null,\"factor\":0,\"lowerValueLimit\":0.0,\"upperValueLimit\":0.0,\"measuringUnit\":null,\"value\":0.0}";
        var actualValue = Sensor.sensorToJsonConverter(new Sensor());
        assertEquals(expectedValue,actualValue);
    }
}
