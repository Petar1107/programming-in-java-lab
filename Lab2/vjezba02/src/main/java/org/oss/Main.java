package org.oss;


import org.oss.classes.Menu;
import org.oss.classes.Publisher;
import org.oss.classes.Sensor;
import org.oss.classes.WaterFlowMeter;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        var clientId = "Petar";
        var broker = "tcp://localhost:1883";
        var publisher = new Publisher(clientId, broker);
        List<Sensor> sensors = new ArrayList<>();
        do {
            try {
                Menu.displayMenu();
                sensors = new WaterFlowMeter().getSensors();
            }
            catch (Exception e){
                System.out.println("Try again");
            }
        } while (sensors.size() == 0);
        Menu.publishMessage(publisher, sensors);
        Menu.sensorOutput(sensors);
    }
}
