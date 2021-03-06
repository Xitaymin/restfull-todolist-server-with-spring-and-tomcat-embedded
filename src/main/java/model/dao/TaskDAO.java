package model.dao;

import model.entity.Task;

import java.util.Collection;

public interface TaskDAO {
    Collection<Task> getAllTasks();

    Task addTaskOrUpdateIfExist(Task requested);

    void deleteAll();

    boolean deleteById(Integer id);
}
