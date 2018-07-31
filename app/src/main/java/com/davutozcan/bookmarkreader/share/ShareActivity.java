package com.davutozcan.bookmarkreader.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.davutozcan.bookmarkreader.R;

import java.net.URL;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

public class ShareActivity extends Activity
{
    static final String TAG = ShareActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    void handleSendText(Intent intent)
    {
        try {
            String receivedURL = intent.getStringExtra(Intent.EXTRA_TEXT);
            onUrlReceived(receivedURL);
        } catch (Exception e) {
            Log.i(TAG, "url failed");
            Toast.makeText(this , R.string.failed , Toast.LENGTH_SHORT).show();
        }
    }

    private void onUrlReceived(String receivedURL) {
        Log.i(TAG, "onUrlReceived: "+receivedURL);
        receivedURL = fixPreUrl(receivedURL);
        if(isUrl(receivedURL)) {
            if(isAlreadyAdded(receivedURL)) {
                Toast.makeText(this , R.string.already_added , Toast.LENGTH_SHORT).show();
            }else {
                addNew(receivedURL);
                Log.i(TAG, "url added showing success toast");
                Toast.makeText(this , R.string.url_added , Toast.LENGTH_SHORT).show();
            }
        }else
            throw new RuntimeException("Invalid value received:"+receivedURL);
    }

    private boolean isAlreadyAdded(String urlToAdd) {
        return myApp().getRealmFacade().exists(urlToAdd);
    }

    private void addNew(String url) {
        myApp().getWebunitService().add(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }

    boolean isUrl(final String url) {
        try
        {
            URL mURl = new URL(url);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    private String fixPreUrl(String url) {
        if(url != null && url.startsWith("www."))
            url = "http://"+url;
        return url;
    }
}
