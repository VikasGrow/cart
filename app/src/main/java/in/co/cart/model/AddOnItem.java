package in.co.cart.model;

public class AddOnItem {
    private String itemId;         // ID of parent food item (optional)
    private String addonItemId;    // Addon unique ID
    private String addonItemName;           // Addon addonItemName
    private int addonItemRate;             // Addon addonItemRate
    private int quantity = 0;      // Default 0

    // Full constructor (recommended when parsing)
    public AddOnItem(String itemId, String addonItemId, String addonItemName, int addonItemRate, int quantity) {
        this.itemId = itemId;
        this.addonItemId = addonItemId;
        this.addonItemName = addonItemName;
        this.addonItemRate = addonItemRate;
        this.quantity = quantity;
    }

    // Alternate constructor (e.g., used in adapter with selected list)
    public AddOnItem(String addonItemId, int quantity, String addonItemName, int addonItemRate) {
        this.addonItemId = addonItemId;
        this.quantity = quantity;
        this.addonItemName = addonItemName;
        this.addonItemRate = addonItemRate;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAddonItemId() {
        return addonItemId;
    }

    public void setAddonItemId(String addonItemId) {
        this.addonItemId = addonItemId;
    }

    public String getaddonItemName() {
        return addonItemName;
    }

    public void setaddonItemName(String addonItemName) {
        this.addonItemName = addonItemName;
    }

    public int getaddonItemRate() {
        return addonItemRate;
    }

    public void setaddonItemRate(int addonItemRate) {
        this.addonItemRate = addonItemRate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
