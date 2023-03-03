package org.lecture3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.lecture3.classes.Sensor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonConfig {
    private HashMap<String, String> publisher = new HashMap<>();
    private HashMap<Character, Sensor> sensors = new HashMap<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("sensors")
    private void unpackSensors(Map<String,Object> sensorMap) {
        for (var key : sensorMap.keySet()) {
            Map<String,Object> sensorRawMap = (Map<String,Object>)sensorMap.get(key);
            this.sensors.put(key.charAt(0), new Sensor(
                    (String) sensorRawMap.get("sensorPurpose"),
                    (String) sensorRawMap.get("dataType"),
                    (int) sensorRawMap.get("factor"),
                    (Double) sensorRawMap.get("lowerValueLimit"),
                    (Double) sensorRawMap.get("upperValueLimit"),
                    (String) sensorRawMap.get("measuringUnit"),
                    (Double) sensorRawMap.get("value")
            ));
        }
    }

    @JsonProperty("publisher")
    private void unpackPublisher(Map<String,Object> publisher) {
        this.publisher.put("broker", (String)publisher.get("broker"));
        this.publisher.put("clientId", (String)publisher.get("clientId"));
    }
}
