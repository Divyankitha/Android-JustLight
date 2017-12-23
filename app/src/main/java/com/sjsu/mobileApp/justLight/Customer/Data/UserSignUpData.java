package com.sjsu.mobileApp.justLight.Customer.Data;



public class UserSignUpData
{
    public String username, password;
    public int ID = 0;

    public void setUsername(String uname)
    {
        this.username = uname;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setID(int id)
    {
        this.ID = id;
    }
}
