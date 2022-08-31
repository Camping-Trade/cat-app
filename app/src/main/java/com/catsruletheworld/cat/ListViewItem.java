package com.catsruletheworld.cat;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable imgDrawable;
    private String titleStr;
    private String detailStr;

    public void setImgDrawable(Drawable image) {
        imgDrawable = image;
    }
    public void setTitleStr(String title) {
        titleStr = title;
    }
    public void setDetailStr(String detail) {
        detailStr = detail;
    }

    public Drawable getImgDrawable() {
        return this.imgDrawable;
    }
    public String getTitleStr() {
        return this.titleStr;
    }
    public String getDetailStr() {
        return this.detailStr;
    }
}
