package tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithCallbackDemo {

    private static Logger logger = LoggerFactory.getLogger(ProducerWithCallbackDemo.class);

    public static void main(String[] args) {

        KafkaProducer<String, String> producer = new KafkaProducer<>(getProperties());

        for (int i = 0; i < 100; i++) {
            sendHello(producer, i);
        }


        producer.flush();
        producer.close();
    }

    private static void sendHello(KafkaProducer<String, String> producer, int i) {
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", String.format("id_%s", i), String.format("Nº %s", i));

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    logger.error("Ocorreu um erro", e);
                    return;
                }

                logger.info(String.format("Topic: %s, Partition: %s, Offset: %s, Timestamp: %s",
                        recordMetadata.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset(),
                        recordMetadata.timestamp()));


            }
        });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
