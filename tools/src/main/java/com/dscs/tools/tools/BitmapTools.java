package com.dscs.tools.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */

public class BitmapTools {
    /**
     * 保存Bitmap至本地
     *
     * @param bmp
     */
    public String saveBitmapToFile(Context context, Bitmap bmp,String file_path) {
        File file = new File(file_path);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_path;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param file_path 图片绝对路径
     * @return degree 旋转角度
     */
    public int readPictureDegree(String file_path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(file_path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
