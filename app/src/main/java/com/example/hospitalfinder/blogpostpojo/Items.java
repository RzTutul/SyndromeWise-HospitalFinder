package com.example.hospitalfinder.blogpostpojo;

public class Items
{
    private Replies replies;

    private String kind;

    private Author author;

    private String etag;

    private String id;

    private String published;

    private Blog blog;

    private String title;

    private String updated;

    private String url;

    private String content;

    private String selfLink;

    public Replies getReplies ()
    {
        return replies;
    }

    public void setReplies (Replies replies)
    {
        this.replies = replies;
    }

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    public Author getAuthor ()
    {
        return author;
    }

    public void setAuthor (Author author)
    {
        this.author = author;
    }

    public String getEtag ()
    {
        return etag;
    }

    public void setEtag (String etag)
    {
        this.etag = etag;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPublished ()
    {
        return published;
    }

    public void setPublished (String published)
    {
        this.published = published;
    }

    public Blog getBlog ()
    {
        return blog;
    }

    public void setBlog (Blog blog)
    {
        this.blog = blog;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUpdated ()
    {
        return updated;
    }

    public void setUpdated (String updated)
    {
        this.updated = updated;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
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
        return "ClassPojo [replies = "+replies+", kind = "+kind+", author = "+author+", etag = "+etag+", id = "+id+", published = "+published+", blog = "+blog+", title = "+title+", updated = "+updated+", url = "+url+", content = "+content+", selfLink = "+selfLink+"]";
    }
}

