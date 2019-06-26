package com.ebusiness.kashan.urbandictionary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class BrowseActivity extends AppCompatActivity {
    public static MyDatabaseHelper db;
    private static ArrayList<UrbanExpression> list;
    private static ArrayList<String> listDataHeader;
    private static HashMap<String, ArrayList<String>> listDataChild;
    private static ExpandableListView elv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        db = new MyDatabaseHelper(this);
        elv = findViewById(R.id.eListView);
        Context view = this;
        getData(view);
    }

    public static void getData(Context view){
        list = new ArrayList<>();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        list = db.getAllUrbanExpressions();
        for(int i = 0; i < list.size(); i++){
            listDataHeader.add(list.get(i).getWord());
            ArrayList<String> info = new ArrayList<String>();
            info.add("Definition:\n" + list.get(i).getDefinition());
            info.add("Example(s):\n" + list.get(i).getExample());
            listDataChild.put(listDataHeader.get(i), info);
        }
        final ExpandableListAdapter listAdapter = new ExpandableListAdapter(view, listDataHeader, listDataChild);
        elv.setAdapter(listAdapter);
    }
}
