package algonquin.cst2335.cast0304;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.cast0304.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create a binding object
        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());

        // loads XML file on the page
        setContentView(variableBinding.getRoot());

        Intent fromPrevious = getIntent();
        String email = fromPrevious.getStringExtra("EmailAddress");

        variableBinding.textView.setText("Welcome back " + email);

        // creates a SharedPreferences object
        SharedPreferences prefs = getSharedPreferences("myData", MODE_PRIVATE);
        variableBinding.editTextPhone.setText(prefs.getString("PhoneNumber", ""));


        // on click listener for the call button
        variableBinding.button.setOnClickListener( clk-> {
            // variable for the phone number
            EditText phoneNum = variableBinding.editTextPhone;

            // call intent
            Intent call = new Intent(Intent.ACTION_DIAL);
            // sets the phone number for the call intent
            call.setData(Uri.parse("tel:" + phoneNum.getText().toString()));

            // starts the activity
            startActivity(call);
        } );

        // Loads existing image file
        File file = new File(getFilesDir(), "image.png");
        if (file.exists()) {
            Bitmap imgBitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/image.png");
            variableBinding.imageView2.setImageBitmap(imgBitmap);
        }

            // camera intent
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {

                        @Override
                        public void onActivityResult(ActivityResult result) {

                            if (result.getResultCode() == Activity.RESULT_OK) {

                                Intent data = result.getData();
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                variableBinding.imageView2.setImageBitmap( thumbnail );
                            }
                        }
                    } );

            // on click listener for the image button
            variableBinding.button2.setOnClickListener( clk-> {
                cameraResult.launch(cameraIntent);
            } );
    }

    protected void onPause(){
        super.onPause();

        // saves the phone number into shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("myData", MODE_PRIVATE).edit();
        editor.putString("PhoneNumber", variableBinding.editTextPhone.getText().toString());
        editor.apply();

        // saves the image file
        try (FileOutputStream fOS = openFileOutput("image.png", MODE_PRIVATE)) { // throws fileNotFoundException
            Bitmap bitmap = ((BitmapDrawable) variableBinding.imageView2.getDrawable()).getBitmap();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOS)) {
                fOS.flush(); // throws IOException
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}