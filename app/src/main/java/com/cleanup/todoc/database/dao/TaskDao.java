package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;

import java.util.List;

@Dao
public interface TaskDao {
    @Transaction
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<TaskWithProject>> getTaskByProject(long projectId);

    @Query("SELECT * FROM Task")
    LiveData<List<TaskWithProject>> getAllTasks();

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);
}