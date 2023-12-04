package com.satdroid.serveup.DonorPanel;

public class Donor
{

    private String ConfirmPassword, Emailid, Fname,Lname, Mobile, Password, Pincode,AddressLine1,AddressLine2;

    // Press Alt+Insert


    public Donor(String confirmPassword, String emailid, String fname, String lname, String mobile, String password, String pincode,String addressLine1,String addressLine2)
    {
        this.AddressLine1= addressLine1;
        AddressLine2 = addressLine2;
        ConfirmPassword = confirmPassword;
        Emailid = emailid;
        Fname = fname;

        Lname = lname;
        Mobile = mobile;
        Password = password;
        Pincode = pincode;
    }
    public Donor() {
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public String getEmailid() {
        return Emailid;
    }

    public String getFname() {
        return Fname;
    }
    public String getLname() {
        return Lname;
    }
    public String getMobile() {
        return Mobile;
    }
    public String getPassword() {
        return Password;
    }

    public String getPincode() {
        return Pincode;
    }

}
