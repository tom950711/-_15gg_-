package com.example.proto_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailActivicy extends AppCompatActivity {
    private static final String TAG = DetailActivicy.class.getSimpleName();

    static String Service_number;

    private TextView txt_example1;
    private TextView txt_example2;
    private TextView txt_example3;
    private TextView txt_example4;

    static ImageButton imageButton1;
    static ImageButton imageButton2;

    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activicy);

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.textView) ;
        String name = intent.getStringExtra("name") ;

        textView.setText(name);

        txt_example1 = (TextView) findViewById(R.id.textView1);
        Spannable span = (Spannable) txt_example1.getText();
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_example2 = (TextView) findViewById(R.id.textView4);
        Spannable span2 = (Spannable) txt_example2.getText();
        span2.setSpan(new ForegroundColorSpan(Color.RED), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_example3 = (TextView) findViewById(R.id.textView6);
        Spannable span3 = (Spannable) txt_example3.getText();
        span3.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_example4 = (TextView) findViewById(R.id.textView8);
        Spannable span4 = (Spannable) txt_example4.getText();
        span4.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//지워도 되지 않나

        readTxT();
        init();

        imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton)findViewById(R.id.imageButton2);

        imageButton1.setVisibility(View.VISIBLE);
        imageButton2.setVisibility(View.INVISIBLE);

        findViewById(R.id.imageButton1).setOnClickListener(onClickListener);
        findViewById(R.id.imageButton2).setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.imageButton1:
                    imageButton1.setVisibility(View.INVISIBLE);
                    imageButton2.setVisibility(View.VISIBLE);
                    break;

                case R.id.imageButton2:
                    imageButton1.setVisibility(View.VISIBLE);
                    imageButton2.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    private void readTxT(){

        final String TAG ="Read";
        String data;
        String Service_id;

        String[] array;
        String[] array_;

        TextView textView3 = findViewById(R.id.textView3);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView7 = findViewById(R.id.textView7);

        TextView input_service = (TextView) findViewById(R.id.textView);

        data = null;
        Service_id = input_service.getText().toString(); //이름 받아옴
        InputStream inputStream = getResources().openRawResource(R.raw.new_welfare_service_1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String ser2 = null;
        String ser3 = null;
        String ser4 = null;

        int i;
        try{

            i = inputStream.read();

            while(i!=-1){

                byteArrayOutputStream.write(i);//스트림이 끝에 도달하면 i -1반환
                //byteArrayOutputStream을 사용하면 내부적으로 저장공간이 있어 해당 메소드를 이용해서 출력하게 되면
                //출력되는 내용이 내부적인 저장공간에 쌓이게 됨
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"MS949");// toByteArray()를 이용하면 저장된 모든 내용이 바이트 배열로 반환
            array = data.split("&&");


            for(int k = 0; k<array.length; k++){
                if (array[k].contains("!"+ Service_id)) {
                    data = array[k];
//                    Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1"+k+"");
                }
            }
            Log.d(TAG, Service_id);
            array_ = data.split("@");

            Service_number = array_[1];
            ser2 = array_[11];
            ser3 = array_[12];
            ser4 = array_[13];

//            startToast(ser2+" "+ser3+" "+ser4);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView3.setText(ser2);
        textView5.setText(ser3);
        textView7.setText(ser4);
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private String readNum(String number){

        final String TAG ="Read";
        String data;
        String[] array;
        String[] array_;
        String[] array__;

        data = null;

        InputStream inputStream = getResources().openRawResource(R.raw.new_welfare_service_1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String ser2 = null;

        int i;
        try{

            i = inputStream.read();

            while(i!=-1){

                byteArrayOutputStream.write(i);//스트림이 끝에 도달하면 i -1반환
                //byteArrayOutputStream을 사용하면 내부적으로 저장공간이 있어 해당 메소드를 이용해서 출력하게 되면
                //출력되는 내용이 내부적인 저장공간에 쌓이게 됨
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"MS949");// toByteArray()를 이용하면 저장된 모든 내용이 바이트 배열로 반환
            array = data.split("&&");


            for(int k = 0; k<array.length; k++){
                if (array[k].contains("@"+number)) {

                    data = array[k];
                }
            }

            array_ = data.split("@!");
            array__ = array_[1].split(",");


            ser2 = array__[0];



            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ser2;
    }

    private void init() {

        String data;


        String[] array;
        String[] array_;

        InputStream inputStream = getResources().openRawResource(R.raw.doc2vec_result);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String ser2 = null;
        String ser3 = null;
        String ser4 = null;
        String ser5 = null;
        String ser6 = null;

        int i;
        try{

            i = inputStream.read();

            while(i!=-1){

                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            array = data.split("\\n");

//            startToast(Service_number);
            for(int k = 0; k<array.length; k++){

                if (array[k].contains("!"+Service_number)) {
//                    startToast("성공");
                    data = array[k];
                }
            }
            data = data.trim(); //공백제거
            array_ = data.split("/");
//            for(int k = 1; k<array_.length; k++){
//                startToast(array_[k]);
//            }
            if (array_.length == 6) {
                ser2 = array_[1];
                mNames.add(readNum(ser2));
                ser3 = array_[2];
                mNames.add(readNum(ser3));
                ser4 = array_[3];
                mNames.add(readNum(ser4));
                ser5 = array_[4];
                mNames.add(readNum(ser5));
                ser6 = array_[5];
                mNames.add(readNum(ser6));
            }
            else if (array_.length == 5) {
                ser2 = array_[1];
                mNames.add(readNum(ser2));
                ser3 = array_[2];
                mNames.add(readNum(ser3));
                ser4 = array_[3];
                mNames.add(readNum(ser4));
                ser5 = array_[4];
                mNames.add(readNum(ser5));
            }
            else if (array_.length == 4) {
                ser2 = array_[1];
                mNames.add(readNum(ser2));
                ser3 = array_[2];
                mNames.add(readNum(ser3));
                ser4 = array_[3];
                mNames.add(readNum(ser4));
            }
            else if (array_.length == 3) {
                ser2 = array_[1];
                mNames.add(readNum(ser2));
                ser3 = array_[2];
                mNames.add(readNum(ser3));
            }
            else if (array_.length == 2) {
                ser2 = array_[1];
                mNames.add(readNum(ser2));
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.pupple)
                .size(15)
                .build());
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}