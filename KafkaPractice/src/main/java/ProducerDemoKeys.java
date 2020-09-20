import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {

    //https://kafka.apache.org/documentation/#producerconfigs
    // NOTE: Same key goes to same partition
    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:9092";
        final Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

        // Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //Key and value serialize: Help producer to know what type of values we are sending to Kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String topic = "first_topic";
        String initial_value = "Hello World: ";
        String initial_key = "Id_";

        for (int i =1; i <= 10; i++) {

            // create a producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, initial_key + i%3, initial_value + i);

            // send data - asynchronous
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        logger.info("Received new metadata. \n" +
                                "Topic:" + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp() + "\n" +
                                "Data: " + record.value());
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            }); //We can use .get() to make it Synchronous. block the .send() to make it synchronous - don't do this in production!
        }


        // flush data - Need to flush to send data to Kafka
        producer.flush();

        // flush and close producer
        producer.close();


    }
}
