package com.ebusiness.kashan.urbandictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UrbanDictionaryDB";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(UrbanExpression.CREATE_TABLE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + UrbanExpression.TABLE_NAME);
        onCreate(database);
    }

    public long insertUrbanExpression(UrbanExpression expression) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UrbanExpression.COLUMN_WORD, expression.getWord());
        values.put(UrbanExpression.COLUMN_DEFINITION, expression.getDefinition());
        values.put(UrbanExpression.COLUMN_EXAMPLE, expression.getExample());

        // insert row
        long id = db.insert(UrbanExpression.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public ArrayList<UrbanExpression> getAllUrbanExpressions() {
        ArrayList<UrbanExpression> expressions = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + UrbanExpression.TABLE_NAME + " ORDER BY " +
                UrbanExpression.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UrbanExpression expression = new UrbanExpression();
                expression.setId(cursor.getInt(cursor.getColumnIndex(UrbanExpression.COLUMN_ID)));
                expression.setWord(cursor.getString(cursor.getColumnIndex(UrbanExpression.COLUMN_WORD)));
                expression.setDefinition(cursor.getString(cursor.getColumnIndex(UrbanExpression.COLUMN_DEFINITION)));
                expression.setExample(cursor.getString(cursor.getColumnIndex(UrbanExpression.COLUMN_EXAMPLE)));
                expression.setTimestamp(cursor.getString(cursor.getColumnIndex(UrbanExpression.COLUMN_TIMESTAMP)));

                expressions.add(expression);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return expressions;
    }

    public void deleteUrbanExpression(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UrbanExpression.TABLE_NAME, UrbanExpression.COLUMN_WORD + " = ?",
                new String[]{word});
        db.close();
    }
}