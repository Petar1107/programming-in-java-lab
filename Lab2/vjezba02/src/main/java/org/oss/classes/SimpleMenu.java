package org.oss.classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;

public class SimpleMenu extends JFrame implements ActionListener  {

    HashMap<Character,JCheckBox> sensors;
    Publisher publisher;
    JScrollPane scrollpane;

    private static Map<Character, Sensor> sensorMap() {
        Map<Character, Sensor> sensorMap = new HashMap<>();
        sensorMap.put('A', new Sensor("Senzor za temperaturu vode", "int", 10, -3266.8, +3266.8, "celzijev stupanj", 0.0));
        sensorMap.put('B', new Sensor("Senzor tlaka vode", "uint16", 1000, 0, 65.336, "Bar", 0.0));
        sensorMap.put('C', new Sensor("Potrosnja u zadnjoj minuti", "uint16", 0, 0, 65336, "litra", 0.0));
        sensorMap.put('D', new Sensor("Potrosnja u zadnjih 10 minuta", "uint16", 0, 0, 65336, "litra", 0.0));
        sensorMap.put('E', new Sensor("Potrosnja u zadnjem satu", "uint16", 0, 0, 65336, "litra", 0.0));
        sensorMap.put('F', new Sensor("Potrosnja u zadnjih 1 dan", "uint16", 0, 0, 65336, "Litra", 0.0));
        sensorMap.put('G', new Sensor("Potrosnja u zadnjem tjednu", "uint16", 10, 0, 6533.6, "m3", 0.0));
        sensorMap.put('H', new Sensor("Potrosnja u zadnjem mjescu", "uint16", 10, 0, 6533.6, "m3", 0.0));
        sensorMap.put('I', new Sensor("Potrosnja u zadnjoj godini", "uint16", 10, 0, 6533.6, "m3", 0.0));
        return sensorMap;
    }
    private void popuniSenzore(){
        sensors = new HashMap<>();
        for (var key : sensorMap().keySet()){
            var sensor = sensorMap().get(key).getSensorPurpose();
            sensors.put(key,new JCheckBox(sensor));
        }
    }
    private void addSensorsToPanel(JPanel p){
        popuniSenzore();
        for(var sensor : sensors.values()){
            p.add(sensor);
        }
    }
    public JMenuBar createMenuBar() {

        var menuBar = new JMenuBar();
        var menuItem = new JMenuItem("Close",KeyEvent.VK_C);
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.addActionListener(this);
        menuBar.add(menuItem);

        return menuBar;
    }
    public SimpleMenu(String clientID, String broker){
        super("Izbornik senzora");
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addSensorsToPanel(panel);
        var botun = new JButton("submit");
        botun.addActionListener(this);
        panel.add(botun);
        setSize(600,460 );
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        scrollpane = new JScrollPane(panel);
        getContentPane().add(scrollpane, BorderLayout.CENTER);
        publisher = new Publisher(clientID,broker);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        for (var key : sensors.keySet()){
            var sensor = sensors.get(key);
            if(sensor.isSelected()){
                try {
                    var sensorDetail = sensorMap().get(key);
                    sensorDetail.randomValue();
                    System.out.println(Sensor.sensorToJsonConverter(sensorDetail));
                    publisher.publish(Sensor.sensorToJsonConverter(sensorDetail),sensorDetail.getSensorPurpose());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.exit(0);
    }
    public static void main (String[] args){
        var clientID = "Petar";
        var broker = "tcp://localhost:1883";
        SimpleMenu simpleMenu = new SimpleMenu(clientID,broker);
        simpleMenu.setVisible(true);
    }
}

