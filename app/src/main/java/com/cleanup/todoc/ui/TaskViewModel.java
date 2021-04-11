package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    //Repo
    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    public TaskViewModel(ProjectDataRepository projectDataRepository,TaskDataRepository taskDataRepository , Executor executor){
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;
    }

    public LiveData<List<TaskWithProject>> getAllTask(){
        return taskDataRepository.getAllTasks();
    }

    public void createTask(Task task){
        executor.execute(() -> {
            taskDataRepository.insertTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(() -> {
            taskDataRepository.deleteTask(taskId);
        });
    }

    public void updateTask(Task task){
        executor.execute(() -> {
            taskDataRepository.updateTask(task);
        });
    }

    public LiveData<List<Project>> getAllProject(){
            return  projectDataRepository.getAllProjects();
    }


}
