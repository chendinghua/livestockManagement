package hsj.query.fragment;

import android.app.Fragment;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.JSONAnalysis;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.kyindicator.KyIndicator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import hsj.query.R;
import hsj.query.R2;

/**
 * Created by 16486 on 2020/12/25.
 */

public class QueryFragment extends BaseFragment {
    //标题信息
    @BindView(R2.id.kyIndicator)
    public  KyIndicator yIndicator;
    //分页控件
    @BindView(R2.id.viewpager)
    public ViewPager viewPager;


    @Override
    protected int getLayoutResource() {
        return R.layout.query_fragment;
    }

    @Override
    protected void initView() {

        HashMap<String,Object> map = new HashMap<>();
        map.put("RfidNo","A20201201175608000000003");

        InteractiveDataUtil.interactiveMessage(activity,map,new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
            /*  Set<Map.Entry<String,Object>> entrySet  = JSON.parseObject(msg.getData().getString("result")).entrySet();
                Iterator<Map.Entry<String,Object>> it = entrySet.iterator();
                while (it.hasNext()){
                    Map.Entry<String,Object> map = it.next();
                    Log.d("resultKeyOrValue", "handlerExecutionFunction: key="+map.getKey()+"     value="+map.getValue());
                    if(map.getValue()!=null && map.getKey().equals("Data")){
                        Set<Map.Entry<String,Object>> dataSet =  JSON.parseObject(map.getValue().toString()).entrySet();
                        Iterator<Map.Entry<String,Object>> dataIterator = dataSet.iterator();
                        while(dataIterator.hasNext()){
                            Map.Entry<String,Object> dataMap = dataIterator.next();
                            Log.d("resultKeyOrValueMap", "dataMap          key="+dataMap.getKey()+"     dataMap="+map.getValue());

                        }
                    }
                }*/
           JSONAnalysis jsonAnalysis =     JSONAnalysis.getInstance();
             //   jsonAnalysis    .getAnalysisEntities(msg.getData().getString("result"));
                jsonAnalysis.getAnalysisEntry(msg.getData().getString("result"), JSONAnalysis.ANALYSIS.ENTRY);

                jsonAnalysis.getAnalysisEntry(msg.getData().getString("result"), JSONAnalysis.ANALYSIS.ITEMS);

            }
        }), MethodEnum.GETRFIDINFORMATION, InteractiveEnum.GET);

    }

    @Override
    protected boolean isLoad() {
        return true;
    }
}
