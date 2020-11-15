import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class Publisher {

    public static void main(String[] args) {
        String topic        = "Temperatura";
        int qos             = 2;
        String broker       = "tcp://mqtt.eclipse.org:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Random r = new Random();
                    int temp =  r.nextInt((45 - 15) + 1) + 15;
                    System.out.println("Temperatura: "+ temp);
                    MqttMessage message = new MqttMessage(String.valueOf(temp).getBytes());
                    message.setQos(qos);
                    try {
                        sampleClient.publish(topic, message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Message published");
                }
            }, 0, 1000);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}