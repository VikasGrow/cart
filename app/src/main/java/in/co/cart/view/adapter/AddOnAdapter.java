package in.co.cart.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.co.cart.databinding.ItemAddonBinding;
import in.co.cart.model.AddOnItem;
import in.co.cart.model.FoodItem;
import in.co.cart.utils.CartManager;

public class AddOnAdapter extends RecyclerView.Adapter<AddOnAdapter.AddOnViewHolder> {

    private final List<AddOnItem> addOnItemList;
    private final OnAddOnChangedListener listener;
    private final FoodItem parentItem;


    public interface OnAddOnChangedListener {
        void onAddOnChanged();
    }

public AddOnAdapter(FoodItem parentItem, List<AddOnItem> addOnItemList, OnAddOnChangedListener listener) {
    this.parentItem = parentItem;
    this.addOnItemList = addOnItemList;
    this.listener = listener;
}

    @NonNull
    @Override
    public AddOnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddonBinding binding = ItemAddonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new AddOnViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddOnViewHolder holder, int position) {
        AddOnItem addOn = addOnItemList.get(position);

        holder.binding.itemName.setText(addOn.getaddonItemName());
        holder.binding.itemPrice.setText("₹" + addOn.getaddonItemRate() );


        // Quantity logic
        if (addOn.getQuantity() > 0) {
            holder.binding.addButton.setVisibility(View.GONE);
            holder.binding.quantityLayout.setVisibility(View.VISIBLE);
            holder.binding.quantityText.setText(String.valueOf(addOn.getQuantity()));
        } else {
            holder.binding.addButton.setVisibility(View.VISIBLE);
            holder.binding.quantityLayout.setVisibility(View.GONE);
        }

        holder.binding.addButton.setOnClickListener(v -> {
            addOn.setQuantity(1);
            updateParentAndCart();
            holder.binding.quantityText.setText("1");
            holder.binding.addButton.setVisibility(View.GONE);
            holder.binding.quantityLayout.setVisibility(View.VISIBLE);
        });

        holder.binding.btnIncrease.setOnClickListener(v -> {
            int qty = addOn.getQuantity() + 1;
            addOn.setQuantity(qty);
            holder.binding.quantityText.setText(String.valueOf(qty));
            listener.onAddOnChanged();
        });

        holder.binding.btnDecrease.setOnClickListener(v -> {
            int qty = addOn.getQuantity() - 1;
            if (qty <= 0) {
                addOn.setQuantity(0);
                holder.binding.quantityLayout.setVisibility(View.GONE);
                holder.binding.addButton.setVisibility(View.VISIBLE);
            } else {
                addOn.setQuantity(qty);
            }
            holder.binding.quantityText.setText(String.valueOf(addOn.getQuantity()));
            updateParentAndCart();
        });
    }

    private void updateParentAndCart() {
        List<AddOnItem> selected = new ArrayList<>();
        for (AddOnItem addon : addOnItemList) {
            if (addon.getQuantity() > 0) {
                selected.add(new AddOnItem(
                        addon.getAddonItemId(),
                        addon.getQuantity(),
                        addon.getaddonItemName(),
                        addon.getaddonItemRate()
                ));
            }
        }

        parentItem.setSelectedAddOnItem(selected);
        CartManager.getInstance().updateItem(parentItem); // Ensure CartManager has this method
        listener.onAddOnChanged();
    }

    @Override
    public int getItemCount() {
        return addOnItemList != null ? addOnItemList.size() : 0;
    }

    static class AddOnViewHolder extends RecyclerView.ViewHolder {
        ItemAddonBinding binding;

        public AddOnViewHolder(@NonNull ItemAddonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
