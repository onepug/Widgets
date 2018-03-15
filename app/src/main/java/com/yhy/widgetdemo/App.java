package com.yhy.widgetdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yhy.widget.core.preview.ImgPreHelper;
import com.yhy.widgetdemo.utils.ImgUtils;
import com.yhy.widgetdemo.utils.ToastUtils;

import java.io.File;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-03-15 13:38
 * version: 1.0.0
 * desc   :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        ToastUtils.init(this);

        //图片加载器工具初始化
        ImgUtils.init(new ImgUtils.ImgLoader() {
            @Override
            public <T> void load(Context ctx, final ImageView iv, T model) {
                Glide.with(ctx)
                        .load(model)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//使用磁盘缓存
                        .skipMemoryCache(true)//跳过内存缓存
                        .animate(R.anim.anim_alpha_image_load)
                        .crossFade()//渐变切换
                        .into(iv);
//                        .into(new SimpleTarget<Bitmap>() {
//                            @Override
//                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super
//                                    Bitmap> glideAnimation) {
//                                iv.setImageBitmap(bitmap);
//                            }
//                        });
            }
        });

        ImgPreHelper.getInstance().init(this).setLoader(new ImgPreHelper.ImgLoader() {
            @Override
            public <T> void load(ImageView iv, T model, ProgressBar pbLoading) {
                Glide.with(iv.getContext()).load(model).into(iv);
            }
        }).setOnDownloadListener(new ImgPreHelper.OnDownloadListener() {
            @Override
            public void onProgress(float progress, long current, long total) {
                Log.i("ImgDownloader", "下载进度：" + (progress * 100F) + "%，总大小：" + total + " bytes, 已下载：" + current + " bytes.");
            }

            @Override
            public void onSuccess(File img, String msg) {
                ToastUtils.shortT(msg);
            }

            @Override
            public void onError(String error) {
                ToastUtils.shortT(error);
            }
        });
    }
}
