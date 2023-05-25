package algonquin.cst2335.cast0304.data.ui.data.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import algonquin.cst2335.cast0304.data.ui.data.data.MainViewModel;
import algonquin.cst2335.cast0304.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has: " + s);
        });

        model.checkedButton.observe(this, selected -> {
            variableBinding.checkbox.setChecked(selected);
            variableBinding.radiobutton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
        });

        variableBinding.mybutton.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        variableBinding.checkbox.setOnCheckedChangeListener( (checkbox, isChecked) ->
        {
            model.checkedButton.postValue(variableBinding.checkbox.isChecked());
        });

        variableBinding.radiobutton.setOnCheckedChangeListener( (radiobutton, isChecked) ->
        {
            model.checkedButton.postValue(variableBinding.radiobutton.isChecked());
        });

        variableBinding.switch1.setOnCheckedChangeListener( (switch1, isChecked) ->
        {
            model.checkedButton.postValue(variableBinding.switch1.isChecked());
        });

    }
}
