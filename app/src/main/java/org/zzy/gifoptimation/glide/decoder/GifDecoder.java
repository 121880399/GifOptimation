package org.zzy.gifoptimation.glide.decoder;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import android.support.rastermill.FrameSequence;
import android.support.rastermill.FrameSequenceDrawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2021/4/19 11:02
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GifDecoder implements ResourceDecoder<InputStream, FrameSequenceDrawable> {

    private BitmapPool mBitmapPool;

    public GifDecoder(BitmapPool bitmapPool){
        this.mBitmapPool = bitmapPool;
    }

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        return true;
    }

    @Nullable
    @Override
    public Resource<FrameSequenceDrawable> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        FrameSequence frameSequence = FrameSequence.decodeStream(source);
        FrameSequenceDrawable frameSequenceDrawable = new FrameSequenceDrawable(frameSequence, new FrameSequenceDrawable.BitmapProvider() {
            @Override
            public Bitmap acquireBitmap(int minWidth, int minHeight) {
                return mBitmapPool.get(minWidth,minHeight,Bitmap.Config.ARGB_8888);
            }

            @Override
            public void releaseBitmap(Bitmap bitmap) {
                mBitmapPool.put(bitmap);
            }
        });
        return new GifResource(frameSequenceDrawable);
    }
}
