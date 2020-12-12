package com.example.prefinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {

    Button insertButton;
    EditText todoEdit;

    private ArrayList<Todo> todoArrayList;
    private TodoAdapter todoAdapter;
    private String prefName = "DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        SharedPreferences data = getSharedPreferences(prefName, 0); // sharedprefernace 설정
        String userMail = data.getString("userMail", "NONE");
        String userExp = data.getString("userExp","-1");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todoArrayList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoArrayList);
        recyclerView.setAdapter(todoAdapter);

        insertButton = (Button) findViewById(R.id.button_insert_main);
        todoEdit = (EditText) findViewById(R.id.edit_todo_main);
        
        todoEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    Todo newTodo = new Todo(todoEdit.getText().toString());
                    todoArrayList.add(newTodo);
                    todoAdapter.notifyDataSetChanged();
                    todoEdit.setText(null);
                    return true;
                }
                return false;
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Todo newTodo = new Todo(todoEdit.getText().toString());
                todoArrayList.add(newTodo);
                todoAdapter.notifyDataSetChanged();
                todoEdit.setText(null);
            }
        });
    }

}

