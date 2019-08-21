package com.shemuchemi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.shemuchemi.R;
import com.shemuchemi.restaurants.JavaHouse;


public class CustomerActivity extends AppCompatActivity {

    private AppCompatSpinner appCompatSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private AppCompatButton appCompactButtonCheckMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_customer);

        init();
        adapter = ArrayAdapter.createFromResource(this, R.array.restaurants, R.layout.coloured_spinner_layout);
        appCompatSpinner.setAdapter( adapter );

        appCompactButtonCheckMenu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = appCompatSpinner.getSelectedItem().toString();
                if (item.equals( "JAVA HOUSE NYALI" )){
                    startActivity( new Intent( CustomerActivity.this, JavaHouse.class ) );
                }
            }
        } );

    }

    public void init(){
        appCompatSpinner =  findViewById( R.id.appCompactSpinner );
        appCompactButtonCheckMenu = findViewById( R.id.appCompactButtonCheckMenu );
    }
}




