package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface  AddCityDialogListener {
        void addCity(City city);
        void editCity(City city);
    }
    private AddCityDialogListener listener;
    private City cityToEdit;


    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }


    public static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city",city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        //If we are editing a city , u have to be able to pre-populate the nesscary fields.
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String title = cityToEdit != null ? "Edit city" : "Add a City";
        String buttonText = cityToEdit !=null ? "update" : "Add";
        
        return builder
                .setView(view)
                .setTitle(title) 
                .setNegativeButton("cancel", null)
                .setPositiveButton(buttonText, (dialog,which)->{
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (cityToEdit != null) {
                        // check if a city already exsists in order to edit it ,
                        cityToEdit.setName(cityName);
                        cityToEdit.setProvince(provinceName);
                        listener.editCity(cityToEdit);
                    } else {
                        //we have to add a new city
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
