package com.example.anthony.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MAIN = "MainActivity";
    public static final int SELECT_TODO_LIST = 10;
    public static final String TODO_LIST_NAME = "listName";

    ListView mListView;
    ArrayAdapter mAdapter;
    FloatingActionButton fab;
    EditText inputText;
    TextView emptyListMessage;
    //TextView toDoListHeader;

    ArrayList<String> mTodoList;
    ArrayList<ArrayList<String>> mListItems;


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

        //instantiate array list
        mTodoList = new ArrayList<>(); // maybe set this to 40 or so to save mem on it doubling
        mListItems = new ArrayList<>();

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
                if (inputText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill out the field to add something to the list", Toast.LENGTH_SHORT).show();
                } else {
                    emptyListMessage.setVisibility(View.INVISIBLE);
                    mListView.setVisibility(View.VISIBLE);

                    String newTodo = inputText.getText().toString();
                    mAdapter.add(newTodo);
                    mAdapter.notifyDataSetChanged();

                    inputText.setText("");
                }

            }
        });
    }

    private void SetOnItemClickListener() {
        // sets on item click listener for each item in the list
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // this will go to the next activity with the new list
                String todoListName = mAdapter.getItem(position).toString();
                Intent intent = new Intent(MainActivity.this, ListDetail.class);
                intent.putExtra(TODO_LIST_NAME, todoListName);
                if (!mListItems.isEmpty()) {
                    ArrayList<String> todolist = mListItems.get(0);
                    intent.putExtra("listItems", todolist);  //TODO: make constant and clarify
                }
                    startActivityForResult(intent, SELECT_TODO_LIST);
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
                if (mTodoList.size() == 0) {
                    emptyListMessage.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //confirm the request being responded too
        if (requestCode == SELECT_TODO_LIST) {
            if (resultCode == RESULT_OK) {
                data.getExtras();
                Bundle extras = data.getExtras();
                mListItems.add(extras.getStringArrayList("listName")); //TODO: make constant and clarify

            }

        }
    }
}
