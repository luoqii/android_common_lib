package org.bbs.android.commonlib;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class ExceptionCatcher {
	private static final String TAG = ExceptionCatcher.class.getSimpleName();

	public static void attachExceptionHandler(final Application app) {
		File crashFile = null;
		String name = "00_" + app.getPackageName()  + "_" + System.currentTimeMillis() + "_crash.log.txt";
		crashFile = app.getFileStreamPath(name);
		crashFile.delete();
		try {
			app.openFileOutput(name, Context.MODE_WORLD_READABLE);
			crashFile = app.getFileStreamPath(name);
			crashFile.createNewFile();

			attachExceptionHandler(app, crashFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void attachExceptionHandler(final Application app, final File crashFile) {
		final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread
				.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@SuppressLint("WorldReadableFiles")
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				PrintStream writer;
				try {
					writer = new PrintStream(crashFile);
					writer.append("crash at: " + new Date().toString());
					writer.append("\n");
					writer.flush();

					ex.printStackTrace(writer);
					ex.printStackTrace();
					writer.flush();
					writer.close();

					Intent view = new Intent(Intent.ACTION_VIEW);
					view.setDataAndType(Uri.fromFile(crashFile), "text/*");
					view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					view.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//					view.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//					view.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					app.startActivity(view);
				} catch (Exception e) {
					e.printStackTrace();
				}

				defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
			}
		});

		if (false) {
			npe();
		}
	}

	/**
	 * force a NullPointerException.
	 * <p>
	 * debug only
	 */
	public static void npe() {
		String nullStr = null;
		if (nullStr.length() > 0) {
			; // do nothing
		}
	}


	static public class HTMLViewerActivity extends Activity {
		private static final String TAG = "HTMLViewer";

		private WebView mWebView;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			mWebView = new WebView(this);
			ViewGroup.LayoutParams lp =
					new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT);
			setContentView(mWebView, lp);

			mWebView.setWebChromeClient(new ChromeClient());
			mWebView.setWebViewClient(new ViewClient());

			WebSettings s = mWebView.getSettings();
			s.setUseWideViewPort(true);
			s.setSupportZoom(true);
			s.setBuiltInZoomControls(true);
//			s.setDisplayZoomControls(false);
			s.setSavePassword(false);
			s.setSaveFormData(false);
//            s.setBlockNetworkLoads(true);

			// Javascript is purposely disabled, so that nothing can be
			// automatically run.
			s.setJavaScriptEnabled(false);
			s.setDefaultTextEncodingName("utf-8");

			final Intent intent = getIntent();
			if (intent.hasExtra(Intent.EXTRA_TITLE)) {
				setTitle(intent.getStringExtra(Intent.EXTRA_TITLE));
			}

			mWebView.loadUrl(String.valueOf(intent.getData()));
//            mWebView.loadUrl("https://www.baidu.com/");
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
			mWebView.destroy();
		}

		private class ChromeClient extends WebChromeClient {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				if (!getIntent().hasExtra(Intent.EXTRA_TITLE)) {
					HTMLViewerActivity.this.setTitle(title);
				}
			}
		}

		private class ViewClient extends WebViewClient {
			@Override
			public void onPageFinished(WebView view, String url) {
			}

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
															  WebResourceRequest request) {
//				final Uri uri = request.getUrl();
//				if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())
//						&& uri.getPath().endsWith(".gz")) {
//					Log.d(TAG, "Trying to decompress " + uri + " on the fly");
//					try {
//						final InputStream in = new GZIPInputStream(
//								getContentResolver().openInputStream(uri));
//						final WebResourceResponse resp = new WebResourceResponse(
//								getIntent().getType(), "utf-8", in);
//						resp.setStatusCodeAndReasonPhrase(200, "OK");
//						return resp;
//					} catch (IOException e) {
//						Log.w(TAG, "Failed to decompress; falling back", e);
//					}
//				}
				return null;
			}
		}
	}
}