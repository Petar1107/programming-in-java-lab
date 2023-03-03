package org.lecture3.classes;

import java.util.*;

import static java.lang.Character.toUpperCase;

public class Menu {
    private final HashMap<Character, Sensor> sensorMap;

    public Menu(HashMap<Character, Sensor> sensorMap){
        this.sensorMap=sensorMap;
    }
    public void displayMenu() {
        System.out.println("Odaberite slovo senzora ciji podatak zelite dohvatiti. Za izlaz odaberite slovo Q");
        System.out.println("---------------------------------------------------------------------------------");
        for  (var character : sensorMap.keySet()){
            System.out.println(character + ") " + sensorMap.get(character).getSensorPurpose());
        }
        System.out.println("Q) Quit");
    }

    public static Set<Character> letterChoice() {
        System.out.println("Unesite ponudeno slovo ili vise njih da ocitate podatke odredenog senzora");
        String options = null;
        Set<Character> optionsSet = new LinkedHashSet<Character>();
        Scanner scan = new Scanner(System.in);
        do {
            options = scan.nextLine();
            for (var c : options.toCharArray()) {
                optionsSet.add(toUpperCase(c));
            }
        } while (!options.toUpperCase().equals("Q"));
        //System.out.println(optionsSet);
        return optionsSet;
    }

    public static List<Sensor> sensorChoice(Set<Character> optionsSet, HashMap<Character, Sensor> sensorMap) throws Exception {
        var sensors = new ArrayList<Sensor>();
        for (var option : optionsSet) {
            if(option == 'Q'){
                break;
            }
            if(sensorMap.get(option) != null){
                sensors.add(sensorMap.get(option));
            }
            else{
                throw new Exception("Unknown character " + option);
            }
        }
        return sensors;
    }

    public static void publishMessage(Publisher publisher, List<Sensor> sensors) {
        for (var sensor : sensors) {
            sensor.randomValue();
            try {
                publisher.publish(Sensor.sensorToJsonConverter(sensor), sensor.getSensorPurpose());
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }
    public static void sensorOutput(List<Sensor> sensors) {
        for (var sensor : sensors) {
            sensor.randomValue();
            System.out.println(Sensor.sensorToJsonConverter(sensor));
        }
    }
}
