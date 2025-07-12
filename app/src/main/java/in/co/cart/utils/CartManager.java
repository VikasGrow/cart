package in.co.cart.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.cart.model.FoodItem;

public class CartManager {

    private static CartManager instance;
    private final List<FoodItem> cartItems = new ArrayList<>();
    private OnCartChangeListener listener;

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public interface OnCartChangeListener {
        void onCartChanged(List<FoodItem> updatedCart);
    }

    public void setOnCartChangeListener(OnCartChangeListener listener) {
        this.listener = listener;
    }

    public void addToCart(FoodItem item) {
        boolean exists = false;
        for (FoodItem i : cartItems) {
            if (i.getItemId().equals(item.getItemId())) {
                i.setQuantity(i.getQuantity() + 1);
                exists = true;
                break;
            }
        }
        if (!exists) {
            item.setQuantity(1);
            cartItems.add(item);
        }
        notifyCartChanged();
    }

    public void updateItem(FoodItem item) {
        for (int i = 0; i < cartItems.size(); i++) {
            FoodItem existing = cartItems.get(i);
            if (existing.getItemId().equals(item.getItemId())) {
                existing.setQuantity(item.getQuantity());
                existing.setSelectedAddOnItem(item.getSelectedAddOnItem());
                break;
            }
        }

        // Remove if quantity is now 0
        cartItems.removeIf(f -> f.getQuantity() <= 0);

        notifyCartChanged();
    }


    public void removeFromCart(FoodItem item) {
        Iterator<FoodItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            FoodItem i = iterator.next();
            if (i.getItemId().equals(item.getItemId())) {
                i.setQuantity(i.getQuantity() - 1);
                if (i.getQuantity() <= 0) {
                    iterator.remove();
                }
                break;
            }
        }
        notifyCartChanged();
    }

    public List<FoodItem> getCartItems() {
        return cartItems;
    }

    public void notifyCartChanged() {
        if (listener != null) {
            listener.onCartChanged(cartItems);
        }
    }
}