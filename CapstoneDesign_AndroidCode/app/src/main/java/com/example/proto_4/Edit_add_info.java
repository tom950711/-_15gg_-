package com.example.proto_4;

public class Edit_add_info {

    private String marry;

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public String getDisabled_person() {
        return disabled_person;
    }

    public void setDisabled_person(String disabled_person) {
        this.disabled_person = disabled_person;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    private String disabled_person;
    private String children;


    public Edit_add_info(String disabled_person, String children, String marry){
        this.disabled_person = disabled_person;
        this.marry = marry;
        this.children = children;
    }
}
