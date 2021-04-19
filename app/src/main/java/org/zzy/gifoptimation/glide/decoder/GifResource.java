package org.zzy.gifoptimation.glide.decoder;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.drawable.DrawableResource;

import android.support.rastermill.FrameSequenceDrawable;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2021/4/19 11:05
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GifResource extends DrawableResource<FrameSequenceDrawable> {

    public GifResource(FrameSequenceDrawable drawable) {
        super(drawable);
    }

    @NonNull
    @Override
    public Class<FrameSequenceDrawable> getResourceClass() {
        return FrameSequenceDrawable.class;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.destroy();
    }
}
