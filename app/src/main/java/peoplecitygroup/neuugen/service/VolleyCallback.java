package peoplecitygroup.neuugen.service;

public interface VolleyCallback {
    void onSuccess(String result);

    void onError(String response);

    void onVolleyError();
}
