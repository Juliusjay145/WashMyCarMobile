package com.example.washmycar;

public class ProfileList {

    private String image,id, name,email,address,contact;

    public ProfileList(String image, String id, String name, String email, String address, String contact) {
        super();
        this.image = image;
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() { return email; }

    public void  setEmail(String email) {  this.email = email; }

    //end of email

    public String getAddress() { return address; }

    public void  setAddress(String address) {  this.address = address; }

    //end of address

    public String getContact() { return contact; }

    public void  setContact(String contact) {  this.contact = contact; }

    //end of contact










}
