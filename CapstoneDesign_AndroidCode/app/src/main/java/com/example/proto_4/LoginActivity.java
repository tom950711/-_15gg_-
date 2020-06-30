package com.example.proto_4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    public static String email;
    public static String password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.LoginButton).setOnClickListener(onClickListener);
        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotopassword).setOnClickListener(onClickListener);

    }
    public static String email(){
        String a = email;
        return email;
    }

    @Override public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.LoginButton:
                    Login();
                    break;
                case R.id.signUpButton:
                    passwordActivity(signUpActivity.class);
                    break;
                case R.id.gotopassword:
                    passwordActivity(passwordActivity.class);
                    break;
            }
        }
    };

    public void Login(){
        email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        if(email.length()>0 && password.length()>0 ){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                passwordActivity(MainActivity.class);
                                finish();
                            } else {
                                if (task.getException() != null) {
                                    startToast("이메일 또는 비밀번호를 제대로 입력해 주세요.");
                                }
                            }
                        }
                    });

        }
        else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }

    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    private void startActivity(){
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void signUpActivity(){
//        Intent intent = new Intent(this,signUpActivity.class);
//        startActivity(intent);
//
//    }
    private void passwordActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
