package org.lecture3.classes;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MenuTest {
    @Test
    public void sensorChoiceTestUnknownChar(){
        HashMap<Character, Sensor> sensorMap = new HashMap<>();
        sensorMap.put('A',new Sensor());
        Set<Character> optionsSet = new LinkedHashSet<Character>();
        optionsSet.add('Ž');
        Exception thrown = assertThrows(
                Exception.class,
                () -> Menu.sensorChoice(optionsSet,sensorMap)
        );

        assertTrue(thrown.getMessage().contains("Unknown character Ž"));
    }

    @Test
    public void sensorChoiceTestSuccess(){
        HashMap<Character, Sensor> sensorMap = new HashMap<>();
        sensorMap.put('A',new Sensor());
        Set<Character> optionsSet = new LinkedHashSet<Character>();
        optionsSet.add('A');
        List<Sensor> sensors = new ArrayList<>();
        try{
            sensors = Menu.sensorChoice(optionsSet,sensorMap);
        }
        catch (Exception ignore){

        }
        assertEquals(1, sensors.size());
    }
}
