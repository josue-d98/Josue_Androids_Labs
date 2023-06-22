package algonquin.cst2335.cast0304;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the functionality of a login page that checks that the password
 * meets the requirements.
 * @author Josue Castro
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Holds the text at the centre of the screen
     */
    private TextView textv = null;

    /**
     * Holds the text that represents the user's password
     */
    private EditText editt = null;

    /**
     * Holds the button for the login
     */
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textv = findViewById(R.id.textView);
        editt = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener( clk -> {
            String password = editt.getText().toString();
            if(checkPasswordComplexity(password)) {
                textv.setText("Your password meets the requirements");
            } else
                textv.setText("You shall not pass!");
        });
    }

    /**
     * Checks that password has the correct information based on the
     * requirements of the password
     *
     * @param pw represents the password to be checked
     * @return returns true if all the requirements are met
     */
    private boolean checkPasswordComplexity(String pw) {

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        // for loop to iterate over each character in the String
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if(! foundUpperCase) {
            // toast message
            String tMessage = "Missing Uppercase Letter";
            Toast.makeText(getApplicationContext(), tMessage, Toast.LENGTH_SHORT).show();

            return false;
        }

        else if(!foundLowerCase ) {
            // toast message
            String tMessage = "Missing Lowercase Letter";
            Toast.makeText(getApplicationContext(), tMessage, Toast.LENGTH_SHORT).show();

            return false;
        }

        else if(! foundNumber) {
            // toast message
            String tMessage = "Missing Number";
            Toast.makeText(getApplicationContext(), tMessage, Toast.LENGTH_SHORT).show();

            return false;
        }

        else if(! foundSpecial) {
            // toast message
            String tMessage = "Missing Special Character";
            Toast.makeText(getApplicationContext(), tMessage, Toast.LENGTH_SHORT).show();

            return false;
        }

        else

        return true;
    }

    /**
     * Checks for a special character in the password, if no special character is found
     * returns false
     * @param c the character to be checked
     * @return true if a special character is found or false if no character
     */
    boolean isSpecialCharacter(char c) {
        //return true if c is one of: #$%^&*!@?
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}