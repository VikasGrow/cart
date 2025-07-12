package in.co.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FnbResponse {
    public int status;
    public String msg;
    public boolean isPopularAvailable;
    public boolean isRepeatAvailable;

    @SerializedName("listOfFnbItems")
    public List<FoodItem> listOfFnbItems;

    private List<CinemaDetail> cinemaDetails;


    public FnbResponse(int status, String msg, boolean isPopularAvailable, boolean isRepeatAvailable, List<FoodItem> listOfFnbItems) {
        this.status = status;
        this.msg = msg;
        this.isPopularAvailable = isPopularAvailable;
        this.isRepeatAvailable = isRepeatAvailable;
        this.listOfFnbItems = listOfFnbItems;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isPopularAvailable() {
        return isPopularAvailable;
    }

    public void setPopularAvailable(boolean popularAvailable) {
        isPopularAvailable = popularAvailable;
    }

    public boolean isRepeatAvailable() {
        return isRepeatAvailable;
    }

    public void setRepeatAvailable(boolean repeatAvailable) {
        isRepeatAvailable = repeatAvailable;
    }

    public List<FoodItem> getListOfFnbItems() {
        return listOfFnbItems;
    }

    public void setListOfFnbItems(List<FoodItem> listOfFnbItems) {
        this.listOfFnbItems = listOfFnbItems;
    }

    public List<CinemaDetail> getCinemaDetails() {
        return cinemaDetails;
    }
}