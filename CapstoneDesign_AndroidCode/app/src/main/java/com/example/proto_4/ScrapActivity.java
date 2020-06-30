package com.example.proto_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class ScrapActivity extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    static Scrap load;
    static String TAG = "Scrap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Scrap insert = new Scrap();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);
        findViewById(R.id.textView9);

        FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();

        insert.setUID(log_user.getUid());
        insert.setNum1("1");
        insert.setNum2("2");


        load = new Scrap();
        load.setUID(log_user.getUid());
//        load = MainActivity.db.scrapDao().loadScrap(load.getUID());

        Log.d(TAG,load.show());

        init();
    }

    private void init() {
        mNames.add("산모·신생아건강관리 지원사업");

        mNames.add("선천성대사이상검사 및 환아관리 지원");

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.scraprecycler);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.pupple)
                .size(15)
                .build());
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}