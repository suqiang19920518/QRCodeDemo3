package com.thinkive.bank.qrcodedemo3.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: sq
 * @date: 2017/8/4
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: Bitmap工具类
 */
public class BitmapUtils {

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//此处格式可更改为PNG
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * bitmap转为base64
     *
     * @param photoFilePath
     * @return
     */
    public static String bitmapToBase64(String photoFilePath) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(photoFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//此处格式可更改为PNG
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 指定路径的file转化为Base64String
     *
     * @param path
     * @return
     */
    public static String fileToBase64String(String path) {
        try {
            File file = new File(path);
            FileInputStream inputFile;
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * bitmap转 byte[]
     * 将图片保存到本地
     *
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap, int mCompressVal) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, mCompressVal, baos);

        byte[] bs = baos.toByteArray();
        return bs;
    }

    /**
     * byte[] 转bitmap
     *
     * @param _data
     * @return
     */
    public static Bitmap loadBitmapWithByte(byte[] _data) {
        Bitmap photo;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            photo = BitmapFactory.decodeByteArray(_data, 0, _data.length, options);
        } catch (Exception e) {
            e.printStackTrace();
            photo = null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            photo = null;
        }
        return photo;
    }

    /**
     * file 转bitmap
     *
     * @param pathName
     * @return
     */
    public static Bitmap loadBitmapWithFile(String pathName) {
        Bitmap b;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            b = BitmapFactory.decodeFile(pathName, opts);
        } catch (Exception e) {
            e.printStackTrace();
            b = null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
        }
        return b;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 组合图片和源图片
     *
     * @param src       源图片
     * @param watermark 涂鸦图片
     * @return
     */
    public static Bitmap doodle(Bitmap src, Bitmap watermark) {

        // 另外创建一张图片
        // 创建一个新的和SRC长度宽度一样的位图
        Bitmap newb = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newb);

        canvas.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入原图片src

        //在src的右下角画入水印
        canvas.drawBitmap(watermark, src.getWidth() - watermark.getWidth() + 5, 5, null);

        canvas.save(Canvas.ALL_SAVE_FLAG);

        canvas.restore();

        watermark.recycle();

        watermark = null;

        return newb;

    }

    public static Bitmap makeWaterMark(String text) {

        int w = 360, h = 140;

        Bitmap waterMark = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvasTemp = new Canvas(waterMark);

        canvasTemp.drawColor(Color.TRANSPARENT);

        Paint p = new Paint();

        String familyName = "宋体";

//        Typeface font = Typeface.create(familyName, Typeface.BOLD);

        p.setColor(Color.BLUE);

//        p.setTypeface(font);

        p.setTextSize(24);

        canvasTemp.drawText(text, 0, 50, p);

        return waterMark;
    }

    /**
     * 解析图片为宽高不超过width和height的Bitmap对象
     *
     * @param source 字节数组
     * @param width  宽
     * @param height 高
     * @return 图片对象
     */
    public static Bitmap decodeByteArray(byte[] source, int width, int height) {
        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new ByteArrayInputStream(source), null,
                    o);

            // The new size we want to scale to
            final int REQUIRED_HEIGHT = height;
            final int REQUIRED_WIDTH = width;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;

            System.out.println(width_tmp + "  " + height_tmp);
            Log.w("===", (width_tmp + "  " + height_tmp));

            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_WIDTH
                        && height_tmp / 2 < REQUIRED_HEIGHT)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;

                Log.w("===", scale + "''" + width_tmp + "  " + height_tmp);
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new ByteArrayInputStream(source),
                    null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析图片为宽高不超过width和height的Bitmap对象
     *
     * @param source 输入流
     * @param width  宽
     * @param height 高
     * @return 图片对象
     */
    public static Bitmap decodeStream(InputStream source, int width, int height) {
        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(source, null, o);

            // The new size we want to scale to
            final int REQUIRED_HEIGHT = height;
            final int REQUIRED_WIDTH = width;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;

            System.out.println(width_tmp + "  " + height_tmp);
            Log.w("===", (width_tmp + "  " + height_tmp));

            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_WIDTH
                        && height_tmp / 2 < REQUIRED_HEIGHT)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;

                Log.w("===", scale + "''" + width_tmp + "  " + height_tmp);
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(source, null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap zipPic(Bitmap sourceBm, float targetWidth,
                                float targetHeight) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPurgeable = true; // 可删除
        newOpts.inInputShareable = true; // 可共享
        // 转成数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sourceBm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] temp = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length,
                newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = targetHeight;
        float ww = targetWidth;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 只进行分辨率压缩，不进行图片的质量压缩
     *
     * @param sourceBm
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap zipPicWithoutCompress(Bitmap sourceBm,
                                               float targetWidth, float targetHeight) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPurgeable = true; // 可删除
        newOpts.inInputShareable = true; // 可共享
        // 转成数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sourceBm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] temp = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length,
                newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = targetHeight;
        float ww = targetWidth;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片，使其小于100kb
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options > 10) {
                options -= 10;// 每次都减少10
            } else {
                break;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 压缩图片至指定大小
     *
     * @param image
     * @param size  目标大小，单位为kb
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int quality = 100;
        int nKB = 1024;
        do { // 循环判断如果压缩后图片是否大于sizekb,大于继续压缩
            baos.reset();// 重置baos即清空baos

            if (quality > 5) {
                quality -= 5;// 每次都减少10
            } else {
                break;
            }
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        } while (baos.size() / nKB > size);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 缩放Bitmap大小
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 将两张位图拼接成一张(纵向拼接)
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap add2Bitmap(Bitmap first, Bitmap second) {

        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }

    /**
     * 原始图片保存到本地
     *
     * @param bitmap
     */
    public static String saveBitmap(Bitmap bitmap, String savePath, String fiemName) {
        ByteArrayOutputStream bmStream = new ByteArrayOutputStream();
        int quality = 100;
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bmStream);

            File f = new File(savePath, fiemName);
            File dir = f.getParentFile();
            if (!dir.exists()) dir.mkdir();
            if (!f.exists()) f.createNewFile();
            bmStream.writeTo(new FileOutputStream(f));
            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bmStream.flush();
                bmStream.close();
                if (bitmap != null) bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩后的图片保存到本地
     *
     * @param bitmap
     */
    public static String saveCompressBitmap(Bitmap bitmap, String savePath, String fiemName) throws IOException {
        String path;
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(savePath + fiemName);
        path = myCaptureFile.getAbsolutePath();
        FileOutputStream fileOutputStream = new FileOutputStream(myCaptureFile);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        Bitmap compressImage = compressImage(bitmap);//图片进行压缩处理
        compressImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return path;
    }

    /**
     * 保存bitmap为图片
     *
     * @param bitmap
     * @param filePath
     * @throws IOException
     */

    public static void saveBitmap(Bitmap bitmap, String filePath)
            throws IOException {
        File f = null;
        String strPath = "";
        try {
            strPath = filePath;
            f = new File(strPath);
        } catch (Exception e) {
        }
        if (null != f) {
            f.createNewFile();
        } else {
            return;
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存bitmap为指定格式
     *
     * @param bmp
     * @param path
     * @param format
     * @return
     */
    public static boolean saveBmpToFile(Bitmap bmp, String path, Bitmap.CompressFormat format) {
        if (bmp == null || bmp.isRecycled())
            return false;

        OutputStream stream = null;
        try {
            File file = new File(path);
            File filePath = file.getParentFile();
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            stream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("return bmp.compress(format, 80, stream);..........");
        return bmp.compress(format, 80, stream);
    }

    public static Bitmap loadBitmap(String pathName) {
        Bitmap b = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            b = BitmapFactory.decodeFile(pathName, opts);
        } catch (Exception e) {
            e.printStackTrace();
            b = null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
        }
        return b;
    }

    public static String uri2FilePath(Context context, Uri imgUri) {

        String img_path = "";
        try {
            if (imgUri.getScheme().equals("file")) {
                img_path = imgUri.getPath();
            } else {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = ((Activity) context).managedQuery(imgUri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                actualimagecursor.moveToFirst();
//                img_path = actualimagecursor.getString(actual_image_column_index);
                if (actualimagecursor.moveToFirst()) {
                    img_path = actualimagecursor.getString(actual_image_column_index);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return img_path;
    }
}
