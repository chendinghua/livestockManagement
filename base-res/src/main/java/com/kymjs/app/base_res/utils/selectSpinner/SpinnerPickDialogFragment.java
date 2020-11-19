package com.kymjs.app.base_res.utils.selectSpinner;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.DatePicker.date.Baseres_DatePicker;
import com.kymjs.app.base_res.utils.DatePicker.date.DatePickerDialogFragment;

import java.util.List;

/**
 * Created by 16486 on 2020/10/23.
 */

public class SpinnerPickDialogFragment<T> extends DialogFragment {

    protected Baseres_SpinnerPicker spinnerPicker;

    List<T> listData;

    private Baseres_SpinnerPicker.OnSpinnerSelectedListener mOnSpinnerChooseListener;
    private boolean mIsShowAnimation = true;
    protected Button mCancelButton, mDecideButton;

    public void setOnDateChooseListener(Baseres_SpinnerPicker.OnSpinnerSelectedListener mOnSpinnerChooseListener) {
      this.mOnSpinnerChooseListener = mOnSpinnerChooseListener;
    }

    public void showAnimation(boolean show) {
        mIsShowAnimation = show;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baseres_dialog_spinner, container);

        spinnerPicker = view.findViewById(R.id.spinnerPicker_dialog);
        mCancelButton = view.findViewById(R.id.btn_dialog_spinner_cancel);
        mDecideButton = view.findViewById(R.id.btn_dialog_spinner_decide);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDecideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSpinnerChooseListener != null) {
                    mOnSpinnerChooseListener.onYearSelected(spinnerPicker.getSelectedSpinner());
                }
                dismiss();
            }
        });

      /*  if (mSelectedYear > 0) {
            setSelectedDate();
        }*/

        initChild();
        return view;
    }

    protected void initChild() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.baseres_DatePickerBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.baseres_dialog_date);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        Window window = dialog.getWindow();
        if (window != null) {
            if (mIsShowAnimation) {
                window.getAttributes().windowAnimations = R.style.baseres_DatePickerDialogAnim;
            }
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            lp.dimAmount = 0.35f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        return dialog;
    }

    public void setSelectedDate(List<T> listData) {
       this. listData= listData;
        setSelectedDate();
    }

    private void setSelectedDate() {
        if (spinnerPicker != null) {
            spinnerPicker.updateSpinner(listData);
        }
    }

    public interface OnDateChooseListener {
        void onDateChoose(int year, int month, int day);
    }
}
