package in.co.cart.view.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.co.cart.databinding.CouponItemBinding;
import in.co.cart.model.CouponItem;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    private List<CouponItem> couponList;

    public CouponAdapter(List<CouponItem> couponList) {
        this.couponList = couponList;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CouponItemBinding binding = CouponItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CouponViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        CouponItem item = couponList.get(position);
        holder.binding.couponTitle.setText(item.title);
        holder.binding.couponCode.setText("Use " + item.code);
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    static class CouponViewHolder extends RecyclerView.ViewHolder {
        CouponItemBinding binding;

        public CouponViewHolder(@NonNull CouponItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
