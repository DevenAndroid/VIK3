package com.vik3.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vik3.R;
import com.vik3.databinding.ItemHomeAlbumBinding;
import com.vik3.ui.models.ModelCollections;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterHomeAlbumList extends RecyclerView.Adapter<AdapterHomeAlbumList.ViewHolder> {

    private final List<ModelCollections> mList;
    private final Context context;
    private ItemClickListener mClickListener;

    public AdapterHomeAlbumList(Context context, List<ModelCollections> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHomeAlbumBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, int position) {
        setData(viewHolder, position);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setData(@NotNull ViewHolder viewHolder, int position) {
        ModelCollections model = mList.get(position);
        viewHolder.binding.textView.setText(model.getPost_title());
        viewHolder.binding.textViewPostedBy.setText(context.getResources().getString(R.string.posted_by)+model.getAuthor().getDisplay_name());
        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date newDate= null;
        try {
            newDate = spf.parse(model.getPost_date().split(" ")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String date = spf.format(newDate);
        viewHolder.binding.textViewPostedOn.setText(context.getResources().getString(R.string.posted_on)+" "+date);
        Glide.with(context)
                .load(model.getPost_content().split("src=\"")[1].split("\"")[0])
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.binding.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemHomeAlbumBinding binding;
        ViewHolder(ItemHomeAlbumBinding binding) {
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