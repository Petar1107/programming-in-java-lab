package org.oss.classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Sensor {
    private String sensorPurpose;
    private String dataType;
    private int factor;
    private double lowerValueLimit;
    private double upperValueLimit;
    private String measuringUnit;
    private double value;

    public void randomValue()
    {
        this.value = Math.round((Math.random() * (this.upperValueLimit - this.lowerValueLimit)) + this.lowerValueLimit);
    }
    public static String sensorToJsonConverter (Sensor sensor){
        ObjectMapper mapper = new ObjectMapper();
        var jsonString = "{}";
        try {
            jsonString = mapper.writeValueAsString(sensor);
            return jsonString;
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
