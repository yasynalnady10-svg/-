package com.kolhaga.app;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
  WebView w;
  @Override protected void onCreate(Bundle b) {
    super.onCreate(b);
    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
      requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    w = new WebView(this);
    setContentView(w);
    WebSettings s = w.getSettings();
    s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setGeolocationEnabled(true);
    w.setWebChromeClient(new WebChromeClient() {
      @Override public void onGeolocationPermissionsShowPrompt(String o, GeolocationPermissions.Callback c) { c.invoke(o, true, false); }
    });
    w.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
        Uri u = r.getUrl();
        if ("file".equals(u.getScheme())) return false;
        startActivity(new Intent(Intent.ACTION_VIEW, u));
        return true;
      }
    });
    w.loadUrl("file:///android_asset/index.html");
  }
  @Override public void onRequestPermissionsResult(int c, String[] p, int[] g) { if (w != null) w.reload(); }
  @Override public void onBackPressed() { if (w.canGoBack()) w.goBack(); else super.onBackPressed(); }
}
