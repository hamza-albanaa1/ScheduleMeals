package com.bawp.babyneeds;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;





    private Button saveButton;
    private EditText dayItem;
    private EditText itemBrekfast;
    private EditText itemLunch;
    private EditText itemDinner;




    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        dayItem = view.findViewById(R.id.day);
        itemBrekfast = view.findViewById(R.id.first_meal);
        itemLunch = view.findViewById(R.id.second_meal);
        itemDinner = view.findViewById(R.id.third_meal);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dayItem.getText().toString().isEmpty()
                        && !itemBrekfast.getText().toString().isEmpty()
                        && !itemLunch.getText().toString().isEmpty()
                        && !itemDinner.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

        builder.setView(view);
        dialog = builder.create();// creating our dialog object
        dialog.show();// important step!



    }






    private void saveItem(View view) {
        //Todo: save each baby item to db
        Item item = new Item();

        String newDay = dayItem.getText().toString().trim();
        String Breakfast = itemBrekfast.getText().toString().trim();
        String Lunch = itemLunch.getText().toString().trim();
        String Dinner = itemDinner.getText().toString().trim();

        item.setItemDay(newDay);
        item.setItemBreakFast(Breakfast);
        item.setItemLunch(Lunch);
        item.setItemDinner(Dinner);

        databaseHandler.addItem(item);

        Snackbar.make(view, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();







//Todo: move to list after delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //code to be run
                dialog.dismiss();
                //Todo: move to next screen - details screen
                startActivity(new Intent(MainActivity.this, ListActivity.class));

            }
        }, 1200);// 1sec
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        databaseHandler = new DatabaseHandler(this);





        //check if item was saved
        byPassActivity();
        List<Item> items = databaseHandler.getAllItems();
        for (Item item : items) {
            Log.d("Main", "onCreate: " + item.getItemLunch());
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }









//check database
    private void byPassActivity() {
        if (databaseHandler.getItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
