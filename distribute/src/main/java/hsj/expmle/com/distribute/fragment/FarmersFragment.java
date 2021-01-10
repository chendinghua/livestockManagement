package hsj.expmle.com.distribute.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.DicInfo;
import com.kymjs.app.base_res.utils.base.entry.Farmer.Farmers;
import com.kymjs.app.base_res.utils.base.entry.location.LocationEntry;
import com.kymjs.app.base_res.utils.base.entry.packing.PackingTask;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerPorts;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;
import com.kymjs.app.base_res.utils.tools.NumberUtils;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hsj.expmle.com.distribute.R;
import hsj.expmle.com.distribute.R2;
import hsj.expmle.com.distribute.activity.DistributeActivity;
import hsj.expmle.com.distribute.entry.ProductInfo;

/**
 * 耳标分发输入分发部门信息页面
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
    @BindView(R2.id.tv_location)
    TextView tvLocation;
    OptionsPickerView pvOptions;
    @BindView(R2.id.et_farmers_name)
    EditText etFarmersName;



    DicInfo spFarmersDeptData;

//    OptionsPickerView packageOptions;


    boolean isLoadFarmersName=false;


    String provinceIndex="-1";

    String cityIndex="-1";

    String countyIndex="-1";


    String currentLoginProvinceIndex;

    String currentLoginCityIndex;
    String currentLoginCountyIndex;


    @Override
    protected int getLayoutResource() {
        return R.layout.distribute_farmers_fragment;
    }

    @Override
    protected void initView() {

        currentLoginProvinceIndex=   SPUtils.getSharedStringData(activity, "AdressProvince");
        currentLoginCityIndex = SPUtils.getSharedStringData(activity, "AdressCity");
        currentLoginCountyIndex =  SPUtils.getSharedStringData(activity, "AdressCounty");

        //初始化部门类型
        HashMap<String, Object> deptMap = new HashMap<>();
        deptMap.put("groupName", "DeptType");
        final   List<DicInfo>  deptList = new ArrayList<>();


        deptList.addAll( SpinnerTools.change(activity, spFarmersDeptName, deptMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", new SpinnerPorts<DicInfo>() {
            @Override
            public void selectChangeData(DicInfo data) {


                spFarmersDeptData=data;
                if(!isLoadFarmersName) {

                    provinceIndex = currentLoginProvinceIndex;
                    cityIndex=currentLoginCityIndex;
                    countyIndex=currentLoginCountyIndex;

                    loadFarmersNameInfo(currentLoginProvinceIndex,currentLoginCityIndex,currentLoginCountyIndex, etFarmersName.getText().toString(), -1);
                    isLoadFarmersName=true;
                }else{
//                    tvDeptTitleInfo.setText(deptList.get(spFarmersDeptName.getSelectedItemPosition()).getName()+"信息:");
                    loadFarmersNameInfo(provinceIndex,cityIndex,countyIndex,etFarmersName.getText().toString(),SPUtils.getSharedIntData(activity,"Type"));
                }

            }
        }, false));


        //初始化畜种信息
        SpinnerTools.change(activity, spFarmersProduct, null, MethodEnum.PRODCTINFO, ProductInfo.class, "Name", "ID", null, false);

        HashMap<String, Object> dicMap = new HashMap<>();
        dicMap.put("groupName", "storagetype");
        //初始化分发类型
        SpinnerTools.change(activity, spFarmersProductType,
                dicMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", new SpinnerPorts<DicInfo>() {
                    @Override
                    public void selectChangeData(DicInfo data) {
                        if ("2".equals(data.getValue())) {
                            spFarmersProduct.setEnabled(false);
                        } else {
                            spFarmersProduct.setEnabled(true);
                        }
                    }
                }, false);


        HandlerUtils handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if (MethodEnum.GETREGINFO.equals(msg.getData().getString("method"))) {
                    List<LocationEntry> entries = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), LocationEntry.class);
                    if (entries != null) {
                        final List<LocationEntry> provinceLocationItems = new ArrayList<>();
                        final List<List<LocationEntry>> cityLocationItems = new ArrayList<>();
                        final List<List<List<LocationEntry>>> countyLocationItems = new ArrayList<>();
                        for (int i = 0; i < entries.size(); i++) {
                            //判断当前为省级   并且判断当前省级id不为空
                            if (entries.get(i).getLevel() == 1
                                /*    && !Utils.isStrEmpty(currentLoginProvinceIndex)
                                    && currentLoginProvinceIndex.equals(entries.get(i).getID())
                                    ||
                                    entries.get(i).getLevel() == 1 && Utils.isStrEmpty(currentLoginProvinceIndex)*/
                                    ) {
                                //添加省级集合
                                provinceLocationItems.add(entries.get(i));
                                List<LocationEntry> cityItems = new ArrayList<>();
                                List<List<LocationEntry>> countyItems = new ArrayList<>();
                                cityItems.add(new LocationEntry("-1","-全部-",2));

                                boolean isOne=true;
                                List<LocationEntry> countyTempItems = new ArrayList<>();
                                for (int j = 0; j < entries.size(); j++) {
                                    //判断当前市级内容为全部 则县级也添加全部字段
                                    if(cityItems.size()==1 && isOne){
                                        countyTempItems.add(new LocationEntry("-1","-全部-",2));
                                        countyItems.add(countyTempItems);
                                        isOne=false;
                                        j--;
                                    }else
                                    //判断当前市级parentId和市级id一样
                                    if (entries.get(j).getParent_id().equals(entries.get(i).getID())) {
                                        cityItems.add(entries.get(j));
                                         countyTempItems = new ArrayList<>();
                                        countyTempItems.add(new LocationEntry("-1","-全部-",2));
                                       // countyItems.add(countyTempItems);
                                        for (int k = 0; k < entries.size(); k++) {
                                            //判断当前县级和区级parentid  跟市级的parentid一样
                                            if (entries.get(k).getParent_id().equals(entries.get(j).getID())) {
                                                countyTempItems.add(entries.get(k));
                                            }
                                        }
                                        countyItems.add(countyTempItems);
                                    }
                                }
                                cityLocationItems.add(cityItems);
                                countyLocationItems.add(countyItems);
                            }
                        }
                        pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                                provinceIndex=provinceLocationItems.get(options1).getID();
                                cityIndex=cityLocationItems.get(options1).get(options2).getID();
                                countyIndex=countyLocationItems.get(options1).get(options2).get(options3).getID();

                                loadFarmersNameInfo(provinceIndex,cityIndex,countyIndex,etFarmersName.getText().toString(),SPUtils.getSharedIntData(activity,"Type"));


                                tvLocation.setText(provinceLocationItems.get(options1).getName()+
                                        "-"+cityLocationItems.get(options1).get(options2).getName()+
                                        "-"+countyLocationItems.get(options1).get(options2).get(options3).getName());


                            }
                        }).setTitleText("城市选择")
                                .setDividerColor(Color.BLACK)
                                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                                .setContentTextSize(20)
                                .build();
                        pvOptions.setPicker(provinceLocationItems, cityLocationItems, countyLocationItems);//三级选择器
                    }

                }else if(MethodEnum.GETQELBOXPACKAGE.equals(msg.getData().getString("method"))){
                 /*   List<PackingTask.PackingTaskItems>  caseNumberItems = new ArrayList<>();
                    List<List<PackingTask.PackingTaskItems>> packingItems = new ArrayList<>();
                    List<List<List<Integer>>> packSizeItems = new ArrayList<>();
                    PackingTask packingTask =   JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),PackingTask.class);
                    if(packingTask!=null  && packingTask.getResult().size()>0){
                        List<PackingTask.PackingTaskItems> tempList=  packingTask.getResult();
                        for (int i = 0;i<tempList.size();i++){
                            //判断获取当前所有箱号信息
                            if(tempList.get(i).getTYPE()==2){
                                caseNumberItems.add(tempList.get(i));
                                List<PackingTask.PackingTaskItems>  packingItem = new ArrayList<>();
                                List<List<Integer>> packSizeTemp = new ArrayList<>();
                                packingItem.add(new PackingTask.PackingTaskItems(-1,"--全部--"));

                                for (int j =0;j<tempList.size();j++){
                                    if(tempList.get(j).getPID() == tempList.get(i).getId()){
                                        List<Integer> packSizeItem = new ArrayList<>();
                                        packingItem.add(tempList.get(j));
                                        for (int k =1;k<=tempList.get(j).getNum();k++){
                                            packSizeItem.add(k);
                                        }
                                        packSizeTemp.add(packSizeItem);
                                    }
                                }
                                packingItems.add(packingItem);
                                packSizeItems.add(packSizeTemp);
                            }
                        }
                        packageOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            }
                        }).setTitleText("请选择箱包信息")

                                .setDividerColor(Color.BLACK)
                                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                                .setContentTextSize(20)
                                .build();
                        packageOptions.setPicker(caseNumberItems, packingItems, packSizeItems);//三级选择器



                    }*/
                }
            }
        });





        HashMap<String, Object> locationMap = new HashMap<>();
        locationMap.put("Type", -1);
        InteractiveDataUtil.interactiveMessage(activity, locationMap, handlerUtils, MethodEnum.GETREGINFO, InteractiveEnum.GET);

        HashMap<String,Object> packageMap = new HashMap<>();
        packageMap.put("Type",-1);
        packageMap.put("pageSize",100000);
        packageMap.put("pageIndex",1);
        InteractiveDataUtil.interactiveMessage(activity,packageMap,handlerUtils,MethodEnum.GETQELBOXPACKAGE,InteractiveEnum.GET);


        etFarmersName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadFarmersNameInfo(provinceIndex,cityIndex,countyIndex,etFarmersName.getText().toString(),SPUtils.getSharedIntData(activity,"Type"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
          }

    public void loadFarmersNameInfo(String adressProvince,String adressCity,String adressCounty,String deptName,Integer tempType){
        //初始化养殖户信息
        HashMap<String, Object> map = new HashMap<>();

        if(spFarmersDeptData!=null) {
            map.put("DeptType", spFarmersDeptData.getValue());
        }
        //
        map.put("AdressProvince", adressProvince);
        map.put("AdressCity", adressCity);
        map.put("AdressCounty", adressCounty);
        map.put("TempType",tempType);
        map.put("DeptName",deptName);
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
        }, false);

    }

    @Override
    protected boolean isLoad() {
        return false;
    }


   /* @OnClick(R2.id.btn_farmers_next)
    public void onViewClicked() {

        if (activity instanceof DistributeActivity) {

            DistributeActivity distributeActivity = (DistributeActivity) activity;
            distributeActivity.scanCount = NumberUtils.getNumberData(asvReceiveSingleNumber.getText().toString());
            distributeActivity.farmersId = Integer.parseInt(spFarmersName.getTag().toString());
            distributeActivity.farmersProductId = Integer.parseInt(spFarmersProduct.getTag().toString());
            distributeActivity.farmersProductTypeId = Integer.parseInt(spFarmersProductType.getTag().toString());
            distributeActivity.pager.setCurrentItem(distributeActivity.pager.getCurrentItem() + 1);

        }

    }*/


    @OnClick({R2.id.tv_location, R2.id.btn_farmers_next})
    public void onViewClicked(View view) {
        RCaster caster = new RCaster(R.class,R2.class);

        switch (caster.cast(view.getId())) {
            case R2.id.tv_location:
                if(pvOptions!=null) {
                    pvOptions.show();
                }else{
                    UIHelper.ToastMessage(activity,"地区加载中。。。");
                }
                break;
            case R2.id.btn_farmers_next:
                if (activity instanceof DistributeActivity) {

                    DistributeActivity distributeActivity = (DistributeActivity) activity;
                    distributeActivity.scanCount = NumberUtils.getNumberData(asvReceiveSingleNumber.getText().toString());
                    distributeActivity.farmersId = Integer.parseInt(spFarmersName.getTag().toString());
                    distributeActivity.farmersProductId = Integer.parseInt(spFarmersProduct.getTag().toString());
                    distributeActivity.farmersProductTypeId = Integer.parseInt(spFarmersProductType.getTag().toString());
                    distributeActivity.pager.setCurrentItem(distributeActivity.pager.getCurrentItem() + 1);
                }
                break;
        }
    }
}