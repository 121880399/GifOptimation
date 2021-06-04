package org.zzy.gifoptimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.rastermill.FrameSequenceDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.zzy.gifoptimation.glide.extension.GlideApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://5b0988e595225.cdn.sohucs.com/images/20190420/1d1070881fd540db817b2a3bdd967f37.gif";
        mImageView = findViewById(R.id.sample_img);
        mButton = findViewById(R.id.btn_next);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        //CPU:3% MEMORY:75.9
        GlideApp.with(this).asGif2().load(url).listener(new RequestListener<FrameSequenceDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<FrameSequenceDrawable> target,
                                        boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(FrameSequenceDrawable resource, Object model, Target<FrameSequenceDrawable> target,
                                           DataSource dataSource, boolean isFirstResource) {
                Log.d("MainActivity","current Thread:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                String gifOptimation = getImageCacheDir(MainActivity.this, "gifOptimation");
                String encrypt = encrypt(url);
                byte[] rawData = resource.getRawData();
                String path = gifOptimation + File.separator + encrypt+ "_" + System.currentTimeMillis() + ".gif";
                writeFile(path,rawData);
                return false;
            }
        }).into(mImageView);
        //CPU:18% MEMORY:140 - 175
//        Glide.with(this).asGif().load(R.mipmap.test).into(mImageView);
    }

    /**
     * 把数据流写入文件
     * @param path
     * @param bytes
     */
    public static void writeFile(String path, byte[] bytes) {
        try {
            FileOutputStream out = new FileOutputStream(path);//指定写到哪个路径中
            FileChannel fileChannel = out.getChannel();
            fileChannel.write(ByteBuffer.wrap(bytes)); //将字节流写入文件中
            fileChannel.force(true);//强制刷新
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(@NonNull String str) {
        String result = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            Log.e("MD5", "encrypt", var3);
        }

        return result;
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        byte[] var2 = bByte;
        int var3 = bByte.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            sBuffer.append(byteToArrayString(b));
        }

        return sBuffer.toString();
    }

    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    public static String getImageCacheDir(Context context, String dirName){
        File cacheDirectory = context.getApplicationContext().getExternalCacheDir();
        if (cacheDirectory == null) {
            cacheDirectory = context.getApplicationContext().getCacheDir();
        }
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }
        File result = new File(cacheDirectory,dirName);
        if(!result.exists()) {
            if (!result.mkdirs()) {
                // File wasn't able to create a directory, or the result exists but not a directory
                return null;
            }
        }
        return result.getAbsolutePath();
    }


}