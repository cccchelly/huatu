package com.alex.code.foundation.ui.logistics;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.ExpressEntity;
import com.alex.code.foundation.bean.LogisticsBean;
import com.alex.code.foundation.view.CustomToolBar;
import com.alex.code.foundation.view.VerticalStepView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

@Route(path = "/foundation/logistics")
public class LogisticsActivity extends BaseMvpActivity<LogisticsView, LogisticsPresenter> implements LogisticsView{

    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.step_view)
    VerticalStepView mStepView;

    @Autowired
    public ExpressEntity expressEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolbar.setTitle("物流详情")
                .setLeftBackListener(this::finish);

        if (expressEntity != null) {
            String express_code = expressEntity.getExpress_code();
            String express_no = expressEntity.getExpress_no();
            String from_area = expressEntity.getFrom_area();
            String to_area = expressEntity.getTo_area();

            getPresenter().getLogisticsInfo(express_code,express_no,from_area,to_area);
        }
    }

    @Override
    public void onSuccess(LogisticsBean logisticsBean) {
        ArrayList<String> strings = new ArrayList<>();
        List<LogisticsBean.DataEntity> data = logisticsBean.getData();
        for (LogisticsBean.DataEntity dataEntity : data) {

            strings.add(dataEntity.getContext() + "\n" + dataEntity.getTime());
        }

        if (TextUtils.equals(data.get(0).getStatus(), "签收")) {

            mStepView.setStepsViewIndicatorComplectingPosition(strings.size() - 1)//设置完成的步数
                    .reverseDraw(false)//default is true
                    .setStepViewTexts(strings)//总步骤
                    .setTextColor(R.color.text_999999)
                    .setTextSize(12)
                    .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.text_14a83b))//设置StepsViewIndicator完成线的颜色
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.text_999999))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.btn_color))//设置StepsView text完成文字的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.text_999999))//设置StepsView text未完成文字的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator已完成图片
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator默认图片
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator正在进行中的图片
        } else {
            mStepView.setStepsViewIndicatorComplectingPosition(0)//设置完成的步数
                    .reverseDraw(false)//default is true
                    .setStepViewTexts(strings)//总步骤
                    .setTextColor(R.color.text_999999)
                    .setTextSize(12)
                    .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.text_14a83b))//设置StepsViewIndicator完成线的颜色
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.text_999999))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.btn_color))//设置StepsView text完成文字的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.text_999999))//设置StepsView text未完成文字的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator已完成图片
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator默认图片
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator正在进行中的图片

        }

    }
}
