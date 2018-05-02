package com.alex.code.foundation.utils;

import android.net.Uri;
import android.os.Environment;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/17.
 */

public class TakePhotoUtils {

    private TakePhoto mTakePhoto;
    private int mLimit;

    public TakePhotoUtils(TakePhoto takePhoto) {
        mTakePhoto = takePhoto;
    }

    public void takePhotos(int type) {
        switch (type) {
            case 1://拍照
                mTakePhoto.onPickFromCapture(getSaveUri());
                break;
            case 2://相册
                mTakePhoto.onPickMultiple(mLimit);
                break;
        }
    }

    public TakePhotoUtils setLimit(int limit) {
        mLimit = limit;
        return this;
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
    /**
     *
     * @param isPickWithOwn 是否使用takephoto自带相册
     * @param isCorrect 是否纠正拍照角度
     */
    private void configTakePhotoOption(boolean isPickWithOwn, boolean isCorrect){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
            builder.setWithOwnGallery(isPickWithOwn);
            builder.setCorrectImage(isCorrect);
        mTakePhoto.setTakePhotoOptions(builder.create());

    }

    /**
     * 压缩图片
     * @param isOwn  压缩工具，是否使用自带
     * @param maxSize 最大大小
     * @param width 宽
     * @param height    高
     * @param enableRawFile 拍照压缩后是否保存原图
     * @param showProgressBar   是否显示压缩进度框
     */
    private void configCompress(boolean isOwn,int maxSize,int width,int height, boolean enableRawFile,boolean showProgressBar){
        CompressConfig config;
        if(isOwn){
            config=new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width>=height? width:height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        }else {
            LubanOptions option=new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config=CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        mTakePhoto.onEnableCompress(config,showProgressBar);


    }

    /**
     * 裁剪
     * @param withWonCrop  是否使用自带裁剪工具
     * @param isAspect
     * @param height
     * @param width
     * @return
     */
    private CropOptions getCropOptions(boolean withWonCrop,boolean isAspect, int width, int height) {

        CropOptions.Builder builder = new CropOptions.Builder();

        if (isAspect) {
            builder.setAspectX(width).setAspectY(height);
        } else {
            builder.setOutputX(width).setOutputY(height);
        }
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
