package org.oss.classes;

import java.util.*;

public class Menu {
    public static void displayMenu() {
        System.out.println("Odaberite senzora ciji podatak zelite dohvatiti.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("1) Trenutna temperatura vode");
        System.out.println("2) Trenutni tlak vode");
        System.out.println("3) Prosjecna potrosnja u zadnjih 1 minutu");
        System.out.println("4) Prosjecna potrosnja u zadnjih 10 minuta");
        System.out.println("5) Prosjecna potrosnja u zadnjih 1 sat");
        System.out.println("6) Prosjecna potrosnja u zadnjih 1 dan");
        System.out.println("7) Prosjecna potrosnja u zadnjih 1 tjedan");
        System.out.println("8) Prosjecna potrosnja u zadnjih 1 mjesec");
        System.out.println("9) Prosjecna potrosnja u zadnjih 1 godinu");
        System.out.println("0) Quit");
    }

    public static Set<Integer> letterChoice() throws Exception{
        System.out.println("Unesite redni broj senzora cije podatke zelite ocitati, za izlaz unesite nulu");
        Integer sensorNumber = null;
        Set<Integer> optionsSet = new LinkedHashSet<Integer>();
        Scanner scan = new Scanner(System.in);
        do {
            try{
                sensorNumber = scan.nextInt();
                optionsSet.add(sensorNumber);
            }
            catch (Exception e){
                throw new Exception("Unos slova ili znakova nije dozvoljen" +
                        "");
            }

        } while (!sensorNumber.equals(0));
        return optionsSet;
    }
    private static Map<Integer, Sensor> sensorMap() {
        Map<Integer, Sensor> sensorMap = new HashMap<>();
        sensorMap.put(1, new Sensor("Senzor za temperaturu vode", "int", 10, -3266.8, +3266.8, "celzijev stupanj", 0.0));
        sensorMap.put(2, new Sensor("Senzor tlaka vode", "uint16", 1000, 0, 65.336, "Bar", 0.0));
        sensorMap.put(3, new Sensor("Potrosnja u zadnjoj minuti", "uint16", 0, 0, 65336, "litra", 0.0));
        sensorMap.put(4, new Sensor("Potrosnja u zadnjih 10 minuta", "uint16", 0, 0, 65336, "litra", 0.0));
        sensorMap.put(5, new Sensor("Potrosnja u zadnjem satu", "uint16", 0, 0, 65336, "litra", 0.0));
        sensorMap.put(6, new Sensor("Potrosnja u zadnjih 1 dan", "uint16", 0, 0, 65336, "Litra", 0.0));
        sensorMap.put(7, new Sensor("Potrosnja u zadnjem tjednu", "uint16", 10, 0, 6533.6, "m3", 0.0));
        sensorMap.put(8, new Sensor("Potrosnja u zadnjem mjescu", "uint16", 10, 0, 6533.6, "m3", 0.0));
        sensorMap.put(9, new Sensor("Potrosnja u zadnjoj godini", "uint16", 10, 0, 6533.6, "m3", 0.0));
        return sensorMap;
    }

    public static List<Sensor> sensorChoice(Set<Integer> optionsSet) throws Exception {
        var sensors = new ArrayList<Sensor>();
        for (var option : optionsSet) {
            if(option == 0){
                break;
            }
            if(sensorMap().get(option) != null){
                sensors.add(sensorMap().get(option));
            }
            else{
                throw new Exception("Unknown input " + option);
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
