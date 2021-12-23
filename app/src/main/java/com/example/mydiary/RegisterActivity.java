package com.example.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface verification {
    public   boolean isValid(String email);

    public  boolean isValidUsername(String name);

}


public class RegisterActivity extends AppCompatActivity implements  verification {
    private static final Logger logger = Logger.getLogger("logging sample");

    EditText rUserName, rUserEmail, rUserPass, rUserConfPass;
    Button createAccount;
    TextView loginAct;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    public boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean isValidUsername(String name) {

        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        rUserName = findViewById(R.id.name);
        rUserEmail = findViewById(R.id.EmailRegister);
        rUserPass = findViewById(R.id.registerPassword);
        rUserConfPass = findViewById(R.id.Confirmpassword);

        createAccount = findViewById(R.id.signup);
        loginAct = findViewById(R.id.loginNav);


        fAuth = FirebaseAuth.getInstance();

        loginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.mydiary.LoginActivity.class));
            }
        });


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String name = rUserName.getText().toString();
                String emailreg = rUserEmail.getText().toString();
                String passwordreg = rUserConfPass.getText().toString();

                fAuth.createUserWithEmailAndPassword(emailreg, passwordreg)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Registration successful!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar

                                String uid = task.getResult().getUser().getUid();
                                // after adding this data we are showing toast message.
                                Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                                Intent intent
                                        = new Intent(RegisterActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                            } else {

                                // Registration failed

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                            }

                            try {
                                if (!isValidUsername(name)) {
                                    throw new Exception("Name format error");
                                }

                            } catch (Exception NameException) {
                                Toast.makeText(getApplicationContext(), "Please enter valid username with alphabets", Toast.LENGTH_SHORT).show();
                            }
                            try {
                                if (!isValid(emailreg)) {
                                    throw new NumberFormatException("Email format error");
                                }

                            } catch (Exception EmailException) {
                                Toast.makeText(getApplicationContext(), "Please enter the valid email", Toast.LENGTH_SHORT).show();
                            }

                            try {
                                if (passwordreg.length() < 8) {
                                    throw new Exception("Size Error");
                                }
                            } catch (Exception passwordException) {
                                Toast.makeText(getApplicationContext(), "Password size must be atleast 8", Toast.LENGTH_SHORT).show();
                            }
                            if (isValidUsername(name) && isValid(emailreg) && passwordreg.length() >= 8) {
                                Toast.makeText(getApplicationContext(), "Sucessfully registered ", Toast.LENGTH_SHORT).show();


                            }


                        });

            }
        });


    }


}