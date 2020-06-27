package com.oppoindia.billionbeats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("refferal")
    @Expose
    private String refferal;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("reffere")
    @Expose
    private String reffere;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("password2")
    @Expose
    private String password2;

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    //private String refferal_count;

    public String getRefferal() {
        return refferal;
    }

    public void setRefferal(String refferal) {
        this.refferal = refferal;
    }

    public String getReffere() {
        return reffere;
    }

    public void setReffere(String reffere) {
        this.reffere = reffere;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }






    @Override
    public String toString() {
        return "UserInfo{" +

                ", name='" + name + '\'' +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                ", refferal_code='" + refferal + '\'' +
                ", city='" + city + '\'' +
                ", refferee_code='" + reffere + '\'' +
                 '\'' +
                '}';
    }

}
