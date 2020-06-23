package edu.gatech.seclass.sdpencryptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    // define fields that reference to widgets
    private EditText messageID;
    private EditText keyNumberID;
    private EditText incrementNumberID;
    private EditText encryptedMessageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // method allow to go from ID to object and widgets
        messageID = findViewById(R.id.messageID);
        keyNumberID = findViewById(R.id.keyNumberID);
        incrementNumberID = findViewById(R.id.incrementNumberID);
        encryptedMessageID = findViewById(R.id.encryptedMessageID);

    }

    // associate handler to a widgets which generate a event. (throw a view object)
    public void handleClick(View view) {
        if (view.getId() == R.id.encryptButtonID) {
            if (messageID.getText().toString().matches("")) {
                messageID.setError("Invalid Message");
            }
            if (keyNumberID.getText().toString().matches("")) {
                keyNumberID.setError("Invalid Key Number");
            }
            if (incrementNumberID.getText().toString().matches("")) {
                incrementNumberID.setError("Invalid Increment Number");
            }
            if (!messageID.getText().toString().isEmpty() && (messageID.getText().toString().matches("[0-9]+") && messageID.getText().toString().length() > 2)) { // if letterless
                //System.out.println("error" + Pattern.matches("[a-zA-Z]+", messageID.getText().toString()));
                messageID.setError("Invalid Message");
            }
            if (!keyNumberID.getText().toString().matches("")) {
                if (Integer.parseInt(keyNumberID.getText().toString()) < 1 ||
                        Integer.parseInt(keyNumberID.getText().toString()) >= 26) { // if key not valid
                    keyNumberID.setError("Invalid Key Number");
                }
            }
            if (!incrementNumberID.getText().toString().matches("")) {
                if (Integer.parseInt(incrementNumberID.getText().toString()) < 1 ||
                        Integer.parseInt(incrementNumberID.getText().toString()) >= 26) {
                    incrementNumberID.setError("Invalid Increment Number");
                }
            }
            if (!messageID.getText().toString().matches("") && !keyNumberID.getText().toString().matches("") && !incrementNumberID.getText().toString().matches("")) {
                if (!messageID.getText().toString().matches("[0-9]+") && messageID.getText().toString().length() > 2
                        && Integer.parseInt(keyNumberID.getText().toString()) >= 1 && Integer.parseInt(keyNumberID.getText().toString()) < 26
                        && Integer.parseInt(incrementNumberID.getText().toString()) >= 1 && Integer.parseInt(incrementNumberID.getText().toString()) < 26) {
                    String result;
                    String inputMessage = messageID.getText().toString();
                    Integer keyNumber = Integer.parseInt(keyNumberID.getText().toString());
                    Integer incrementNumber = Integer.parseInt(incrementNumberID.getText().toString());
                    result = encryptFunction(inputMessage, keyNumber, incrementNumber);
                    encryptedMessageID.setText(result);
                }
            }
        }
    }

    // main function doing encryption
    private String encryptFunction(String inputMessage, Integer keyNumber, Integer incrementNumber) {

        String[] lowerCaseArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] upperCaseArray = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder outputString = new StringBuilder();

        int charOnlyIdx = 0; //create idx that count only alphabetic char
        for (int charIdx = 0; charIdx < inputMessage.length(); charIdx = charIdx + 1) { // iterate along message
            char messageChar = inputMessage.charAt(charIdx); //get message character

            int M = (keyNumber + (charOnlyIdx * incrementNumber)) % 26; //get encryption index M

            if (Character.isUpperCase(messageChar)) { // if upper case
                //find out character initial position index
                int temp_integer = 64; //for lower case
                int charPosition = 0;
                if ((int) messageChar <= 90 & (int) messageChar >= 65) {
                    charPosition = (int) messageChar - temp_integer;
                }
                //create a replacement string
                String replaceString = lowerCaseArray[(M + charPosition - 1) % 26];
                outputString.append(replaceString);
                charOnlyIdx += 1;
            } else if (Character.isLowerCase(messageChar)) {// if lower case
                //find out character initial position index
                int temp_integer = 96; //for lower case
                int charPosition = 0;
                if ((int) messageChar <= 122 & (int) messageChar >= 97) {
                    charPosition = (int) messageChar - temp_integer;
                }
                //create a replacement string
                String replaceString = upperCaseArray[(M + charPosition - 1) % 26];
                outputString.append(replaceString);
                charOnlyIdx += 1;
            } else {// if others, keep as the same
                String replaceString = Character.toString(messageChar);
                outputString.append(replaceString);
            }
        }
        return outputString.toString();
    }
}