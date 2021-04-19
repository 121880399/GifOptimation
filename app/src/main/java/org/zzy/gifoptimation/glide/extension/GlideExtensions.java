package org.zzy.gifoptimation.glide.extension;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.RequestOptions;

import android.support.rastermill.FrameSequenceDrawable;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2021/4/19 11:07
 * 描    述：
 * 修订历史：
 * ================================================
 */
@GlideExtension
public class GlideExtensions {

    private GlideExtensions(){

    }

    final static RequestOptions DECODE_TYPE = RequestOptions.decodeTypeOf(FrameSequenceDrawable.class).lock();

    @GlideType(FrameSequenceDrawable.class)
    public static RequestBuilder<FrameSequenceDrawable> asGif2(RequestBuilder<FrameSequenceDrawable> requestBuilder){
        return requestBuilder.apply(DECODE_TYPE);
    }
}
