package com.goseeky.checkdo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

public class AddFolderActivity extends AppCompatActivity {

    EditText etFolderName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etFolderName = findViewById(R.id.etFolderName);

        FloatingActionButton fab = findViewById(R.id.fabFolderSave);
        fab.setOnClickListener(view -> {
            if(etFolderName.getText().toString().length() < 1){
                Toast.makeText(getApplicationContext(),"Please enter a valid folder name",Toast.LENGTH_LONG).show();
            } else{
                Intent data = new Intent();
                data.putExtra("FolderName",etFolderName.getText().toString());
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

}
