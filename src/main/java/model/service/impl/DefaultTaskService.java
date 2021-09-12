package model.service.impl;

import model.dao.TaskDAO;
import model.entity.Task;
import model.service.TaskService;

import java.util.Collection;

public class DefaultTaskService implements TaskService {

    private final TaskDAO taskDAO;

    public DefaultTaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public Collection<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    public Task addTaskOrUpdateIfExist(Task requested) {
        return taskDAO.addTaskOrUpdateIfExist(requested);
    }

    @Override
    public void deleteAll() {
        taskDAO.deleteAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        return taskDAO.deleteById(id);
    }
}
