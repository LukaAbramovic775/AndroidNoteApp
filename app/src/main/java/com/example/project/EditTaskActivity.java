package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTaskEditText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        editTaskEditText = findViewById(R.id.editTaskEditText);
        Button editButton = findViewById(R.id.editButton);
        Button backButton = findViewById(R.id.backToListButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("position") && intent.hasExtra("taskDescription")) {
            position = intent.getIntExtra("position", -1);
            String taskDescription = intent.getStringExtra("taskDescription");
            editTaskEditText.setText(taskDescription);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditedTask();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveEditedTask() {
        String editedTask = editTaskEditText.getText().toString();
        if (position != -1 && !editedTask.isEmpty()) {
            MainActivity.taskList.set(position, editedTask);
            MainActivity.adapter.updateData(MainActivity.taskList);
            finish();
        }
    }
}
