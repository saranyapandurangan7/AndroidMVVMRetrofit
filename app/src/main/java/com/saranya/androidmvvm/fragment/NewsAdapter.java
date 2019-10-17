package com.saranya.androidmvvm.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.saranya.androidmvvm.R;
import com.saranya.androidmvvm.model.UserContentResponse;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final List<UserContentResponse.Contents> news;
    private final Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_content_item, parent, false);
        return new ViewHolder(view);
    }
    public NewsAdapter(List<UserContentResponse.Contents> list, Context context) {
        this.news = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        final UserContentResponse.Contents model = news.get(position);
        holder.headlines.setText(news.get(position).getTitle());
        holder.description.setText(news.get(position).getDescription());
        Glide.with(context)
                .load(model.getImageHref())
                .apply(new RequestOptions()
                        /*.placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)*/
                        .apply(new RequestOptions().override(200, 200))
                        .centerCrop()
                        .fitCenter()
                        .centerCrop())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final TextView headlines;
        final ImageView imageView;
        final TextView description;
        private ViewHolder(View v) {
            super(v);
            headlines = (itemView.findViewById(R.id.headline));
            description = (itemView.findViewById(R.id.description));
            imageView = (itemView.findViewById(R.id.image));
        }
    }
}
