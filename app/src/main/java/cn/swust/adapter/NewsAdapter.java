package cn.swust.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.swust.R;
import cn.swust.databinding.RecycleviewNewsItemBinding;
import cn.swust.impl.NewsItemClickListner;
import cn.swust.pojo.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> news;
    private Context context;
    private NewsItemClickListner clickListner;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public NewsAdapter(Context context,List<News> news) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news1 = news.get(position);
        holder.binding.tvNewsTitle.setText(news1.getTitle());
        holder.binding.tvNewsDescrip.setText(news1.getSource());
        holder.itemView.setOnClickListener(v -> clickListner.lookNews(news1.getUrl()));
        holder.binding.tvNewsCtime.setText(news1.getCtime());
        Glide.with(context)
                .load(news1.getPicUrl())
                .placeholder(R.drawable.news_cover)
                .into(holder.binding.tvNewsCover);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void addNews(List<News> news) {
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    public void setClickListner(NewsItemClickListner clickListner) {
        this.clickListner = clickListner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecycleviewNewsItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RecycleviewNewsItemBinding.bind(itemView);
        }
    }
}
