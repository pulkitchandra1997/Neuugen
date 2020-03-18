package peoplecitygroup.neuugen.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;


public class SMSReceiver extends BroadcastReceiver {

    private OTPReceiveListener otpListener;
    private String PreOtp;
    private int otp;
    public SMSReceiver(){

    }
    public SMSReceiver(String PreOtp,int otp){
        this.PreOtp = PreOtp;
        this.otp = otp;
        Log.d("otpcheck: ","smsreceiver created->"+PreOtp+otp);
    }

    /**
     * @param otpListener
     */
    public void setOTPListener(OTPReceiveListener otpListener) {
        this.otpListener = otpListener;
        Log.d("otpcheck","setOtplistener");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("otpcheck","onreceive1");

        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Log.d("otpcheck","onreceive2");

            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            Log.d("otpcheck","onreceive3: "+status.getStatusCode());

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:

                    //This is the full message
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                    /*<#> Your ExampleApp code is: 123ABC78
                    FA+9qCX9VSu*/

                    //Extract the OTP code and send to the listener

                    if (otpListener != null) {
                        otpListener.onOTPReceived(autoDetectOtp(message));
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    if (otpListener != null) {
                        otpListener.onOTPTimeOut();
                    }
                    break;

                case CommonStatusCodes.API_NOT_CONNECTED:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("API NOT CONNECTED");
                    }

                    break;

                case CommonStatusCodes.NETWORK_ERROR:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("NETWORK ERROR");
                    }

                    break;

                case CommonStatusCodes.ERROR:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("SOME THING WENT WRONG");
                    }

                    break;

            }
        }
    }

    public interface OTPReceiveListener {
        void onOTPReceived(String otp);
        void onOTPTimeOut();
        void onOTPReceivedError(String error);
    }

    private String autoDetectOtp(String message) {
        String code=parseCode(message);
        boolean isFound = code.indexOf(String.valueOf(otp)) !=-1? true: false;
        if(isFound)
            return code;
        else
            return "";
    }
    private String parseCode(String message) {
        boolean isFound = message.indexOf(PreOtp) !=-1? true: false;
        if(isFound)
            return message.split(":")[1].split(" ")[0];
        else
            return "";
    }
}
