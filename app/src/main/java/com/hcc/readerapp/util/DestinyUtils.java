package com.hcc.readerapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * android屏幕单位转换
 * Created by Administrator on 2016/8/2 0002.
 */
public class DestinyUtils {

    @Deprecated
    public static int applyDimension(int unit, int value) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics()));
    }

    public static int applyDimension(int value){
        return applyDimension(TypedValue.COMPLEX_UNIT_DIP, value);
    }

    public static float fromDp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    public static float fromSp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, Resources.getSystem().getDisplayMetrics());
    }

    public static float toDp(float value) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(value / density);
    }

    public static float toSp(float value) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return Math.round(value / scaledDensity);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
