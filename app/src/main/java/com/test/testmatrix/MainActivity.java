package com.test.testmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doPress();
    }

    public void doPress(){
        Resources res=getResources();
        Bitmap bitmap=BitmapFactory.decodeResource(res, R.drawable.ic_launcher);


        // 1
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] bytes = baos.toByteArray();
        /*
            bitmap.compress(CompressFormat.JPEG, 10, baos)只是改变位深及透明度，没有改变长、宽和1个像素所占字节，
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length)也没有改变长宽像素，故bitmap占用内存不变，
            但bytes本身变小了，适合存储和传输！
        */
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // 2
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*
            ALPHA_8表示只有透明度A=8,没有颜色RGB, 1个像素占8位=1字节。
            ARGB_4444表示A=4,R=4,G=4,B=4, 1个像素占4+4+4+4=16位=2字节，ARGB_4444的画质惨不忍睹，所以弃用。
            ARGB_8888表示A=8,R=8,G=8,B=8, 1个像素占8+8+8+8=32位=4字节 。
            RGB_565表示没有透明度A，R=5,G=6,B=5, 1个像素占5+6+5=16位=2字节。
            如果没有透明度A需求，将ARGB_8888改为RGB_565可以降低1个像素所占字节，Bitmap占用内存也就降低
        */
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // 设置取样大小,假设inSampleSize为2，则长和宽都变为原来1/2，Bitmap占用内存也就降低
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeFile("",options);

        // 3
        // 使用Matrix.setScale(···)让新Bitmap长宽缩小，新Bitmap占用内存也就降低
        bitmap = Bitmap.createScaledBitmap(bitmap, 160, 160, true); //内部调用了Bitmap.createBitmap(···matrix···)
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);




    }
}