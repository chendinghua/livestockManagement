package hsj.query.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.currencyEntry.MapEntry;
import com.kymjs.app.base_res.utils.fragment.MyFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.JSONAnalysis;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.kyindicator.KyIndicator;
import com.kymjs.app.base_res.utils.view.kyindicator.OnSelectedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import butterknife.BindView;
import butterknife.OnClick;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import hsj.query.R;
import hsj.query.R2;

/**
 * Created by 16486 on 2020/12/25.
 */

public class QueryFragment extends BaseFragment implements ResponseHandlerInterface {
    //标题信息
    @BindView(R2.id.kyIndicator)
    public KyIndicator yIndicator;
    //分页控件
    @BindView(R2.id.viewpager)
    public ViewPager viewPager;
    @BindView(R2.id.tv_page_count)
    public TextView pageCount;
    @BindView(R2.id.et_input_rfid)
    public EditText etInputRfid;
    @BindView(R2.id.layout_query_content)
    LinearLayout layoutQueryContent;

    MyPagerAdapter myPageAdapater;
    private List<MyFragment> fragmentList = new ArrayList<>();
    List<MapEntry> mapList = new ArrayList<>();
    public String ACTION_NAME = "my_action_fragment";
    boolean isLoad = false;
    private LayoutInflater inflate;
    private FragmentManager fragmentManager;
    HashMap<String, String> tempMap = new HashMap<>();
    Device device;

    int posIndex=-1;
    @Override
    protected int getLayoutResource() {
        return R.layout.query_fragment;
    }

    @Override
    protected void initView() {
        rootView.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        rootView.setFocusableInTouchMode(true);
        rootView.setOnKeyListener(listener);
        device = new DeviceFactory(activity, this).getDevice();
        device.initUHF();
        SharedPreferences sp=activity.getSharedPreferences("pda_config", Context.MODE_PRIVATE);
        device.setPower(sp.getInt("lowFrequency",5));
    }

    @Override
    protected boolean isLoad() {
        return true;
    }

    @Override
    public void onDestroy() {
        if(device!=null)
            device.destroy();
        super.onDestroy();
    }

    @OnClick(R2.id.btn_query)
    public void onViewClicked() {
        if(!Utils.isStrEmpty(etInputRfid.getText())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("RfidNo", etInputRfid.getText());
            InteractiveDataUtil.interactiveMessage(activity, map, new HandlerUtils(activity, new HandlerUtilsCallback() {
                @Override
                public void handlerExecutionFunction(Message msg) {
                    mapList.clear();
                    yIndicator.clearTab();
                    fragmentList.clear();

                    JSONAnalysis jsonAnalysis = JSONAnalysis.getInstance();
                    //获取键值对对象
                    Set<Map.Entry<String, Object>> keyValueSet = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data")).getJSONObject("Map").entrySet();
                    Iterator<Map.Entry<String, Object>> keyValueIterator = keyValueSet.iterator();
                    while (keyValueIterator.hasNext()) {
                        Map.Entry<String, Object> keyValueMap = keyValueIterator.next();
                        tempMap.put(keyValueMap.getKey(), keyValueMap.getValue().toString());
                    }

                    mapList.addAll(jsonAnalysis.getAnalysisEntry(msg.getData().getString("result"), JSONAnalysis.ANALYSIS.ITEMS));
                    if (inflate == null)
                        inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    for (int i = 0; i < mapList.size(); i++) {
                        Log.d("scanMapList", "handlerExecutionFunction: " + mapList.get(i).toString());
                        View tab = (View) (inflate.inflate(R.layout.query_tab, null));
                        yIndicator.addTab(tab);
                        TextView tv = (TextView) tab.findViewById(R.id.text);
                        tv.setText(mapList.get(i).getTitle());
                        fragmentList.add(new MyFragment());
                    }
                    myPageAdapater = new MyPagerAdapter(fragmentManager);
                    fragmentManager = getFragmentManager();
                    viewPager.setAdapter(new MyPagerAdapter(fragmentManager));
                    viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
                    yIndicator.setCurrentTab(0);//默认当前tab为序号0的
                    yIndicator.setOnSelectedListener(new OnSelectedListener() {
                        @Override
                        public void OnSelected(View tab, int index) {
                            initListData(index);
                        }

                        @Override
                        public void OnNoSelected(View tab, int index) {

                        }
                    });
                    initListData(0);
                    posIndex=0;
                    initLayoutContent(   jsonAnalysis.getAnalysisEntry(msg.getData().getString("result"), JSONAnalysis.ANALYSIS.ENTRY).get(0).getTempList().get(0));
                }
            }), MethodEnum.GETRFIDINFORMATION, InteractiveEnum.GET);
        }else{
            UIHelper.ToastMessage(activity,"当前无RFID数据");
        }
    }

    LinearLayout layoutHorizontal;
    //初始化库存信息
    private void initLayoutContent(HashMap<String,String> map){
        layoutQueryContent.removeAllViews();
        Log.d("contentInfo", "initLayoutContent: "+map.toString());
        int count=0;
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, String>> validDataIterator = entrySet.iterator();

        while(validDataIterator.hasNext()) {
            if(layoutHorizontal==null || count==0 || count%2==0){
                layoutHorizontal = new LinearLayout(activity);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0 , 1.0f);
                layoutHorizontal.setLayoutParams(lp);
                layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
                layoutQueryContent.addView(layoutHorizontal);
            }
            Map.Entry<String, String> validDataKeyValueMap = validDataIterator.next();

            //标题信息
            TextView tvTitle = new TextView(activity);
            tvTitle.setText(tempMap.get(validDataKeyValueMap.getKey())+":");
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            tvTitle.setLayoutParams(lp);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setMaxLines(2);
            tvTitle.setEllipsize(TextUtils.TruncateAt.END);
            layoutHorizontal.addView(tvTitle);

            //库存数据
            TextView tvTitle1 = new TextView(activity);
            tvTitle1.setText(validDataKeyValueMap.getValue());
            tvTitle1.setLayoutParams(lp);
            tvTitle1.setGravity(Gravity.CENTER);
            tvTitle1.setMaxLines(2);
            tvTitle1.setEllipsize(TextUtils.TruncateAt.END);
            layoutHorizontal.addView(tvTitle1);
            count++;
        }
    }
    @Override
    public void onPause() {
        if(posIndex!=-1){
            yIndicator.setCurrentTab(posIndex);
            initListData(posIndex);
        }
        super.onPause();
    }

    /**
     * 定义OnPageChangeListener  监听器
     *
     * @author pc
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            initListData(position);
            yIndicator.setCurrentTab(position);
            posIndex=position;
        }

    }

    Handler loadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initListData(0);
        }
    };

    //初始化PagerView的fragment的数据
    private void initListData(int index) {

        List<HashMap<String, String>> currentTaskList = new ArrayList<>();

        if (mapList != null && mapList.size() > index) {
            Log.d("myFragment", "handlerExecutionFunction: "+index +"    value:" +mapList.get(index).getTempList().toString());
            currentTaskList.addAll(mapList.get(index).getTempList());
        }
        //3.使用广播传递数据
        Intent intent = new Intent();
        intent.setAction(ACTION_NAME);
        intent.putExtra("taskListInfoItems", (Serializable) currentTaskList);
        intent.putExtra("map", tempMap);
        //更新当前page总数量
        updatePageCountInfo(currentTaskList);
        //使用本地广播更加安全
        LocalBroadcastManager.getInstance(activity).sendBroadcastSync(intent);
    }

    private void updatePageCountInfo(List<HashMap<String, String>> taskListInfoItems) {
        if (taskListInfoItems != null)
            pageCount.setText(taskListInfoItems.size() + "");
    }

    /**
     * 定义viewpager适配器
     *
     * @author pc
     */
    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            MyFragment myFragment = fragmentList.get(arg0);
            if (!isLoad) {
                isLoad = true;
                myFragment.updateData(loadHandler);
            }
            return myFragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private View.OnKeyListener listener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                Log.d("onKeyCode", "onKey: " + i);
                device.onKeyDown(i, keyEvent, 1, true);
                return true;
            }
            return false;
        }
    };

    @Override
    public void handleTagdata(String rfid) {
        etInputRfid.setText(rfid);
    }

    @Override
    public void handleTriggerPress(boolean pressed) {
    }

    @Override
    public void scanCode(String code) {
    }
}