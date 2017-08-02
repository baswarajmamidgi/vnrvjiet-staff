package com.baswarajmamidgi.vnrstaff;

/**
 * Created by baswarajmamidgi on 21/01/17.
 */
public class Carddetails {
    private String name;
    private int thumbnail;
    private String title;
    private String address;
    private String contact;
    private String mail;



    Carddetails(String name, int thumbnail) {
        this.name=name;
        this.thumbnail=thumbnail;
    }
    Carddetails(String title,String name,String address,String contact,String mail){
        this.title=title;
        this.name=name;
        this.address=address;
        this.contact=contact;
        this.mail=mail;

    }

    public String getName(){
        return name;
    }
    public int getThumbnail(){
        return thumbnail;
    }

    public String getContact() {
        return contact;
    }

    public String getMail() {
        return mail;
    }


    public String getTitle() {
        return title;
    }


    public String getAddress() {
        return address;
    }
}
