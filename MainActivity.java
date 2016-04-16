package com.example.umesh.countrylistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IDemoInterface {
    SqlHandler sqlHandler;
    ListView lst,stateLst;
    private Button btn,btnState;
    CountryListAdapter countryListAdapter;
    ArrayList<CountryListItems> countryList;

    StateListAdapter stateListAdapter;
    ArrayList<StateListItems> stateList;
    MyUtility myUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst=(ListView)findViewById(R.id.lstCountry);
        myUtility=new MyUtility(MainActivity.this);
        sqlHandler = new SqlHandler(this);
        countryList= myUtility.selectCountry();
        countryListAdapter = new CountryListAdapter(MainActivity.this, countryList);
        lst.setAdapter(countryListAdapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setSelected(true);

                final CountryListItems countryListItems = countryList.get(position);
                final int cid = countryListItems.getId();
                stateLst = (ListView) findViewById(R.id.lstState);
                stateList = myUtility.selectState(cid);
                stateListAdapter = new StateListAdapter(MainActivity.this, stateList);
                stateLst.setAdapter(stateListAdapter);
                btnState = (Button) findViewById(R.id.btnStateAdd);
                btnState.setEnabled(true);
                btnState.setVisibility(View.VISIBLE);
                btnState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myUtility.stateInputDialog(stateList, stateListAdapter, cid);

                    }
                });
            }
        });
        myAction();
    }
    private void myAction()
    {
        btn=(Button)findViewById(R.id.btnAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addCountryDialog();
                myUtility.MyInputDialog(countryList,countryListAdapter);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void delete() {
        stateLst.setAdapter(null);
        btnState.setEnabled(false);
        btnState.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this,"Ha bhai",Toast.LENGTH_LONG).show();
    }
}
