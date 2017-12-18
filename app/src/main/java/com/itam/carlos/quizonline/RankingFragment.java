package com.itam.carlos.quizonline;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RankingFragment extends Fragment {

    public WebView wv;

    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        WebView wv = (WebView) getView().findViewById(R.id.webview);
        // Unknown class: "PlanWebView";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_ranking, container, false);
        wv = (WebView) view.findViewById(R.id.webview);
        wv.loadUrl("https://comunidad.itam.mx");

        // Enable Javascript
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        wv.setWebViewClient(new WebViewClient());

        return view;
    }

}
