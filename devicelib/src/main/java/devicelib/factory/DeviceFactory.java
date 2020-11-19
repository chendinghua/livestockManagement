package devicelib.factory;

import android.content.Context;
import android.util.Log;

import devicelib.Become.BecomeDevice;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;


/**
 * Created by 16486 on 2020/4/9.
 */

public class DeviceFactory {

    Context context;
    ResponseHandlerInterface responseHandler;

    public DeviceFactory(Context context, ResponseHandlerInterface responseHandler) {
        this.context = context;
        this.responseHandler = responseHandler;
    }

    public Device getDevice(){
        String deviceType = getPhoneModel();
        Log.d("currentName", "deviceType:  "+deviceType);
        Device device=null;
        //成为
     //   if("C72".equals(deviceType) || "handheld".equals(deviceType)){
            device = new BecomeDevice(context,responseHandler);
        /*//斑马
        } else if("MC33".equals(deviceType)){
            device = new ZebraDevice(context,responseHandler);
        //优博讯
        }else if("DT50".equals(deviceType)){
            device = new TestDT50Device(context,responseHandler);
        }else if("common".equals(deviceType)){
         //   device = new CommonDevice(context,responseHandler);
        }else if("AUTOID9N".equals(deviceType)){
            device = new A9Device(context,responseHandler);*/
    //    }

        return device;
    }



    /**     * 获取机型     */
    public String getPhoneModel() {
        //        TelephonyManager manager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);//
        String mtype = android.os.Build.MODEL;
        String brand = android.os.Build.BRAND;
        //手机品牌
        String model = android.os.Build.MODEL;
        //手机型号
        Log.w("", "手机型号：" + brand + " " + model);
        return model;
    }

}
