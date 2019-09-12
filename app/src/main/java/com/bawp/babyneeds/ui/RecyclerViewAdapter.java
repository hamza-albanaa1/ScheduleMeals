package com.bawp.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bawp.babyneeds.R;
import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {
    //Todo a)constructor
    private Context context;
    private List<Item> itemList;


    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }









    //1

    private LayoutInflater inflater;
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {








        //Todo E call the view which is created
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);


        return new ViewHolder(view, context);
    }















    //2
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {





        //h show
        Item item = itemList.get(position); // object Item
        viewHolder.itemday.setText(MessageFormat.format("Day {0}", item.getItemDay()));
        viewHolder.itembreakfast.setText(MessageFormat.format("Break_Fast {0}", item.getItemBreakFast()));
        viewHolder.lunch.setText(MessageFormat.format("Lunch {0}", String.valueOf(item.getItemLunch())));
        viewHolder.dinner.setText(MessageFormat.format("Dinner {0}", String.valueOf(item.getItemDinner())));
        viewHolder.dateAdded.setText(MessageFormat.format("Added on: {0}", item.getDateItemAdded()));
    }











    //3
    @Override
    public int getItemCount() {
        //F
        return itemList.size();
    }

















    //4
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {




        //Todo b  what we add to list_row_layout

        public TextView itemday;
        public TextView itembreakfast;
        public TextView lunch;
        public TextView dinner;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;

        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) { //add Context
            super(itemView);


            //c
            context = ctx;
            itemday = itemView.findViewById(R.id.item_day);
            itembreakfast = itemView.findViewById(R.id.item_breakfast);
            dinner = itemView.findViewById(R.id.item_dinner);
            lunch = itemView.findViewById(R.id.item_lunch);
            dateAdded = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }




        //from edit button

        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Item item = itemList.get(position);


            //d
            switch (v.getId()) {
                case R.id.editButton:
                    //edit item
                    editItem(item);
                    break;
                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
            }

        }








        //h delete method
        private AlertDialog.Builder builder;
        private AlertDialog dialog;

        private void deleteItem(final int id) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop, null);



            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);




            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });





            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });




            builder.setView(view);
            dialog = builder.create();
            dialog.show();


        }








        //edit Item method
        private void editItem(final Item newItem) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);


             Button saveButton;
             final EditText dayItem;
             final EditText itemBrekfast;
             final EditText itemLunch;
             final EditText itemDinner;
             TextView title;



            dayItem = view.findViewById(R.id.day);
            itemBrekfast = view.findViewById(R.id.first_meal);
            itemLunch = view.findViewById(R.id.second_meal);
            itemDinner = view.findViewById(R.id.third_meal);
            saveButton = view.findViewById(R.id.saveButton);

            title = view.findViewById(R.id.title);
            saveButton.setText(R.string.update_text);





            dayItem.setText(newItem.getItemDay());
            itemBrekfast.setText(String.valueOf(newItem.getItemBreakFast()));
            itemLunch.setText(newItem.getItemLunch());
            itemDinner.setText(String.valueOf(newItem.getItemDinner()));


            builder.setView(view);
            dialog = builder.create();
            dialog.show();



            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update our item
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);

                    //update items
                    newItem.setItemDay(dayItem.getText().toString());
                    newItem.setItemBreakFast(itemBrekfast.getText().toString());
                    newItem.setItemLunch(itemLunch.getText().toString());
                    newItem.setItemDinner (itemDinner.getText().toString());




                    if (!dayItem.getText().toString().isEmpty()
                    && !itemBrekfast.getText().toString().isEmpty()
                    && !itemLunch.getText().toString().isEmpty()
                    && !itemDinner.getText().toString().isEmpty()) {

                        databaseHandler.updateItem(newItem);
                        notifyItemChanged(getAdapterPosition(),newItem); //important!


                    }else {
                        Snackbar.make(view, "Fields Empty",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    dialog.dismiss();

                }
            });
        }


    }
}
