package com.simp.myapplicationforopencv.list;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.math.BigInteger;

import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_64F;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.makeType;

public class ImageProcessUtils {
    public static Bitmap getROIArea(Bitmap bitmap){
        Rect roi = new Rect(200,150,200,300);
        Bitmap roimap = Bitmap.createBitmap(roi.width,roi.height,Bitmap.Config.ARGB_8888);
        Mat src = new Mat() ;
        Utils.bitmapToMat(bitmap,src);
        Mat roimat = src.submat(roi);
        Mat roidstmat = new Mat();
        Imgproc.cvtColor(roimat,roidstmat,Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(roidstmat,roimap);
        src.release();
        roidstmat.release();
        roimat.release();
        return roimap;

    }
    public static  Bitmap demoMatUsage(){
        Bitmap bitmap = Bitmap.createBitmap(400,800,Bitmap.Config.ARGB_8888);
        Mat dst =  new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_8UC3,new Scalar(255,255,0));
                // Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC4,Scalar.all(0));


        Utils.matToBitmap(dst,bitmap);
         dst.release();

         return bitmap;
    }
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
        long startTime = System.currentTimeMillis();
        Core.bitwise_not(src,src);
        long endTime = System.currentTimeMillis()-startTime;
        Log.i("TIME","\t"+endTime);
        Log.i("cvType","\t"+src.type());
        Utils.matToBitmap(src,bitmap);
        src.release();
        return bitmap;
    }
    public static Bitmap subStarct(Bitmap bitmap) {
        Mat src = new Mat();

        Utils.bitmapToMat(bitmap,src);
        Mat whiteImg = new Mat(src.size(),src.type(),Scalar.all(255));

        long startTime = System.currentTimeMillis();
        Core.subtract(whiteImg,src,src);
        long endTime = System.currentTimeMillis()-startTime;
        Log.i("TIME","\t"+endTime);
        Log.i("cvType","\t"+src.type());
        Utils.matToBitmap(src,bitmap);
        src.release();
        whiteImg.release();
        return bitmap;
    }
    public static void add(Bitmap bitmap) {
        Mat src = new Mat();
        //Mat dst = new Mat();
        Utils.bitmapToMat(bitmap,src);

        Mat whiteImg = new Mat(src.size(),src.type(),Scalar.all(255));

        long startTime = System.currentTimeMillis();
        Core.addWeighted(whiteImg,0.5,src,0.5,0.0,src);
        long endTime = System.currentTimeMillis()-startTime;
        Log.i("TIME","\t"+endTime);
        Log.i("cvType","\t"+src.type());
        Utils.matToBitmap(src,bitmap);
        src.release();
        whiteImg.release();
       // return bitmap;
    }
    public static void adjustConstrast(Bitmap bitmap) {
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap,src);
        src.convertTo(src,CvType.CV_32F);

        Mat whiteImg = new Mat(src.size(),src.type(),Scalar.all(1.25));
        Mat bwImg = new Mat(src.size(),src.type(),Scalar.all(30));
        long startTime = System.currentTimeMillis();
        Core.multiply(whiteImg,src,src);
        Core.add(bwImg,src,src);

        long endTime = System.currentTimeMillis()-startTime;
        Log.i("TIME","\t"+endTime);
        Log.i("cvType","\t"+src.type());
        src.convertTo(src,CvType.CV_8U);
        Utils.matToBitmap(src,bitmap);
        src.release();
        whiteImg.release();
        bwImg.release();
        // return bitmap;
    }
    public static Bitmap localInvert(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int [] pixels = new int[width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        int index = 0;
        int a= 0,r = 0,g = 0,b = 0;
        long startTime = System.currentTimeMillis();
        for(int row = 0; row < height; row++){
            index = row*width;
            for(int col = 0;col<width;col++){
                int pixel = pixels[index];
                a = (pixel>>24)&0xff;
                r =(pixel>>16)&0xff;
                g = (pixel>>8)&0xff;
                b = pixel&0xff;
                r = 255-r;
                g = 255-g;
                b = 255-b;
                pixel = ((a&0xff)<<24)|((r&0xff)<<16)|((g&0xff)<<8)|(b&0xff);
                pixels[index] = pixel;
                index++;
            }
        }
        long endTime = System.currentTimeMillis()-startTime;
        Log.i("TIME","\t"+endTime);
        bitmap.setPixels(pixels,0,width,0,0,width,height);
        return bitmap;
    }
    public static Bitmap meanBlur(Bitmap bitmap) {
        Mat src = new Mat();
        Mat dst = new Mat();
        Utils.bitmapToMat(bitmap,src);
//        调节Size()来实现模糊的程度，width是X方向的上的模糊，hight是Y方向上的模糊
        Imgproc.blur(src,dst,new Size(25,25),new Point(-1,-1),Imgproc.BORDER_DEFAULT);
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
        return bitmap;
    }
    public static void gaussianBlur(Bitmap bitmap ){
    Mat src = new Mat();
    Mat dst = new Mat();
    Utils.bitmapToMat(bitmap,src);
    Imgproc.GaussianBlur(src,dst,new Size(0,0),25,25,4);
    //以防万一每一个参数都输入一个值，现在是输入 Size()后就会自动算出sigmaX，sigmaY现在的api就是这种情况
    Utils.matToBitmap(dst,bitmap);
    src.release();
    dst.release();
    }
    public static void biBlur(Bitmap bitmap ){
        Mat src = new Mat();
        Mat dst = new Mat();
        Utils.bitmapToMat(bitmap,src);
        Imgproc.bilateralFilter(src,dst,15,150,15,Imgproc.BORDER_DEFAULT);
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
    }
}
