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
import cn.swust.pojo.Chat;
import cn.swust.pojo.FoundMenu;

/**
 * @author ASUS
 */
public class ChatRecycleAdapter extends RecyclerView.Adapter<ChatRecycleAdapter.ViewHolder> {

    private Context context;

    private List<Chat> chats;

    public ChatRecycleAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false);
        ChatRecycleAdapter.ViewHolder viewHolder = new ViewHolder(view);
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.height = parent.getHeight()/10
        ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecycleAdapter.ViewHolder holder, int position) {
            Chat chat = chats.get(position);
            holder.avator.setImageResource(chat.getAvator());
            holder.name.setText(chat.getName());
            holder.time.setText(chat.getTime());
            holder.content.setText(chat.getContent());
            holder.itemView.setOnClickListener(v->{
                Toast.makeText(context, chat.getContent(), Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avator;
        private TextView name,content,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avator = itemView.findViewById(R.id.img_avator);
            name = itemView.findViewById(R.id.tv_name);
            content = itemView.findViewById(R.id.tv_content);
            time = itemView.findViewById(R.id.tv_time);
        }
    }
}
