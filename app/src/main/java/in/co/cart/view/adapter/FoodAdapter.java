package in.co.cart.view.adapter;

import in.co.cart.BuildConfig;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import in.co.cart.R;
import in.co.cart.databinding.ItemFoodCardBinding;
import in.co.cart.model.AddOnItem;
import in.co.cart.model.FoodItem;
import in.co.cart.utils.CartManager;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> itemList;

    public FoodAdapter(List<FoodItem> itemList) {
        this.itemList = itemList != null ? itemList : new ArrayList<>();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodCardBinding binding = ItemFoodCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = itemList.get(position);

        // Basic binding
        holder.binding.itemName.setText(item.getItemName());
        holder.binding.itemPrice.setText("₹" + item.getItemRate());

        // Customizable label
        boolean hasAddons = item.isAddOnAvailable() && item.getAddOnItems() != null && !item.getAddOnItems().isEmpty();
        holder.binding.customizableLabel.setVisibility(hasAddons ? View.VISIBLE : View.GONE);

        // Veg/Non-Veg icon
        holder.binding.vegIcon.setImageResource(item.getFoodType().equalsIgnoreCase("veg") ?
                R.drawable.ic_veg : R.drawable.ic_non_veg);

        // Image
        String baseUrl = BuildConfig.FNB_BASE_URL;
        String imageUrl = item.getItemImageURL();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            if (!imageUrl.startsWith("http")) {
                // Ensure a slash in between
                if (!imageUrl.startsWith("/")) {
                    imageUrl = "/" + imageUrl;
                }
                imageUrl = baseUrl + imageUrl;
            }

            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.fastfood)
                    .error(R.drawable.pvr)
                    .into(holder.binding.itemImage);
        } else {
            holder.binding.itemImage.setImageResource(R.drawable.ic_error);
        }


        // Show Quantity or Add Button
        if (item.getQuantity() > 0) {
            holder.binding.addButton.setVisibility(View.GONE);
            holder.binding.quantitySelector.setVisibility(View.VISIBLE);
            holder.binding.quantityText.setText(String.valueOf(item.getQuantity()));
        } else {
            holder.binding.addButton.setVisibility(View.VISIBLE);
            holder.binding.quantitySelector.setVisibility(View.GONE);
        }

        // ADD-ON section logic (only show when item is added AND add-ons available)
        if (item.getQuantity() > 0 && hasAddons) {
            holder.binding.pairWithLabel.setVisibility(View.VISIBLE);
            holder.binding.addOnRecycler.setVisibility(View.VISIBLE);

            AddOnAdapter addOnAdapter = new AddOnAdapter(item, item.getAddOnItems(), () -> {
                List<AddOnItem> selected = new ArrayList<>();
                for (AddOnItem addon : item.getAddOnItems()) {
                    if (addon.getQuantity() > 0) {
                        selected.add(new AddOnItem(
                                addon.getAddonItemId(),
                                addon.getQuantity(),
                                addon.getaddonItemName(),
                                addon.getaddonItemRate()
                        ));
                    }
                }
                item.setSelectedAddOnItem(selected);
                CartManager.getInstance().updateItem(item);
            });

            holder.binding.addOnRecycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            holder.binding.addOnRecycler.setAdapter(addOnAdapter);

        } else {
            holder.binding.pairWithLabel.setVisibility(View.GONE);
            holder.binding.addOnRecycler.setVisibility(View.GONE);
        }

        // ADD button logic
        holder.binding.addButton.setOnClickListener(v -> {
            item.setQuantity(1);
            CartManager.getInstance().addToCart(item);
            notifyItemChanged(position); // refresh to show quantity + addon
        });

        // Increment
        holder.binding.incrementButton.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.binding.quantityText.setText(String.valueOf(item.getQuantity()));
            CartManager.getInstance().updateItem(item);
        });

        // Decrement
        holder.binding.decrementButton.setOnClickListener(v -> {
            int qty = item.getQuantity();
            if (qty > 1) {
                item.setQuantity(qty - 1);
                holder.binding.quantityText.setText(String.valueOf(item.getQuantity()));
                CartManager.getInstance().updateItem(item);
            } else {
                item.setQuantity(0);
                if (item.getAddOnItems() != null) {
                    for (AddOnItem addon : item.getAddOnItems()) {
                        addon.setQuantity(0);
                    }
                }
                item.setSelectedAddOnItem(new ArrayList<>());
                CartManager.getInstance().removeFromCart(item);
                notifyItemChanged(position); // refresh to hide addon
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<FoodItem> newList) {
        this.itemList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ItemFoodCardBinding binding;

        public FoodViewHolder(@NonNull ItemFoodCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}