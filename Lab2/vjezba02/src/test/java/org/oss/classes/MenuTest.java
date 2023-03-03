package org.oss.classes;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class MenuTest {
    @Test
    public void sensorChoiceTestUnknownChar(){
        Set<Integer> optionsSet = new LinkedHashSet<Integer>();
        optionsSet.add(100);
        Exception thrown = assertThrows(
                Exception.class,
                () -> Menu.sensorChoice(optionsSet)
        );

        assertTrue(thrown.getMessage().contains("Unknown input 100"));
    }
}
