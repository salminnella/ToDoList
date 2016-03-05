package com.example.anthony.todolists;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDetail extends AppCompatActivity {

    int toDoListIndex;
    TextView emptyListMessage;
    TextView titleListName;
    EditText inputText;
    ListView mListView;
    FloatingActionButton fab;
    ImageButton backButton;
    ArrayList<String> mToDoItems;
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> receiveItemsFromMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        initViews();
        setOnClickListeners();
        setOnItemClickListener();
        setOnItemLongClickListener();
        receiveIntentData();

    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.list_details_fab);
        backButton = (ImageButton) findViewById(R.id.list_details_back_button);
        emptyListMessage = (TextView) findViewById(R.id.list_details_empty_list_notes);
        titleListName = (TextView) findViewById(R.id.list_details_header_todo_list_name);
        inputText = (EditText) findViewById(R.id.list_details_edit_text);
        mListView = (ListView) findViewById(R.id.list_details_listview);

        //instantiate arraylist
        mToDoItems = new ArrayList<>();

        //instantiate adapter
        mArrayAdapter = new ArrayAdapter<String>(ListDetail.this, android.R.layout.simple_list_item_1, mToDoItems);

        //set the adapter to the list
        mListView.setAdapter(mArrayAdapter);

    }

    private void setOnClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputText.getText().toString().equals("")) {
                    Toast.makeText(ListDetail.this, R.string.empty_editText_error_message, Toast.LENGTH_SHORT).show();
                } else {
                    emptyListMessage.setVisibility(View.INVISIBLE);
                    mListView.setVisibility(View.VISIBLE);
                    String newToDoItem = inputText.getText().toString();
                    mToDoItems.add(newToDoItem);
                    mArrayAdapter.notifyDataSetChanged();
                    inputText.setText("");
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntentData();
            }
        });
    }

    /**
     * strikethrough on items list
     */
    private void setOnItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setItemChecked((TextView) view, position);
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

                if (mToDoItems.size() == 0) {
                    emptyListMessage.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.INVISIBLE);
                } else {

                    emptyListMessage.setVisibility(View.INVISIBLE);
                    mListView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }


    /**
     * Receives the data from the previous activity
     */
    private void receiveIntentData() {
        Intent intent = getIntent();
        titleListName.setText(intent.getStringExtra(MainActivity.TODO_LIST_NAME).toUpperCase());

            receiveItemsFromMain = intent.getStringArrayListExtra(MainActivity.TODO_LIST_ITEMS);
            mToDoItems.addAll(receiveItemsFromMain);
            mArrayAdapter.notifyDataSetChanged();

            //after lists is received, make the instructions go away, and have the list now visible
        if (receiveItemsFromMain.size() == 0) {
            emptyListMessage.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        } else {
            emptyListMessage.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
        }
        toDoListIndex = intent.getIntExtra(MainActivity.LIST_ITEMS_INDEX, MainActivity.ERROR_INDEX);
    }


    /**
     * Sends the data back to the original activity
     * thanks for the demo on this alex
     */
    private void returnIntentData() {
        Intent intentToMain = new Intent();
        intentToMain.putStringArrayListExtra(MainActivity.TODO_LIST_ITEMS, mToDoItems);
        intentToMain.putExtra(MainActivity.LIST_ITEMS_INDEX, toDoListIndex);
        setResult(RESULT_OK, intentToMain);
        finish();
    }


    /**
     * overrides back button on device if pressed
     */
    @Override
    public void onBackPressed() {
        returnIntentData();
    }


    /**
     *
     * @param item
     * @param position
     *
     * one of the bonuses --- i did get some help from stackoverflow though
     * i do need to thank bill for networking with the new york class on this one.
     * mike got me started, found some info on stackoverflow, and then i saw a posting to the android - fundamentals channel with the rest
     *
     * if you do give points for this method, i don't deserve them because this wasn't my work.
     */
    public void setItemChecked(TextView item, int position) {
        if (!mListView.isItemChecked(position) &&
                !((item.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0)) {
            Log.i("[TAP]", "Strikethrough");
            item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mListView.setItemChecked(position, true);
        } else {
            // http://stackoverflow.com/questions/18881817/removing-strikethrough-from-textview
            Log.i("[TAP]", "Un-strike");
            item.setPaintFlags(item.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            mListView.setItemChecked(position, false);
        }
    }
}
