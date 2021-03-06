package com.oldgoat5.bmstacticalreference.tacticalreference;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.oldgoat5.bmstacticalreference.tools.database.DBTools;

import java.util.ArrayList;

public class ListViewPopulateDataBase extends ListActivity
{
    private SQLiteDatabase database;
    private ArrayList<String> ordinanceList = new ArrayList<String>();
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //Log.d("listviewpopulate", "on Create");
        openDatabase();
        //writeToListView();
    }

    private void openDatabase()
    {
        DBTools dbTools;
        Cursor cursor;
        
        try
        {
            dbTools = new DBTools(this.getApplicationContext());
            database = dbTools.getWritableDatabase();
            cursor = database.rawQuery("SELECT * FROM ordinance", null);
            
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        int id;
                        int weight;
                        int drag;
                        String damage_type;
                        String guidance;
                        int range_km;
                        int blast_radius;
                        String best_used;
                        
                        id = cursor.getInt(cursor.getColumnIndex("id"));
                        weight = cursor.getInt(cursor.getColumnIndex("weight"));
                        drag = cursor.getInt(cursor.getColumnIndex("drag"));
                        damage_type = cursor.getString(cursor.getColumnIndex(
                                "damage_type"));
                        guidance = cursor.getString(cursor.getColumnIndex(
                                "guidance"));
                        range_km = cursor.getInt(cursor.getColumnIndex(
                                "range_km"));
                        blast_radius = cursor.getInt(cursor.getColumnIndex(
                                "blast_radius"));
                        best_used = cursor.getString(cursor.getColumnIndex(
                                "best_used"));
                        
                        ordinanceList.add("Id: " + id + "Weight: " + weight + 
                                "Drag: " + drag + "Damage Type: " +
                                damage_type + "Guidance: " + guidance + 
                                "Range(km): " + range_km + "Blast Radius: " + 
                                blast_radius + "Best used: " + best_used);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
        catch(SQLiteException sqle)
        {
            //Log.d("DBTools.openDatabase()", "Could not open database.");
            throw sqle;
        }
        finally
        {
            if (database != null)
            {
                database.close();
            }
        }
    }
}
