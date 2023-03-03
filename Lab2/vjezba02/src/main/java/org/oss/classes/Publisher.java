package org.oss.classes;

import lombok.Getter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

@Getter
public class Publisher {
    private String broker;
    private String clientId;
    private MqttClient mqttClient;

    public Publisher(String clientId, String broker) {
        try {
            mqttClient = new MqttClient(broker, clientId);
        } catch (MqttException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String content, String topic) throws Exception {
        try {
            mqttClient.connect();
            var mqttMessage = new MqttMessage(content.getBytes());
            mqttClient.publish(topic, mqttMessage);
            mqttClient.disconnect();
        } catch (MqttException e) {
            throw new Exception("Failed to publish", e);
        } catch (Exception e) {
            throw new Exception("Exception in publish", e);
        }
    }


}
