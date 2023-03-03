package org.lecture3.classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


@Getter
@Setter
@NoArgsConstructor
public class Publisher {
    private MqttClient mqttClient;

    public Publisher(String clientId, String broker) throws Exception{
        try {
            mqttClient = new MqttClient(broker, clientId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Cannot connect to broker",e);
        }
    }

    public void publish(String content, String topic) throws Exception {
        try {
            mqttClient.connect();
            var mqttMessage = new MqttMessage(content.getBytes());
            mqttClient.publish(topic, mqttMessage);
            mqttClient.disconnect();
        } catch (Exception e) {
            throw new Exception("Failed to publish", e);
        }
    }
}
