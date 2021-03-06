package com.kymjs.app.base_res.utils.tools;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.entry.currencyEntry.MapEntry;
import com.kymjs.app.base_res.utils.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 16486 on 2020/12/30.
 */

public class JSONAnalysis {

    private static JSONAnalysis jsonAnalysis = null;
    private JSONAnalysis(){}

    public static JSONAnalysis getInstance(){
        if (jsonAnalysis == null) {
            jsonAnalysis = new JSONAnalysis();
        }
        return jsonAnalysis;
    }




    public HashMap<String,String> getAnalysisEntities(String result){


        HashMap<String,String> hashEntities =null;
        Set<Map.Entry<String,Object>> entrySet  = JSON.parseObject(result).entrySet();
        Iterator<Map.Entry<String,Object>> it = entrySet.iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> map = it.next();
            Log.d("resultKeyOrValue", "handlerExecutionFunction: key="+map.getKey()+"     value="+map.getValue());
            if(map.getValue()!=null && map.getKey().equals("Data")){
                Set<Map.Entry<String,Object>> dataSet =  JSON.parseObject(map.getValue().toString()).entrySet();
                Iterator<Map.Entry<String,Object>> dataIterator = dataSet.iterator();
                while(dataIterator.hasNext()){
                    Map.Entry<String,Object> dataMap = dataIterator.next();
                    if(dataMap.getKey().endsWith("Entities")){
                        Log.d("resultKeyOrValueMap", "dataMap          key="+dataMap.getKey()+"     dataMap="+dataMap.getValue());

                        hashEntities = new HashMap<>();
                        Set<Map.Entry<String,Object>> hashSet =     JSON.parseObject(map.getValue().toString()).entrySet();
                        Iterator<Map.Entry<String,Object>> hashIterator = hashSet.iterator();
                        while(hashIterator.hasNext()){
                            Map.Entry<String,Object>  hashMap = hashIterator.next();
                            hashEntities.put(hashMap.getKey(),hashMap.getValue().toString());
                        }
                        break;
                    }
                }
                break;
            }
        }
        return hashEntities;

    }


    public List<MapEntry> getAnalysisEntry(Context context, String result, ANALYSIS analysis){
       List<MapEntry> mapList= new ArrayList<>();



        Set<Map.Entry<String,Object>> entrySet  = JSON.parseObject(result).entrySet();
        Iterator<Map.Entry<String,Object>> it = entrySet.iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> map = it.next();
            LogUtils.d(context,"resultKeyOrValue", "handlerExecutionFunction: key="+map.getKey()+"     value="+map.getValue());
            if(map.getValue()!=null && map.getKey().equals("Data")){
                Set<Map.Entry<String,Object>> dataSet =  JSON.parseObject(map.getValue().toString()).entrySet();
                Iterator<Map.Entry<String,Object>> dataIterator = dataSet.iterator();
                while(dataIterator.hasNext()){
                    Map.Entry<String,Object> dataMap = dataIterator.next();
                    Set<Map.Entry<String,Object>> keyValueSet =   JSON.parseObject( dataMap.getValue().toString()).entrySet();
                    Iterator<Map.Entry<String,Object>> keyValueIterator = keyValueSet.iterator();
                    HashMap<String,String> tempMap  = new HashMap<>();

                    while(keyValueIterator.hasNext()) {
                        Map.Entry<String, Object> keyValueMap = keyValueIterator.next();
                       // if("key".equals(keyValueMap.getKey()) && analysis.getIndex().equals(keyValueMap.getValue()) ){
                        tempMap.put(keyValueMap.getKey(),keyValueMap.getValue().toString());
                      //  keyValueMap
                     //   }
                    }
                    //判断当前有效数据添加到集合中
                    if(analysis.getIndex().equals(tempMap.get("key"))){
                        List<HashMap<String,String>> lists = new ArrayList<>();
                        MapEntry entry = new MapEntry();
                        LogUtils.d(context,"validDataMapItem", "getAnalysisEntry: key: "+tempMap.get("key")+"   value"+tempMap.get("Value"));
                        entry.setTitle(tempMap.get("Name"));
                   //   List< HashMap<String,Object>> mapSet =   JSON.parseArray(tempMap.get("value"),  HashMap<String,Object>().getClass());
                        for (int i =0;i<JSON.parseArray(tempMap.get("Value")).size();i++){
                            Set<Map.Entry<String,Object>> validDataSet =      JSON.parseArray(tempMap.get("Value")).getJSONObject(i).entrySet();
                            HashMap<String,String> validDataMap = new HashMap<>();
                            Iterator<Map.Entry<String,Object>> validDataIterator = validDataSet.iterator();
                            while(validDataIterator.hasNext()) {
                                Map.Entry<String, Object> validDataKeyValueMap = validDataIterator.next();
                                validDataMap.put(validDataKeyValueMap.getKey(),validDataKeyValueMap.getValue().toString());
                                LogUtils.d(context,"validDataMap", "getAnalysisEntry:  key:"+validDataKeyValueMap.getKey()+"   value"+validDataKeyValueMap.getValue().toString());

                            }
                            lists.add(validDataMap);

                        }
                        entry.setTempList(lists);
                        mapList.add(entry);

                    }


                }
                break;
            }
        }

        return mapList;

    }



    public  enum ANALYSIS{

        ENTRY("1"),ITEMS("2");
        private String index;

        private ANALYSIS(String index) {
            this.index = index;
        }


        public String getIndex() {
            return index;
        }
        public void setIndex(String index) {
            this.index = index;
        }
    }

}
