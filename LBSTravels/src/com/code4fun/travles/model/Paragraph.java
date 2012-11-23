package com.code4fun.travles.model;

/**
 * 段落
 * @author pengfan
 *
 */
public class Paragraph {

    private String imgSrc;
    private String content;

    public Paragraph(String imgSrc, String content) {
        super();
        this.imgSrc = imgSrc;
        this.content = content;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
