package hsj.instock.com.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.utils.Utils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hsj.instock.com.R;
import hsj.instock.com.R2;
import hsj.instock.com.entry.MoveStockInfoResult;
import hsj.instock.com.entry.MoveStockTaskInfo;

/**
 * Created by 16486 on 2020/12/11.
 */

public class MoveStockInfoResultActivity extends Activity {


    MoveStockTaskInfo.MoveStockTaskInfoList moveStockTaskInfo;
    @BindView(R2.id.lv_move_stock_info)
    ListView lvMoveStockInfo;

    @BindView(R2.id.tv_move_stock_count)
    TextView tvMoveStockCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instock_move_stock_info_result_activity);
        ButterKnife.bind(this);
      /*  ActionBar actionBar = getActionBar();
        //    actionBar.setTitle(R.string.ChangePassword);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("移栏详情");*/
        moveStockTaskInfo = (MoveStockTaskInfo.MoveStockTaskInfoList) getIntent().getExtras().getSerializable("MoveStockInfo");

        HashMap<String, Object> map = new HashMap<>();
        map.put("MoveID", moveStockTaskInfo.getID());
        InteractiveDataUtil.interactiveMessage(this, map, new HandlerUtils(this, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                List<MoveStockInfoResult> moveStockInfoResults = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), MoveStockInfoResult.class);
                for (int i = 0; i < moveStockInfoResults.size(); i++) {
                    moveStockInfoResults.get(i).setInStockName(moveStockTaskInfo.getStockName());
                    moveStockInfoResults.get(i).setOpTime(moveStockTaskInfo.getOpTime());
                }
                tvMoveStockCount.setText("" + moveStockInfoResults.size());
                AutoAdapter<MoveStockInfoResult> adapter =
                        new AutoAdapter<MoveStockInfoResult>(MoveStockInfoResultActivity.this, moveStockInfoResults, "ID", "RfidNo", "SerialNo", "OutStockName", "InStockName");
                lvMoveStockInfo.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }), MethodEnum.GETMOVESTOCKINFO, InteractiveEnum.GET);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Utils.onBackDialog(this,null);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R2.id.btn_move_stock_back)
    public void onViewClicked() {
        Utils.onBackDialog(this,null);
    }
}
