package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;

public class HelpSupportActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        webview=findViewById(R.id.webview);
        WebSettings wb=webview.getSettings();
        webview.loadUrl(UrlNeuugen.helpSupportActivity);
        wb.setJavaScriptEnabled(true);
        webview.setWebViewClient(new HelpSupportActivity.WebViewController());

        wb.setLoadsImagesAutomatically(true);
        webview.getSettings().setLoadWithOverviewMode(true);

    }

    private class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
