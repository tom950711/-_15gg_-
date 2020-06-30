package com.example.proto_4;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Scrap {

    @PrimaryKey
    @NonNull
    private String UID;

    private String num1;
    private String num2;
    private String num3;


    @NonNull
    public String getUID() {
        return UID;
    }

    public void setUID(@NonNull String UID) {
        this.UID = UID;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String show() {
        return "Client{" +
                "UID=" + UID +
                ", name='" + num1 + '\'' +
                ", phone='" + num2 + '\'' +
                ", birth='" + num3 + '\'' +
                "}";
    }
}
