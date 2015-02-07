
package org.bangbang.song.android.commonlib.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.bangbang.song.android.commonlib.ActivityUtil;
import org.bangbang.song.android.commonlib.R;
import org.bangbang.song.android.commonlib.activity.LogcatActivity.LogcatProcess.OnLogListener;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import org.bangbang.song.android.commonlib.FileHierachySpec;

/**
 * android logcat application output log viewer.
 * 
 * <pre>
 * need permission android.permission.READ_LOGS
 * need permission android.permission.WRITE_EXTERNAL_STORAGE if you do not give a 
 * log dir in {@link #EXTRA_LOG_SAVE_DIR}, or the dir you give live on SDCard.
 * 
 * 
 * @TODO how to see system log???
 * 
 * @author bysong@tudou.com
 * 
 * @see {@link permission#READ_LOGS}
 */
public class LogcatActivity extends 
                                   LogActivity 
//                                   Activity
{
    private static final String TAG = LogcatActivity.class.getSimpleName();

    /**
     * type: {@link FilterSpec}
     */
    public static final String EXTRA_FILTER_SPEC = "LogcatActivity.EXTRA_FILTER_SPEC";
    /**
     * type: {@link String}
     */
    public static final String EXTRA_LOG_SAVE_DIR ="LogcatActivity.EXTRA_LOG_SAVE_DIR";
    /**
     * how many logs should output. (by line)
     * 
     * type: {@link Integer}
     */
    public static final String EXTRA_LOG_LIMIT ="LogcatActivity.EXTRA_LOG_LIMIT";
    
    private static final int DEFAULT_LOG_LIMIT = 1000;

    private Spinner mFilter;
    private FilterSpec[] mFilterSpecs;
    private ArrayAdapter<FilterSpec> mSpecAdapter;
    
    private EditText mRealTimeFilter;
    protected FilterSpec mTemplateSpec;
    protected FilterSpec mRealTimeFilterSpec;
    protected String mRealFilterText;
    protected LevelSpec mRealFilterLevel;
    
    private Spinner mRealTimeFilterLevel;

    private ListView mLogs;
    private FilterLogAdapter mAdapter;

    private static final String REAL_TIME_FILTER = "pref_real_time_filter";
    private static LogcatProcess mLogcat;
    private Handler mH;

    private String mLogSaveDir;

	private int mLogLimit;
    
    private static final String[] CMD = {
            // DATE TIME PID TID LEVEL TAG MESSAGE
            "/system/bin/logcat", "-v", "threadtime"
    };
    //                    DATE      TIME       PID      TID      LEVEL     TAG      MESSAGE
    private static final String REG = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(.*$)";
    private static final Pattern PATTERN = Pattern.compile(REG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // init this before parseIntent().
        mFilterSpecs = new FilterSpec[] {
                new FilterSpec.ALL(), new FilterSpec.V(),
                new FilterSpec.I(), new FilterSpec.W(),
                new FilterSpec.D(), new FilterSpec.E()
        };
        parseIntent(getIntent());
        
        if (null == mLogcat) {
            mLogcat = new LogcatProcess(mLogLimit);
        }
        mLogcat.setOnLogListerner(new OnLogListener() {

            @Override
            public void onLog(String log) {
                mH.sendMessage(mH.obtainMessage(0, log));
            }
        });
        
        mH = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                mAdapter.add((String) msg.obj);
                return true;
            }
        });        
        
        mTemplateSpec = new FilterSpec.ALL();
        mRealTimeFilterSpec = new FilterSpec.ALL();
        mRealFilterLevel = new LevelSpec.ALL();
        
        // auto hide soft IME
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);        
        setContentView(R.layout.lib_logcat);
        
        mFilter = ((Spinner) findViewById(R.id.filter));
        mSpecAdapter = new ArrayAdapter<FilterSpec>(this, android.R.layout.simple_spinner_item,
                mFilterSpecs);
        mSpecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilter.setAdapter(mSpecAdapter);
        mFilter.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTemplateSpec = mSpecAdapter.getItem(position);
                updateFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRealTimeFilter = ((EditText) findViewById(R.id.real_time_filter));
        mRealTimeFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mRealFilterText = s.toString();
                updateFilter();
            }
        });         
        mRealTimeFilterLevel = ((Spinner) findViewById(R.id.real_time_level));
        LevelSpec[] levelSpecs = new LevelSpec[]{new LevelSpec.ALL(), new LevelSpec.E(),
                new LevelSpec.D(), new LevelSpec.W(), new LevelSpec.I(), new LevelSpec.V()};
        ArrayAdapter<LevelSpec> adapter = new ArrayAdapter<LogcatActivity.LevelSpec>(this, android.R.layout.simple_spinner_item, levelSpecs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRealTimeFilterLevel.setAdapter(adapter);
        mRealTimeFilterLevel.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRealFilterLevel = (LevelSpec) parent.getAdapter().getItem(position);
                updateFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });
        
        mLogs = ((ListView) findViewById(R.id.logs));
        mAdapter = new FilterLogAdapter(this, R.layout.lib_log_info_item, mLogLimit);
        mAdapter.setFilter(new Filter(new FilterSpec.ALL()));
        mLogs.setAdapter(mAdapter);
        mLogs.setOnScrollListener(new OnScrollListener() {
            
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                view.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
            }
        });
        mLogs.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);  
    }
    
    protected void updateFilter() {
        Filter filter = null;

        if (mRealFilterText != null && mRealFilterText.length() > 0) {
            mRealTimeFilterSpec.mMsg = mRealFilterText;
        }        
        mRealTimeFilterSpec.mLevelReg = mRealFilterLevel.mLevelReg;
        filter = new MergeFilter(new Filter(mTemplateSpec), new Filter(mRealTimeFilterSpec));

        mAdapter.setFilter(filter);
        mLogs.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    private void parseIntent(Intent intent) {
        if (null == intent) {
            return;
        }

        mLogSaveDir = intent.getStringExtra(EXTRA_LOG_SAVE_DIR);
        if (TextUtils.isEmpty(mLogSaveDir)) {
            mLogSaveDir = Environment.getExternalStorageDirectory().getPath();
        }
        
        mLogLimit = intent.getIntExtra(EXTRA_LOG_LIMIT, DEFAULT_LOG_LIMIT);
        
        Parcelable p = intent.getParcelableExtra(EXTRA_FILTER_SPEC);
        if (null == p) {
            Log.w(TAG, "no filter in intent, ignore.");
            return ;
        }
        
        FilterSpec spec = (FilterSpec) p;
        final int COUNT = mFilterSpecs.length + 1;
        FilterSpec[] specs = new FilterSpec[COUNT];
        specs[0] = spec;
        System.arraycopy(mFilterSpecs, 0, specs, 1, mFilterSpecs.length);
        mFilterSpecs = specs;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.lib_logcat, menu);
        
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            menu.findItem(R.id.save).setEnabled(false);
        }
        
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            performShare();            
            return true;
        } else if (id == R.id.save) {
            performSaveLog();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void performSaveLog() {
        String name = new Date(System.currentTimeMillis()).toGMTString() + ".log.txt";
        name = name.replace(" ", "_");
        name = name.replace(":", "_");
        
        File logFile = new File(mLogSaveDir + "/" + name);
        boolean saved = false;
        if (logFile.getParentFile().exists() || logFile.getParentFile().mkdirs())  {
            try {

                if (logFile.createNewFile()) {
                    FileWriter writer = new FileWriter(logFile);
                    writer.write(collectLog());
                    
                    writer.flush();
                    writer.close();
                    saved = true;
                }
            } catch (IOException e) {
                Log.d(TAG, "IOException", e);
            }
        }
        
		String message = "";
		if (saved) {
			message = "log has saved at " + logFile.getPath();
		} else {
			message = "save log error at " + logFile.getPath();
		}
		ActivityUtil.toast(this, message, Toast.LENGTH_LONG);
//		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    private String collectLog() {
        StringBuilder builder = new StringBuilder();
        final int count = mAdapter.getCount();
        for (int index = 0 ; index < count; index++ ) {
            builder.append(mAdapter.getItem(index));
            builder.append("\n");
        }
        
        return builder.toString();
    }

    private void performShare() {
        
		String target = "libproject";
		try {
			target = getPackageName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_SUBJECT, "log for " + target);
		share.putExtra(Intent.EXTRA_TEXT, collectLog());

		startActivity(Intent.createChooser(share, "send log by"));
    }

    @Override
    protected void onResume() {
        super.onResume(); 
        
        saveFilter();
        
        mLogcat.pushLog(true);     
    }

    private void saveFilter() {
        String realtimeFilter = getSharedPreferences("logcat", 0).getString(REAL_TIME_FILTER, "");
        mRealTimeFilter.setText(realtimeFilter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        restoreFilter();
        
        mLogcat.pushLog(false);
    }

    private void restoreFilter() {
        String realtimeFilter = mRealTimeFilter.getText().toString();
        getSharedPreferences("logcat", 0)
            .edit()
            .putString(REAL_TIME_FILTER, realtimeFilter)
            .commit();
    }

    class FilterLogAdapter extends LimitedLogAdapter {
        private Filter mFilter;
        private boolean mFilterable;
        private LimitArray mFilterArray;
        private Formator mFormator;

        public FilterLogAdapter(Context context, int textViewResourceId, int limited) {
            super(context, textViewResourceId, limited);

            mFilterArray = new LimitArray(limited);
            mFormator = new Formator();
        }

        public void setFilter(Filter filter) {
            mFilter = filter;
            
            mFilterable = mFilter != null;            
            if (mFilterable) {
                mFilterArray.clear();
                
                int count = super.getCount();
                String log = null;
                for (int i = 0; i < count; i++) {
                    log = super.getItem(i);
                    if (mFilter.isLoggable(log)) {
                        mFilterArray.addLog(mFormator.format(log));
                    }
                }
            }

            notifyDataSetChanged();
        }

        public void add(String log) {
            super.add(log);

            if (mFilterable) {
                if (mFilter.isLoggable(log)) {
                    mFilterArray.addLog(mFormator.format(log));
                }
            }

            notifyDataSetChanged();
        }

        @Override
        public String getItem(int position) {
            if (mFilterable) {
                return mFilterArray.getAt(position).replace("\\/", "/");
            } else {
                return super.getItem(position).replace("\\/", "/");
            }
        }

        @Override
        public int getCount() {
            if (!mFilterable) {
                return super.getCount();
            } else {
                return mFilterArray.getCount();
            }
        }

    }

    class LimitedLogAdapter extends BaseAdapter {

        private LimitArray mArray;
        private int mRes;
        private Context mContext;
        
        private int mColorDefault = getResources().getColor(R.color.white);
        private int mColorWarring = getResources().getColor(R.color.yellow);
        private int mColorError = getResources().getColor(R.color.red);
        private int mColorInfo = getResources().getColor(R.color.green);

        public LimitedLogAdapter(Context context, int textViewResourceId, int limited) {
            super();

            mContext = context;
            mRes = textViewResourceId;
            mArray = new LimitArray(limited);
        }

        public void add(String log) {
            mArray.addLog(log);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mArray.getCount();
        }

        @Override
        public String getItem(int position) {
            return mArray.getAt(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            if (null != convertView) {
                v = convertView;
            } else {
                v = LayoutInflater.from(mContext).inflate(mRes, null);
            }
            
            TextView text = ((TextView) v.findViewById(R.id.log_item));
            String log = getItem(position);
            
            // TODO re-factor with Formator
            if (log.matches("(?i)[\\d:.]*[D].*")) {
                text.setTextColor(mColorDefault);
            } else if (log.matches("(?i)[\\d:.]*I.*")) {
                text.setTextColor(mColorInfo);
            } else if (log.matches("(?i)[\\d:.]*W.*")) {
                text.setTextColor(mColorWarring);
            } else if (log.matches("(?i)[\\d:.]*E.*")) {
                text.setTextColor(mColorError);
            }
            
            text.setText(log);

            return v;
        }
    }

    // cycle buffer
    class LimitArray {
        private String[] mArray;
        private int mLimit;
        private int mCursor;
        private int mCount;
    
        LimitArray(int limit) {
            mCursor = 0;
            mLimit = limit;
            mArray = new String[limit];
        }
    
        void clear() {
            mCursor = 0;
            mCount = 0;
            mArray = new String[mLimit];
        }
    
        void addLog(String log) {
            mArray[mCursor] = log;
            mCursor = (mCursor + 1) % mLimit;
        }
    
        String getAt(int index) {
            String log = null;
    
            int count = 0;
            boolean find = false;
            for (int i = mCursor; i < mLimit; i++) {
                if (mArray[i] != null) {
                    if (index == count) {
                        log = mArray[i];
                        find = true;
                        break;
                    }
                    count++;
                }
            }
            if (!find) {
                for (int i = 0; i < mCursor; i++) {
                    if (mArray[i] != null) {
                        if (index == count) {
                            log = mArray[i];
                            find = true;
                            break;
                        }
                        count++;
                    }
                }
            }
    
            return log;
        }
    
        int getCount() {
            if (mCount == mLimit) {
                return mCount;
            }
    
            int count = 0;
    
            for (int i = mCursor; i < mLimit; i++) {
                if (mArray[i] != null) {
                    count++;
                }
            }
            for (int i = 0; i < mCursor; i++) {
                if (mArray[i] != null) {
                    count++;
                }
            }
    
            if (count == mLimit) {
                mCount = count;
            }
    
            return count;
        }
    }
    
    static class LogcatProcess {
        private OnLogListener mListener;
        private boolean mPush;
        private CacheLog mCachelog;
        private Thread mWorkerThread;

        LogcatProcess(int logCapcity) {
            mCachelog = new CacheLog(logCapcity);

            mWorkerThread = new Thread("logcat thread") {
                public void run() {
                    final String[] command = CMD;
                    Process mProcess = null;
                    try {
                        mProcess = new ProcessBuilder(command).start();
                        BufferedReader r = new BufferedReader(new InputStreamReader(
                                mProcess.getInputStream()));
                        String line = null;
                        while ((line = r.readLine()) != null) {
                            mCachelog.addLog(line);
                        }
                    } catch (Exception e) {
                        // ignore this, it's safe.
                        Log.e(TAG, "KO. command: " + command, e);
                    } finally {
                        if (null != mProcess) {
                            mProcess.destroy();
                        }
                    }

                };
            };
            
            mWorkerThread.start();
        }

        void close() {
            if (null != mWorkerThread) {
                mWorkerThread.interrupt();
                mWorkerThread = null;
            }
        }

        void pushLog(boolean push) {
            mPush = push;

            mCachelog.pushLog();
        }

        void doLog(String log) {
            if (null != mListener) {
                mListener.onLog(log);
            }
        }

        void setOnLogListerner(OnLogListener listener) {
            mListener = listener;
        }

        void unsetOnLogListener(OnLogListener listener) {
            mListener = null;
        }

        class CacheLog {
            private int mcapacity;
            private int mCursor;
            private String[] mLogs;

            CacheLog(int capacity) {
                mcapacity = capacity;
                mCursor = 0;
                mLogs = new String[capacity];
            }

            void addLog(String log) {
                if (mPush) {
                    doLog(log);
                }
                mLogs[mCursor] = log;
                mCursor = (mCursor + 1) % mcapacity;
            }

            void pushLog() {
                for (int i = mCursor; i < mcapacity; i++) {
                    if (mLogs[i] != null) {
                        doLog(mLogs[i]);
                    }
                }

                for (int i = 0; i < mCursor; i++) {
                    if (mLogs[i] != null) {
                        doLog(mLogs[i]);
                    }
                }
            }
        }

        interface OnLogListener {
            void onLog(String log);
        }
    }

    class Formator {
        Matcher mMatcher;

        public String format(String log) {
            String l = null;
            mMatcher = PATTERN.matcher(log);
            if (mMatcher.matches()) {
                l = mMatcher.group(2) + ":" + mMatcher.group(5) + "/" + mMatcher.group(6) + mMatcher.group(7);
            }

            return l;
        }
    }

    class Filter {
        private FilterSpec mSpec;

        Matcher mMatcher;
        String mDate;
        String mTime;
        String mPid;
        String mTid;
        String mLevel;
        String mTag;
        String mMsg;

        public Filter() {
        }

        Filter(FilterSpec spec) {
            mSpec = spec;
        }

        public boolean isLoggable(String log) {
            boolean filter = true;
            try {
                mMatcher = PATTERN.matcher(log);
                if (mMatcher.matches()) {
                    mDate = mMatcher.group(1);
                    mTime = mMatcher.group(2);
                    mPid = mMatcher.group(3);
                    mTid = mMatcher.group(4);
                    mLevel = mMatcher.group(5);
                    mTag = mMatcher.group(6);
                    mMsg = mMatcher.group(7);

                    if (mSpec.mPid > 0) {
                        filter &= (mPid.contains(mSpec.mPid + ""));
                    }
                    if (mSpec.mLevelReg != null) {
                        filter &= mLevel.matches(mSpec.mLevelReg);
                    }
                    if (mSpec.mTag != null) {
                        filter &= mTag.contains(mSpec.mTag);
                    }
                    if (mSpec.mMsg != null) {
                        filter &= (mMsg.contains(mSpec.mMsg) || mMsg.matches("(?i).*" + mSpec.mMsg + ".*"));
                    }
                }
            } catch (PatternSyntaxException e) {
                Log.e(TAG, "PatternSyntaxException", e);
                filter = false;
            }

            return filter;
        }
    }

    class MergeFilter extends Filter {
        Filter mBaseFilter;
        private Filter mMergeFilter;

        MergeFilter(Filter baseFilter, Filter mergerFilter) {
            super();
            mBaseFilter = baseFilter;
            mMergeFilter = mergerFilter;
        }

        @Override
        public boolean isLoggable(String log) {
            return mBaseFilter.isLoggable(log) && mMergeFilter.isLoggable(log);
        }
    }
    
    public static class LevelSpec {
        public String mId;
        public String mLabel;
        public String mLevelReg;
        
        @Override
        public String toString() {
            return mLabel;
        }
        
        public static class ALL extends LevelSpec {
            
            public ALL() {
                mId = "com.tudou.android.logcat.LEVLE_ALL";
                mLabel = "ALL";
                mLevelReg = "[EDWIV]";
            }
        }
        
        public static class E extends LevelSpec {
            
            public E() {
                mId = "com.tudou.android.logcat.LEVLE_E";
                mLabel = "ERR";
                mLevelReg = "[E]";
            }
        }
        
        public static class D extends LevelSpec {
            
            public D() {
                mId = "com.tudou.android.logcat.LEVLE_D";
                mLabel = "DEBUG";
                mLevelReg = "[ED]";
            }
        }
        
        public static class W extends LevelSpec {
            
            public W() {
                mId = "com.tudou.android.logcat.LEVLE_W";
                mLabel = "WARNING";
                mLevelReg = "[EDW]";
            }
        }
        
        public static class I extends LevelSpec {
            
            public I() {
                mId = "com.tudou.android.logcat.LEVLE_D";
                mLabel = "DEBUG";
                mLevelReg = "[EDWI]";
            }
        }
        
        public static class V extends LevelSpec {
            
            public V() {
                mId = "com.tudou.android.logcat.LEVLE_V";
                mLabel = "VERBOSE";
                mLevelReg = "[EDWIV]";
            }
        }  
    }

    public static class FilterSpec implements Parcelable {
        public String mId;
        public String mLabel;
        public String mDesc;

        public String mTag;
        public String mMsg;
        public int mPid = -1;
        public String mpackage;
        public String mLevelReg;

        public static final Parcelable.Creator<FilterSpec> CREATOR = new Parcelable.Creator<FilterSpec>() {
            public FilterSpec createFromParcel(Parcel in) {
                return new FilterSpec(in);
            }

            public FilterSpec[] newArray(int size) {
                return new FilterSpec[size];
            }
        };
        
        public FilterSpec() {
            
        }

        public FilterSpec(Parcel in) {
            mId = in.readString();
            mLabel = in.readString();
            mDesc = in.readString();
            
            mTag = in.readString();
            mMsg = in.readString();
            mPid = in.readInt();
            mpackage = in.readString();
            mLevelReg = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeString(mLabel);
            dest.writeString(mDesc);

            dest.writeString(mTag);
            dest.writeString(mMsg);
            dest.writeInt(mPid);
            dest.writeString(mpackage);
            dest.writeString(mLevelReg);
        }

        @Override
        public String toString() {
            return mLabel;
        }

        static class ALL extends FilterSpec {
            public ALL() {
                mId = "com.tudou.android.Logcat.FILTER_ALL";
                mLabel = "All Message";
            }
        }

        static class E extends FilterSpec {
            public E() {
                mId = "com.tudou.android.Logcat.FILTER_E";
                mLabel = "Error Message";

                mLevelReg = "[E]";
            }
        }

        static class D extends FilterSpec {
            public D() {
                mId = "com.tudou.android.Logcat.FILTER_D";
                mLabel = "Debug Message";

                mLevelReg = "[ED]";
            }
        }

        static class W extends FilterSpec {
            public W() {
                mId = "com.tudou.android.Logcat.FILTER_W";
                mLabel = "Warning Message";

                mLevelReg = "[EDW]";
            }
        }

        static class I extends FilterSpec {
            public I() {
                mLabel = "Infor Message";

                mLevelReg = "[EDWI]";
            }
        }

        static class V extends FilterSpec {
            public V() {
                mId = "com.tudou.android.Logcat.FILTER_V";
                mLabel = "Verbose Message";

                mLevelReg = "[EDWIV]";
            }
        }
    }

}
