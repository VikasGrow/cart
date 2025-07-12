package in.co.cart.view;


import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import in.co.cart.R;
import in.co.cart.databinding.ActivityMainBinding;
import in.co.cart.model.AddOnItem;
import in.co.cart.model.CouponItem;
import in.co.cart.model.FoodItem;
import in.co.cart.model.FilterItem;
import in.co.cart.model.FnbResponse;
import in.co.cart.utils.CartManager;
import in.co.cart.view.adapter.CouponAdapter;
import in.co.cart.view.adapter.FoodAdapter;

public class MainActivity extends AppCompatActivity implements CartManager.OnCartChangeListener {

    private ActivityMainBinding binding;
    private FoodAdapter foodAdapter;
    private FoodAdapter repeatAdapter;
    private FoodAdapter recommendedAdapter;

    private List<FoodItem> allItems = new ArrayList<>();
    private List<FoodItem> repeatItems = new ArrayList<>();
    private List<FoodItem> recommendedItems = new ArrayList<>();
    private List<CouponItem> couponList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        CartManager.getInstance().setOnCartChangeListener(this);


        // Load JSON data
        loadItemsFromAssets();

        // Setup all items list
        foodAdapter = new FoodAdapter(allItems);
        binding.mainItemRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.mainItemRecycler.setAdapter(foodAdapter);

        // Setup Repeat Again Section (isRepeat = true)
        repeatAdapter = new FoodAdapter(repeatItems);
        binding.repeatAgainRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.repeatAgainRecycler.setAdapter(repeatAdapter);

        couponList.add(new CouponItem("Get 50% Off up to ₹140", "PVRFOOD50"));
        couponList.add(new CouponItem("Get 20% Off up to ₹100", "PVRFOOD20"));

        CouponAdapter couponAdapter = new CouponAdapter(couponList);
        binding.couponRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.couponRecycler.setAdapter(couponAdapter);

        // Setup Recommended Section (isPopularItem = true)
        recommendedAdapter = new FoodAdapter(recommendedItems);
        binding.recommendedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recommendedRecycler.setAdapter(recommendedAdapter);

        // Setup Filter Chips
        setupFilters();

        binding.proceedBtn.setOnClickListener(v -> {
            List<FoodItem> cartItems = CartManager.getInstance().getCartItems();

            JsonArray jsonArray = new JsonArray();
            for (FoodItem item : cartItems) {
                JsonObject obj = new JsonObject();
                obj.addProperty("ItemID", item.getItemId());
                obj.addProperty("ItemName", item.getItemName());
                obj.addProperty("IsVeg", item.getFoodType().equalsIgnoreCase("veg") ? "veg" : "non-veg");
                obj.addProperty("quantity", item.getQuantity());
                obj.addProperty("price", item.getItemRate());
                obj.addProperty("foodType", item.getFoodType());
                obj.addProperty("PrimaryItemID", item.getItemId());

                // Add selected AddOnItems if available
                JsonArray addons = new JsonArray();
                if (item.getSelectedAddOnItem() != null) {
                    for (AddOnItem addOn : item.getSelectedAddOnItem()) {
                        JsonObject addonObj = new JsonObject();
                        addonObj.addProperty("itemId", item.getItemId());
                        addonObj.addProperty("addonItemId", addOn.getAddonItemId());
                        addonObj.addProperty("name", addOn.getaddonItemName());
                        addonObj.addProperty("price", addOn.getaddonItemRate());
                        addonObj.addProperty("quantity", addOn.getQuantity());
                        addons.add(addonObj);
                    }
                }

                obj.add("AddOnItem", addons);
                jsonArray.add(obj);
            }

            // Convert to pretty JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(jsonArray);

            // Show in AlertDialog
            new AlertDialog.Builder(this)  // or use `YourActivity.this` in Activity
                    .setTitle("Cart JSON")
                    .setMessage(prettyJson)
                    .setPositiveButton("Proceed", (dialog, which) -> {
                        // Continue checkout logic
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


    }

    private void loadItemsFromAssets() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("fnb.json");
            Gson gson = new Gson();

            FnbResponse fnbResponse = gson.fromJson(new InputStreamReader(inputStream), new TypeToken<FnbResponse>() {}.getType());

            if (fnbResponse != null) {
                // Load F&B items
                if (fnbResponse.getListOfFnbItems() != null) {
                    allItems.clear();
                    repeatItems.clear();
                    recommendedItems.clear();

                    for (FoodItem item : fnbResponse.getListOfFnbItems()) {
                        allItems.add(item);

                        if (item.isRepeat()) {
                            repeatItems.add(item);
                        }

                        if (item.isPopularItem()) {
                            recommendedItems.add(item);
                        }
                    }

                    Log.d("MainActivity", "Total: " + allItems.size() + ", Repeat: " + repeatItems.size() + ", Recommended: " + recommendedItems.size());
                }

                // Set Theater Name to TextView
                if (fnbResponse.getCinemaDetails() != null && !fnbResponse.getCinemaDetails().isEmpty()) {
                    String theaterName = fnbResponse.getCinemaDetails().get(0).getTheaterName();

                    TextView locationText = findViewById(R.id.locationText);
                    locationText.setText(theaterName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupFilters() {
        List<FilterItem> filterList = new ArrayList<>();
        filterList.add(new FilterItem("All", true));
        filterList.add(new FilterItem("Veg", false));
        filterList.add(new FilterItem("Non Veg", false));
        filterList.add(new FilterItem("Popular", false));
        filterList.add(new FilterItem("Snacks", false));
        filterList.add(new FilterItem("Combos", false));
        filterList.add(new FilterItem("Popcorn", false));
        filterList.add(new FilterItem("Cold Beverages", false));
        filterList.add(new FilterItem("Hot Beverages", false));

        binding.filterChips.removeAllViews();

        for (FilterItem filter : filterList) {
            TextView chip = new TextView(this);
            chip.setText(filter.name);
            chip.setTextSize(13);
            chip.setPadding(32, 16, 32, 16);
            chip.setBackgroundResource(filter.isSelected ? R.drawable.bg_chip_selected : R.drawable.bg_chip_unselected);
            chip.setTextColor(filter.isSelected ? Color.BLACK : Color.parseColor("#757575"));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8, 8, 8);
            chip.setLayoutParams(params);

            chip.setOnClickListener(v -> {
                // Deselect all
                for (int i = 0; i < binding.filterChips.getChildCount(); i++) {
                    View view = binding.filterChips.getChildAt(i);
                    view.setBackgroundResource(R.drawable.bg_chip_unselected);
                    ((TextView) view).setTextColor(Color.parseColor("#757575"));
                }

                // Select clicked
                chip.setBackgroundResource(R.drawable.bg_chip_selected);
                chip.setTextColor(Color.BLACK);
                applyFilter(chip.getText().toString());
            });

            binding.filterChips.addView(chip);
        }
    }

    private void applyFilter(String filterName) {
        List<FoodItem> filtered = new ArrayList<>();

        for (FoodItem item : allItems) {
            switch (filterName.toLowerCase()) {
                case "all":
                    filtered = allItems;
                    break;
                case "veg":
                    if ("veg".equalsIgnoreCase(item.getFoodType())) filtered.add(item);
                    break;
                case "non veg":
                    if ("non veg".equalsIgnoreCase(item.getFoodType())) filtered.add(item);
                    break;
                case "popular":
                    if (item.isPopularItem()) filtered.add(item);
                    break;
                default:
                    if (item.getItemCategory() != null && item.getItemCategory().equalsIgnoreCase(filterName)) {
                        filtered.add(item);
                    }
                    break;
            }
        }

        foodAdapter.updateList(filtered);
    }


    @Override
    public void onCartChanged(List<FoodItem> updatedCart) {
        int totalQty = 0;
        double totalPrice = 0;

        for (FoodItem item : updatedCart) {
            totalQty += item.getQuantity();
            totalPrice += item.getQuantity() * item.getItemRate();

            if (item.getSelectedAddOnItem() != null) {
                for (AddOnItem addOn : item.getSelectedAddOnItem()) {
                    totalPrice += addOn.getQuantity() * addOn.getaddonItemRate();
                }
            }
        }

        if (totalQty == 0) {
            binding.cartContainer.setVisibility(View.GONE);
        } else {
            binding.cartContainer.setVisibility(View.VISIBLE);
            binding.cartItemCount.setText(totalQty + " item" + (totalQty > 1 ? "s" : "") + " added");
            binding.cartTotalPrice.setText("₹" + String.format("%.0f", totalPrice));
        }
    }


}