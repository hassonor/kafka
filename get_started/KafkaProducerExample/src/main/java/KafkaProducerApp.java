import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerApp {
    public static void main(String[] args){
        Properties props = new Properties();
        props.put("bootstrap.servers","BROKER-1:9093, BROKER-2:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer myProducer = new KafkaProducer(props);
        ProducerRecord myRecord = new ProducerRecord("my_topic","Hello-001","My name is Or Hasson Message 1");
        myProducer.send(myRecord); // Best practice -> try...catch
    }
}
