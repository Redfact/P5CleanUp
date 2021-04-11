package com.cleanup.todoc.DaoTest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import android.content.Context;
import android.graphics.Color;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.SaveDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectDaoTest {
    private SaveDatabase database;
    private static long PROJECT_ID = 1;
    private static Project projectTest = new Project(PROJECT_ID,"Projet Tartampion", Color.argb(255, 0,255,0) );

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
    public void createGetProject() throws InterruptedException {
        //create Project
        this.database.projectDao().createProject(projectTest);

        //Get project
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));

        assertTrue(project.getName().equals(projectTest.getName()));
        assertEquals(project.getId(),projectTest.getId());
    }

}
