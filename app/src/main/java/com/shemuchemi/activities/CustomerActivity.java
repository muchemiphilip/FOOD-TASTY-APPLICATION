package com.shemuchemi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.shemuchemi.R;
import com.shemuchemi.restaurants.BlueRoom;
import com.shemuchemi.restaurants.JavaHouse;

public class CustomerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AppCompatButton appCompatButtonNext;
    private AppCompatSpinner appCompatSpinner;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_customer );

        init();
        adapter = ArrayAdapter.createFromResource(this, R.array.restaurants, R.layout.coloured_spinner_layout);
        adapter.setDropDownViewResource( R.layout.coloured_spinner_layout );
        appCompatSpinner.setAdapter( adapter );
        appCompatSpinner.setOnItemClickListener( this );
    }

    private void init() {
        appCompatSpinner= findViewById( R.id.appCompactSpinner );
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText( this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT ).show();
        String restaurantSelected = adapterView.getSelectedItem().toString();
        if (restaurantSelected.equals( "JAVA HOUSE NYALI" )){
            startActivity( new Intent( CustomerActivity.this, JavaHouse.class ) );
        }
        else if (restaurantSelected.equals( "BLUE ROOM" )){
            startActivity( new Intent( CustomerActivity.this, BlueRoom.class ) );
        }
        else if (restaurantSelected.equals( "TARBUSH SWAHILI FOODS" )){
            startActivity( new Intent( CustomerActivity.this, BlueRoom.class ) );
        }
        else if (restaurantSelected.equals( "BARCA SWAHILI FOODS" )){
            startActivity( new Intent( CustomerActivity.this, BlueRoom.class ) );
        }
        else if (restaurantSelected.equals( "CHICKEN INN" )){
            startActivity( new Intent( CustomerActivity.this, BlueRoom.class ) );
        }
        else if (restaurantSelected.equals( "CAFE MOCHA" )){
            startActivity( new Intent( CustomerActivity.this, BlueRoom.class ) );
        }

    }
}




