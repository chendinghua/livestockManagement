package hsj.expmle.com.prevention;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.kymjs.app.base_res.utils.DatePicker.date.DatePickerDialogFragment;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.OnClick;
import hsj.expmle.com.prevention.activity.PreventionActivity;

/** 疫苗模块
 * Created by 16486 on 2020/10/20.
 */
public class PreventionFragment extends BaseFragment {

    @BindView(R2.id.btn_distribute_add)
    Button btnDistribute;

    @BindView(R2.id.tv_put_out_start_time)
    TextView tvStartTime;

    @BindView(R2.id.tv_put_out_end_time)
    TextView tvEndTime;

    Activity activity;

    @Override
    protected int getLayoutResource() {
        return R.layout.prevention_fragment;
    }

    @Override
    protected void initView() {
            activity = getActivity();
            tvStartTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
            tvEndTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
    }

    @Override
    protected boolean isLoad() {
        return true;
    }

    @OnClick({R2.id.btn_distribute_add})
    public void onClickListener(View v) {
        RCaster rcaster = new RCaster(R.class,R2.class);
        Log.d("fragmentOnClick:", "onClick: "+v.getId() +"    "+rcaster.cast(v.getId())+"           "+R2.id.btn_distribute_add);

        switch (rcaster.cast(v.getId())) {
            case R2.id.btn_distribute_add:
                Utils.gotoActivity(getActivity(), PreventionActivity.class,null,null);
                break;
            case R2.id.tv_put_out_start_time:
             //   datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
                selectTime(tvStartTime);
                break;
            case R2.id.tv_put_out_end_time:
                selectTime(tvEndTime);
                break;
        }
    }
    private void selectTime(final TextView tvTime){
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
            @Override
            public void onDateChoose(int year, int month, int day) {
                tvTime.setText(year + "-" + month + "-" + day);

            }
        });
    }
}
