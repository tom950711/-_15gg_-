package com.example.proto_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class RecommandActivity extends AppCompatActivity {

    private TextView txt_example1;

    static ArrayList<Integer> values = new ArrayList<>();

    private ArrayList<String> mNames = new ArrayList<>();
    static String TAG = "Recommend";


    static Client load;

    // 소켓통신에 필요한것
    private String html = "";
    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "172.30.1.5";            // 내 IP 번호
    private int port = 9999;                          // port 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommand);

        FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();

        load = new Client();
        load.setUID(log_user.getUid());
        load = MainActivity.db.clientDao().loadClients(load.getUID());
        Log.d(TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        int size = 130;


        TextView textView = (TextView) findViewById(R.id.textView10);
//        textView.setText(load.getName());






        Log.d(TAG,load.toString());
//        startToast("<"+load.getBirth().substring(0,4)+","+load.getGender()+","+load.getChildren()+","+load.getDisabled()+","+load.getMoney()+">");

        connect();

        while(true){
            if(values.size()==10) {
                init();
                break;
            }
        }


    }

    private void init() {





        String data;
        int a = 0;
        String[] array;
        String[] array_ = new String[values.size()];
        String[] recommend_array;

        InputStream inputStream = getResources().openRawResource(R.raw.recommend);
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

            forout:
            for(int k = 0; k<array.length; k++){

                if (array[k].contains("!@"+values.get(a)+".")&& a < 10) {
                    data = array[k];
                    data = data.trim();

                    recommend_array = data.split("!");
                    recommend_array = recommend_array[1].split(",");
                    array_[a] = recommend_array[0];
                    a++;
                    if(a==values.size()){
                        break forout;
                    }
                }
            }
            for(int k = 0; k<array_.length; k++){
                mNames.add(array_[k]);
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initRecyclerView();
    }


    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recomendrecycler);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.blue)
                .size(15)
                .build());
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    void connect(){
        mHandler = new Handler();

        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                // ip받기
                //String newip = String.valueOf(ip_edit.getText()); 여기는 text부분으로 ip주소 입력하는 곳
                String newip = "172.30.1.16"; //여기에 파이썬 ip주소를 적으면 입력안해도 자동으로 소켓통신

                // 서버 접속
                try {
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 :  ","안드로이드에서 서버로 연결요청" );

                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
//                    dos.writeUTF("<1932,female,Yes,Yes,Yes>"); // 안드로이드에서 보낼 때임 이게!!!!!
                    dos.writeUTF("<"+load.getBirth().substring(0,4)+","+load.getGender()+","+load.getChildren()+","+load.getDisabled()+","+load.getMoney()+">");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                //System.out.println("try 전단계");
                // 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    String line = "";
                    int line2;
                    while(true) {
                        values = new ArrayList<Integer>();
                        for(int i=0;i<20;i++) {

                            //System.out.println("읽기 전에 나오는 문구");
                            line = (String) dis.readUTF();
                            //System.out.println("읽기 성공하면 나오는 문구");
                            line2 = (int) dis.read(); // 이게 안드로이드에서 받을 때임!!!!!
                            //Integer.toString(line2); // 숫자를 문자로?
                            //System.out.println("읽기 성공하면 나오는 문구2");
                            if(line2 != 0) {
                                values.add(line2);
                            }
                        }

                        System.out.println(values);



                        /*if(line2 > 0) {
                            Log.w("------서버에서 받아온 값 ", "" + line2);
                            dos.writeUTF("하나 받았습니다. : " + line2);
                            dos.flush();
                        }
                        if(line2 == 1000) {
                            Log.w("------서버에서 받아온 값 ", "" + line2);
                            socket.close();
                            break;
                        }*/

//                        Log.w("서버에서 받아온 값 ",""+line);
//                        System.out.println(line);
//                        System.out.println(line2);
//                        Log.w("서버에서 받아온 값 ",""+line2);
                    }
                }catch (Exception e){

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();

    }
}


