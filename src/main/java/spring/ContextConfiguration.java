package spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.TaskDAO;
import model.dao.impl.SimpleTaskDAO;
import model.entity.Task;
import model.service.TaskService;
import model.service.impl.SimpleTaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ContextConfiguration {

    @Bean
    public TaskService simpleTaskService() {
        return new SimpleTaskService(simpleTaskDao());
    }

    @Bean
    public TaskDAO simpleTaskDao() {
        return new SimpleTaskDAO(atomicInteger(), hashMap());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Map<Integer, Task> hashMap() {
        return new HashMap<>();
    }

    @Bean
    public AtomicInteger atomicInteger() {
        return new AtomicInteger(1);
    }

}
