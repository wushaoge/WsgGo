package com.wsg.go.entity;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-05-06 09:58
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CategoryData extends BaseData {


    /**
     * id : 14
     * name : 广告
     * alias : null
     * description : 为广告人的精彩创意点赞
     * bgPicture : http://img.kaiyanapp.com/57472e13fd2b6c9655c8a600597daf4d.png?imageMogr2/quality/60/format/jpg
     * bgColor :
     * headerImage : http://img.kaiyanapp.com/fc228d16638214b9803f46aabb4f75e0.png
     * defaultAuthorId : 2162
     * tagId : 16
     */

    private int id;
    private String name;
    private Object alias;
    private String description;
    private String bgPicture;
    private String bgColor;
    private String headerImage;
    private int defaultAuthorId;
    private int tagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAlias() {
        return alias;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBgPicture() {
        return bgPicture;
    }

    public void setBgPicture(String bgPicture) {
        this.bgPicture = bgPicture;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public int getDefaultAuthorId() {
        return defaultAuthorId;
    }

    public void setDefaultAuthorId(int defaultAuthorId) {
        this.defaultAuthorId = defaultAuthorId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
