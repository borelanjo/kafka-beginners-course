package tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class ConsumerAssignSeekDemo {

    private static Logger logger = LoggerFactory.getLogger(ConsumerAssignSeekDemo.class);

    public static void main(String[] args) {

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getProperties());

        TopicPartition partitionToRead = new TopicPartition("first_topic", 0);
        consumer.assign(Arrays.asList(partitionToRead));
        long offsetToReadFrom = 15L;

        consumer.seek(partitionToRead, offsetToReadFrom);

        int numberOfMessageToRead = 5;
        int numberOfMessageReadSoFar = 0;
        boolean keepOnReading = true;

        while (keepOnReading) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                numberOfMessageReadSoFar++;
                logger.info(String.format("%s - Key: %s | Value: %s", record.topic(), record.key(), record.value()));
                logger.info(String.format("%s - Partition: %s | Offset: %s", record.topic(), record.partition(), record.offset()));
                if (numberOfMessageToRead == numberOfMessageReadSoFar) {
                    keepOnReading = false;
                    break;
                }
            }
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
}
