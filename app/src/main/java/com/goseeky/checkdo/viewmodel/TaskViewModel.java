package com.goseeky.checkdo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.goseeky.checkdo.database.TaskRoomDatabase;
import com.goseeky.checkdo.repository.DataRepository;

/**
 * Created by abhay on 28/4/18.
 */

public class TaskViewModel extends AndroidViewModel {

    private DataRepository mtaskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mtaskRepository = DataRepository.getInstance(TaskRoomDatabase.getInstance(application),application);
    }

    public DataRepository getTaskRepository() {
        return mtaskRepository;
    }
}
