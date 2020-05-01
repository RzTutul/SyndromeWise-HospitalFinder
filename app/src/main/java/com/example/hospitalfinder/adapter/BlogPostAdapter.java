package com.example.hospitalfinder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hospitalfinder.R;
import com.example.hospitalfinder.blogpostpojo.Image;
import com.example.hospitalfinder.blogpostpojo.Items;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BlogPostAdapter extends RecyclerView.Adapter<BlogPostAdapter.PostViewHolder> {

    private Context context;
    private List<Items> itemsList;

    public BlogPostAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.blog_post_row,parent,false);


        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Items items = itemsList.get(position);

       //Parse HTML to textContent
        Document document = Jsoup.parse(items.getContent());

        holder.postTitle.setText(items.getTitle());
        holder.postText.setText(document.text());

        Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for (Element image : images) {

            System.out.println("\nsrc : " + image.attr("src"));
            System.out.println("height : " + image.attr("height"));
            System.out.println("width : " + image.attr("width"));
            System.out.println("alt : " + image.attr("alt"));
            Glide.with(context).load(image.attr("src")).into(holder.post_image);


        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        ImageView post_image;
        TextView postText,postTitle;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image = itemView.findViewById(R.id.postImage);
            postText = itemView.findViewById(R.id.postText);
            postTitle = itemView.findViewById(R.id.postTitle);

        }
    }
}
