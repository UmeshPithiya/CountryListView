package com.example.umesh.countrylistview;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by umesh on 26/03/16.
 */
public class MyUtility {
    private Context context;
    private String responseText;
    private EditText input;
    private SqlHandler sqlHandler;
    private CountryListAdapter countryListAdpt;
    ;
    private StateListAdapter stateListAdpt;
    ;
    private ArrayList<CountryListItems> countryLst;
    private ArrayList<StateListItems> stateLst;

    MyUtility(Context context) {
        this.context = context;
    }

    public void MyInputDialog(ArrayList<CountryListItems> countryList, CountryListAdapter countryListAdapter) {
        countryLst = countryList;
        countryListAdpt = countryListAdapter;
        sqlHandler = new SqlHandler(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("COUNTRY");
        builder.setMessage("Enter Country Name:");

        input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                responseText = input.getText().toString();
                String query = "INSERT INTO COUNTRY(country_name) values ('" + responseText + "')";
                sqlHandler.executeQuery(query);
                selectCountry(countryLst);
                countryListAdpt.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public ArrayList selectCountry() {
        ArrayList<CountryListItems> countryList = new ArrayList<CountryListItems>();
        countryList.clear();
        String query = "SELECT * FROM COUNTRY ";
        sqlHandler = new SqlHandler(context);
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    CountryListItems countryListItems = new CountryListItems();

                    countryListItems.setId(c1.getInt(c1
                            .getColumnIndex("id")));
                    countryListItems.setName(c1.getString(c1
                            .getColumnIndex("country_name")));

                    countryList.add(countryListItems);

                } while (c1.moveToNext());
            }
        }
        c1.close();
        return countryList;
    }

    public void selectCountry(ArrayList<CountryListItems> countryList) {
        countryList.clear();
        String query = "SELECT * FROM COUNTRY ";
        sqlHandler = new SqlHandler(context);
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    CountryListItems countryListItems = new CountryListItems();
                    countryListItems.setId(c1.getInt(c1.getColumnIndex("id")));
                    countryListItems.setName(c1.getString(c1.getColumnIndex("country_name")));

                    countryList.add(countryListItems);

                } while (c1.moveToNext());
            }
        }
        c1.close();
    }

    public void selectState(ArrayList<StateListItems> stateList, int cid) {
        stateList.clear();
        String query = "SELECT * FROM STATE WHERE country_id=" + cid;
        sqlHandler = new SqlHandler(context);
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    StateListItems stateListItems = new StateListItems();

                    stateListItems.setId(c1.getInt(c1
                            .getColumnIndex("id")));
                    stateListItems.setCid(c1.getInt(c1
                            .getColumnIndex("country_id")));
                    stateListItems.setName(c1.getString(c1
                            .getColumnIndex("state_name")));

                    stateList.add(stateListItems);

                } while (c1.moveToNext());
            }
        }
        c1.close();
    }

    public ArrayList selectState(int cid) {
        ArrayList<StateListItems> stateList = new ArrayList<StateListItems>();
        stateList.clear();
        String query = "SELECT * FROM STATE WHERE country_id=" + cid;

        sqlHandler = new SqlHandler(context);
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    /*Toast.makeText(context, c1.getInt(c1.getColumnIndex("id")) + "", Toast.LENGTH_LONG).show();*/
                    StateListItems stateListItems = new StateListItems();

                    stateListItems.setId(c1.getInt(c1
                            .getColumnIndex("id")));
                    stateListItems.setCid(c1.getInt(c1
                            .getColumnIndex("country_id")));
                    stateListItems.setName(c1.getString(c1
                            .getColumnIndex("state_name")));

                    stateList.add(stateListItems);

                } while (c1.moveToNext());
            }
        }
        c1.close();
        return stateList;
    }

    public void stateInputDialog(ArrayList<StateListItems> stateList, StateListAdapter stateListAdapter, final int cid) {
        stateLst = stateList;
        stateListAdpt = stateListAdapter;
        sqlHandler = new SqlHandler(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("STATE");
        builder.setMessage("Enter State Name:");

        input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                responseText = input.getText().toString();
                String query = "INSERT INTO STATE(country_id,state_name) values (" + cid + ",'" + responseText + "')";
                sqlHandler.executeQuery(query);
                selectState(stateLst, cid);
                stateListAdpt.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
