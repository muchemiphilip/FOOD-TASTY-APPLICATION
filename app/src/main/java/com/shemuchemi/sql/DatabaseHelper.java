package com.shemuchemi.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shemuchemi.model.Order;
import com.shemuchemi.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TastyFoods.db";

    // User table name and columns
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Order table name and columns
    private static final String TABLE_ORDER = "orders";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_FOOD_TYPE = "food_type";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PRICE = "price";

    // create user table sql query
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create order table sql query
    private static final String CREATE_ORDER_TABLE = "CREATE TABLE  " + TABLE_ORDER + "("
            + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FOOD_TYPE + " TEXT,"
            + COLUMN_LOCATION + " TEXT," + COLUMN_PRICE + " TEXT" + ")";
    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_USER_TABLE );
        db.execSQL( CREATE_ORDER_TABLE );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exists
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER +  "'" );
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_ORDER +  "'" );
        // Create Tables again
        onCreate( db );
    }
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( COLUMN_USER_NAME, user.getName() );
        values.put( COLUMN_USER_EMAIL, user.getEmail() );
        values.put( COLUMN_USER_PASSWORD, user.getPassword() );
        // Inserting Row
        db.insert( TABLE_USER, null, values );
        db.close();
    }
    public boolean addOrder(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_TYPE, " Barbecue");
        values.put( COLUMN_LOCATION, "Nyali" );
        values.put( COLUMN_PRICE, 600 );

        long result = db.insert( TABLE_ORDER ,null, values);
        return result != -1;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /*
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'shem@gmail.com';
         */
        Cursor cursor = db.query( TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null );                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_USER_ID
        };
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query( TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null );

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    /**
     * This method is to fetch all order and return the list of order records
     *
     * @return list
     */
    public List<Order> getAllOrder() {
        //array of columns to fetch
        String[] columns = {
                COLUMN_ORDER_ID,
                COLUMN_FOOD_TYPE,
                COLUMN_LOCATION,
                COLUMN_PRICE
        };
        List<Order> orderList = new ArrayList<Order>();
        SQLiteDatabase db = this.getReadableDatabase();
         /*query the order table
         * SELECT order_id,food_type,location,price FROM user ORDER BY food_type;
         */
        Cursor cursor = db.query(TABLE_ORDER,
                columns,
                null,
                null,
                null,
                null,
                 null);
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId( Integer.parseInt( cursor.getString( cursor.getColumnIndex( COLUMN_ORDER_ID ) ) ) );
                order.setFoodType( cursor.getString( cursor.getColumnIndex( COLUMN_FOOD_TYPE ) ) );
                order.setLocation( cursor.getString( cursor.getColumnIndex( COLUMN_LOCATION ) ) );
                order.setPrice( cursor.getDouble( cursor.getColumnIndex( COLUMN_PRICE ) ) );
                // Adding order record to list
                orderList.add(order);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        //return order list
        return orderList;
    }
}
