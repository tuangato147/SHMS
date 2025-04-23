package com.example.shms.ui.adapters;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import com.example.shms.R;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.databinding.ItemAppointmentBinding;

public class AppointmentAdapter extends BaseAdapter<Appointment> {
    private OnAppointmentClickListener listener;

    public AppointmentAdapter(OnAppointmentClickListener listener) {
        super(new AppointmentDiffCallback());
        this.listener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_appointment;
    }

    @Override
    protected void bind(ViewDataBinding binding, Appointment item) {
        ItemAppointmentBinding b = (ItemAppointmentBinding) binding;
        b.setAppointment(item);
        b.setListener(listener);
    }

    public interface OnAppointmentClickListener {
        void onAppointmentClick(Appointment appointment);
        void onApproveClick(Appointment appointment);
        void onCancelClick(Appointment appointment);
    }

    static class AppointmentDiffCallback extends DiffUtil.ItemCallback<Appointment> {
        @Override
        public boolean areItemsTheSame(@NonNull Appointment oldItem, @NonNull Appointment newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Appointment oldItem, @NonNull Appointment newItem) {
            return oldItem.equals(newItem);
        }
    }
}