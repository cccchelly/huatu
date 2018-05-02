package com.alex.code.foundation.ui.address;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.AddressBean;
import com.alex.code.foundation.bean.ProvinceBean;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.KeyboardUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

@Route(path = "/foundation/newAddress")
public class NewAddressActivity extends BaseMvpActivity<NewAddressView, NewAddressPresenter> implements NewAddressView{

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.et_name)
    EditText      mEtName;
    @BindView(R.id.et_phone)
    EditText      mEtPhone;
    @BindView(R.id.tv_city)
    TextView      mTvCity;
    @BindView(R.id.et_address)
    TextView      mEtAddress;
    @BindView(R.id.ll_address)
    LinearLayout  mLlAddress;
    @BindView(R.id.checkbox)
    CheckBox      mCheckbox;
    @BindView(R.id.ll_default)
    LinearLayout  mLlDefault;

    @Inject
    ToastInstance mToastInstance;

    private ArrayList<ProvinceBean>                 options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>>            options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    public static final int TYPE_UPDATE = 100;//标记是编辑过来的，走更新方法

    private boolean isLoaded = false;
    private String mUserName;
    private String mUserPhone;
    private String mUserAddress;
    private String mProvince;
    private String mCity;
    private String mDistrict;

    @Autowired
    AddressBean.DataEntity addressData;

    @Autowired
    int type = 0;
    private String mAddressId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);

        mToolbar.setTitle("新增地址")
                .setLeftImage(R.drawable.ic_black_back)
                .setLeftBackListener(() -> finish());

        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        initData();
    }

    private void initData() {

        if (addressData != null) {
            mProvince = addressData.getProvince();
            mCity = addressData.getCity();
            mDistrict = addressData.getDistrict();
            mAddressId = addressData.getId();

            mEtName.setText(addressData.getConsigner());
            mEtPhone.setText(addressData.getMobile());
            mTvCity.setText(addressData.getProvince()+" "+addressData.getCity()+" "+addressData.getDistrict());
            mEtAddress.setText(addressData.getAddress());
            mCheckbox.setChecked(addressData.getIs_default() == 1);
        }
    }


    @OnClick({R.id.ll_address, R.id.ll_default, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                if (!KeyboardUtils.hideKeyboard(mEtName)) {
                    KeyboardUtils.hideKeyboard(mEtPhone);
                }
                if (isLoaded){
                    ShowPickerView();
                }else {
                    Toast.makeText(App.getAppContext(),"数据解析中",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_default:
                break;
            case R.id.tv_save:
                if (checkRules()) {
                    VLog.d("mCheckbox.isChecked(): "+mCheckbox.isChecked());
                    if (type == TYPE_UPDATE) {
                        getPresenter().updateAddress(mAddressId,mUserName,mUserPhone,mProvince,mCity,mDistrict,mUserAddress,mCheckbox.isChecked() ? "1" : "0");
                    } else {
                        getPresenter().postAddress(mUserName,mUserPhone,mProvince,mCity,mDistrict,mUserAddress,mCheckbox.isChecked() ? "1" : "0");
                    }
                }
                break;
        }
    }

    private boolean checkRules() {

        Editable name = mEtName.getText();
        Editable phone = mEtPhone.getText();
        CharSequence address = mEtAddress.getText();

        if (name == null || TextUtils.isEmpty(name.toString().trim())) {
            mToastInstance.showToast("姓名不能为空!");
            return false;
        }
        if (phone == null || TextUtils.isEmpty(phone.toString().trim()) || !CommonUtils.isMobileNO(phone.toString().trim())) {
            mToastInstance.showToast("手机号码不合法！");
            return false;
        }

        if (TextUtils.equals(mTvCity.getText(),"请选择")) {
            mToastInstance.showToast("请选择省市区！");
            return false;
        }

        if (address == null || TextUtils.isEmpty(address.toString().trim())) {
            mToastInstance.showToast("请输入详细地址!");
            return false;
        }

        mUserName = name.toString();
        mUserPhone = phone.toString().trim();
        mUserAddress = address.toString();

        return true;
    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(App.getAppContext(),"获取省市区地址失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };



    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mProvince = options1Items.get(options1).getPickerViewText();
                mCity = options2Items.get(options1).get(options2);
                mDistrict = options3Items.get(options1).get(options2).get(options3);

                String address = mProvince +" "+ mCity + " "+ mDistrict;

                mTvCity.setText(address);
//                Toast.makeText(App.getAppContext(),tx,Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<ProvinceBean> ProvinceBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = ProvinceBean;

        for (int i=0;i<ProvinceBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<ProvinceBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = ProvinceBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (ProvinceBean.get(i).getCityList().get(c).getArea() == null
                        ||ProvinceBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < ProvinceBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = ProvinceBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<ProvinceBean> parseData(String result) {//Gson 解析
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public void onSuccess() {

        EventBus.getDefault().post(new MessageEvent(EventCons.ADDRESS_REFRESH));
        finish();
    }
}
