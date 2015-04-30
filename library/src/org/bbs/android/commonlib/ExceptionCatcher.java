package org.bbs.android.commonlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ExceptionCatcher {
	private static final String TAG = ExceptionCatcher.class.getSimpleName();
	
	public static void attachExceptionHandler(final Application app) {
		File crashFile = null;
		String name = "00_" + app.getPackageName() + "_crash.log.txt";
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
					view.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
					view.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
	 */
	public static void npe() {
		String nullStr = null;
		if (nullStr.length() > 0) {
		}
	}
}
