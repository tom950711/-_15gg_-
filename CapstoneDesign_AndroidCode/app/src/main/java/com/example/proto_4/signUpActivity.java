package com.example.proto_4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static String email;
    static String TAG = "Signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.LoginButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotologinButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.LoginButton:
                    signUp();
                    break;
                case R.id.gotologinButton:
                    startActivity();
                    break;
            }

        }
    };
    private void signUp(){
        email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        final String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();
        if(email.length()>0 && password.length()>0 && passwordCheck.length()>0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();

                                    String UID = log_user.getUid();
                                    String named ="";
                                    String email = log_user.getEmail();;
                                    String phone ="";
                                    String birth ="";
                                    String address ="";
                                    String gender ="";
                                    String disabled ="";
                                    String children ="";
                                    String money ="";

                                    Client client = new Client();
                                    client.setUID(UID);
                                    client.setName(named);
                                    client.setName(email);
                                    client.setName(gender);
                                    client.setPhone(phone);
                                    client.setBirth(birth);
                                    client.setAddress(address);
                                    client.setDisabled(disabled);
                                    client.setChildren(children);
                                    client.setMoney(money);

                                    MainActivity.db.clientDao().insert(client);

                                    Log.d(TAG,client.toString());


                                    startActivity();
                                    startToast("회원가입 완료");
                                    //UI
                                } else {
                                    if(task.getException()!=null){
                                        if(password.length()<6){
                                            startToast("비밀번호를 6자리 이상 설정해주세요.");
                                        }
                                        else {
                                            startToast("중복된 이메일입니다.");
                                        }
                                    }
                                }

                                // ...
                            }
                        });
            }
            else{
                startToast("비밀번호가 일치하지 않습니다.");
            }
        }
        else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }

    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}
