package nomeiasdg.com.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Created by Administrator on 2016-06-07.
 */
public class ImageManager {


    private ImageManager() {
    }

    private static class ImageManagerSingle {
        private static ImageManager imageManager = new ImageManager();
    }

    public static ImageManager instance() {
        return ImageManagerSingle.imageManager;
    }

    /**
     * 图片加载
     * @param context
     * @param simpleDraweeView
     * @param uri
     * @param resId 默认图片 包括 展位图 失败图  重新加载图
     */
    public void disPlayImage(Context context,
                             SimpleDraweeView simpleDraweeView, Uri uri, int resId) {
        disPlayImage(context, simpleDraweeView, uri,resId,resId,resId, null);
    }

    /**
     * 图片加载
     * @param context
     * @param simpleDraweeView
     * @param uri
     */
    public void disPlayImage(Context context,
                             final SimpleDraweeView simpleDraweeView, final Uri uri) {
        disPlayImage(context, simpleDraweeView, uri,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, null);
    }

    /**
     * 图片加载
     * @param context
     * @param simpleDraweeView
     * @param url
     */
    public void disPlayImage(Context context,
                             final SimpleDraweeView simpleDraweeView, final String url) {
        disPlayImage(context, simpleDraweeView, url, null);
    }

    /**
     * 图片加载
     * @param context
     * @param simpleDraweeView
     * @param url
     * @param listener
     */
    public void disPlayImage(Context context,
                             final SimpleDraweeView simpleDraweeView, final String url,
                             OnImageLoaderListener listener) {
        disPlayImage(context, simpleDraweeView, url,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, listener);
    }

    /**
     * @description 加载图片
     * @author wujian
     * @date 2016-08-18 12:26
     **/
    public void disPlayImage(Context context,
                             final SimpleDraweeView simpleDraweeView, final String url, int resId) {
        disPlayImage(context, simpleDraweeView, url, resId, null);
    }

    /**
     * @description 加载图片
     * @author wujian
     * @date 2016-08-18 12:26
     **/
    public void disPlayImage(Context context,
                             final SimpleDraweeView simpleDraweeView, final String url,
                             int resId, OnImageLoaderListener listener) {
        disPlayImage(context, simpleDraweeView, url, resId, resId,resId, listener);
    }

    /**
     * @description 加载图片
     * @author wujian
     * @date 2016-08-18 12:26
     **/
    public void disPlayImage(Context context,
                             final SimpleDraweeView simpleDraweeView, String url,
                             int placeholderResId, int failureResId, int retyResId,
                             final OnImageLoaderListener listener) {
        if(TextUtils.isEmpty(url) || !(url.startsWith("http:") || url.startsWith("file:"))){
            simpleDraweeView.setImageResource(placeholderResId);
            return;
        }
        disPlayImage(context, simpleDraweeView,
                Uri.parse(url), placeholderResId,
                failureResId,retyResId, listener);
    }

    /**
     * @description 加载图片
     * @author wujian
     * @date 2016-08-18 12:26
     **/
    public void disPlayImage(final Context context,
                             final SimpleDraweeView simpleDraweeView, final Uri uri,
                             final int placeholderResId, final  int failureResId, final int retyResId,
                             final OnImageLoaderListener listener) {
        try {
            Uri uriTag = (Uri) simpleDraweeView.getTag(R.id.tag_url);
            if (uriTag != null && uri != null && TextUtils.equals(uri.toString(), uriTag.toString())) {
                return;
            }
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            if (hierarchy == null) {
                hierarchy = new GenericDraweeHierarchyBuilder(
                        context.getResources()).build();
                simpleDraweeView.setHierarchy(hierarchy);
            }
            hierarchy.setFadeDuration(300);
            hierarchy.setPlaceholderImage(placeholderResId);
            hierarchy.setRetryImage(retyResId);
            // hierarchy.setPlaceholderImageScaleType(ScaleType.FIT_CENTER);
            // hierarchy.setActualImageScaleType(ScaleType.CENTER_CROP);
            hierarchy.setFailureImage(failureResId);
            // hierarchy.setProgressBarImage(new ProgressBarDrawable());

            PipelineDraweeControllerBuilder builder = Fresco
                    .newDraweeControllerBuilder();
            builder.setOldController(simpleDraweeView.getController());
            builder.setTapToRetryEnabled(true);

            ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                    .newBuilderWithSource(uri);
            imageRequestBuilder.setProgressiveRenderingEnabled(true);

            if (listener != null) {
                Postprocessor postprocessor = new BasePostprocessor() {
                    @Override
                    public String getName() {
                        return "redMeshPostprocessor";
                    }

                    @Override
                    public void process(Bitmap bitmap) {
                        listener.complete(simpleDraweeView, bitmap,
                                uri.toString());
                    }
                };
                imageRequestBuilder.setPostprocessor(postprocessor);
            }

            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {

                @Override
                public void onFinalImageSet(String id,
                                            @Nullable ImageInfo imageInfo,
                                            @Nullable Animatable anim) {
                    if (imageInfo == null) {
                        return;
                    }
                    simpleDraweeView.setTag(R.id.tag_url, uri);
                    if (listener != null) {
                        listener.onFinalImageSet(id, imageInfo, anim);
                    }
                }

                @Override
                public void onIntermediateImageSet(String id,
                                                   @Nullable ImageInfo imageInfo) {
                    // FLog.d("Intermediate image received");
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    FLog.e(getClass(), throwable, "Error loading %s", id);
                }
            };
            builder.setControllerListener(controllerListener);
            builder.setImageRequest(imageRequestBuilder.build());
            simpleDraweeView.setController(builder.build());

            // mSimpleDraweeView.setHierarchy(hierarchy);
        } catch (Exception e) {
        }
    }

    /**
     * 图片加载监听器
     *
     */
    public interface OnImageLoaderListener {
        /**
         * Image加载完成
         *
         * @param imageView
         *            显示该image的ImageView的控件
         * @param bitmap
         *            加载完成的image
         * @param url
         *            image的加载地址
         */
        public void complete(ImageView imageView, Bitmap bitmap, String url);

        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo,
                                    @Nullable Animatable anim);

    }


}
