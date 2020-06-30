package com.example.proto_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase db;
    public static AppDatabase sc;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ShimmerTextView tv;
        ShimmerTextView tv1;
        Shimmer shimmer;


        // DB ROOM(SQLite 생성)
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"client-db").allowMainThreadQueries().build();
//        context = this;
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerConetent(nvDrawer);

        if(user == null){
            startActivity(LoginActivity.class);//로그인 돼있지 않으면 로그인창으로 넘어감
        }

        FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();




        findViewById(R.id.chatBotButton).setOnClickListener(onClickListener);
        findViewById(R.id.mybutton).setOnClickListener(onClickListener);

        tv = (ShimmerTextView) findViewById(R.id.shimmer_tv);
        tv1 = (ShimmerTextView) findViewById(R.id.shimmer_tv1);
        shimmer = new Shimmer();
        shimmer.start(tv);
        shimmer.start(tv1);

        ImageView robot = (ImageView)findViewById(R.id.imageView3);
        Glide.with(this).load(R.drawable.robot).into(robot);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.mybutton:
                    startActivity(RecommandActivity.class);
                    break;

                case R.id.chatBotButton:
                    startActivity(MainChatbot.class);
                    break;
            }
        }
    };
    private void startActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectIterDrawer(MenuItem menuItem) {
//        Fragment myFragement = null;
//        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.db:
//                fragmentClass = EditActivity.class;
                startActivity(EditActivity.class);
                break;
            case R.id.scrap:
//                fragmentClass = ;
                startActivity(ScrapActivity.class);
                break;
            case R.id.search:
//                fragmentClass = ;
                startActivity(SearchActivity.class);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
//                fragmentClass = LoginActivity.class;
                startActivity(LoginActivity.class);
                break;
            default:
                startActivity(EditActivity.class);
        }
    }

    private void setupDrawerConetent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectIterDrawer(item);
                return true;
            }
        });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
