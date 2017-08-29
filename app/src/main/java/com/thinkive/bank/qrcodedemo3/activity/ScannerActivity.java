package com.thinkive.bank.qrcodedemo3.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.thinkive.bank.qrcodedemo3.utils.BitmapUtils;
import com.thinkive.bank.qrcodedemo3.utils.DecoderLocalFile;
import com.thinkive.bank.qrcodedemo3.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * @author: sq
 * @date: 2017/8/22
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 扫描二维码界面
 */
public class ScannerActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = ScannerActivity.class.getSimpleName();
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + File.separator + "fengci/";
    private final int CHOOSE_PICTURE = 1003;
    private boolean flashlightFlag;
    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        mQRCodeView = ((ZXingView) findViewById(R.id.zxingview));
        if (mQRCodeView != null) {
            mQRCodeView.setDelegate(this);
        }
    }

    public void btn(View view) {
        switch (view.getId()) {
            case R.id.top_light:
                if (!flashlightFlag) {
                    mQRCodeView.openFlashlight(); //打开闪光灯
                    flashlightFlag = true;
                } else {
                    mQRCodeView.closeFlashlight();  //关闭闪光灯
                    flashlightFlag = false;
                }
                break;
            case R.id.top_openpicture:
                getPicture();  //获取相册图片
                break;
            case R.id.top_back:
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect(); //显示扫描框
        mQRCodeView.startSpot(); //开始识别(延迟1.5秒)
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, "扫描结果：" + result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    /***
     * 调用系统相册
     */
    private void getPicture() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }

    /**
     * 设置文件名称
     *
     * @return
     */
    public static String setImageName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
        return simpleDateFormat.format(new Date()) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver resolver = getContentResolver();
            // 照片的原始资源地址
            Uri originalUri = data.getData();
            try {
                // 使用ContentProvider通过URI获取原始图片
                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                if (photo != null) {
                    // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                    Bitmap smallBitmap = BitmapUtils.zoomBitmap(photo, photo.getWidth() / 2, photo.getHeight() / 2);
                    photo.recycle(); // 释放原始图片占用的内存，防止out of memory异常发生
                    String bitmappath = BitmapUtils.saveCompressBitmap(smallBitmap, ALBUM_PATH, setImageName());
                    DecoderLocalFile decoder = new DecoderLocalFile(bitmappath);
                    String phone = decoder.handleQRCodeFormPhoto(ScannerActivity.this, DecoderLocalFile.loadBitmap(bitmappath));
                    if ("-1".equals(phone)) {
                        Toast.makeText(ScannerActivity.this, "图片中无二维码信息.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScannerActivity.this, "扫描结果:" + phone, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
