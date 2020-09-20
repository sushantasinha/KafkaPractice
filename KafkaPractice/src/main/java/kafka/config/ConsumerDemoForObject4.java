package kafka.config;

import kafka.model.Employee;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemoForObject4 {

    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ConsumerDemoForObject4.class);
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-nine-group";
        String topic = "first_topic";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EmployeeDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Bit confusion! but AUTO_OFFSET_RESET_CONFIG + earliest is eq. to "from beginning"

        KafkaConsumer<String, Employee> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));

        int i =0;
        while (i < 10) {
            i++;
            ConsumerRecords<String, Employee> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, Employee> record : records) {
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }

        consumer.close();
    }
}
