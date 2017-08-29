package com.thinkive.bank.qrcodedemo3.task;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkive.bank.qrcodedemo3.R;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ChineseQRCodeWithLogoTask extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private ImageView mChineseLogoIv;

    public ChineseQRCodeWithLogoTask(Context context, ImageView mChineseLogoIv) {
        this.context = context;
        this.mChineseLogoIv = mChineseLogoIv;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
        return QRCodeEncoder.syncEncodeQRCode("王浩", BGAQRCodeUtil.dp2px(context, 150), Color.parseColor("#ff0000"), logoBitmap);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            mChineseLogoIv.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
        }
    }
}
