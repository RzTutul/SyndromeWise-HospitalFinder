package com.example.hospitalfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hospitalfinder.R;
import com.example.hospitalfinder.blogpostpojo.Items;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class TrendingBlogPostAdapter  extends RecyclerView.Adapter<TrendingBlogPostAdapter.TredingPostViewHolder> {

    private Context context;
    private List<Items> itemsList;

    public TrendingBlogPostAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public TredingPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trending_blog_post,parent,false);
        return new TredingPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TredingPostViewHolder holder, int position) {
        Items items = itemsList.get(position);
        Document document = Jsoup.parse(items.getContent());

        holder.postTitle.setText(items.getTitle());
        holder.postText.setText(document.text());
        Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for (Element image : images) {

       /*     System.out.println("\nsrc : " + image.attr("src"));
            System.out.println("height : " + image.attr("height"));
            System.out.println("width : " + image.attr("width"));
            System.out.println("alt : " + image.attr("alt"));*/
            Glide.with(context).load(image.attr("src")).into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {

        if (itemsList.size()>=10)
        {
            return 10;
        }
        else
        {
            return itemsList.size();
        }



    }

    public class TredingPostViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView postTitle,postText;
        public TredingPostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.t_postImage);
            postTitle = itemView.findViewById(R.id.t_postTitle);
            postText = itemView.findViewById(R.id.t_postText);

        }
    }
}
