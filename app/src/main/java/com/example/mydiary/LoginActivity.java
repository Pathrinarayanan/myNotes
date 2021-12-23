package com.example.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    EditText lEmail,lPassword;
    Button loginNow,lgm;
    TextView forgetPass,createAcc;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    boolean isBound = false;
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent i = new Intent(this, MyLocalService.class);
//        bindService(i,connection,BIND_AUTO_CREATE);
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(isBound){
//            unbindService(connection);
//            isBound = false;
//        }
//    }
//    public void displaydate(){
//        if(isBound){
//            Date date = localService.getcurrentDate();
//            Toast.makeText(this,String.valueOf(date),Toast.LENGTH_LONG).show();
//        }
//
//    }
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            MyLocalService.LocalBinder binder = (MyLocalService.LocalBinder) iBinder;
//            localservice = (MyLocalService.LocalBinder).binder.getService;
//            isBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//isBound = false;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        lEmail = findViewById(R.id.Emails);
        lPassword = findViewById(R.id.lPassword);
        loginNow = findViewById(R.id.loginButton);


        forgetPass = findViewById(R.id.forgotPassword);
        createAcc = findViewById(R.id.createAccount);
        user = FirebaseAuth.getInstance().getCurrentUser();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUserAccount()
    {

        String emails, passwords;
        emails = lEmail.getText().toString();
        passwords= lPassword.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(emails)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(passwords)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        fAuth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    Intent intentr
                                            = new Intent(LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intentr);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });
    }
    public void onBackPressed(){

    }
}


