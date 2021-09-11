package controller.servlet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.dao.TaskDAO;
import model.entity.Task;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "MainServlet", urlPatterns = {"/todo"})
public class MainServlet extends HttpServlet {
    //    private static final String QUERY_WITH_ID_PREFIX = "id=";
    private final TaskDAO taskDAO = new TaskDAO();
    private final ObjectMapper mapper =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Collection<Task> tasksList = taskDAO.getAllTasks();
            mapper.writeValue(resp.getOutputStream(), tasksList);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        //        sendResponse(resp.getWriter(), json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //        String json = getRequestBody(req.getReader());
        //        Task task = null;
        try {
            Task requested = mapper.readValue(req.getReader(), Task.class);
            Task result = taskDAO.addTaskOrUpdateIfExist(requested);
            //            sendResponse(resp.getWriter(), mapper
            //            .writeValueAsString(result));
            mapper.writeValue(resp.getOutputStream(), result);
        } catch (IllegalArgumentException | JsonParseException e) {
            //            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(resp, e.getMessage(),
                         HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //        String query = req.getQueryString();
        //        if (query == null) {
        //            taskDAO.deleteAll();
        //            resp.setStatus(HttpServletResponse.SC_OK);
        //        } else if (query.startsWith(QUERY_WITH_ID_PREFIX)) {
        //            Integer id = getIdFromQuery(query);
        //            if (taskDAO.deleteById(id)) {
        //                resp.setStatus(HttpServletResponse.SC_OK);
        //            } else {
        //                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //            }
        //        } else {
        //            resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
        //        }
        String id = req.getParameter("id");
        if (id == null) {
            taskDAO.deleteAll();
        } else if (taskDAO.deleteById(Integer.valueOf(id))) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            sendResponse(resp, "Unknown id", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    //    private String getRequestBody(BufferedReader reader) {
    //        return reader.lines().collect(Collectors.joining());
    //    }

    private void sendResponse(
            HttpServletResponse response, String body, int status
    ) throws IOException {
        response.setStatus(status);
        response.getWriter().print(body);
        //        out.print(body);
        //        out.flush();
        //        out.close();
    }

    //    private Integer getIdFromQuery(String query) {
    //        String id = query.substring(QUERY_WITH_ID_PREFIX.length());
    //        return Integer.valueOf(id);
    //    }
}
