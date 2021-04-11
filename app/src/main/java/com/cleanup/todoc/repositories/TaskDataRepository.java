package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;

import java.util.List;

public class TaskDataRepository {
    private TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao=taskDao ;
    }

    public LiveData<List<TaskWithProject>> getTaskByProject(long projectId){
        return this.taskDao.getTaskByProject(projectId);
    }

    public LiveData<List<TaskWithProject>> getAllTasks(){
        return taskDao.getAllTasks();
    }

    public void insertTask(Task task){
        this.taskDao.insertTask(task);
    }

    public void updateTask(Task task){
        this.taskDao.updateTask(task);
    }

    public void deleteTask(long taskId){
        this.taskDao.deleteTask(taskId);
    }

}
