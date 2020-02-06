/**
 * 
 */
package com.zhxl.arcgisdemo.welcome.guide.bean;

import java.io.Serializable;

public class WelcomeGuideBean implements Serializable {

    private int imgId;
    private String content;

    public WelcomeGuideBean(int imgId, String content) {
        this.imgId = imgId;
        this.content = content;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
