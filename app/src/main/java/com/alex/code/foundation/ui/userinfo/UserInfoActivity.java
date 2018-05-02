package com.alex.code.foundation.ui.userinfo;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.ProfileInfo;
import com.alex.code.foundation.bean.UploadPicBean;
import com.alex.code.foundation.utils.DensityUtil;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.TimePickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

@Route(path = "/foundation/userinfo")
public class UserInfoActivity extends BaseMvpActivity<UserInfoView, UserInfoPresenter> implements UserInfoView, TakePhoto.TakeResultListener,InvokeListener {

    @BindView(R.id.toolBar)
    CustomToolBar    mToolBar;
    @BindView(R.id.username)
    TextView         mUsername;
    @BindView(R.id.tv_gender)
    TextView         mTvGender;
    @BindView(R.id.tv_birthday)
    TextView         mTvBirthday;
    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;

    @Inject
    ToastInstance mToastInstance;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private TimePickerView pvTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void init() {

        mToolBar.setTitle("个人信息").setLeftBackListener(() -> finish());
        initTimePicker();
        getPresenter().getProfileInfo();
    }

    @OnClick({R.id.ll_header, R.id.ll_username, R.id.ll_sex, R.id.ll_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header:
                DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(R.layout.dialog_select_photo))
                        .setGravity(Gravity.BOTTOM)
                        .setMargin(0,0,0, DensityUtil.dip2px(10))
                        .setCancelable(true)
                        .setContentBackgroundResource(Color.TRANSPARENT)
                        .setOnClickListener((dialog, view1) -> {
                            switch (view1.getId()) {
                                case R.id.tv_takePhoto:
                                    if (getSaveUri() != null) {
                                        getTakePhoto().onPickFromCaptureWithCrop(getSaveUri(),getCropOptions());
                                    }
                                    break;
                                case R.id.tv_select:
                                    if (getSaveUri() != null) {
                                        getTakePhoto().onPickFromGalleryWithCrop(getSaveUri(),getCropOptions());
                                    }
                                    break;
                                case R.id.tv_cancle:
                                    break;
                                default:
                            }
                            dialog.dismiss();
                        })
                        .create()
                        .show();
                break;
            case R.id.ll_username:
                ARouter.getInstance().build("/foundation/changeName").navigation();
                break;
            case R.id.ll_sex:
                DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(R.layout.dialog_select_sex))
                        .setGravity(Gravity.BOTTOM)
                        .setMargin(0,0,0, DensityUtil.dip2px(10))
                        .setCancelable(true)
                        .setContentBackgroundResource(Color.TRANSPARENT)
                        .setOnClickListener((dialog, view1) -> {
                            switch (view1.getId()) {
                                case R.id.tv_man:
                                    mTvGender.setText("男");
                                    getPresenter().updateProfileInfo("","1","");
                                    break;
                                case R.id.tv_woman:
                                    mTvGender.setText("女");
                                    getPresenter().updateProfileInfo("","2","");
                                    break;
                                case R.id.tv_cancle:
                                    break;
                                default:
                            }
                            dialog.dismiss();
                        })
                        .create()
                        .show();
                break;
            case R.id.ll_birthday:
                pvTime.show(mTvBirthday);
                break;
            default:
        }
    }

    @Override
    public void onSuccess(ProfileInfo.UserinfoEntity userInfo) {

        mUsername.setText(userInfo.getNick_name());
        mSdvHead.setImageURI(AppConstants.PIC_BASE_URL+ userInfo.getAvatar());
        mTvGender.setText(userInfo.getSex() == 2 ? "女" : "男");
        mTvBirthday.setText(userInfo.getBirthday());

    }

    @Override
    public void showUpdateData(ProfileInfo.UserinfoEntity userInfo) {
        mUsername.setText(userInfo.getNick_name());
        mSdvHead.setImageURI(AppConstants.PIC_BASE_URL+ userInfo.getAvatar());
        mTvGender.setText(userInfo.getSex() == 2 ? "女" : "男");
        mTvBirthday.setText(userInfo.getBirthday());
    }

    @Override
    public void onPicSuccess(UploadPicBean uploadPicBean) {

        if (uploadPicBean != null && uploadPicBean.getFile_path() != null) {
            getPresenter().updateHead(uploadPicBean.getFile_path().get(0));
            mSdvHead.setImageURI(AppConstants.PIC_BASE_URL + uploadPicBean.getFile_path().get(0));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<ProfileInfo.UserinfoEntity> event) {

        if (TextUtils.equals(event.getMsg(), EventCons.UPDATE_NAME)) {
            ProfileInfo.UserinfoEntity data = event.getData();
            mUsername.setText(data.getNick_name());
        }

    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 0, 23);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
            // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
            TextView textView = (TextView) v;
            textView.setText(getTime(date));
            getPresenter().updateProfileInfo("","",getTime(date));
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
//                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public Uri getSaveUri() {

        try {
            String path = Environment.getExternalStorageDirectory()
                    + "/ishop";
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            File file = new File(f, "ishop.jpg");

            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }


    @Override
    public void takeSuccess(TResult result) {

        String originalPath = result.getImage().getOriginalPath();
        VLog.d("takeSuccess：" + originalPath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("type", "avatar");//上传图片类型

            File file = new File(originalPath);//filePath 图片地址
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            //                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file_upload[0]", file.getName(), requestBody);//"imgfile"+i 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();
        getPresenter().uploadPic(parts);
    }

    @Override
    public void takeFail(TResult result, String msg) {

        VLog.d("takeFail：" + msg);
    }

    @Override
    public void takeCancel() {
        VLog.d("takeCancel：" );
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    private CropOptions getCropOptions(){
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
        return cropOptions;
    }
}
