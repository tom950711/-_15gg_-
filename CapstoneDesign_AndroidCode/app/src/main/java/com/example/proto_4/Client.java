package com.example.proto_4;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Client {

    @PrimaryKey
    @NonNull
    private String UID;

    private String name;
    private String phone;
    private String birth;
    private String address;
    private String gender;
    private String disabled;
    private String children;
    private String money;


//    public Client(String UID, String name, String phone, String birth, String address, String gender, String disabled, String children, String money) {
//        this.UID = UID;
//        this.name = name;
//        this.phone = phone;
//        this.birth = birth;
//        this.address = address;
//        this.gender = gender;
//        this.disabled = disabled;
//        this.children = children;
//        this.money = money;
//    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    @Override
    public String toString() {
        return "Client{" +
                "UID=" + UID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", disabled='" + disabled + '\'' +
                ", children='" + children + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
