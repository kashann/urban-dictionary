package com.ebusiness.kashan.urbandictionary;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MainActivity extends AppCompatActivity {
    public static OkHttpClient client = new OkHttpClient();
    public static UrbanExpression urbanExpression = new UrbanExpression();
    private MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDatabaseHelper(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); // bad practice
    }

    public void getRequest(View view){
        EditText value = findViewById(R.id.searchBar);
        if (value.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Search for a specific word or expression!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            Toast.makeText(getApplicationContext(), "Loading... Please wait!", Toast.LENGTH_SHORT).show();
            String query = value.getText().toString();
            query = query.replace(' ', '+');

            Request request = new Request.Builder()
                    .url("https://api.urbandictionary.com/v0/define?term=" + query)
                    .build();
            Response response = MainActivity.client.newCall(request).execute();
            JsonElement jelement = new JsonParser().parse(response.body().string());
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("list");
            if(jarray.size() == 0){
                Toast.makeText(getApplicationContext(), "No results... :(", Toast.LENGTH_SHORT).show();
                return;
            }
            jobject = jarray.get(0).getAsJsonObject();
            urbanExpression.setWord(jobject.get("word").getAsString());
            urbanExpression.setDefinition(jobject.get("definition").getAsString());
            urbanExpression.setExample(jobject.get("example").getAsString());

            TextView w = findViewById(R.id.tvWord);
            TextView d = findViewById(R.id.tvDef);
            TextView e = findViewById(R.id.tvEx);
            View l1 = findViewById(R.id.line1);
            View l2 = findViewById(R.id.line2);
            w.setText("Exp: " + urbanExpression.getWord());
            l1.setVisibility(View.VISIBLE);
            d.setText("Definition: \n" + urbanExpression.getDefinition());
            l2.setVisibility(View.VISIBLE);
            e.setText("Example(s): \n" + urbanExpression.getExample());
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void saveExpression(View view){
        db.insertUrbanExpression(urbanExpression);
        Toast.makeText(getApplicationContext(), "Expression saved locally", Toast.LENGTH_SHORT).show();
    }

    public void launchBrowseActivity(View view){
        Intent myIntent = new Intent(MainActivity.this, BrowseActivity.class);
        startActivity(myIntent);
    }
}