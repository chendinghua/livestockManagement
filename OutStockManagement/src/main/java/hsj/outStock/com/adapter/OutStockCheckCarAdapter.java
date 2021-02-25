package hsj.outStock.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.List;
import hsj.outStock.com.R;
import hsj.outStock.com.entry.OutStockCarList;
import wang.relish.widget.vehicleedittext.VehicleKeyboardHelper;

/**
 * Created by 16486 on 2020/12/7.
 */

public class OutStockCheckCarAdapter extends BaseAdapter {

    private Context mContext;

    private List<OutStockCarList> tagList;
    //初始化当前列表添加点击事件
    private OnItemOperationClickListener operationListener;



    boolean isDelete=false;
    public OutStockCheckCarAdapter(Context mContext, List<OutStockCarList> tagList, OnItemOperationClickListener operationListener) {
        this.mContext = mContext;
        this.tagList = tagList;
        this.operationListener = operationListener;


    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        int count = 0;
        if(tagList!=null)
            count=tagList.size();

        return count;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        OutStockCarList outStockCarList = null;
        if(tagList!=null)
            outStockCarList = tagList.get(position);

        return outStockCarList;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater from = LayoutInflater.from(mContext);
        View view = from.inflate(R.layout.out_stock_check_car_list_items, null);
        final EditText etOperName = view.findViewById(R.id.et_out_stock_check_OPERName);
        final EditText etOperTelPhone = view.findViewById(R.id.et_out_stock_check_OPERTelphone);
        final EditText etCar = view.findViewById(R.id.et_out_stock_check_Car);
        ImageView addCarInfo  = view.findViewById(R.id.iv_add_car_info);
        ImageView removeCarInfo  = view.findViewById(R.id.iv_remove_car_info);

        VehicleKeyboardHelper.bind(etCar);

        final OutStockCarList outStock =  tagList.get(position);
        if(outStock!=null){
           addCarInfo.setVisibility(outStock.isShowAdd()?View.VISIBLE:View.GONE ) ;
        }

        if(outStock!=null  &&  !outStock.isShowAdd()  || tagList.size()-1==0 && outStock.isShowAdd()  || tagList.size()-1==position && isDelete  &&  outStock.isShowAdd()){

            etCar.setText(outStock.getCar());
            etOperName.setText(outStock.getOPERName());
            etOperTelPhone.setText(outStock.getOPERTelphone());
        }


        addCarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operationListener!=null){
                    OutStockCarList outStockCarList = new OutStockCarList();
                    outStockCarList.setCar(etCar.getText().toString());
                    outStockCarList.setOPERName(etOperName.getText().toString());
                    outStockCarList.setOPERTelphone(etOperTelPhone.getText().toString());
                    //隐藏添加按钮
                    outStockCarList.setShowAdd(false);
                    operationListener.onAddClick(position,outStockCarList);
                    isDelete=false;
                }
            }
        });
        removeCarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operationListener!=null){
                    operationListener.onRemoveClick(position);
                    isDelete=true;
                }
            }
        });



        return view;
    }



    public  interface OnItemOperationClickListener{
        /**
         *
         * @param index
         */
        void onAddClick(int index,OutStockCarList outStockCarList);

        void onRemoveClick(int index);
    }

}
