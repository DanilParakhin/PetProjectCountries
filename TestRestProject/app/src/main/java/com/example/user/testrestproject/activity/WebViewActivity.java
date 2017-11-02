package com.example.user.testrestproject.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.user.testrestproject.R;

import static com.example.user.testrestproject.Constants.CITY_NAME;
import static com.example.user.testrestproject.Constants.WIKI_URL;

public class WebViewActivity extends AppCompatActivity {
	private String cityName;
	private String wikiUrl;

	private WebView webView;
	private ProgressBar progressBarLoad;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			cityName = extras.getString(CITY_NAME);
			wikiUrl = extras.getString(WIKI_URL);
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(cityName);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_action_back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		webView = (WebView) findViewById(R.id.web_view);
		progressBarLoad = (ProgressBar) findViewById(R.id.progress_bar);
		progressBarLoad.setVisibility(View.GONE);

		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progressBarLoad.setProgress(newProgress);
			}
		});

		webView.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progressBarLoad.setVisibility(View.VISIBLE);
				progressBarLoad.setProgress(webView.getProgress());
			}


			@Override
			public void onPageFinished(WebView view, String url) {
				progressBarLoad.setProgress(webView.getProgress());
				progressBarLoad.setVisibility(View.GONE);
			}

			@TargetApi(Build.VERSION_CODES.N)
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				return super.shouldOverrideUrlLoading(view, request);
			}

			@SuppressWarnings("deprecation")
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}
		});

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDisplayZoomControls(false);

		webView.loadUrl("https://" + wikiUrl);
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_web, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id){
			case R.id.action_reload:
				webView.reload();
				break;
			case R.id.action_goback:
				webView.goBack();
				break;
			case R.id.action_goforward:
				webView.goForward();
				break;

		}


		return super.onOptionsItemSelected(item);
	}

}
