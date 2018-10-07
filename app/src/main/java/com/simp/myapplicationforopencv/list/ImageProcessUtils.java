package com.simp.myapplicationforopencv.list;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageProcessUtils {
    public static Bitmap convert2Gray(Bitmap bitmap){
        Mat src = new Mat();
        Mat dst = new Mat();
        Utils.bitmapToMat(bitmap,src);
        Imgproc.cvtColor(src,dst,Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(dst,bitmap);
        src.release();//释放内存
        dst.release();//释放内存
        return bitmap;
    }

    public static Bitmap invert(Bitmap bitmap) {
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap,src);
        //pixel operation 对整个像素取反
        int width = src.cols();
        int height = src.height();
        int cnum = src.channels();
        byte[] bgra = new byte[cnum];
        long startTime = System.currentTimeMillis();
        for (int row = 0;row < height;row++){
            for(int col = 0; col < width; col++){
                src.get(row,col,bgra);
                for(int  i = 0;i<cnum;i++){
                    bgra[i]=(byte)(255-bgra[i]&0xff);
                }
                src.put(row,col,bgra);
            }
        }
        long endTime = System.currentTimeMillis()-startTime;
        Log.i("TIME","\t"+endTime);
        Utils.matToBitmap(src,bitmap);
        src.release();
        return bitmap;
    }
}
