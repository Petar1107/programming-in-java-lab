package org.oss.classes;

import org.junit.Test;
import org.oss.classes.Publisher;

import java.lang.Exception;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class PublisherTest {
    @Test
    public void publishFailWhenContentNullTest() {
        var clientId = "Petar";
        var broker = "tcp://localhost:1883";
        var p = new Publisher(clientId,broker);

        Exception thrown = assertThrows(
                Exception.class,
                () -> p.publish(null,"")
        );

        assertTrue(thrown.getMessage().contains("Exception in publish"));
    }
    @Test
    public void publishFailWhenTopicNullTest() {
        var clientId = "Petar";
        var broker = "tcp://localhost:1883";
        var p = new Publisher(clientId,broker);

        Exception thrown = assertThrows(
                Exception.class,
                () -> p.publish("",null)
        );

        assertTrue(thrown.getMessage().contains("Exception in publish"));
    }

    @Test
    public void FailToConnectWithBroker() {
        var clientId = "Petar";
        var broker = "tcp://dummyAddress:1883";
        var p = new Publisher(clientId,broker);

        Exception thrown = assertThrows(
                Exception.class,
                () -> p.publish("","")
        );

        assertTrue(thrown.getMessage().contains("Failed to publish"));
    }
}
