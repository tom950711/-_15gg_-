package com.example.proto_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DetailActivicy2 extends AppCompatActivity {

    private static final String TAG = DetailActivicy.class.getSimpleName();
    private TextView txt_example1;
    private TextView txt_example2;
    private TextView txt_example3;
    private TextView txt_example4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activicy);

        getIncomingIntent();


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
        span3.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        readTxT();
    }

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
        Service_id = input_service.getText().toString();
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
            ser2 = array_[11];
            ser3 = array_[12];
            ser4 = array_[13];

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView3.setText(ser2);
        textView5.setText(ser3);
        textView7.setText(ser4);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "get IncomingIntent: checking for incoming intents.");
        if (getIntent().hasExtra("name")) {
            Log.d(TAG,"getIncomingIntent: found intent extras.");

            String nxname = getIntent().getStringExtra("name");
            setname(nxname);
        }
    }

    private void setname(String nxname){
        Log.d(TAG, "setImage: setting the name");

        TextView name = findViewById(R.id.textView);
        name.setText(nxname);
    }
}