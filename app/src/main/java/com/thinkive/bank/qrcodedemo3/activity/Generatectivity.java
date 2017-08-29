package com.thinkive.bank.qrcodedemo3.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.thinkive.bank.qrcodedemo3.R;
import com.thinkive.bank.qrcodedemo3.task.ChineseQRCodeTask;
import com.thinkive.bank.qrcodedemo3.task.ChineseQRCodeWithLogoTask;
import com.thinkive.bank.qrcodedemo3.task.DecodeTask;
import com.thinkive.bank.qrcodedemo3.task.EnglishQRCodeTask;
import com.thinkive.bank.qrcodedemo3.task.EnglishQRCodeWithLogoTask;

/**
 * @author: sq
 * @date: 2017/8/22
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 二维码图片生成、识别
 */
public class Generatectivity extends AppCompatActivity {
    private ImageView mChineseIv;
    private ImageView mEnglishIv;
    private ImageView mChineseLogoIv;
    private ImageView mEnglishLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        initView();
        createQRCode();
    }

    private void initView() {
        mChineseIv = (ImageView) findViewById(R.id.iv_chinese);
        mChineseLogoIv = (ImageView) findViewById(R.id.iv_chinese_logo);
        mEnglishIv = (ImageView) findViewById(R.id.iv_english);
        mEnglishLogoIv = (ImageView) findViewById(R.id.iv_english_logo);
    }

    /**
     * 生成二维码
     */
    private void createQRCode() {
        createChineseQRCode();
        createEnglishQRCode();
        createChineseQRCodeWithLogo();
        createEnglishQRCodeWithLogo();
    }

    private void createChineseQRCode() {
        ChineseQRCodeTask task = new ChineseQRCodeTask(this, mChineseIv);
        task.execute();
    }

    private void createEnglishQRCode() {
        EnglishQRCodeTask task = new EnglishQRCodeTask(this, mEnglishIv);
        task.execute();
    }

    private void createChineseQRCodeWithLogo() {
        ChineseQRCodeWithLogoTask task = new ChineseQRCodeWithLogoTask(this, mChineseLogoIv);
        task.execute();
    }

    private void createEnglishQRCodeWithLogo() {
        EnglishQRCodeWithLogoTask task = new EnglishQRCodeWithLogoTask(this, mEnglishLogoIv);
        task.execute();
    }

    public void decodeChinese(View v) {
        mChineseIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mChineseIv.getDrawingCache();
        DecodeTask task = new DecodeTask(this, bitmap, "解析中文二维码失败");
        task.execute();
    }

    public void decodeEnglish(View v) {
        mEnglishIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mEnglishIv.getDrawingCache();
        DecodeTask task = new DecodeTask(this, bitmap, "解析英文二维码失败");
        task.execute();
    }

    public void decodeChineseWithLogo(View v) {
        mChineseLogoIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mChineseLogoIv.getDrawingCache();
        DecodeTask task = new DecodeTask(this, bitmap, "解析带logo的中文二维码失败");
        task.execute();
    }

    public void decodeEnglishWithLogo(View v) {
        mEnglishLogoIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mEnglishLogoIv.getDrawingCache();
        DecodeTask task = new DecodeTask(this, bitmap, "解析带logo的英文二维码失败");
        task.execute();
    }

    public void decodeIsbn(View v) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test_isbn);
        DecodeTask task = new DecodeTask(this, bitmap, "解析条形码失败");
        task.execute();
    }

}