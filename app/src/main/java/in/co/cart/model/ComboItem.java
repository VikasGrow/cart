package in.co.cart.model;

import com.google.gson.annotations.SerializedName;

public class ComboItem {

    @SerializedName("itemId")
    public String itemId;

    @SerializedName("itemQty")
    public int itemQty;

    @SerializedName("itemRate")
    public double itemRate;

    @SerializedName("itemAmount")
    public double itemAmount;

    @SerializedName("itemSaleRate")
    public double itemSaleRate;

    @SerializedName("itemSaleAmount")
    public double itemSaleAmount;

    @SerializedName("itemName")
    public String itemName;

    @SerializedName("foodType")
    public String foodType;

    @SerializedName("itemCGSTTaxPer")
    public double itemCGSTTaxPer;

    @SerializedName("itemSGSTTaxPer")
    public double itemSGSTTaxPer;

    @SerializedName("itemUTGSTTaxPer")
    public double itemUTGSTTaxPer;

    @SerializedName("itemOtherTaxPer")
    public double itemOtherTaxPer;

    @SerializedName("itemTaxValue")
    public double itemTaxValue;

    @SerializedName("itemBasePrice")
    public double itemBasePrice;

    @SerializedName("itemOfferBasePrice")
    public double itemOfferBasePrice;

    @SerializedName("itemOfferTaxValue")
    public double itemOfferTaxValue;

    // You can add constructors, getters, and setters if needed
}
