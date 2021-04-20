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
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2021/4/19 11:02
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GifDecoder implements ResourceDecoder<InputStream, FrameSequenceDrawable> {
    private static final String TAG = GifDecoder.class.getSimpleName();

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
        source.reset();
        byte[] data = inputStreamToBytes(source);
        Log.d("GifDecoder","data count"+data.length);
        FrameSequenceDrawable frameSequenceDrawable = new FrameSequenceDrawable(data,frameSequence, new FrameSequenceDrawable.BitmapProvider() {
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

    private static byte[] inputStreamToBytes(InputStream is) {
        final int bufferSize = 16384;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bufferSize);
        try {
            int nRead;
            byte[] data = new byte[bufferSize];
            while ((nRead = is.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Error reading data from stream", e);
            }
            return null;
        }
        return buffer.toByteArray();
    }
}
