package com.thinkive.bank.qrcodedemo3.task;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class EnglishQRCodeTask extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private ImageView mEnglishIv;

    public EnglishQRCodeTask(Context context, ImageView mEnglishIv) {
        this.context = context;
        this.mEnglishIv = mEnglishIv;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return QRCodeEncoder.syncEncodeQRCode("bingoogolapple", BGAQRCodeUtil.dp2px(context, 150), Color.parseColor("#ff0000"));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            mEnglishIv.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "生成英文二维码失败", Toast.LENGTH_SHORT).show();
        }
    }
}
