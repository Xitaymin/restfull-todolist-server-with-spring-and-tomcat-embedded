package spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.TaskDAO;
import model.dao.impl.InMemoryTaskDAO;
import model.entity.Task;
import model.service.TaskService;
import model.service.impl.DefaultTaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ContextConfiguration {

    @Bean
    public TaskService defaultTaskService() {
        return new DefaultTaskService(inMemoryTaskDAO());
    }

    @Bean
    public TaskDAO inMemoryTaskDAO() {
        return new InMemoryTaskDAO(atomicInteger(), hashMap());
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
