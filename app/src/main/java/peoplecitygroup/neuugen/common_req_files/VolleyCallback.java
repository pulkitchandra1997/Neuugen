package peoplecitygroup.neuugen.common_req_files;

public interface VolleyCallback {
    void onSuccess(String result);

    void onError(String response);

    void onVolleyError();
}
