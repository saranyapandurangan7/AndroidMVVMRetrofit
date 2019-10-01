package com.saranya.androidmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserContentResponse  {

    @SerializedName("title")
    private String title ;

    @SerializedName("rows")
    private List<Contents> ContentList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Contents> getContentList() {
        return ContentList;
    }

    public void setContentList(List<Contents> contentList) {
        ContentList = contentList;
    }

    private class Contents {
        @SerializedName("title")
        private String title ;

        @SerializedName("description")
        private String description ;

        @SerializedName("imageHref")
        private String imageHref ;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageHref() {
            return imageHref;
        }

        public void setImageHref(String imageHref) {
            this.imageHref = imageHref;
        }
    }
}
