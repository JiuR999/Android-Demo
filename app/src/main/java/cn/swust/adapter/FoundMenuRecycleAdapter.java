package cn.swust.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.swust.R;
import cn.swust.pojo.FoundMenu;

public class FoundMenuRecycleAdapter extends RecyclerView.Adapter<FoundMenuRecycleAdapter.ViewHolder> {

    private Context context;

    private List<FoundMenu> menus;

    public FoundMenuRecycleAdapter(Context context, List<FoundMenu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public FoundMenuRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fount_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundMenuRecycleAdapter.ViewHolder holder, int position) {
            holder.imageView.setImageResource(menus.get(position).getImg());
            holder.textView.setText(menus.get(position).getText());
            holder.textView.setOnClickListener(v->{
                Toast.makeText(context, "你点击了"+menus.get(position).getText(), Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_icon);
            textView = itemView.findViewById(R.id.item_text);
        }
    }
}
