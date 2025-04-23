package com.example.shms.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T> extends ListAdapter<T, BaseAdapter.BaseViewHolder> {
    protected BaseAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, getLayoutId(), parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        bind(holder.binding, getItem(position));
        holder.binding.executePendingBindings();
    }

    protected abstract int getLayoutId();
    protected abstract void bind(ViewDataBinding binding, T item);

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}