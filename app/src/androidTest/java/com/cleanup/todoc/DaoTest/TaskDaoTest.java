package com.cleanup.todoc.DaoTest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.content.Context;
import android.graphics.Color;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.SaveDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private SaveDatabase database;
    private static long TASK_ID = 1;
    private static long PROJECT_ID = 1;
    private static Task taskTest1 = new Task(PROJECT_ID,"first task",1615554360);
    private static Task taskTest2 = new Task(PROJECT_ID,"second task",1615554360);
    private static Project projecTest = new Project(PROJECT_ID,"Projet Tartampion",Color.argb(255, 0,255,0) );

    // Force run test sync
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    //Create single instance in database
    @Before
    public void initDb() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        this.database = Room.inMemoryDatabaseBuilder(context,
                SaveDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertGetTask() throws InterruptedException {
        //insert task
        this.database.projectDao().createProject(projecTest);
        this.database.taskDao().insertTask(taskTest1);

        //Get task and test it
        List<TaskWithProject> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        TaskWithProject fisrtTask = tasks.get(0);
        assertTrue(fisrtTask.getTask().getName().equals(taskTest1.getName()));
    }


    @Test
    public void deleteTask() throws InterruptedException {
        //insert task
        this.database.projectDao().createProject(projecTest);
        this.database.taskDao().insertTask(taskTest2);
        //get task to delete and delete it
        List<TaskWithProject> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        int tasksSize  = tasks.size();
        TaskWithProject secondTAsk = tasks.get(tasks.size()-1);
        this.database.taskDao().deleteTask(secondTAsk.getTask().getId());

      //test if deleted
        List<TaskWithProject> newTasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertEquals(tasksSize-1,newTasks.size());
    }

}
