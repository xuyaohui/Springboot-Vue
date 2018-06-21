package com.teradata.model.lucene;

/**
 * @author xuyaohui
 * 2018-3-23
 * 文件定义的DTO，用于搜索文件
 */

public class DocumentFileDTO {

    private int id;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
