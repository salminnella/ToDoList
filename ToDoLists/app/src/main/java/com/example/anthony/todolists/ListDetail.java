package com.example.anthony.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListDetail extends AppCompatActivity {
    public static final String TAG_DETAILS = "ListDetail";

    FloatingActionButton fab;
    Button backButton;
    TextView emptyListMessage;
    EditText inputText;
    ListView mListView;
    ArrayList<String> mToDoItems;
    ArrayAdapter<String> mArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setOnClickListeners();
        setOnItemClickListener();
        setOnItemLongClickListener();


    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.list_details_fab);
        backButton = (Button) findViewById(R.id.list_details_back_button);
        emptyListMessage = (TextView) findViewById(R.id.list_details_empty_list_notes);
        inputText = (EditText) findViewById(R.id.list_details_edit_text);
        mListView = (ListView) findViewById(R.id.list_details_listview);
        //instantiate arraylist
        mToDoItems = new ArrayList<>(); // maybe set this to 40 or so to save mem from it doubling
        //instantiate adapter
        mArrayAdapter = new ArrayAdapter<String>(ListDetail.this, android.R.layout.simple_list_item_1, mToDoItems);
        //set the adapter to the list
        mListView.setAdapter(mArrayAdapter);

    }

    private void setOnClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyListMessage.setVisibility(View.INVISIBLE);
                String newToDoItem = inputText.getText().toString();
                mToDoItems.add(newToDoItem);
                mArrayAdapter.notifyDataSetChanged();

                inputText.setText("");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOnItemClickListener() {
        // i might try to add another activity to actually edit the list item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setOnItemLongClickListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String positionItem = mArrayAdapter.getItem(position);
                mToDoItems.remove(positionItem);
                mArrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

}
