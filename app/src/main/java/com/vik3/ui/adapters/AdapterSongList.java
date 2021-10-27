package com.vik3.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vik3.R;
import com.vik3.databinding.ItemSongListBinding;
import com.vik3.ui.models.History;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterSongList extends RecyclerView.Adapter<AdapterSongList.ViewHolder> {

    private final List<History> mList;
    private final Context context;
    private ItemClickListener mClickListener;

    public AdapterSongList(Context context, List<History> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSongListBinding view = ItemSongListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, int position) {
        History model = mList.get(position);

        viewHolder.binding.textView.setText(model.getTitle().split("- ")[1]);
        viewHolder.binding.textViewSinger.setText(model.getTitle().split("- ")[0]);
        Glide.with(context)
                .load(model.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(viewHolder.binding.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemSongListBinding binding;

        ViewHolder(ItemSongListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}