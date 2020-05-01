package com.example.hospitalfinder.blogpostpojo;

public class Replies {
    private String totalItems;

    private String selfLink;

    public String getTotalItems ()
    {
        return totalItems;
    }

    public void setTotalItems (String totalItems)
    {
        this.totalItems = totalItems;
    }

    public String getSelfLink ()
    {
        return selfLink;
    }

    public void setSelfLink (String selfLink)
    {
        this.selfLink = selfLink;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [totalItems = "+totalItems+", selfLink = "+selfLink+"]";
    }
}
