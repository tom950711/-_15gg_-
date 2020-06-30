package com.example.proto_4;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    MenuItem mSearch;
    private ArrayList<String> mNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
    }

    //메뉴 생성하는 onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //search_menu.xml 등록
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        mSearch=menu.findItem(R.id.search);

        //메뉴 아이콘 클릭했을 시 확장, 취소했을 시 축소
//        mSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                TextView text=(TextView)findViewById(R.id.txtstatus);
//                text.setText("현재 상태 : 확장됨");
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                TextView text=(TextView)findViewById(R.id.txtstatus);
//                text.setText("현재 상태 : 축소됨");
//                return true;
//            }
//        });


        //menuItem을 이용해서 SearchView 변수 생성
        SearchView sv=(SearchView)mSearch.getActionView();
        //확인버튼 활성화
        sv.setSubmitButtonEnabled(true);

//        //SearchView의 검색 이벤트
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            //검색버튼을 눌렀을 경우
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                TextView text = (TextView)findViewById(R.id.txtresult);
//                text.setText(query + "를 검색합니다.");
//                return true;
//            }
//
//            //텍스트가 바뀔때마다 호출
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                TextView text = (TextView)findViewById(R.id.txtsearch);
//                text.setText("검색식 : "+newText);
//                return true;
//            }
//        });
        return true;
    }

    // 검색 확장,축소를 버튼으로 생성
//    public void mOnClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnexpand:
//                mSearch.expandActionView();
//                break;
//            case R.id.btncollapse:
//                mSearch.collapseActionView();
//                break;
//        }
//    }

    private void init() {
        mNames.add("산모·신생아건강관리 지원사업");
        mNames.add("선천성대사이상검사 및 환아관리 지원");
        mNames.add("(독립유공자등)단순수훈유족생계부조금(제수비 포함)");
        mNames.add("(특수교육대상자) 치료지원서비스");
        mNames.add("6.25자녀수당");
        mNames.add("60세이상고령자고용지원금");
        mNames.add("WEE 클래스 상담지원");
        mNames.add("가정양육수당 지원");
        mNames.add("가정위탁아동 상해보험료 지원");
        mNames.add("가정폭력·성폭력 피해자 무료법률지원");
        mNames.add("가정폭력상담소 운영지원");
        mNames.add("가정폭력피해자 치료회복 프로그램 및 의료비지원");
        mNames.add("가출청소년 보호지원 쉼터 운영 지원");
        mNames.add("개발제한구역 거주민 생활비용보조사업");
        mNames.add("건강가정지원센터운영");
        mNames.add("결혼이민자 통번역 서비스");

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.searchrecycler);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.pupple)
                .size(15)
                .build());
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}