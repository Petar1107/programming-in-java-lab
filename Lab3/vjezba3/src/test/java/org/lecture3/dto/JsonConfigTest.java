package org.lecture3.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.lecture3.classes.Sensor;

import static org.junit.Assert.*;

public class JsonConfigTest {
    @Test
    public void parseConfigFromJson() {
        String jsonInput = "{\n" +
                "  \"publisher\" : {\n" +
                "    \"broker\" : \"tcp://localhost:1883\",\n" +
                "    \"clientId\" : \"Petar\"\n" +
                "  },\n" +
                "  \"sensors\": {\n" +
                "    \"A\": {\n" +
                "      \"sensorPurpose\": \"Senzor za temperaturu vode\",\n" +
                "      \"dataType\": \"int\",\n" +
                "      \"factor\": 10,\n" +
                "      \"lowerValueLimit\": -3266.8,\n" +
                "      \"upperValueLimit\": 3266.8,\n" +
                "      \"measuringUnit\": \"celzijev stupanj\",\n" +
                "      \"value\": 0.0\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        ObjectMapper mapper = new ObjectMapper();
        var jsonConfig = new JsonConfig();
        try {
            jsonConfig = mapper.readValue(jsonInput, JsonConfig.class);
        } catch (Exception ignore) {
        }
        Sensor mockedSensor = new Sensor("Senzor za temperaturu vode","int", 10, -3266.8, 3266.8, "celzijev stupanj", 0.0);
        assertEquals(jsonConfig.getPublisher().get("broker"), "tcp://localhost:1883");
        assertEquals(jsonConfig.getPublisher().get("clientId"), "Petar");
        assertEquals(jsonConfig.getSensors().get('A').getSensorPurpose(), mockedSensor.getSensorPurpose());
        assertEquals(jsonConfig.getSensors().get('A').getDataType(), mockedSensor.getDataType());
        assertEquals(jsonConfig.getSensors().get('A').getFactor(), mockedSensor.getFactor());
        assertEquals(jsonConfig.getSensors().get('A').getValue(), mockedSensor.getValue(),0);
        assertEquals(jsonConfig.getSensors().get('A').getUpperValueLimit(), mockedSensor.getUpperValueLimit(),0);
        assertEquals(jsonConfig.getSensors().get('A').getLowerValueLimit(), mockedSensor.getLowerValueLimit(),0);
        assertEquals(jsonConfig.getSensors().get('A').getMeasuringUnit(), mockedSensor.getMeasuringUnit());
    }

    @Test
    public void parseConfigFromJsonError() {
        String jsonInput = "{\n" +
                "  \"publisher\" : {\n" +
                "    \"broker\" : \"tcp://localhost:1883\",\n" +
                "    \"clientId\" : \"Petar\"\n" +
                "  },\n" +
                "  \"testField\": {\n" +
                "    \"A\": {\n" +
                "      \"sensorPurpose\": \"Senzor za temperaturu vode\",\n" +
                "      \"lowerValueLimit\": -3266.8,\n" +
                "      \"upperValueLimit\": 3266.8,\n" +
                "      \"measuringUnit\": \"celzijev stupanj\",\n" +
                "      \"value\": 0.0\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        ObjectMapper mapper = new ObjectMapper();
        Exception thrown = assertThrows(
                Exception.class,
                () ->  mapper.readValue(jsonInput, JsonConfig.class)
        );

        assertTrue(thrown.getMessage().contains("Unrecognized field \"testField\""));
    }
}
