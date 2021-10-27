package com.vik3.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vik3.databinding.ItemMoreBinding;
import com.vik3.ui.models.ModelContactUs;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterMoreContactList extends RecyclerView.Adapter<AdapterMoreContactList.ViewHolder> {

    private List<ModelContactUs> mList;
    private Context context;
    private ItemClickListener mClickListener;

    public AdapterMoreContactList(Context context, List<ModelContactUs> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMoreBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, int position) {
        ModelContactUs model = mList.get(position);
        viewHolder.binding.textView.setText(model.getName());
        Glide.with(context)
                .load(model.getImage())
                .into(viewHolder.binding.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMoreBinding binding;
        ViewHolder(ItemMoreBinding binding) {
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