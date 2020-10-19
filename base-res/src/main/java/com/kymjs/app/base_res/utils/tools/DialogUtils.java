package com.kymjs.app.base_res.utils.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.kymjs.app.base_res.R;


/**
 * Created by Administrator on 2019/2/26/026.
 */

public class DialogUtils {

    public static AlertDialog.Builder showAlertDialog(Context context, final AlertDialogCallBack callBack, String message, DialogInterface.OnKeyListener keyListener, View view ) {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.baseres_SystemPrompt).setIcon(android.R.drawable.ic_dialog_info).setView(view);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.baseres_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callBack.alertDialogFunction();
            }
        });
        builder.setNegativeButton(R.string.baseres_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(keyListener!=null){
            builder.setOnKeyListener(keyListener);
        }

        builder.show();
        return builder;
    }


    public static AlertDialog.Builder showAlertDialog(Context context, final AlertDialogCallBack callBack,
                                                      int message, DialogInterface.OnKeyListener keyListener, View view ) {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.baseres_SystemPrompt).setIcon(android.R.drawable.ic_dialog_info).setView(view);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.baseres_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callBack.alertDialogFunction();
            }
        });
        builder.setNegativeButton(R.string.baseres_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(keyListener!=null){
            builder.setOnKeyListener(keyListener);
        }

        builder.show();
        return builder;
    }



    public static AlertDialog.Builder showAlertDialog(Context context, final AlertDialogCallBack callBack, final AlertDialogNegativeCallBack negative, String message, DialogInterface.OnKeyListener keyListener, View view ) {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.baseres_SystemPrompt).setIcon(android.R.drawable.ic_dialog_info).setView(view);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.baseres_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callBack.alertDialogFunction();
            }
        });
        builder.setNegativeButton(R.string.baseres_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                negative.alertDialogFunction();
                dialog.dismiss();
            }
        });
        if(keyListener!=null){
            builder.setOnKeyListener(keyListener);
        }

        builder.show();
        return builder;
    }


    public static AlertDialog.Builder showAlertDialog(Context context, final AlertDialogCallBack callBack, final AlertDialogNegativeCallBack negative, String message, DialogInterface.OnKeyListener keyListener, View view , boolean isOk, boolean isCancel) {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.baseres_SystemPrompt).setIcon(android.R.drawable.ic_dialog_info).setView(view);
        builder.setMessage(message);
     //   builder.setCancelable(false); //不响应back按钮
        if(isOk) {
            builder.setPositiveButton(R.string.baseres_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(callBack!=null)
                    callBack.alertDialogFunction();
                }
            });
        }
        if(isCancel) {
            builder.setNegativeButton(R.string.baseres_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(negative!=null)
                    negative.alertDialogFunction();
                    // dialog.dismiss();
                }
            });
        }
        if(keyListener!=null){
            builder.setOnKeyListener(keyListener);
        }

        builder.create().show();

        return builder;
    }
}
