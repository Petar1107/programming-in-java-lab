package org.lecture3;

import org.lecture3.classes.*;
import org.lecture3.dto.JsonConfig;
import org.lecture3.utils.JsonFileReader;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String CONFIG_FILE_PATH = "/JsonConfig.json";

    public static void main(String[] args) {
        var jsonConfig = new JsonConfig();
        try{
            jsonConfig = JsonFileReader.parseJsonConfig(CONFIG_FILE_PATH);
        }
        catch (Exception ex){
            System.exit(1);
        }

        Publisher publisher = new Publisher();
        try{
            publisher = new Publisher(jsonConfig.getPublisher().get("clientId"),jsonConfig.getPublisher().get("broker"));
        }
        catch (Exception e){
            System.exit(1);
        }

        var waterFlowMeter = new WaterFlowMeter(jsonConfig.getSensors());
        var menu = new Menu(jsonConfig.getSensors());
        List<Sensor> sensors = new ArrayList<>();
        do {
            try {
                menu.displayMenu();
                sensors = waterFlowMeter.getSensors();
            }
            catch (Exception e){
                System.out.println("Try again");
            }
        } while (sensors.size() == 0);
        Menu.publishMessage(publisher, sensors);
        Menu.sensorOutput(sensors);
    }
}
