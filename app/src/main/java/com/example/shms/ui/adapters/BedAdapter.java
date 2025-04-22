package com.example.shms.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shms.data.model.Bed;
import com.example.shms.databinding.ItemBedBinding;
import com.example.shms.ui.viewmodel.DatPhongViewModel;

import java.util.List;

public class BedAdapter extends RecyclerView.Adapter<BedAdapter.BedViewHolder> {
    private List<Bed> beds;
    private final DatPhongViewModel viewModel;

    public BedAdapter(DatPhongViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBedBinding binding = ItemBedBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new BedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BedViewHolder holder, int position) {
        Bed bed = beds.get(position);
        holder.bind(viewModel, bed);
    }

    @Override
    public int getItemCount() {
        return beds != null ? beds.size() : 0;
    }

    static class BedViewHolder extends RecyclerView.ViewHolder {
        private final ItemBedBinding binding;

        BedViewHolder(ItemBedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DatPhongViewModel viewModel, Bed bed) {
            binding.setBed(bed);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}