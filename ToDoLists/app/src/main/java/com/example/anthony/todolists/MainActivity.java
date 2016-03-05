package com.example.anthony.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int SELECTED_TODO_LIST = 10;
    public static final String TODO_LIST_NAME = "listName";
    public static final String TODO_LIST_ITEMS = "listItems";
    public static final String LIST_ITEMS_INDEX = "listIndex";
    public static final int ERROR_INDEX = -1;

    ListView mListView;
    ArrayAdapter mAdapter;
    MyAdapter myAdapter; // i had wanted to have each list item a background of white
    FloatingActionButton fab;
    EditText inputText;
    TextView emptyListMessage;

    ArrayList<String> mTodoList;
    ArrayList<ArrayList<String>> mListItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        SetOnItemClickListener();
        SetOnItemLongClickListener();

    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.main_act_fab);
        mListView = (ListView) findViewById(R.id.main_act_listview);
        inputText = (EditText) findViewById(R.id.main_act_edit_text);
        emptyListMessage = (TextView) findViewById(R.id.main_act_empty_list_notes);

        //instantiate array lists
        mTodoList = new ArrayList<>(); // maybe set this to 40 or so to save mem on it doubling
        mListItems = new ArrayList<>();

        //instantiate adapter
        mAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mTodoList);
        //myAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, mTodoList);

        //get the listview and set the adapter
        mListView.setAdapter(mAdapter);

        setOnButtonClickListeners();
    }

    private void setOnButtonClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.empty_editText_error_message, Toast.LENGTH_SHORT).show();
                } else {
                    emptyListMessage.setVisibility(View.INVISIBLE);
                    mListView.setVisibility(View.VISIBLE);

                    String newTodo = inputText.getText().toString();
                    mAdapter.add(newTodo);
                    mAdapter.notifyDataSetChanged();
                    mListItems.add(new ArrayList<String>());

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
                stuffIntent(position);
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
                mListItems.remove(position);
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
        if (requestCode == SELECTED_TODO_LIST) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> receiveList = data.getStringArrayListExtra(TODO_LIST_ITEMS);
                int listIndex = data.getIntExtra(LIST_ITEMS_INDEX, ERROR_INDEX);
                if (listIndex >= mListItems.size()) {
                    mListItems.add(receiveList);
                } else {
                    mListItems.set(listIndex, receiveList);
                }
            }

        }
    }

    protected void stuffIntent(int itemPosition) {
        String todoListName = mTodoList.get(itemPosition);

        Intent intent = new Intent(MainActivity.this, ListDetail.class);
        intent.putExtra(TODO_LIST_NAME, todoListName);
        intent.putExtra(LIST_ITEMS_INDEX, itemPosition);
        ArrayList<String> todolist = mListItems.get(itemPosition);
        intent.putExtra(TODO_LIST_ITEMS, todolist);

        startActivityForResult(intent, SELECTED_TODO_LIST);
    }
}
