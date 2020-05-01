package com.example.hospitalfinder.blogpostpojo;

public class Author
{
    private Image image;

    private String displayName;

    private String id;

    private String url;

    public Image getImage ()
    {
        return image;
    }

    public void setImage (Image image)
    {
        this.image = image;
    }

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", displayName = "+displayName+", id = "+id+", url = "+url+"]";
    }
}

