package com.goseeky.checkdo.repository;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.goseeky.checkdo.BusinessObjects.FolderBO;
import com.goseeky.checkdo.BusinessObjects.TaskBO;
import com.goseeky.checkdo.FolderActivity;
import com.goseeky.checkdo.dao.BaseDao;
import com.goseeky.checkdo.database.TaskRoomDatabase;

import java.util.List;

/**
 * Created by abhay on 28/4/18.
 */

public class DataRepository {

    private static DataRepository sInstance;

    private final TaskRoomDatabase mDatabase;
    public MediatorLiveData<List<TaskBO>> mObservableTasks;
    public MediatorLiveData<List<FolderBO>> mObservablefolders;
    private FolderBO rootFolderBO;
    private Context mContext;

    private DataRepository(final TaskRoomDatabase database,Context context) {
        mDatabase = database;
        mContext = context;
        mObservableTasks = new MediatorLiveData<>();
        mObservablefolders = new MediatorLiveData<>();

        mObservableTasks.addSource(mDatabase.baseDao().getAllTasks(),
                taskEntities -> {
                        mObservableTasks.postValue(taskEntities);
                });

        mObservablefolders.addSource(mDatabase.baseDao().getAllFolders(),
                folderEntities -> {
                    mObservablefolders.postValue(folderEntities);
                });
    }

    public static DataRepository getInstance(final TaskRoomDatabase database,Context context) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database,context);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<TaskBO>> getTasks() {
        return mObservableTasks;
    }

    public LiveData<List<FolderBO>> getFolders() {
        return mObservablefolders;
    }

    public LiveData<TaskBO> loadTask(final int taskid) {
        return mDatabase.baseDao().getTaskByIdAsync(taskid);
    }

    public LiveData<FolderBO> loadFolder(final int folderid) {
        return mDatabase.baseDao().getFolderByIdAsync(folderid);
    }


    public void insertTask (TaskBO taskBO) {
        new insertAsyncTask(mDatabase.baseDao()).execute(taskBO);
    }

    private static class insertAsyncTask extends AsyncTask<TaskBO, Void, Void> {

        private BaseDao mAsyncTaskDao;

        insertAsyncTask(BaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskBO... params) {
            mAsyncTaskDao.insertTask(params[0]);
            return null;
        }
    }

    public void insertFolder (FolderBO folderBO) {
        new insertAsyncFolder(mDatabase.baseDao()).execute(folderBO);
    }

    private static class insertAsyncFolder extends AsyncTask<FolderBO, Void, Void> {

        private BaseDao mAsyncTaskDao;

        insertAsyncFolder(BaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FolderBO... params) {
            mAsyncTaskDao.insertFolder(params[0]);
            return null;
        }
    }

    public void updateTask (TaskBO taskBO) {
        new updateAsyncTask(mDatabase.baseDao()).execute(taskBO);
    }

    private static class updateAsyncTask extends AsyncTask<TaskBO, Void, Void> {

        private BaseDao mAsyncTaskDao;

        updateAsyncTask(BaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskBO... params) {
            mAsyncTaskDao.updateTask(params[0]);
            return null;
        }
    }

    public void updateFolder (FolderBO folderBO) {
        new updateAsyncFolder(mDatabase.baseDao()).execute(folderBO);
    }

    private static class updateAsyncFolder extends AsyncTask<FolderBO, Void, Void> {

        private BaseDao mAsyncTaskDao;

        updateAsyncFolder(BaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FolderBO... params) {
            mAsyncTaskDao.updateFolder(params[0]);
            return null;
        }
    }

    public void deleteTask (TaskBO taskBO) {
        new deleteAsyncTask(mDatabase.baseDao()).execute(taskBO);
    }

    private static class deleteAsyncTask extends AsyncTask<TaskBO, Void, Void> {

        private BaseDao mAsyncTaskDao;

        deleteAsyncTask(BaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskBO... params) {
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }

    public void deleteFolderWithoutChildren (FolderBO folderBO) {
        new deleteAsyncFolder(mDatabase.baseDao()).execute(folderBO);
    }

    private static class deleteAsyncFolder extends AsyncTask<FolderBO, Void, Void> {

        private BaseDao mAsyncTaskDao;

        deleteAsyncFolder(BaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FolderBO... params) {
            mAsyncTaskDao.deleteFolder(params[0]);
            return null;
        }
    }

    private class getRootFolderAsync extends AsyncTask<Void, Void, FolderBO> {

        private BaseDao mAsyncTaskDao;
        private FolderActivity caller;

        getRootFolderAsync(FolderActivity folderActivity, BaseDao dao) {
            mAsyncTaskDao = dao;
            caller = folderActivity;
        }

        @Override
        protected FolderBO doInBackground(Void... voids) {
            if(mAsyncTaskDao.getRootFolder().size() == 0){
                FolderBO folderBO = new FolderBO();
                folderBO.setActive(true);
                folderBO.setFolderDesc("/");
                folderBO.setFolderParent(-1);
                try {
                    mAsyncTaskDao.insertFolder(folderBO);
                }catch (Exception e){
                    Log.e("create / folder",e.getStackTrace().toString());
                }
            }
            return mAsyncTaskDao.getRootFolder().get(0);
        }

        @Override
        protected void onPostExecute(FolderBO folderBO) {
            //do stuff
            caller.setRootFolderAndInitialize(folderBO);
            return;
        }

    }


    public void setRootFolder(FolderActivity folderActivity){
        new getRootFolderAsync(folderActivity,mDatabase.baseDao()).execute();
    }

    public void deleteFolder(FolderBO folderBO){
        for(TaskBO taskBO : mDatabase.baseDao().getTaskByParentId(folderBO.getFolderId())){
            deleteTask(taskBO);
        }
        for(FolderBO folderBO1 : mDatabase.baseDao().getFolderByParentId(folderBO.getFolderId())){
            deleteFolderWithoutChildren(folderBO1);
        }
        deleteFolderWithoutChildren(folderBO);
    }

    public List<FolderBO> getAllSubFolders(FolderBO folderBO){
        return mDatabase.baseDao().getFolderByParentId(folderBO.getFolderId());
    }

    public List<TaskBO> getAllSubTasks(TaskBO taskBO){
        return mDatabase.baseDao().getTaskByParentId(taskBO.getTaskId());
    }

    public void changeFolder(FolderBO folderBO){
        mObservableTasks = new MediatorLiveData<>();
        mObservablefolders = new MediatorLiveData<>();
        mObservableTasks.addSource(mDatabase.baseDao().getTaskByParentIdAsync(folderBO.getFolderId()),
                taskEntities -> {
                    mObservableTasks.postValue(taskEntities);
                });

        mObservablefolders.addSource(mDatabase.baseDao().getFolderByParentIdAsync(folderBO.getFolderId()),
                folderEntities -> {
                    mObservablefolders.postValue(folderEntities);
                });
    }
}