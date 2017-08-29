package com.thinkive.bank.qrcodedemo3.task;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

public class DecodeTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private Bitmap bitmap;
    private String errorTip;

    public DecodeTask(Context context, Bitmap bitmap, String errorTip) {
        this.context = context;
        this.bitmap = bitmap;
        this.errorTip = errorTip;
    }

    @Override
    protected String doInBackground(Void... params) {
        return QRCodeDecoder.syncDecodeQRCode(bitmap);
    }

    @Override
    protected void onPostExecute(String result) {
        if (TextUtils.isEmpty(result)) {
            Toast.makeText(context, errorTip, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}
