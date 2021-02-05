package hsj.home.com.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.currencyEntry.MapEntry;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.JSONAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hsj.home.com.R;
import hsj.home.com.R2;
import hsj.home.com.entry.HomeEntry;
import lecho.lib.hellocharts.model.PieChartData;
/**
 * Created by Administrator on 2019/2/22/022.
 */

/***
 * 个人中心页面
 */
public class HomeFragment extends BaseFragment {
    @Override
    protected int getLayoutResource() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        layoutHome = rootView.findViewById(R.id.layout_home);

        layoutHome.removeAllViews();
        setPieDatas();
    }
    @Override
    protected boolean isLoad() {
        return true;
    }

   LinearLayout layoutHome;

    /**
     * 设置相关数据
     */
    private void setPieDatas() {

        InteractiveDataUtil.interactiveMessage(activity, null, new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
              //  List<HomeEntry> entries = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), HomeEntry.class);
                HashMap<String,String> tempMap = new HashMap<>();
                //获取键值对对象
                Set<Map.Entry<String, Object>> keyValueSet = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data")).getJSONObject("Map").entrySet();
                Iterator<Map.Entry<String, Object>> keyValueIterator = keyValueSet.iterator();
                while (keyValueIterator.hasNext()) {
                    Map.Entry<String, Object> keyValueMap = keyValueIterator.next();
                    tempMap.put(keyValueMap.getKey(), keyValueMap.getValue().toString());
                }

                List<MapEntry> entries =      JSONAnalysis.getInstance().getAnalysisEntry(msg.getData().getString("result"), JSONAnalysis.ANALYSIS.ITEMS);
                Log.d("entryDatas", "handlerExecutionFunction: "+entries.toString());
                for (MapEntry entry:entries) {

                    setData(entry, tempMap);
                }


            }
        }), MethodEnum.GETINDEX, InteractiveEnum.GET);

    }

    private void setData(MapEntry entry,HashMap<String,String> map) {
        PieChart chart1 = new PieChart(activity);
        PieChart.LayoutParams lp = new PieChart.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,800);
        chart1.setLayoutParams(lp);
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        List<HashMap<String, String>> datas = entry.getTempList();

        if(datas.size()>0){
            HashMap<String, String> data = datas.get(0);
            Set<Map.Entry<String, String>>  entrySet =   data.entrySet();
            Iterator<Map.Entry<String, String>> entryIterator =  entrySet.iterator();
            int count = 0;


            while (entryIterator.hasNext()) {
                Map.Entry<String, String> entryNet = entryIterator.next();
             float current =   Float.parseFloat( entryNet.getValue().toString());
                count+=current;
                entries.add(new PieEntry(  current, map.get(entryNet.getKey())));
            }
            if(count==0){
                return;
            }
        }else{
            return;
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        chart1.getDescription().setText(entry.getTitle());
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        //  data.setValueTypeface(tf);
        chart1.setData(data);

        // undo all highlights
        chart1.highlightValues(null);

        chart1.invalidate();

        layoutHome.addView(chart1);
    }
}