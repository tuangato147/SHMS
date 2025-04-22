package com.example.shms.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.shms.databinding.DatphongBinding;
import com.example.shms.ui.adapters.BedAdapter;
import com.example.shms.ui.viewmodel.DatPhongViewModel;

public class DatPhongActivity extends AppCompatActivity {
    private DatphongBinding binding;
    private DatPhongViewModel viewModel;
    private BedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding
        binding = DatphongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DatPhongViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Setup RecyclerView
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new BedAdapter(viewModel);
        binding.bedGridRecyclerView.setAdapter(adapter);

        // Observe bed list changes
        viewModel.getBedList().observe(this, beds -> {
            if (beds != null) {
                adapter.setBeds(beds);
            }
        });
    }
}