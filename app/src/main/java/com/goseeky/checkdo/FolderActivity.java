package com.goseeky.checkdo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.goseeky.checkdo.BusinessObjects.FolderBO;
import com.goseeky.checkdo.BusinessObjects.TaskBO;
import com.goseeky.checkdo.viewmodel.TaskViewModel;

public class FolderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int REQ_ADD_OR_EDIT_TASK = 101;
    private final int REQ_ADD_FOLDER = 100;

    FloatingActionButton fabCreateTask,fabCreateFolder;
    GridAdapter gridAdapter;
    TaskViewModel taskViewModel;
    FolderBO currentFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        initialize();
    }

    private  void initialize(){
        taskViewModel.getTaskRepository().setRootFolder(this);
    }

    public void setRootFolderAndInitialize(FolderBO folderBO){
        currentFolder = folderBO;
        initializeUIActions();
    }

    private void initializeUIActions(){
        gridAdapter = new GridAdapter(this);
        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(gridAdapter);
        gridview.setOnItemClickListener(this);
        fabCreateTask = findViewById(R.id.fabFolder);
        fabCreateTask.setOnClickListener(this);
        fabCreateFolder = findViewById(R.id.fabTask);
        fabCreateFolder.setOnClickListener(this);
        changeFolder(currentFolder);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i < gridAdapter.getNumFolders()) {
            FolderBO folderBO = gridAdapter.getFolderBOAtPos(i);
            changeFolder(folderBO);
        }else{
            TaskBO taskBO = gridAdapter.getTaskBOAtPos(i-gridAdapter.getNumFolders());
            editTask(taskBO);
        }
    }

    private void editTask(TaskBO taskBO) {
        Intent intent = new Intent(this,AddNewTaskActivity.class);
        intent.putExtra("TASK_ID",taskBO.getTaskId());
        intent.putExtra("TASK_DESC",taskBO.getTaskDesc());
        intent.putExtra("TASK_DDLINE",taskBO.getTaskDeadline());
        intent.putExtra("TASK_PRIORITY",taskBO.getTaskPriority());
        intent.putExtra("TASK_PARENT",taskBO.getTaskParent());
        startActivityForResult(intent, REQ_ADD_OR_EDIT_TASK);
    }

    public void changeFolder(FolderBO folderBO) {
        taskViewModel.getTaskRepository().changeFolder(folderBO);
        taskViewModel.getTaskRepository().mObservableTasks.observe(this, taskBOS -> gridAdapter.updateSubTasks(taskBOS));

        taskViewModel.getTaskRepository().mObservablefolders.observe(this, folderBOS -> gridAdapter.updateSubFolders(folderBOS));

        gridAdapter.reloadData();
        currentFolder = folderBO;
        setTitle(folderBO.getFolderDesc());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fabFolder:
                Intent addFolderIntent = new Intent(this,AddFolderActivity.class);
                startActivityForResult(addFolderIntent,REQ_ADD_FOLDER);
                break;

            case R.id.fabTask:
                Intent intent = new Intent(this,AddNewTaskActivity.class);
                startActivityForResult(intent, REQ_ADD_OR_EDIT_TASK);
                Log.i("id",String.valueOf(view.getId()));
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ADD_FOLDER:
                if (resultCode == RESULT_OK && null != data && data.hasExtra("FolderName")) {
                    FolderBO folderBO = new FolderBO();
                    folderBO.setFolderParent(currentFolder.getFolderId());
                    folderBO.setFolderDesc(currentFolder.getFolderDesc()
                            + "/"
                            + data.getExtras().getString("FolderName"));
                    Log.i("Folder",folderBO.toString());
                    taskViewModel.getTaskRepository().insertFolder(folderBO);
                }
                break;

            case REQ_ADD_OR_EDIT_TASK:
                if (resultCode == RESULT_OK && null != data && data.hasExtra("TaskDesc")) {
                    TaskBO taskBO = new TaskBO();
                    taskBO.setTaskParent(currentFolder.getFolderId());
                    taskBO.setTaskDesc(data.getExtras().getString("TaskDesc"));
                    taskBO.setTaskDeadline(data.getExtras().getString("TaskDeadline"));
                    taskBO.setTaskPriority(data.getExtras().getInt("TaskPriority"));
                    if(data.hasExtra("TaskID")){ //check if it is an update call
                        taskBO.setTaskId(data.getIntExtra("TaskID",1));
                        taskBO.setTaskParent(data.getIntExtra("TaskParent",0));
                        taskViewModel.getTaskRepository().updateTask(taskBO);
                    } else {
                        taskViewModel.getTaskRepository().insertTask(taskBO);
                    }
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if(currentFolder.getFolderDesc().equals("/")) {
            super.onBackPressed();
        }else{
            taskViewModel.getTaskRepository().loadParentFolder(this);
        }
    }

    public FolderBO getCurrentFolder() {
        return currentFolder;
    }
}



