package org.bbs.android.commonlib.activity;

import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
* Created by bb.S on 15-9-23.
*/
public class AllLauncherActivity extends FragmentActivity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       getSupportFragmentManager().beginTransaction().add(android.R.id.content, new ActFragment()).commit();
   }

   public static class ActFragment extends Fragment {
       public static final  java.lang.String EXTRA_RESOVLEINFO = ActFragment.class.getName() + ".EXTRA_RESOLVEINFO";
       private ListView mListV;
       private PackageManager mPm;

       public ActFragment(){

       }

       @Override
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
       }

       @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           mListV = new ListView(getActivity());
           return mListV;
//           return super.onCreateView(inflater, container, savedInstanceState);
       }

       @Override
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);

           mPm = getActivity().getPackageManager();
           fillData(mListV);
       }

       void fillData(ListView view){
           Intent i = new Intent(Intent.ACTION_MAIN);
           i.addCategory(Intent.CATEGORY_LAUNCHER);
           List<ResolveInfo> acts = mPm.queryIntentActivities(i, 0);
           ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(), 0, acts){
               @Override
               public View getView(int position, View convertView, ViewGroup parent) {
                   ResolveInfo r = getItem(position);
                   LinearLayout v = null;
                   if (convertView == null){
                       v = new LinearLayout(getActivity());
                       ImageView iv = new ImageView(getActivity());
                       v.addView(iv, dp2px(100), dp2px(100));
                       TextView t = new TextView(getActivity());
                       v.addView(t, AbsListView.LayoutParams.MATCH_PARENT, dp2px(100));

                       convertView = v;
                   }
                   v = (LinearLayout) convertView;
                   try {
                       ((ImageView)v.getChildAt(0)).setImageDrawable(mPm.getActivityIcon(new ComponentName(r.activityInfo.packageName, r.activityInfo.name)));
                   } catch (PackageManager.NameNotFoundException e) {
                       e.printStackTrace();
                   }
                   ((TextView)v.getChildAt(1)).setText(r.activityInfo.applicationInfo.className);

                   return v;
//                   return super.getView(position, convertView, parent);
               }
           };

           view.setAdapter(adapter);
           view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   ResolveInfo r = (ResolveInfo) parent.getAdapter().getItem(position);
                   onCompClick(r);
               }

           });
       }


       private void onCompClick(ResolveInfo r) {
           Intent result = new Intent();
           result.putExtra(EXTRA_RESOVLEINFO, r);
           getActivity().setResult(RESULT_OK, result);
           getActivity().finish();
       }

       private int dp2px(int dp) {
           return dp;
       }
   }

}
