package com.example.hospitalfinder.blogpostpojo;

import java.util.List;

public class BlogResponseBody
{
    private String kind;

    private String nextPageToken;

    private String etag;

    private List<Items> items;

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    public String getNextPageToken ()
    {
        return nextPageToken;
    }

    public void setNextPageToken (String nextPageToken)
    {
        this.nextPageToken = nextPageToken;
    }

    public String getEtag ()
    {
        return etag;
    }

    public void setEtag (String etag)
    {
        this.etag = etag;
    }

    public List<Items> getItems ()
    {
        return items;
    }

    public void setItems (List<Items> items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [kind = "+kind+", nextPageToken = "+nextPageToken+", etag = "+etag+", items = "+items+"]";
    }
}