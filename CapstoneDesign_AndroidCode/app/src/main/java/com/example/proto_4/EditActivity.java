package com.example.proto_4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    // private static final TAG = "EditActivity";
    // private FirebaseAuth mAuth;
    static String email;
    static String UID, named,gender;
    static String Money = "No";
    static String Disable = "No";
    static String Child = "No";
    static String TAG = "Edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //  mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.NextButton).setOnClickListener(onClickListener);

        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        CheckBox money_check = findViewById(R.id.MoneyBox);
        CheckBox disabled_check = findViewById(R.id.DisabledBox);
        CheckBox child_check = findViewById(R.id.ChildBox);

        money_check.setOnClickListener(Clickname);
        disabled_check.setOnClickListener(Clickname);
        child_check.setOnClickListener(Clickname);

//        FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();
//        startToast(log_user.getUid());
//
//        Client load = new Client();
//        load.setUID(log_user.getUid());
//        load = MainActivity.db.clientDao().loadClients(load.getUID());
//
//
//        Log.d(TAG,load.toString());
//        startToast("<"+load.getBirth().substring(0,4)+","+load.getGender()+","+load.getChildren()+","+load.getDisabled()+","+load.getMoney()+">");


    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.NextButton:
                    profile();
                    break;
            }

        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup rg, @IdRes int i) {
            if(i == R.id.input_male){
                gender = "male";
//                startToast(gender);
            }
            else if(i == R.id.input_female){
                gender = "female";
            }
        }
    };

    public void profile(){
        FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();
        if(log_user!=null){
            email =  log_user.getEmail();
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();
//        startToast(UID);
        named = ((EditText)findViewById(R.id.inputName)).getText().toString();
        String phone = ((EditText)findViewById(R.id.input_phonenumber)).getText().toString();
        String birth = ((EditText)findViewById(R.id.input_Rn)).getText().toString();
        String address = ((EditText)findViewById(R.id.input_address)).getText().toString();

        Client client = new Client();
        client.setUID(UID);
        client.setName(named);
        client.setPhone(phone);
        client.setGender(gender);
        client.setBirth(birth);
        client.setAddress(address);
        client.setDisabled(Disable);
        client.setChildren(Child);
        client.setMoney(Money);



        if(named.length()>0 && phone.length()>9 && birth.length()>5 && address.length()>0 && gender.length()>0){

            MainActivity.db.clientDao().update(client);
            Log.d(TAG,client.toString());

//            db.clientDao().insert(new Client(UID, named, phone,birth,address,gender,null,null,null));
//            Edit_info edit_info = new Edit_info(UID, named, phone, birth, address, gender);
//            startToast(Disable + Child + Money);
            startActivity(MainActivity.class);





//
//            if(user!=null){
//                db.collection("users").document(email).set(edit_info)
//                        .addOnSuccessListener(aVoid -> {
//
//
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                startToast("제대로 해라");
//                                //               Log.w(TAG, "Error writing document", e);
//                            }
//                        });
//            }
        }
        else {
            startToast("회원정보를 모두 입력해 주세요.");
        }
    }
    View.OnClickListener Clickname = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ChildBox:
                    Child = "Yes";
                    break;
                case R.id.MoneyBox:
                    Money = "Yes";
                    break;
                case R.id.DisabledBox:
                    Disable = "Yes";
                    break;
            }
        }
    };

    private void startActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}