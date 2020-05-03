package com.example.hospitalfinder.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalfinder.blogpostpojo.BlogResponseBody;
import com.example.hospitalfinder.repos.BlogPostRepository;

public class BlogPostViewModel extends ViewModel {

    private BlogPostRepository blogPostRepository;
    public MutableLiveData<BlogResponseBody> blogLD = new MutableLiveData<>();
    public BlogPostViewModel() {
        blogPostRepository = new BlogPostRepository();
    }

      public MutableLiveData<BlogResponseBody> getCurrentBlogPost(String blogId,String apiKey)
    {

        return blogPostRepository.getCurrentPost(blogId,apiKey);

    }

    public MutableLiveData<BlogResponseBody> getLatestBlogPost(String blogId,String apiKey,String label)
    {

        return blogPostRepository.getLastestPost(blogId,apiKey,label);
    }
}
