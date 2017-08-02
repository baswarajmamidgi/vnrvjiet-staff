package com.baswarajmamidgi.vnrstaff;

/**
 * Created by baswarajmamidgi on 21/02/17.
 */

public class Workshopclass {
    private String name;
    private String duration;
    private String contactno;
    private String registrationfee;
    private String ImageUrl;

    Workshopclass(String title,String duration,String contactno,String registrationfee,String ImageUrl){

        this.name=title;
        this.duration=duration;
        this.contactno=contactno;
        this.registrationfee=registrationfee;
        this.ImageUrl=ImageUrl;
    }

    public Workshopclass(String info, String imageurl) {
        this.name=info;
        this.ImageUrl=imageurl;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getContactno() {
        return contactno;
    }

    public String getRegistrationfee() {
        return registrationfee;
    }
    public String getImageUrl(){
        return ImageUrl;
    }
}
