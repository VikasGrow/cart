package in.co.cart.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FoodItem {

    private int quantity = 0; // main item quantity
    private List<AddOnItem> selectedAddOns = new ArrayList<>();
    private String isVeg; // "veg" or "non-veg"

    @SerializedName("itemId")
    public String itemId;

    @SerializedName("itemName")
    public String itemName;

    @SerializedName("itemImageURL")
    public String itemImageURL;

    @SerializedName("itemRate")
    public double itemRate;

    @SerializedName("itemOfferRate")
    public double itemOfferRate;

    @SerializedName("foodType")
    public String foodType;

    @SerializedName("isComboAvailable")
    public boolean isComboAvailable;

    @SerializedName("comboListItems")
    public List<ComboItem> comboListItems;

    @SerializedName("isAddOnAvailable")
    public boolean isAddOnAvailable;

    @SerializedName("addOnItems")
    public List<AddOnItem> addOnItems;

    @SerializedName("itemCategory")
    public String itemCategory;

    @SerializedName("isPopuplarItem")
    public boolean isPopularItem;

    @SerializedName("isRepeat")
    public boolean isRepeat;

    @SerializedName("calories")
    public String calories;

    @SerializedName("itemWeight")
    public String itemWeight;

    private List<AddOnItem> selectedAddOnItem;


    public FoodItem(String itemId, String itemName, String itemImageURL, double itemRate, double itemOfferRate, String foodType, boolean isComboAvailable, List<ComboItem> comboListItems, boolean isAddOnAvailable, List<AddOnItem> addOnItems, String itemCategory, boolean isPopularItem, boolean isRepeat, String calories, String itemWeight) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageURL = itemImageURL;
        this.itemRate = itemRate;
        this.itemOfferRate = itemOfferRate;
        this.foodType = foodType;
        this.isComboAvailable = isComboAvailable;
        this.comboListItems = comboListItems;
        this.isAddOnAvailable = isAddOnAvailable;
        this.addOnItems = addOnItems;
        this.itemCategory = itemCategory;
        this.isPopularItem = isPopularItem;
        this.isRepeat = isRepeat;
        this.calories = calories;
        this.itemWeight = itemWeight;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<ComboItem> getComboListItems() {
        return comboListItems;
    }

    public void setComboListItems(List<ComboItem> comboListItems) {
        this.comboListItems = comboListItems;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageURL() {
        return itemImageURL;
    }

    public void setItemImageURL(String itemImageURL) {
        this.itemImageURL = itemImageURL;
    }

    public double getItemRate() {
        return itemRate;
    }

    public void setItemRate(double itemRate) {
        this.itemRate = itemRate;
    }

    public double getItemOfferRate() {
        return itemOfferRate;
    }

    public void setItemOfferRate(double itemOfferRate) {
        this.itemOfferRate = itemOfferRate;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public boolean isComboAvailable() {
        return isComboAvailable;
    }

    public void setComboAvailable(boolean comboAvailable) {
        isComboAvailable = comboAvailable;
    }

    public boolean isAddOnAvailable() {
        return isAddOnAvailable;
    }

    public void setAddOnAvailable(boolean addOnAvailable) {
        isAddOnAvailable = addOnAvailable;
    }

    public List<AddOnItem> getAddOnItems() {
        return addOnItems;
    }

    public void setAddOnItems(List<AddOnItem> addOnItems) {
        this.addOnItems = addOnItems;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public boolean isPopularItem() {
        return isPopularItem;
    }

    public void setPopularItem(boolean popularItem) {
        isPopularItem = popularItem;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String itemWeight) {
        this.itemWeight = itemWeight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<AddOnItem> getSelectedAddOns() {
        return selectedAddOns;
    }

    public void setSelectedAddOns(List<AddOnItem> selectedAddOns) {
        this.selectedAddOns = selectedAddOns;
    }

    public String getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(String isVeg) {
        this.isVeg = isVeg;
    }

    public List<AddOnItem> getSelectedAddOnItem() {
        return selectedAddOnItem != null ? selectedAddOnItem : new ArrayList<>();
    }

    public void setSelectedAddOnItem(List<AddOnItem> selectedAddOnItem) {
        this.selectedAddOnItem = selectedAddOnItem;
    }
}
