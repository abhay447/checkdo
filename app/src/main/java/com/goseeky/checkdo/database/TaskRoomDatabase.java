package com.goseeky.checkdo.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.goseeky.checkdo.BusinessObjects.FolderBO;
import com.goseeky.checkdo.BusinessObjects.TaskBO;
import com.goseeky.checkdo.dao.BaseDao;

/**
 * Created by abhay on 28/4/18.
 * Class extending roomDB for android
 */
@Database(entities = {FolderBO.class, TaskBO.class}, version = 1)
public abstract class TaskRoomDatabase extends RoomDatabase {
    public abstract BaseDao baseDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    private static TaskRoomDatabase instance = null;

    public static TaskRoomDatabase getInstance(final Context mContext){
        if(instance == null){
            synchronized (TaskRoomDatabase.class){
                if( instance == null){
                    instance = Room.databaseBuilder(mContext.getApplicationContext(),
                            TaskRoomDatabase.class,"task_database").build();
                }
            }
        }
        return instance;
    }
}
