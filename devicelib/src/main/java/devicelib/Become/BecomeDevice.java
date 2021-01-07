package devicelib.Become;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.io.UnsupportedEncodingException;

import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.dao.SoundManager;

/** H7设备
 * Created by 16486 on 2020/4/8.
 */
public class BecomeDevice implements Device{
    Context context;
    private Handler tagHandler = new Handler();
    private boolean loopFlag = false;
    private  Runnable myRunnable;
    public RFIDWithUHF mReader;
    ResponseHandlerInterface responseHandler;
    public Barcode2DWithSoft barcode2DWithSoft=null; //二维码对象
    HomeKeyEventBroadCastReceiver receiver;
    /**
     *
     * @param context
     */
    public BecomeDevice(Context context,ResponseHandlerInterface responseHandler){
        this.context=context;
        this.responseHandler=responseHandler;
    }
    /**
     * 初始化RFID模块
     */
    @Override
    public void initUHF() {
        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {
            toastMessage(ex.getMessage());
            return;
        }
        if (mReader != null) {
            new InitTask().execute();
        }
    }
    public void toastMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean isLoop(){
        return loopFlag;

    }
    /**
     * 开始读取RFID
     */
    public void startRead() {
        loopFlag=true;
        read();
    }
    //扫描物理按键触发
    @Override
    public void onKeyDown(int keyCode, KeyEvent event,int status,boolean isSingle) {
        if(keyCode==280 && status==1){
            //判断是否读取当个RFID数据
            if(isSingle){
                String strUII = mReader.inventorySingleTag();
                if (!TextUtils.isEmpty(strUII)) {
                    String strResult = "";
                    //这里是用来生成编码的
                    final String strEPC = mReader.convertUiiToEPC(strUII);
                   responseHandler.handleTagdata(strEPC);
                } else {
                }
            }else {
                responseHandler.handleTriggerPress(loopFlag);
                if (!loopFlag) {
                    if (mReader.startInventoryTag(0, 0)) {
                        startRead();
                    } else {
                        //用户点击停止识别则暂停上面扫描的线程
                        mReader.stopInventory();
                    }
                } else {
                    //用户点击停止识别则暂停上面扫描的线程
                    mReader.stopInventory();
                    stopRead();
                }
            }
        }else if(keyCode==280 && status==2){
            Log.i("readTag","外部 "+barcode2DWithSoft);
            if (barcode2DWithSoft != null) {

                if(!loopFlag) {
                    loopFlag=true;
                    Log.i("readTag", "ScanBarcode");
                    barcode2DWithSoft.scan();
                    barcode2DWithSoft.setScanCallback(ScanBack1);
                }else{
                    loopFlag=false;
                    barcode2DWithSoft.stopScan();

                }


            }
        }
    }
    //二维码扫描回调函数
    public Barcode2DWithSoft.ScanCallback  ScanBack1= new Barcode2DWithSoft.ScanCallback(){
        @Override
        public void onScanComplete(int i, int length, byte[] bytes) {
            String barcode;
            if (length < 1) {
                if (length == -1) {
                    loopFlag=false;
                    Toast.makeText(context,"Scan cancel",Toast.LENGTH_SHORT).show();// inputServer.setText("Scan cancel");
                } else if (length == 0) {
                    loopFlag=false;
                    Toast.makeText(context,"Scan TimeOut",Toast.LENGTH_SHORT).show();   //inputServer.setText("Scan TimeOut");
                } else {
                }
            }else{
                SoundManager.getInstance(context).playSound(1);
                barcode="";
                try {
                    barcode = new String(bytes, 0, length, "ASCII");
                    zt();
                }
                catch (UnsupportedEncodingException ex)   {}
                responseHandler.scanCode(barcode);
                loopFlag=false;
                Log.d("ScanCodeEnabled", "扫描到了数据 "+barcode);
            }
        }
    };
    void zt() {
        Vibrator vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }
    @Override
    public void destroy(){
        if(mReader!=null){
            mReader.free();
        }
        // 销毁二维码扫描模块对象
        if(barcode2DWithSoft!=null){
            barcode2DWithSoft.stopScan();
            barcode2DWithSoft.close();
        }
    }
    @Override
    public String onResume() {
        return null;
    }
    @Override
    public void onPause() {
        barcode2DWithSoft=Barcode2DWithSoft.getInstance();
        receiver = new HomeKeyEventBroadCastReceiver();
        context.registerReceiver(receiver, new IntentFilter("com.rscja.android.KEY_DOWN"));
        new InitCodeTask().execute();
    }
    @Override
    public boolean setPower(int power) {
        return  mReader.setPower(power);
    }
    @Override
    public void setSanModelEnabel(boolean flag) {
    }
    //停止读取RFID
    public void stopRead() {
        loopFlag=false;
        if(myRunnable!=null)
            tagHandler.removeCallbacks(myRunnable);
    }
    @Override
    public void playSound(int id) {
        SoundManager.getInstance(context).playSound(id);
    }
    /**
     * �豸�ϵ��첽��
     *
     * @author liuruifeng
     */
    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if (!result) {
                Toast.makeText(context, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(context);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }
    //读取数据
    public void read(){
        myRunnable = new Runnable(){

            public void run() {
                RFIDWithUHF mReader = null;
                try {
                    mReader = RFIDWithUHF.getInstance();
                } catch (ConfigurationException e) {
                    e.printStackTrace();
                }
                String strTid;
                String strResult;
                String[] res = null;
                if (loopFlag && mReader != null) {
                    res = mReader.readTagFromBuffer();
                    if (res != null) {
                        strTid = res[0];
                        if (strTid.length() != 0 && !strTid.equals("0000000" +
                                "000000000") && !strTid.equals("000000000000000000000000")) {
                            strResult = "TID:" + strTid + "\n";
                        } else {
                            strResult = "";
                        }
                        Log.d("epcCode", "run: " + res[1] + strResult);

                        String newRfid = (res[1] + strResult).length() == 24 ? (res[1] + strResult) : (res[1] + strResult).substring(4, (res[1] + strResult).length());
                        Log.d("epcCode1", "run: " + newRfid);
                        //数据传输
                        responseHandler.handleTagdata(newRfid);
                    }
                }
                tagHandler.postDelayed(this,1);
            }

        };
        tagHandler.postDelayed(myRunnable,1);
    }
     public  class InitCodeTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            boolean reuslt=false;
            if(barcode2DWithSoft!=null) {
                reuslt=  barcode2DWithSoft.open(context);
            }
            return reuslt;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result){
                barcode2DWithSoft.setParameter(6, 1);
                barcode2DWithSoft.setParameter(22, 0);
                barcode2DWithSoft.setParameter(23, 55);
                barcode2DWithSoft.setParameter(402, 1);
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
            mypDialog.cancel();
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(context);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }
    //监听扫描Code广播数据
    class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
        static final String SYSTEM_REASON = "reason";
        static final String SYSTEM_HOME_KEY = "homekey";//home key
        static final String SYSTEM_RECENT_APPS = "recentapps";//long home key
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.rscja.android.KEY_DOWN")) {
                int reason = intent.getIntExtra("Keycode",0);
                boolean long1 = intent.getBooleanExtra("Pressed",false);
                // home key处理点
                if(reason==280 || reason==66){
                }
            }
        }
    }
}