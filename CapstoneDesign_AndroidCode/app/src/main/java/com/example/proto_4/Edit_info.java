package com.example.proto_4;

public class Edit_info {

    private String UID;
    private String name;
    private String phone;
    private String birth;
    private String address;
    private String gender;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirth() {
        return birth;
    }

    public String getAddress() {
        return address;
    }
    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public Edit_info(String UID, String name, String phone, String birth, String address,String gender){
        this.UID = UID;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.gender = gender;
    }
}
