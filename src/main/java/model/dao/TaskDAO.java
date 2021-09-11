package model.dao;

import model.entity.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDAO {
    private final AtomicInteger autoID = new AtomicInteger(1);
    private final Map<Integer, Task> taskMap = new HashMap<>();

    public Collection<Task> getAllTasks() {
        return taskMap.values();
    }

    public boolean deleteById(Integer key) {
        return taskMap.remove(key) != null;
    }

    public void deleteAll() {
        taskMap.clear();
    }

    public Task addTaskOrUpdateIfExist(Task task) {
        //        Task task = mapper.readValue(json, Task.class);
        if (task.getId() == null) {
            task.setId(autoID.getAndIncrement());
            taskMap.put(task.getId(), task);
        } else {
            Integer id = task.getId();
            if (taskMap.containsKey(id)) {
                taskMap.put(id, task);
            } else {
                throw new IllegalArgumentException("Unknown id" + task.getId());
            }
        }
        return task;
    }
}
