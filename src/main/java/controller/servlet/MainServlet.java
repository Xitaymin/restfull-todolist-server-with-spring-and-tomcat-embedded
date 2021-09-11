package controller.servlet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.dao.TaskDAO;
import model.entity.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ContextConfiguration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "MainServlet", urlPatterns = {"/todo"})
public class MainServlet extends HttpServlet {
    private final ApplicationContext context =
            new AnnotationConfigApplicationContext(ContextConfiguration.class);
    private final TaskDAO taskDAO = context.getBean(TaskDAO.class);
    private final ObjectMapper mapper =
            context.getBean(ObjectMapper.class).enable(SerializationFeature.INDENT_OUTPUT).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Collection<Task> tasksList = taskDAO.getAllTasks();
            mapper.writeValue(resp.getOutputStream(), tasksList);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Task requested = mapper.readValue(req.getReader(), Task.class);
            Task result = taskDAO.addTaskOrUpdateIfExist(requested);
            mapper.writeValue(resp.getOutputStream(), result);
        } catch (IllegalArgumentException | JsonParseException e) {
            sendResponse(resp, e.getMessage(),
                         HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            taskDAO.deleteAll();
        } else if (taskDAO.deleteById(Integer.valueOf(id))) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            sendResponse(resp, "Unknown id " + id,
                         HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void sendResponse(
            HttpServletResponse response, String body, int status
    ) throws IOException {
        response.setStatus(status);
        response.getWriter().print(body);
    }
}
