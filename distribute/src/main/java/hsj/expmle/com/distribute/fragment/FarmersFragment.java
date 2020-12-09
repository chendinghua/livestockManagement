package hsj.expmle.com.distribute.fragment;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.DicInfo;
import com.kymjs.app.base_res.utils.base.entry.Farmer.Farmers;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerPorts;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;
import com.kymjs.app.base_res.utils.tools.NumberUtils;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.view.addAndSubView.Baseres_AddAndSubView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import hsj.expmle.com.distribute.R;
import hsj.expmle.com.distribute.R2;
import hsj.expmle.com.distribute.activity.DistributeActivity;
import hsj.expmle.com.distribute.entry.ProductInfo;

/** 耳标分发输入分发部门信息页面
 * Created by 16486 on 2020/10/23.
 */
public class FarmersFragment extends BaseFragment {
    @BindView(R2.id.sp_distribute_farmers_name)
    Spinner spFarmersName;

    @BindView(R2.id.sp_distribute_farmers_Product)
    Spinner spFarmersProduct;

    @BindView(R2.id.sp_distribute_farmers_Product_type)
    Spinner spFarmersProductType;

    @BindView(R2.id.sp_distribute_farmers_dept_name)
    Spinner spFarmersDeptName;


    @BindView(R2.id.asv_receive_single_number)
    EditText asvReceiveSingleNumber;
    @BindView(R2.id.tv_distribute_name)
    TextView tvDistributeName;
    @BindView(R2.id.tv_distribute_phone)
    TextView tvDistributePhone;
    @BindView(R2.id.tv_distribute_email)
    TextView tvDistributeEmail;
    @BindView(R2.id.tv_distribute_OpTime)
    TextView tvDistributeOpTime;
    @BindView(R2.id.tv_distribute_AdressProvinceName)
    TextView tvDistributeAdressProvinceName;
    @BindView(R2.id.tv_distribute_AdressCityName)
    TextView tvDistributeAdressCityName;
    @BindView(R2.id.tv_distribute_AdressCountyName)
    TextView tvDistributeAdressCountyName;
    @BindView(R2.id.tv_distribute_AddressInfo)
    TextView tvDistributeAddressInfo;

    @Override
    protected int getLayoutResource() {
        return R.layout.distribute_farmers_fragment;
    }

    @Override
    protected void initView() {
        //初始化部门类型
        HashMap<String,Object> deptMap = new HashMap<>();
        deptMap.put("groupName","DeptType");
        SpinnerTools.change(activity, spFarmersDeptName, deptMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", new SpinnerPorts<DicInfo>() {
            @Override
            public void selectChangeData(DicInfo data) {
                //初始化养殖户信息
                HashMap<String,Object> map = new HashMap<>();
                map.put("DeptType",data.getValue());
                map.put("AdressProvince", SPUtils.getSharedStringData(activity,"AdressProvince"));
                map.put("AdressCity",SPUtils.getSharedStringData(activity,"AdressCity"));
                map.put("AdressCounty",SPUtils.getSharedStringData(activity,"AdressCounty"));
                SpinnerTools.change(activity, spFarmersName, map, MethodEnum.FARMERSALLLISTBYID, Farmers.class, "Name", "ID", new SpinnerPorts<Farmers>() {
                    //显示养殖户基本信息
                    @Override
                    public void selectChangeData(Farmers data) {
                        if (data != null) {
                            tvDistributeName.setText(data.getName());
                            tvDistributePhone.setText(data.getPhone());
                            tvDistributeEmail.setText(data.getEmail());
                            tvDistributeOpTime.setText(data.getOpTime());
                            tvDistributeAdressProvinceName.setText(data.getAdressProvinceName());
                            tvDistributeAdressCityName.setText(data.getAdressCityName());
                            tvDistributeAdressCountyName.setText(data.getAdressCountyName());
                            tvDistributeAddressInfo.setText(data.getAddressInfo());
                        }
                    }
                },false);
            }
        },false);



        //初始化畜种信息
        SpinnerTools.change(activity, spFarmersProduct, null, MethodEnum.PRODCTINFO, ProductInfo.class, "Name", "ID", null,false);

        HashMap<String,Object> dicMap = new HashMap<>();
        dicMap.put("groupName","storagetype");
        //初始化分发类型
        SpinnerTools.change(activity, spFarmersProductType,
                dicMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", new SpinnerPorts<DicInfo>() {
                    @Override
                    public void selectChangeData(DicInfo data) {
                        if("2".equals(data.getValue())){
                            spFarmersProduct.setEnabled(false);
                        }else{
                            spFarmersProduct.setEnabled(true);
                        }
                    }
                },false);
    }

    @Override
    protected boolean isLoad() {
        return false;
    }


    @OnClick(R2.id.btn_farmers_next)
    public void onViewClicked() {

        if(activity instanceof DistributeActivity){

            DistributeActivity distributeActivity = (DistributeActivity)activity;
            distributeActivity.scanCount = NumberUtils.getNumberData( asvReceiveSingleNumber.getText().toString());
            distributeActivity.farmersId = Integer.parseInt( spFarmersName.getTag().toString());
            distributeActivity.farmersProductId = Integer.parseInt(spFarmersProduct.getTag().toString());
            distributeActivity.farmersProductTypeId = Integer.parseInt(spFarmersProductType.getTag().toString());
            distributeActivity.pager.setCurrentItem(distributeActivity.pager.getCurrentItem()+1);

        }

    }
}
