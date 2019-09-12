package com.bawp.babyneeds;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;
import com.bawp.babyneeds.ui.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";







    //1
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;







    //5 for adding
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText day;
    private EditText bre;
    private EditText lun;
    private EditText din;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);




        //2
        recyclerView = findViewById(R.id.recyclerview);
        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();






        //3
        //Get items from db
        itemList = databaseHandler.getAllItems();

        for (Item item : itemList) {

            Log.d(TAG, "onCreate: " + item.getItemLunch());

        }




        //4 to show item

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();




















        //6 add new day (as main activity)
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }












    //7 (as main activity)
    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);


        day = view.findViewById(R.id.day);
        bre = view.findViewById(R.id.first_meal);
        lun = view.findViewById(R.id.second_meal);
        din = view.findViewById(R.id.third_meal);
        saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!day.getText().toString().isEmpty()
                        && !bre.getText().toString().isEmpty()
                        && !lun.getText().toString().isEmpty()
                        && !din.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }









    //8 (as main activity)
    private void saveItem(View view) {
        //Todo: save each baby item to db
        Item item = new Item();

        String Day1 = day.getText().toString().trim();
        String brakefast = bre.getText().toString().trim();
        String lunch = lun.getText().toString().trim();
        String dinner = din.getText().toString().trim();

        item.setItemDay(Day1);
        item.setItemBreakFast(brakefast);
        item.setItemLunch(lunch);
        item.setItemDinner(dinner);

        databaseHandler.addItem(item);

        Snackbar.make(view, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //code to be run
                alertDialog.dismiss();
                //Todo: move to next screen - details screen
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();

            }
        }, 1200);// 1sec
    }
}
