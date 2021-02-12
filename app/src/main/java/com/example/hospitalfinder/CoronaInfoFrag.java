package com.example.hospitalfinder;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.hospitalfinder.webviewHelper.GeoWebChromeClient;
import com.example.hospitalfinder.webviewHelper.GeoWebViewClient;


public class CoronaInfoFrag extends Fragment {
    WebView webView;


    public CoronaInfoFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_corona_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new GeoWebViewClient(getActivity()));
        // Below required for geolocation
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebChromeClient(new GeoWebChromeClient());

        webView.setOnKeyListener( new View.OnKeyListener()
                                  {
                                      @Override
                                      public boolean onKey( View v, int keyCode, KeyEvent event )
                                      {
                                          //This is the filter
                                          if (event.getAction()!=KeyEvent.ACTION_DOWN)
                                              return true;
                                          if (keyCode == KeyEvent.KEYCODE_BACK) {
                                              if (webView.canGoBack()) {
                                                  webView.goBack();
                                              }
                                              else {
                                                  ((MainActivity)getActivity()).onBackPressed();
                                              }
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );


        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });



        String url = String.format("https://corona.gov.bd/");
        webView.loadUrl(url);

        webView.setFocusableInTouchMode(true);
        webView.requestFocus();

    }
}