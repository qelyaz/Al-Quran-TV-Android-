package com.mushaf.tv;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.*;
import androidx.webkit.WebViewAssetLoader;
public class MainActivity extends Activity {
  WebView w;
  @Override protected void onCreate(Bundle b) {
    super.onCreate(b);
    w = new WebView(this);
    setContentView(w);
    WebSettings s = w.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    final WebViewAssetLoader l = new WebViewAssetLoader.Builder()
      .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this)).build();
    w.setWebViewClient(new WebViewClient() {
      @Override public WebResourceResponse shouldInterceptRequest(WebView v, WebResourceRequest r) {
        return l.shouldInterceptRequest(r.getUrl());
      }
    });
    w.loadUrl("https://appassets.androidplatform.net/assets/index.html");
    w.requestFocus();
  }
  @Override public boolean onKeyDown(int k, KeyEvent e) {
    if (k == KeyEvent.KEYCODE_BACK) {
      w.evaluateJavascript("onBack()", v -> { if (!"true".equals(v)) finish(); });
      return true;
    }
    if (k == KeyEvent.KEYCODE_MENU) { w.evaluateJavascript("openP()", null); return true; }
    return super.onKeyDown(k, e);
  }
}
