package hsj.home.com.fragment;

import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
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

   /* @BindView(R2.id.pcv_main)
    PieChartView pcvMain;*/

    @Override
    protected int getLayoutResource() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        setPieDatas();
    }

    @Override
    protected boolean isLoad() {
        return false;
    }



    /*========= 状态相关 =========*/
    private boolean isExploded = false;                 //每块之间是否分离
    private boolean isHasLabelsInside = false;          //标签在内部
    private boolean isHasLabelsOutside = false;         //标签在外部
    private boolean isHasCenterCircle = false;          //空心圆环
    private boolean isPiesHasSelected = false;          //块选中标签样式
    private boolean isHasCenterSingleText = false;      //圆环中心单行文字
    private boolean isHasCenterDoubleText = false;      //圆环中心双行文字
    /*========= 数据相关 =========*/
    private PieChartData mPieChartData;                 //饼状图数据
 //   @BindView(R2.id.chart1)

 //   private PieChart chart;

    /**
     * 设置相关数据
     */
    private void setPieDatas() {

        HashMap<String,Object> map = new HashMap<>();
        InteractiveDataUtil.interactiveMessage(activity,map,new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                List<HomeEntry> entries = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"),HomeEntry.class);
                 //*===== 随机设置每块的颜色和数据 =====*//*
               /* List<SliceValue> values = new ArrayList<>();
                for (int i = 0; i < entries.size(); ++i) {
                    SliceValue sliceValue = new SliceValue(entries.get(i).getValue(), ChartUtils.pickColor());
                    values.add(sliceValue);
                }
         /*//*===== 设置相关属性 类似Line Chart =====*//**//*
                mPieChartData = new PieChartData(values);
                mPieChartData.setHasLabels(isHasLabelsInside);
                mPieChartData.setHasLabelsOnlyForSelected(isPiesHasSelected);
                mPieChartData.setHasLabelsOutside(isHasLabelsOutside);
                mPieChartData.setHasCenterCircle(isHasCenterCircle);

                pcvMain.setPieChartData(mPieChartData);

*/
              //  setData(entries);


            }
        }), MethodEnum.GETINDEX, InteractiveEnum.GET);
/*
        int numValues = 6;                //把一张饼切成6块

        *//*===== 随机设置每块的颜色和数据 =====*//*
        List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue(20, ChartUtils.pickColor());
            values.add(sliceValue);
        }
         *//*===== 设置相关属性 类似Line Chart =====*//*
        mPieChartData = new PieChartData(values);
        mPieChartData.setHasLabels(isHasLabelsInside);
        mPieChartData.setHasLabelsOnlyForSelected(isPiesHasSelected);
        mPieChartData.setHasLabelsOutside(isHasLabelsOutside);
        mPieChartData.setHasCenterCircle(isHasCenterCircle);

        mPieChartView.setPieChartData(mPieChartData);         //设置控件*/
    }

//    private void setData( List<HomeEntry> datas) {
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < datas.size(); i++) {
//            entries.add(new PieEntry(datas.get(i).getValue(), datas.get(i).getName()));
//        }
//
//        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
//
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//
//        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
//      //  data.setValueTypeface(tf);
//        chart.setData(data);
//
//        // undo all highlights
//        chart.highlightValues(null);
//
//        chart.invalidate();
//    }


}
