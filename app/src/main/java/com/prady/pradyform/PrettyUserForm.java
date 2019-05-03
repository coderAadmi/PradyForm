package com.prady.pradyform;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.PatternMatcher;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class PrettyUserForm extends AppCompatActivity {

    private EditText mUsername, mEmailId, mPhoneNo, mPassword, mConfirmedPassword;
    private Button mSubmit;

    public static String USER_REGISTER_INFO = "Poloman";
    public static String USER_NAME = "usern_name";
    public static String USER_EMAIL_ID = "user_email_id";
    public static String USER_PHONE_NO = "user_phone_no";
    public static String USER_PASSWORD = "user_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_user_form);

        mUsername = findViewById(R.id.userName);
        mEmailId = findViewById(R.id.emailId);
        mPhoneNo = findViewById(R.id.phoneNo);
        mPassword = findViewById(R.id.password);
        mConfirmedPassword = findViewById(R.id.confirmedPAssword);
        mSubmit = findViewById(R.id.submitButton);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDetails();
            }
        });
    }

    private void submitDetails()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mConfirmedPassword.getWindowToken(),0);
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        String username = mUsername.getText().toString();
        if(!(username.length()>=3 && username.length()<=20))
        {
            mUsername.setText("");
            mUsername.setHint("Username Length(3 to 20)");
            mUsername.setHintTextColor(Color.RED);
            return;
        }
        String emailId = mEmailId.getText().toString();
        if(!(pattern.matcher(emailId).matches()))
        {
            mEmailId.setText("");
            mEmailId.setHintTextColor(Color.RED);
            mEmailId.setHint("Enter correct Email");
            return;
        }
        String phoneNo = mPhoneNo.getText().toString();
        if(!(phoneNo.length()==10))
        {
            mPhoneNo.setText("");
            mPhoneNo.setHint("Enter correct phone no");
            mPhoneNo.setHintTextColor(Color.RED);
            return;
        }
        String password = mPassword.getText().toString();
        if(!(password.length()>8))
        {
            mPassword.setText("");
            mPassword.setHintTextColor(Color.RED);
            mPassword.setHint("Passowrd length > 8");
            return;
        }
        if(!(mConfirmedPassword.getText().toString().equals(password)))
        {
            mPassword.setText("");
            mConfirmedPassword.setHintTextColor(Color.RED);
            mConfirmedPassword.setHint("Password didn't match");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(USER_REGISTER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_NAME,username);
        editor.putString(USER_EMAIL_ID,emailId);
        editor.putString(USER_PHONE_NO,phoneNo);
        editor.putString(USER_PASSWORD,password);
        editor.apply();

        Toast.makeText(PrettyUserForm.this,"Info saved successfully",Toast.LENGTH_SHORT).show();
        thankyouUser();

    }

    private void thankyouUser()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(),R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle(R.string.thankYou);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
