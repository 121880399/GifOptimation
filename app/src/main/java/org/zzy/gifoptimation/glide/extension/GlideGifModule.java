package org.zzy.gifoptimation.glide.extension;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import android.support.rastermill.FrameSequenceDrawable;
import org.zzy.gifoptimation.glide.decoder.GifDecoder;

import java.io.InputStream;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2021/4/19 10:57
 * 描    述：
 * 修订历史：
 * ================================================
 */
@GlideModule
public class GlideGifModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.append(Registry.BUCKET_GIF, InputStream.class, FrameSequenceDrawable.class,new GifDecoder(glide.getBitmapPool()));
    }
}
