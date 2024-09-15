package cn.swust.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.swust.R;
import cn.swust.databinding.MusicItemBinding;
import cn.swust.impl.MusicClickListener;
import cn.swust.pojo.Music;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private Context context;

    private List<Music> musics;

    private MusicClickListener clickListener;

    public MusicAdapter(Context context, List<Music> musics) {
        this.context = context;
        this.musics = musics;
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context, R.layout.music_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {

        Music music = musics.get(position);
        holder.binding.musicNum.setText(position + 1 + ".");
        holder.binding.tvItemSongName.setText(music.getName());
        holder.binding.imgItemCover.setImageResource(music.getCover());
//        holder.binding.slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                clickListener.change(seekBar,progress,fromUser,holder,position);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        holder.binding.imgItemPlay.setOnClickListener(v -> {
            try {
                clickListener.play(position, holder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//        holder.binding.imgStop.setOnClickListener(v->{
//            try {
//                clickListener.stop(position,holder);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public void setClickListener(MusicClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MusicItemBinding binding;

        public MusicItemBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MusicItemBinding.bind(itemView);
        }
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
        notifyDataSetChanged();
    }
}
