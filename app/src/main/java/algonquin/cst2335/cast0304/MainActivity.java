package algonquin.cst2335.cast0304;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.cast0304.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create a binding object
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // loads XML file on the page
        setContentView(variableBinding.getRoot());

        Log.w( TAG, "In onCreate() - Loading Widgets" );

        // creates a SharedPreferences object
        SharedPreferences prefs = getSharedPreferences("myData", MODE_PRIVATE);
        variableBinding.emailEditText.setText(prefs.getString("EmailAddress", ""));

        // on click listener
        variableBinding.loginButton.setOnClickListener( clk-> {
            // variable for the email address
            EditText email = variableBinding.emailEditText;

            // from where you are, to where you want to go
            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", email.getText().toString());

            // does the transition
            startActivity(nextPage);
        } );
    }

    @Override // now visible on screen
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "In onStart() - The application is now visible on screen." );
    }

    @Override // now responding to input touch
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "In onResume() - The application is now responding to user input" );
    }

    @Override // not listening to clicks
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "In onPause()- The application no longer responds to user input" );

        // saves the email address into Shared Preferences
        SharedPreferences.Editor editor = getSharedPreferences("myData", Context.MODE_PRIVATE).edit();
        editor.putString("EmailAddress", variableBinding.emailEditText.getText().toString());
        editor.apply();
    }

    @Override // no longer visible
    protected void onStop() {
        super.onStop();
        Log.w( TAG, "In onStop() - The application is no longer visible." );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( TAG, "In onDestroy() - Any memory used by the application is freed." );
    }
}