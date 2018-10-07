package com.simp.myapplicationforopencv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


import android.net.Uri;

import com.simp.myapplicationforopencv.list.CommandConstants;
import com.simp.myapplicationforopencv.list.ImageProcessUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;


public class processImageActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG ="CVLib";
    private String command ;
    private Bitmap selectedBitmap;
    private int REQUEST_GET_IMAGE=1;
    private int MAXSIZE=768;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_image);
        initLoadOpenCVLibs();//操作处理之前加载opencv的库否则会出错
       command= this.getIntent().getStringExtra("Command");
       Button processbtn=(Button) findViewById(R.id.processbutton);
       processbtn.setTag("PROCESS");
       processbtn.setOnClickListener(this);
       processbtn.setText(command);

        Button selectBtn = (Button) findViewById(R.id.select_img_button);
        selectBtn.setTag("SELECT");
        selectBtn.setOnClickListener(this);
        //给selectedBitmap赋初始值
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;
        selectedBitmap= BitmapFactory.decodeResource(this.getResources(),R.drawable.test,options);
    }
    private void initLoadOpenCVLibs() {
        boolean success =OpenCVLoader.initDebug();
        if (success){
            Log.i(TAG,"load library successfully");
        }
    }
    @Override
    public void onClick(View v) {
        Object obj  = v.getTag();
        if(obj instanceof String){
            if("SELECT".equals(obj.toString()))
            {
                selectImage();
                return;
            }else if("PROCESS".equals(obj.toString())){
                processCommand();
            }

        }

    }

    private void processCommand() {
        Bitmap temp=selectedBitmap.copy(selectedBitmap.getConfig(),true);
      if (CommandConstants.TEST_ENV_COMMAND.equals(command)){
          temp=ImageProcessUtils.convert2Gray(temp);
      }
      else if(CommandConstants.MAT_PIXEL_INVERT_COMMAND.equals(command)){
         temp= ImageProcessUtils.invert(temp);
      } else if(CommandConstants.BITMAP_PIXEL_INVERT_COMMAND.equals(command)){
          temp= ImageProcessUtils.localInvert(temp);
      }else if(CommandConstants.PIXEL_SUBSTRACT_COMMAND.equals(command)){
          temp= ImageProcessUtils.subStarct(temp);
      }else if(CommandConstants.PIXEL_ADD_COMMAND.equals(command)){
           ImageProcessUtils.add(temp);
      }else if(CommandConstants.ADJUST_CONSTRAST_COMMAND.equals(command)){
          ImageProcessUtils.adjustConstrast(temp);
      }else if(CommandConstants.IMAGE_CONSTAINTER_COMMAND.equals(command)){
          temp=ImageProcessUtils.demoMatUsage();
      }else if(CommandConstants.SUB_IMAGE_COMMAND.equals(command)){
          temp=ImageProcessUtils.getROIArea(temp);
      }else if(CommandConstants.BLUR_IMAGE_COMMAND.equals(command)){
          temp=ImageProcessUtils.meanBlur(temp);
      }else if(CommandConstants.GAUSSIAN_BLUR_COMMAND.equals(command)){
         ImageProcessUtils.gaussianBlur(temp);
      }else if(CommandConstants.BI_BLUR_COMMAND.equals(command)){
          ImageProcessUtils.gaussianBlur(temp);
      }

        ImageView imgView = (ImageView)this.findViewById(R.id.imageView);
        imgView.setImageBitmap(temp);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Browser Image..."),REQUEST_GET_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GET_IMAGE&&resultCode==RESULT_OK&&data!=null){
         Uri uri = data.getData();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try {
               InputStream inputStream= getContentResolver().openInputStream(uri);
               BitmapFactory.decodeStream(inputStream,null,options);
               int height = options.outHeight;
               int width  = options.outWidth;
               int samplesize =1;
               int max= Math.max(height,width);

               if(max>MAXSIZE){
                   int nw = width/2;
                   int nh = height/2;
                   while ((nw/samplesize)>MAXSIZE||(nh/samplesize)>MAXSIZE){
                       samplesize=samplesize*2;
                   }
               }
               options.inSampleSize=samplesize;
               options.inJustDecodeBounds=false;
               selectedBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,options);
               ImageView imageView = (ImageView)findViewById(R.id.imageView);
               imageView.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG , e.getMessage());
            }
        }
    }
}
