package com.kymjs.app.base_res.utils.tools;

/**
 * Created by 16486 on 2020/12/6.
 */

public class NumberUtils {

    public static int getNumberData(String number){
        return "".equals(number)? 0:Integer.parseInt(number);

    }
}
