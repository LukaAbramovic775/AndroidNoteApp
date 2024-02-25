package com.example.project;

import android.widget.AdapterView;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private Button addButton;
    private ListView taskListView;
    public static TaskAdapter adapter;
    public static ArrayList<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        addButton = findViewById(R.id.addButton);
        taskListView = findViewById(R.id.taskListView);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                taskListView.setItemChecked(position, !taskListView.isItemChecked(position));
                return true;
            }
        });
    }

    private void addTask() {
        String task = taskEditText.getText().toString();
        if (!task.isEmpty()) {
            taskList.add(task);
            adapter.updateData(taskList);
            taskEditText.setText("");
        }
    }

    // Make removeTask method static
    public static void removeTask(int position) {
        taskList.remove(position);
        adapter.updateData(taskList);
    }

    // Custom Adapter for displaying "Edit" and "Delete" buttons for each task
    public static class TaskAdapter extends BaseAdapter {

        private AppCompatActivity context;
        private ArrayList<String> tasks;

        public TaskAdapter(AppCompatActivity context, ArrayList<String> tasks) {
            this.context = context;
            this.tasks = tasks;
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public Object getItem(int position) {
            return tasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            }

            TextView taskTextView = convertView.findViewById(R.id.taskTextView);
            taskTextView.setText(tasks.get(position));

            Button deleteButton = convertView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call removeTask using the MainActivity reference
                    MainActivity.removeTask(position);
                }
            });

            Button editButton = convertView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTask(position);
                }
            });

            return convertView;
        }

        public void updateData(ArrayList<String> tasks) {
            this.tasks = tasks;
            notifyDataSetChanged();
        }

        private void editTask(int position) {
            Intent intent = new Intent(context, EditTaskActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("taskDescription", tasks.get(position));
            context.startActivity(intent);
        }
    }
}

