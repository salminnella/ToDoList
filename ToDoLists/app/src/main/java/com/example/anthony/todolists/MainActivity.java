package com.example.anthony.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MAIN = "MainActivity";
    ListView mListView;
    ArrayAdapter mAdapter;
    FloatingActionButton fab;
    EditText inputText;
    TextView emptyListMessage;
    //TextView toDoListHeader;

    ArrayList<String> mTodoList;
    ArrayList<String> mListItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        SetOnItemClickListener();
        SetOnItemLongClickListener();

    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.main_act_fab);
        mListView = (ListView) findViewById(R.id.main_act_listview);
        inputText = (EditText) findViewById(R.id.main_act_edit_text);
        emptyListMessage = (TextView) findViewById(R.id.main_act_empty_list_notes);
        //toDoListHeader = (TextView) findViewById(R.id.main_act_title);

        //instantiate array list
        mTodoList = new ArrayList<>(); // maybe set this to 40 or so to save mem on it doubling
        //TODO: doesn't look like i need this one
        //mListItems = new ArrayList<>();

        //instantiate adapter
        mAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mTodoList);

        //get the listview and set the adapter
        mListView.setAdapter(mAdapter);

        setOnButtonClickListeners();
    }

    private void setOnButtonClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyListMessage.setVisibility(View.INVISIBLE);
                //toDoListHeader.setVisibility(View.VISIBLE);

                String newTodo = inputText.getText().toString();
                mAdapter.add(newTodo);
                mAdapter.notifyDataSetChanged();

                inputText.setText("");
            }
        });
    }

    private void SetOnItemClickListener() {
        // sets on item click listener for each item in the list
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // this will go to the next activity with the new list
                Intent intent = new Intent(MainActivity.this, ListDetail.class);
                startActivity(intent);

            }
        });
    }

    private void SetOnItemLongClickListener() {
        //sets long item click listener
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String positionName = (String) mAdapter.getItem(position);
                mAdapter.remove(positionName);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}
