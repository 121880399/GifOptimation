package org.zzy.gifoptimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;


import org.zzy.gifoptimation.glide.extension.GlideApp;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://5b0988e595225.cdn.sohucs.com/images/20180213/3bf167ff341a4df1af4f6746f2e936ee.gif";
        mImageView = findViewById(R.id.sample_img);
        //CPU:3% MEMORY:75.9
        GlideApp.with(this).asGif2().load(url).into(mImageView);
        //CPU:18% MEMORY:140 - 175
//        Glide.with(this).asGif().load(R.mipmap.test).into(mImageView);
    }


}