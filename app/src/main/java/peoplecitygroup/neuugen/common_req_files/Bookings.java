package peoplecitygroup.neuugen.common_req_files;

public class Bookings {
    String requestid;
    String mobileno;
    String serviceid;
    String houseno;
    String area;
    String city;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDateofservice() {
        return dateofservice;
    }

    public void setDateofservice(String dateofservice) {
        this.dateofservice = dateofservice;
    }

    public String getDateofrequest() {
        return dateofrequest;
    }

    public void setDateofrequest(String dateofrequest) {
        this.dateofrequest = dateofrequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletiondate() {
        return completiondate;
    }

    public void setCompletiondate(String completiondate) {
        this.completiondate = completiondate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(String noofdays) {
        this.noofdays = noofdays;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public Bookings(String requestid, String mobileno, String serviceid, String houseno, String area, String city, String city_id, String landmark, String pincode, String dateofservice, String dateofrequest, String status, String completiondate, String remark, String servicetype, String noofdays, String eventtype) {
        this.requestid = requestid;
        this.mobileno = mobileno;
        this.serviceid = serviceid;
        this.houseno = houseno;
        this.area = area;
        this.city = city;
        this.city_id = city_id;
        this.landmark = landmark;
        this.pincode = pincode;
        this.dateofservice = dateofservice;
        this.dateofrequest = dateofrequest;
        this.status = status;
        this.completiondate = completiondate;
        this.remark = remark;
        this.servicetype = servicetype;
        this.noofdays = noofdays;
        this.eventtype = eventtype;
    }

    String city_id;
    String landmark;
    String pincode;
    String dateofservice;
    String dateofrequest;
    String status;
    String completiondate;
    String remark;
    String servicetype;
    String noofdays;
    String eventtype;


}
