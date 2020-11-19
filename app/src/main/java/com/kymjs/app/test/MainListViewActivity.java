package com.kymjs.app.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kymjs.app.R;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.entry.TestData;
import com.lwy.paginationlib.PaginationListView;
import com.lwy.paginationlib.PaginationRecycleView;
import com.lwy.paginationlib.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainListViewActivity extends AppCompatActivity implements PaginationListView.Adapter.OnItemClickListener<TestData> {


        private PaginationListView mPaginationRcv;
    private CustomAdapter mAdapter;
    private int[] perPageCountChoices = {10, 20, 30, 50};

        private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<TestData> data = (List<TestData>) msg.obj;
            Log.d("arrayData", "handleMessage: index "+msg.arg1 );
            for (int i =0;i<data.size();i++){
                Log.d("arrayData", "handleMessage: "+data.get(i).getName()+"   "+data.get(i).getTestData());
            }


            mAdapter.setDatas(msg.arg1, data);
            mPaginationRcv.setState(PaginationRecycleView.SUCCESS);
        }
    };
    private int mPerPageCount;
//    private PaginationIndicator mIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
//        mIndicatorView = (PaginationIndicator) findViewById(R.id.indicator);
//        mIndicatorView.setTotalCount(99);
//        mIndicatorView.setPerPageCountChoices(perPageCountChoices);
//        mIndicatorView.setListener(new PaginationIndicator.OnChangedListener() {
//            @Override
//            public void onPageSelectedChanged(int currentPapePos, int lastPagePos, int totalPageCount, int total) {
//                Toast.makeText(MainActivity.this, "选中" + currentPapePos + "页", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onPerPageCountChanged(int perPageCount) {
//
//            }
//        });

        mPaginationRcv = findViewById(R.id.pagination_rcv);

        mAdapter = new CustomAdapter(this, 99,-1,"id","name","testData");
        mAdapter.setDataTotalCount(50);
        mPaginationRcv.setAdapter(mAdapter);
//        mPaginationRcv.setPerPageCountChoices(perPageCountChoices);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
      /*  GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mPaginationRcv.setLayoutManager(layoutManager);*/
        mPaginationRcv.setListener(new PaginationListView.Listener() {
            @Override
            public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                final int loadPos = nextPagePosition;
                mPerPageCount = perPageCount;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(800);
                            Message msg = Message.obtain();
                            msg.obj = geneDatas(loadPos, mPerPageCount);
                            msg.arg1 = loadPos;
                            mHandler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onPerPageCountChanged(int perPageCount) {

            }
        });
        mAdapter.setOnItemClickListener(this);

    }

    /**
     * 产生假数据
     *
     * @param currentPagePosition 当前页面位置
     * @param perPageCount  当前页面数量
     * @return
     */
    public List<TestData> geneDatas(int currentPagePosition, int perPageCount) {
        int from = (currentPagePosition - 1) * perPageCount;
        List<TestData> datas = new ArrayList<>();
            for (int i = 0; i < perPageCount; i++) {
                TestData testData = new TestData(from++,"test","测试");

                datas.add(testData);
            }
        return datas;
    }

    @Override
    public void onItemClick(View view,TestData testData, int position) {
   /*     JSONObject item = mAdapter.getCurrentPageItem(position);  // 此处position返回的是recycleview的位置，所以取当前页显示列表的项
        Toast.makeText(this, item.optString("name"), Toast.LENGTH_LONG).show();*/
        UIHelper.ToastMessage(this,testData.toString());
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    class CustomAdapter extends PaginationListView.Adapter<TestData> {


        private Context mContext;

        public CustomAdapter(Context context, int dataTotalCount, int fixedLength, String... param) {
            super(dataTotalCount,context,fixedLength,param);
            mContext = context;
        }


     /*   @Override
        public void bindViewHolder(ViewHolder viewholder, JSONObject data) {
            viewholder.setText(R.id.text, data.optString("name"));
        }

        @Override
        public ViewHolder createViewHolder(@NonNull ViewGroup parent, int viewTypea) {
            return ViewHolder.createViewHolder(mContext, parent, R.layout.item_list);
        }*/
    }


}
