package com.kymjs.app.base_res.utils.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.adapter.HashMapAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyFragment extends Fragment {
	private HashMapAdapter adapter;
	private LayoutInflater inflate;
	private List<HashMap<String,String>> datas=new ArrayList<>();
	private ListView listView;
	private LinearLayout layoutPageTitle;
	Activity activity;
	private boolean isLoadActivity=false;

	LinearLayout layoutNullData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		LinearLayout root = (LinearLayout)inflater.inflate(R.layout.baseres_page, null);
		listView = (ListView)root.findViewById(R.id.listView);
		layoutPageTitle = root.findViewById(R.id.layout_page_title);
		layoutNullData = root.findViewById(R.id.layout_page_null_data);

		adapter = new HashMapAdapter(getActivity().getBaseContext(),datas);
		listView.setAdapter(adapter);
		myReceiver = new MyReceiver();

		IntentFilter intentFilter = new IntentFilter();
			activity =  getActivity();
		intentFilter.addAction("my_action_fragment");
		LocalBroadcastManager.getInstance(activity).registerReceiver(myReceiver, intentFilter);
		Log.d("myfragment", "onCreateView: ");
		isLoadActivity=true;
		return root;
	}
	private MyReceiver myReceiver;
	@Override
	public void onResume() {
		super.onResume();
	}
	//创建广播接收实例
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//获取数据
			List<HashMap<String,String>> taskListInfoItems =(List<HashMap<String,String>>) intent.getSerializableExtra("taskListInfoItems");
			HashMap<String,String> map = (HashMap<String,String>) intent.getSerializableExtra("map");
			layoutPageTitle.removeAllViews();


			Log.d("myFragmentOnReceive", "onReceive:   taskListInfoItems: "+taskListInfoItems.toString()+"   map:"+map.toString());

			if(taskListInfoItems!=null){
				if(taskListInfoItems.size()>0) {



					layoutNullData.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					layoutPageTitle.setVisibility(View.VISIBLE);
					datas.clear();
					datas.addAll(taskListInfoItems);
					adapter.notifyDataSetChanged();


					HashMap<String,String> temp =	taskListInfoItems.get(0);
					Set<Map.Entry<String, String>> entrySet = temp.entrySet();
					Iterator<Map.Entry<String, String>> validDataIterator = entrySet.iterator();

					while(validDataIterator.hasNext()) {
						Map.Entry<String, String> validDataKeyValueMap = validDataIterator.next();


						Log.d("titleDatas", "onReceive:   defaultKey  :"+validDataKeyValueMap.getKey()   +"   showTitle:     "+map.get(validDataKeyValueMap.getKey()));

						TextView tvTitle = new TextView(activity);
						tvTitle.setText(map.get(validDataKeyValueMap.getKey()));
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
						tvTitle.setLayoutParams(lp);
						tvTitle.setGravity(Gravity.CENTER);
						tvTitle.setMaxLines(2);
						tvTitle.setEllipsize(TextUtils.TruncateAt.END);
						layoutPageTitle.addView(tvTitle);
					}
				}else{
					Log.d("myFragmentOnReceive", "onReceive:    nullView");
					layoutNullData.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					layoutPageTitle.setVisibility(View.GONE);
				}
			}
			Log.d("myfragment", "onReceive: ");
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(activity).unregisterReceiver(myReceiver);
	}
	public void  updateData(final Handler handler){
	  	final Handler handler1 = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(!isLoadActivity){
					handler1.postDelayed(this,500);
				}else{
					handler.sendMessage(new Message());
				}
			}
		};
		handler1.postDelayed(runnable,500);
	}
}
