package spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.TaskDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToDoConfig {

    @Bean
    public TaskDAO taskDao() {
        return new TaskDAO();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
