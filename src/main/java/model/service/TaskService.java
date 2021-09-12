package model.service;

import model.entity.Task;

import java.util.Collection;

public interface TaskService {
    Collection<Task> getAllTasks();

    Task addTaskOrUpdateIfExist(Task requested);

    void deleteAll();

    boolean deleteById(Integer valueOf);
}
