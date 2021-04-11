package com.cleanup.todoc.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveDatabase extends RoomDatabase {

    //--- Singleton
    private static volatile SaveDatabase INSTANCE;

    //--- Dao
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    //--- Instance
    public static SaveDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("id", 1);
                contentValues1.put("name", "Projet Tartampion");
                contentValues1.put("color", Color.rgb(168,137,50));


                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("id", 2);
                contentValues2.put("name", "Projet Lucidia");
                contentValues2.put("color", Color.rgb(50,168,113));


                ContentValues contentValues3 = new ContentValues();
                contentValues3.put("id", 3);
                contentValues3.put("name", "Projet Circus");
                contentValues3.put("color", Color.rgb(56,50,168));

                db.insert("Project", OnConflictStrategy.ABORT, contentValues1);
                db.insert("Project", OnConflictStrategy.ABORT, contentValues2);
                db.insert("Project", OnConflictStrategy.ABORT, contentValues3);
            }
        };
    }

}
