package com.kymjs.app.base_res.utils.selectSpinner;

import android.content.Context;
import android.util.AttributeSet;

import com.kymjs.app.base_res.utils.DatePicker.Baseres_WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * 列表选择器
 * Created by ycuwq on 17-12-27.
 */
@SuppressWarnings("unused")
public    class  Baseres_SpinnerPicker< T>  extends Baseres_WheelPicker<T> {

    private int mSelectedSpinner;

    private List<T> spinnerList = new ArrayList<>();

    private OnSpinnerSelectedListener mOnSpinnerSelectedListener;

    public Baseres_SpinnerPicker(Context context) {
        this(context, null);
    }

    public Baseres_SpinnerPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Baseres_SpinnerPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        setItemMaximumWidthText("0000");
        setSelectedIndex(mSelectedSpinner, false);
        setOnWheelChangeListener(new OnWheelChangeListener<T>() {
            @Override
            public void onWheelSelected(T item, int position) {
                mSelectedSpinner = position;
                if (mOnSpinnerSelectedListener != null) {
                    mOnSpinnerSelectedListener.onYearSelected(item);
                }
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        mSelectedSpinner = Calendar.getInstance().get(Calendar.YEAR);
   //     TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Baseres_YearPicker);
      /*  mStartYear = a.getInteger(R.styleable.Baseres_YearPicker_baseres_startYear, 1900);
        mEndYear = a.getInteger(R.styleable.Baseres_YearPicker_baseres_endYear, 2100);*/
     //   a.recycle();

    }

    public void updateSpinner(List<T> list) {
        spinnerList=list;
        setDataList(list);
        setSelectedIndex(mSelectedSpinner, false);
    }



  

   
    public void setSelectedIndex(int selectedYear) {
        setSelectedIndex(selectedYear, true);
    }

    public void setSelectedIndex(int selectedYear, boolean smoothScroll) {
        setCurrentPosition(selectedYear , smoothScroll);
    }

    public T getSelectedSpinner() {
        return spinnerList.get(mSelectedSpinner);
    }

    public void setOnSpinnerSelectedListener(OnSpinnerSelectedListener onSpinnerSelectedListener) {
        mOnSpinnerSelectedListener = onSpinnerSelectedListener;
    }

    public interface OnSpinnerSelectedListener {
        void onYearSelected(Object obj);

    }

}
