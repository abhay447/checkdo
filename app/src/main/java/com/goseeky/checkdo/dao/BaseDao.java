package com.goseeky.checkdo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.goseeky.checkdo.BusinessObjects.FolderBO;
import com.goseeky.checkdo.BusinessObjects.TaskBO;

import java.util.List;

/**
 * Created by abhay on 28/4/18.
 */
@Dao
public interface BaseDao {

    @Insert
    void insertTask(TaskBO taskBO);

    @Insert
    void insertFolder(FolderBO folderBO);

    @Update
    void updateTask(TaskBO taskBO);

    @Update
    void updateFolder(FolderBO folderBO);

    @Delete
    void deleteTask(TaskBO taskBO);

    @Delete
    void deleteFolder(FolderBO folderBO);

    @Query("DELETE FROM FOLDERS WHERE PARENT_ID = :PARENT_ID")
    void deleteAllFolderInFolder(Integer PARENT_ID);

    @Query("DELETE FROM TASKS WHERE PARENT_ID = :PARENT_ID")
    void deleteAllTasksInFolder(Integer PARENT_ID);

    @Query("SELECT * FROM TASKS WHERE TASK_ID = :TASK_ID")
    List<TaskBO>  getTaskById(Integer TASK_ID);

    @Query("SELECT * FROM FOLDERS WHERE FOLDER_ID = :FOLDER_ID")
    List<FolderBO> getFolderById(Integer FOLDER_ID);

    @Query("SELECT * FROM TASKS WHERE TASK_ID = :TASK_ID")
    LiveData<TaskBO> getTaskByIdAsync(Integer TASK_ID);

    @Query("SELECT * FROM FOLDERS WHERE FOLDER_ID = :FOLDER_ID")
    LiveData<FolderBO>  getFolderByIdAsync(Integer FOLDER_ID);

    @Query("SELECT * FROM TASKS WHERE PARENT_ID = :PARENT_ID")
    List<TaskBO> getTaskByParentId(Integer PARENT_ID);

    @Query("SELECT * FROM FOLDERS WHERE PARENT_ID = :PARENT_ID")
    List<FolderBO> getFolderByParentId(Integer PARENT_ID);

    @Query("SELECT * FROM TASKS WHERE PARENT_ID = :PARENT_ID")
    LiveData<List<TaskBO>> getTaskByParentIdAsync(Integer PARENT_ID);

    @Query("SELECT * FROM FOLDERS WHERE PARENT_ID = :PARENT_ID")
    LiveData<List<FolderBO>> getFolderByParentIdAsync(Integer PARENT_ID);


    @Query("SELECT * FROM FOLDERS WHERE FOLDER_DESC = '/' ")
    List<FolderBO> getRootFolder();

    @Query("SELECT * FROM TASKS")
    LiveData<List<TaskBO>>  getAllTasks();

    @Query("SELECT * FROM FOLDERS")
    LiveData<List<FolderBO>> getAllFolders();
}
