package com.sjsu.mobileApp.justLight.Customer.Data;



public class UserSignUpDetailsData
{
    public String fullname, type, address, city, pincode, workinfo, gender, dob,contact,email,registerID,jdate;

    public int userid = 0;

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setPincode(String pincode)
    {
        this.pincode = pincode;
    }

    public void setWorkinfo(String workinfo)
    {
        this.workinfo = workinfo;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setRegister(String registerid)
    {
        this.registerID = registerid;
    }

    public void setJdate(String jdate)
    {
        this.jdate = jdate;
    }
}
