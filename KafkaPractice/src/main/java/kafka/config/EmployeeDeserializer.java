package kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.model.Employee;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class EmployeeDeserializer implements Deserializer{
    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = null;
        try {
            employee = mapper.readValue(bytes, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }


    @Override
    public void close() {

    }
}
