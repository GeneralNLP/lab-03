package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city){
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }



    @Override
    public void editCity(City city){
        //functionality for the edit fragments
        for (int i=0; i<dataList.size();i++) {
            if (dataList.get(i)==city) {
                dataList.set(i, city);
                break;
            }
        }
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow",

        };

        String[] provinces = {
                "AB", "BC", "ON",
        };

//        dataList = new ArrayList<City>();
        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
//        dataList.addAll(Arrays.asList(cities));
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);


        //Add a onclick listener for each item in the listview ,
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void  onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City selectedCity = dataList.get(position);
                AddCityFragment editFragment = AddCityFragment.newInstance(selectedCity);
                editFragment.show(getSupportFragmentManager(), "Edit City");
            }
        });

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v->{
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });
    }
}