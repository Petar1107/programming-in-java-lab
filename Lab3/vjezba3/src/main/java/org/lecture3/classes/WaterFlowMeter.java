package org.lecture3.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaterFlowMeter {
    private final HashMap<Character,Sensor> sensorMap;
    public WaterFlowMeter(HashMap<Character,Sensor> sensorMap) {
        this.sensorMap = sensorMap;
    }

    public List<Sensor> getSensors() {
        List<Sensor> sensors = new ArrayList<>();
        try{
            var letterChoice = Menu.letterChoice();
            sensors = Menu.sensorChoice(letterChoice, sensorMap);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return sensors;
    }

}

