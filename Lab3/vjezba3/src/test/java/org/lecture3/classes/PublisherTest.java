package org.lecture3.classes;

import org.junit.Test;

import java.lang.Exception;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class PublisherTest {
    @Test
    public void publishInitFail() {
        var clientId = "Petar";
        var broker = "dummyBroker";

        Exception thrown = assertThrows(
                Exception.class,
                () -> new Publisher(clientId,broker)
        );

        assertTrue(thrown.getMessage().contains("Cannot connect to broker"));
    }

    @Test
    public void publishFailWhenContentNullTest() {
        var clientId = "Petar";
        var broker = "tcp://localhost:1883";

        try {
            var p = new Publisher(clientId,broker);

            Exception thrown = assertThrows(
                    Exception.class,
                    () -> p.publish(null,"")
            );

            assertTrue(thrown.getMessage().contains("Failed to publish"));
        } catch (Exception ignore) {}
    }

    @Test
    public void publishFailWhenTopicNullTest() {
        var clientId = "Petar";
        var broker = "tcp://localhost:1883";
        try {
            var p = new Publisher(clientId,broker);

            Exception thrown = assertThrows(
                    Exception.class,
                    () -> p.publish("",null)
            );

            assertTrue(thrown.getMessage().contains("Failed to publish"));
        } catch (Exception ignore) {}
    }
}
