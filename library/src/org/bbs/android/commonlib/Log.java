package org.bbs.android.commonlib;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import android.os.Environment;


/**
 * @author bysong
 * 
 * @see #init(String, String)
 *
 */
public class Log {
	private static final String TAG = Log.class.getSimpleName();

    private static final String TAG_DELIMITER = ":";
    private static String mRootTag = "libproject";
    
    private static boolean sLog2STDOUT = true;
    private static boolean sLog2File = false;
    
    private static Logger sLogger = null;
    private static Handler sFileHandler;
    private static Formatter sFormater;
    
    private static final int COUNT = 5;
    private static final int LIMIT = 20 * 1024 * 1024; // 20M


    public static void setRootTag(String rootTag) {
        mRootTag = rootTag;
    }

    public static void setLog(boolean log) {
    	Log.sLog2STDOUT = log;
    }

    /**
     * @param log2file
     * @see #init(File)
     * @see #init(File, String)
     */
    public static void setLog2File(boolean log2file) {
        log2file = log2file && assureCanLog2File();
        Log.sLog2File = log2file;
    }

    private static boolean assureCanLog2File() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    private static void _d(String tag, String message) {
        android.util.Log.d(tag, message);
    }

    private static void _d2logger(String tag, String message) {
        sLogger.log(Level.ALL, /* tag + TAG_DELIMITER + */message);
    }

    private static void _e(String tag, String message) {
        android.util.Log.e(tag, message);
    }

    private static void _e2logger(String tag, String message) {
        sLogger.log(Level.ALL, /* tag + TAG_DELIMITER + */message);
    }

    private static void _i(String tag, String message) {
        android.util.Log.i(tag, message);
    }

    private static void _i2logger(String tag, String message) {
        sLogger.log(Level.ALL, /* tag + TAG_DELIMITER + */message);
    }

    private static void _v(String tag, String message) {
        android.util.Log.v(tag, message);
    }

    private static void _v2logger(String tag, String message) {
        sLogger.log(Level.ALL, /* tag + TAG_DELIMITER + */message);
    }

    private static void _w(String tag, String message) {
        android.util.Log.w(tag, message);
    }

    private static void _w2logger(String tag, String message) {
        sLogger.log(Level.ALL, /* tag + TAG_DELIMITER + */message);
    }

    public static void d(String tag, String message) {
        if (sLog2STDOUT) {
            _d(tag, message);
        }

        if (sLog2File) {
            _d2logger(mRootTag, tag + TAG_DELIMITER + message);
        }
    }

    public static void d(String tag, String message, Throwable t) {
        if (sLog2STDOUT) {
            _d(tag, message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (sLog2File) {
            _d2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void e(String tag, String message) {
        if (sLog2STDOUT) {
            _e(tag, message);
        }

        if (sLog2File) {
            _e2logger(mRootTag, tag + TAG_DELIMITER + message);
        }
    }

    public static void e(String tag, String message, Throwable t) {
        if (sLog2STDOUT) {
            _e(tag, message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (sLog2File) {
            _e2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void i(String tag, String message) {
        if (sLog2STDOUT) {
            _i(tag, message);
        }

        if (sLog2File) {
            _i2logger(mRootTag, tag + TAG_DELIMITER + message);
        }
    }

    public static void i(String tag, String message, Throwable t) {
        if (sLog2STDOUT) {
            _i(tag, message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (sLog2File) {
            _i2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void v(String tag, String message) {
        if (sLog2STDOUT) {
            _v(tag, message);
        }

        if (sLog2File) {
            _v2logger(mRootTag, tag + TAG_DELIMITER + message);
        }
    }

    public static void v(String tag, String message, Throwable t) {
        if (sLog2STDOUT) {
            _v(tag, message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (sLog2File) {
            _v2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void w(String tag, String message) {
        if (sLog2STDOUT) {
            _w(tag, message);
        }

        if (sLog2File) {
            _w2logger(mRootTag, tag + TAG_DELIMITER + message);
        }
    }

    public static void w(String tag, String message, Throwable t) {
        if (sLog2STDOUT) {
            _w(tag, message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (sLog2File) {
            _w2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    /**
     * init log dir & file.
     * 
     * @param logPath
     * @param logFileName
     * @see #setLog2File(boolean)
     */
    public static void init(String logPath, String logFileName) {
        boolean error = false;
        sLogger = Logger.getAnonymousLogger();
        sLogger.setLevel(Level.ALL);

        File logDir = new File(logPath);
        if (!logDir.exists() && !logDir.mkdirs()) {
            sLog2File = false;
            e(TAG, "can NOT create log dir(s). dir: " + logDir.getPath());
            return;
        }

        try {
            sFileHandler = new FileHandler(logDir.getPath() + "/" + logFileName + "%g" + ".txt",
                    LIMIT, COUNT);
            sFileHandler.setFormatter(sFormater);
        } catch (IOException e) {
            e.printStackTrace();
            error = true;
        }

        if (!error) {
            sLogger.addHandler(sFileHandler);
        }

    }

    static {
        sFormater = new SimpleFormatter() {

            @Override
            public String format(LogRecord r) {
                final Date d = new Date(r.getMillis());
                return "[" + (d.getYear() + 1900) + "/" + d.getMonth() + "/" + d.getDate()
                        + " " + d.getHours()
                        + ":" + d.getMinutes() + ":" + d.getSeconds() + "]" + r.getMessage()
                        + "\n";
            }

        };
    }


}