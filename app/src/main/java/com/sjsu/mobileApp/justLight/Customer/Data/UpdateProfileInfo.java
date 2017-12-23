package com.sjsu.mobileApp.justLight.Customer.Data;



public class UpdateProfileInfo {
    public String fullname,streetAddress,pincode,contact,workInfo,gender,registrationID,joinedDate,rating,Email,city,dob;

    public void setFullname(String uname)
    {

        this.fullname = uname;
    }

    public void setAddress(String add)
    {
        this.streetAddress = add;
    }
    public void setRating(String ra)
    {
        this.rating = ra;
    }
    public void setJoinedDate(String date)
    {
        this.joinedDate = date;
    }
    public void setRegistrationID(String id)
    {
        this.registrationID = id;
    }
    public void setCity(String ci)
    {
        this.city = ci;
    }
    public void contact(String co){
        this.contact=co;
    }
    public void setPincode(String pin)
    {
        this.pincode = pin;
    }
    public void setWork(String wo)
    {
        this.workInfo = wo;
    }
    public void setMobile(String mob)
    {
        this.contact = mob;
    }
    public void setGender(String gen)
    {
        this.gender = gen;
    }
    public void setEmail(String em)
    {
        this.Email = em;
    }
    public void setDob(String dob)
    {
        this.dob = dob;
    }
}
