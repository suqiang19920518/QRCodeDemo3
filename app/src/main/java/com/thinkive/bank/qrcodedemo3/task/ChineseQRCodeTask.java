package com.thinkive.bank.qrcodedemo3.task;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ChineseQRCodeTask extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private ImageView mChineseIv;

    public ChineseQRCodeTask(Context context, ImageView mChineseIv) {
        this.context = context;
        this.mChineseIv = mChineseIv;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return QRCodeEncoder.syncEncodeQRCode("王浩", BGAQRCodeUtil.dp2px(context, 150));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            mChineseIv.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
        }
    }
}
