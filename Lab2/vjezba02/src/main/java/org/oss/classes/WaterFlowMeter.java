package org.oss.classes;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class WaterFlowMeter {
    private List<Sensor> sensors;

    public WaterFlowMeter() throws Exception{
        try{
            sensors = Menu.sensorChoice(Menu.letterChoice());
        }
        catch (Exception e){
            sensors = new ArrayList<>();
            System.out.println(e.getMessage());
        }
    }

}
