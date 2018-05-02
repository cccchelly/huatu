package com.alex.code.foundation.ui.comment.todo;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.bean.TodoCommentBean;
import com.alex.code.foundation.bean.UploadPicBean;
import com.alex.code.foundation.utils.DensityUtil;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
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
 * Date: 2017/11/17.
 */

@Route(path = "/foundation/todoComment")
public class ToDoCommentActivity extends BaseMvpActivity<ToDoCommentView, ToDoCommentPresenter> implements ToDoCommentView, TakePhoto.TakeResultListener,InvokeListener {

    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.sdv_pic)
    SimpleDraweeView mSdvPic;
    @BindView(R.id.tv_goods_des)
    TextView         mTvGoodsDes;
    @BindView(R.id.radioGroup)
    RadioGroup       mRadioGroup;
    @BindView(R.id.rb_good)
    RadioButton      mRbGood;
    @BindView(R.id.rb_mid)
    RadioButton      mRbMid;
    @BindView(R.id.rb_bad)
    RadioButton      mRbBad;
    @BindView(R.id.editText)
    EditText         mEditText;
    @BindView(R.id.recyclerview)
    RecyclerView     mRecyclerview;
    @BindView(R.id.shop_ratingBar)
    RatingBar        mShopRatingBar;
    @BindView(R.id.logistics_ratingBar)
    RatingBar        mLogisticsRatingBar;
    @BindView(R.id.tv_commit)
    TextView         mTvCommit;

    @Autowired
    public OrderDetailBean.GoodsListEntity goodsBean;
    @Autowired
    public String order_no;

    @Inject
    ToastInstance mToastInstance;

    private SelectPicAdapter mSelectPicAdapter;
    private List<TImage> mImgs = new ArrayList<>();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private int explain_type = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo_comment;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolbar.setTitle("评价")
                .setLeftBackListener(this::finish);

        initRecyclerView();
        initListener();
        initData();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        if (goodsBean != null) {
            mSdvPic.setImageURI(AppConstants.PIC_BASE_URL + goodsBean.getPicture());
            mTvGoodsDes.setText(goodsBean.getGoods_name());
        }
        mImgs = new ArrayList<>();
        mImgs.add(TImage.of("", TImage.FromType.OTHER));
        mSelectPicAdapter.setNewData(mImgs);
    }

    private void initListener() {

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_good:
                    explain_type = 1;
                    break;
                case R.id.rb_mid:
                    explain_type = 2;
                    break;
                case R.id.rb_bad:
                    explain_type = 3;
                    break;
            }
        });

        mSelectPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ib_delete:
                    adapter.remove(position);
                    break;
            }
        });

    }

    private void initRecyclerView() {
        mSelectPicAdapter = new SelectPicAdapter();
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h1)
                .create());
        mRecyclerview.setAdapter(mSelectPicAdapter);


    }


    @OnClick({R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:

                if (mImgs.size() > 1) { //需要传图片
                    MultipartBody.Builder builder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)//表单类型
                            .addFormDataPart("type", "goods_comment")//上传图片类型
                            .addFormDataPart("goods_id", goodsBean.getGoods_id()+"");//上传图片依赖的商品id

                    for (int i = 0; i < mImgs.size(); i++) {
                        if (i == 0)
                            continue;
                        TImage tImage = mImgs.get(i);

                        VLog.d("file: " + tImage.getCompressPath());
                        File file = new File(tImage.getCompressPath());//filePath 图片地址
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                        //                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        builder.addFormDataPart("file_upload[" + (i - 1) + "]", file.getName(), requestBody);//"imgfile"+i 后台接收图片流的参数名
                    }

                    List<MultipartBody.Part> parts = builder.build().parts();
                    getPresenter().uploadPic(parts);
                } else {
                    getPresenter().postComment(goodsBean.getGoods_id()+"",order_no,goodsBean.getOrder_goods_id()+"",mEditText.getText().toString()+"","0",explain_type+"",mShopRatingBar.getRating()+"",mLogisticsRatingBar.getRating()+"","");
                }
                break;
        }
    }

    @Override
    public void onPicSuccess(UploadPicBean uploadPicBean) {
        List<String> file_path = uploadPicBean.getFile_path();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < file_path.size(); i++) {
            String path = file_path.get(i);
            if (i == file_path.size() - 1) {
                sb.append(path).append("");
            } else {
                sb.append(path).append(",");
            }
        }

        getPresenter().postComment(goodsBean.getGoods_id()+"",order_no,goodsBean.getOrder_goods_id()+"",mEditText.getText().toString()+"","0",explain_type+"",mShopRatingBar.getRating()+"",mLogisticsRatingBar.getRating()+"",sb.toString());

    }

    @Override
    public void onSuccess(TodoCommentBean todoCommentBean) {

        mToastInstance.showToast("评论成功");
        EventBus.getDefault().post(new MessageEvent<>(EventCons.COMMENT_REFRESH));
        finish();
    }


    class SelectPicAdapter extends BaseQuickAdapter<TImage, BaseViewHolder> {

        public SelectPicAdapter() {
            super(R.layout.item_select_pic);
        }

        @Override
        protected void convert(BaseViewHolder helper, TImage item) {
            int position = helper.getAdapterPosition();
            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);

            if (position == 0) {
                sdvPic.setImageResource(R.drawable.ic_add_pic);
                helper.setGone(R.id.ib_delete, false);
                sdvPic.setOnClickListener(v -> DialogPlus.newDialog(ToDoCommentActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.dialog_select_photo))
                        .setGravity(Gravity.BOTTOM)
                        .setMargin(0,0,0, DensityUtil.dip2px(10))
                        .setCancelable(true)
                        .setContentBackgroundResource(Color.TRANSPARENT)
                        .setOnClickListener((dialog, view1) -> {
                            CompressConfig config = new CompressConfig.Builder()
                                    .setMaxSize(1024*1024)
                                    .setMaxPixel(1280)
                                    .enableReserveRaw(true)
                                    .create();
                            getTakePhoto().onEnableCompress(config,false);
                            switch (view1.getId()) {
                                case R.id.tv_takePhoto:
                                    if (getSaveUri() != null) {
                                        takePhoto.onPickFromCapture(getSaveUri());
                                    }
                                    break;
                                case R.id.tv_select:
                                    if (getSaveUri() != null) {
                                        getTakePhoto().onPickMultiple(9);
                                        //                                        getTakePhoto().onPickFromGalleryWithCrop(getSaveUri(),getCropOptions());
                                    }
                                    break;
                                case R.id.tv_cancle:
                                    break;
                                default:
                            }
                            dialog.dismiss();
                        })
                        .create()
                        .show());
            } else {
                sdvPic.setImageURI(Uri.parse("file://"+item.getCompressPath()));
                helper.setGone(R.id.ib_delete, true);
            }
            helper.addOnClickListener(R.id.ib_delete);

        }
    }


    public Uri getSaveUri() {

        try {
            String path = Environment.getExternalStorageDirectory()
                    + "/ishop";
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            File file = new File(f, System.currentTimeMillis() + ".jpg");

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

        ArrayList<TImage> images = result.getImages();
        for (TImage image : images) {
            VLog.d("CompressPath: "+ image.getCompressPath());
            VLog.d("OriginalPath: "+ image.getOriginalPath());
            VLog.d("uri: "+ Uri.parse(image.getOriginalPath()));
        }


        mImgs.addAll(images);
        if (mImgs.size() > 10) {
            Toast.makeText(this, "最多上传9张图片哦~", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < mImgs.size(); i++) {
            if (i > 9) {
                mImgs.remove(i);
            }
        }

        mSelectPicAdapter.notifyDataSetChanged();
//        mSelectPicAdapter.replaceData(images);
    }

    @Override
    public void takeFail(TResult result, String msg) {

        VLog.e("takeFail：" + msg);
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

}
