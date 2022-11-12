import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerApp {
    public static void main(String[] args){
        Properties props = new Properties();
        props.put("bootstrap.servers","BROKER-1:9093, BROKER-2:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> myProducer = new KafkaProducer<>(props);

        try{
            for (int i = 0; i  < 100; i++){
                myProducer.send(new ProducerRecord<String, String>("my-topic",Integer.toString(i), "Or Hasson Message: " + Integer.toString(i)));
            }
        }
        catch( Exception e){
            e.printStackTrace();
        }
        finally{
            myProducer.close();
        }

    }
}
