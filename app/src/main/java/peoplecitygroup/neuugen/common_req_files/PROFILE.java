package peoplecitygroup.neuugen.common_req_files;

import java.io.Serializable;

public class PROFILE implements Serializable {
    String mobileno,name,email,city,address,state,pincode,gender,dob,emailverified,profileflag,addressverified,pic;

    public PROFILE() {
    }

    public PROFILE(String mobileno, String name, String email, String city, String address, String state, String pincode, String gender, String dob, String emailverified, String profileflag, String addressverified, String pic) {
        this.mobileno = mobileno;
        this.name = name;
        this.email = email;
        this.city = city;
        this.address = address;
        this.state = state;
        this.pincode = pincode;
        this.gender = gender;
        this.dob = dob;
        this.emailverified = emailverified;
        this.profileflag = profileflag;
        this.addressverified = addressverified;
        this.pic = pic;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmailverified() {
        return emailverified;
    }

    public void setEmailverified(String emailverified) {
        this.emailverified = emailverified;
    }

    public String getProfileflag() {
        return profileflag;
    }

    public void setProfileflag(String profileflag) {
        this.profileflag = profileflag;
    }

    public String getAddressverified() {
        return addressverified;
    }

    public void setAddressverified(String addressverified) {
        this.addressverified = addressverified;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
