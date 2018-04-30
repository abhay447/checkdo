package com.goseeky.checkdo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.goseeky.checkdo.util.DateGenUtil;

import java.util.ArrayList;
import java.util.Date;

public class AddNewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    EditText taskDesc;
    EditText taskTime;
    SeekBar taskPriority;
    FloatingActionButton fabSaveTask,fabRecordTask;

    private Integer task_id;
    private Integer parent_id;
    String deadline;
    String description;
    int priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
        checkForUpdate();
    }

    private void checkForUpdate() {
        Intent intent = getIntent();
        if(intent.hasExtra("TASK_ID")){
            taskDesc.setText(intent.getStringExtra("TASK_DESC"));
            taskTime.setText(intent.getStringExtra("TASK_DDLINE"));
            taskPriority.setProgress(intent.getIntExtra("TASK_PRIORITY",1));
            task_id = intent.getIntExtra("TASK_ID",-1);
            parent_id = intent.getIntExtra("TASK_PARENT",0);
        }
    }

    private void initialize(){
        taskDesc = findViewById(R.id.etWhat);
        taskTime = findViewById(R.id.etWhen);
        taskPriority = findViewById(R.id.sbPriority);
        fabSaveTask = findViewById(R.id.fab);
        fabSaveTask.setOnClickListener(this);
        fabRecordTask = findViewById(R.id.fabTaskSpeak);
        fabRecordTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabTaskSpeak:
                recordAndPopulate();
                break;
            case R.id.fab:

                try{
                    populateDetails();
                    Intent intent = new Intent();
                    intent.putExtra("TaskDesc",description);
                    intent.putExtra("TaskDeadline","deadline");
                    intent.putExtra("TaskPriority",priority);
                    if(null != task_id){
                        intent.putExtra("TaskID", task_id);
                        intent.putExtra("TaskParent",parent_id);
                    }
                    setResult(RESULT_OK,intent);
                    finish();
                } catch (Exception e){
                    Snackbar.make(view, "Error in saving task", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
        }
    }

    private void recordAndPopulate() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech_not_supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i("matched" ,result.get(0));
                    taskDesc.setText(result.get(0));
                    populateDetails();
                }
                break;
            }

        }
    }

    private void populateDetails() {
        description = taskDesc.getText().toString();
        deadline = new DateGenUtil(description).getDateTimeString();
        taskTime.setText(deadline);
        priority = taskPriority.getProgress();
    }
}
