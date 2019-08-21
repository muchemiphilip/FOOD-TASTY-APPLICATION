package com.shemuchemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.shemuchemi.R;
import com.shemuchemi.adapters.OrdersRecyclerAdapter;
import com.shemuchemi.model.Order;
import com.shemuchemi.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class OrdersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = OrdersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewOrders;
    private List<Order> listOrders;
    private OrdersRecyclerAdapter ordersRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_orders_list );

        getSupportActionBar().setTitle("");

        initViews();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = findViewById(R.id.textViewName);
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listOrders = new ArrayList<>();
        ordersRecyclerAdapter = new  OrdersRecyclerAdapter( listOrders );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerViewOrders.setLayoutManager( mLayoutManager );
        recyclerViewOrders.setItemAnimator( new DefaultItemAnimator() );
        recyclerViewOrders.setHasFixedSize( true );
        recyclerViewOrders.setAdapter( ordersRecyclerAdapter );

        databaseHelper = new DatabaseHelper( activity );

       String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }
    /**
     * This method is to fetch all user records from SQLite
     */
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                listOrders.clear();
                listOrders.addAll(databaseHelper.getAllOrder());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute( aVoid );
                ordersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
