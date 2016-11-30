package kr.ac.jbnu.se.foodtruckowner.model;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class FestivalModel {

    private String year;
    private String pledgePrice;
    private String festive_title;
    private String place;
    private int requestsCount;
    private String start_date;
    private String end_date;



    private View.OnClickListener requestBtnClickListener;

    public FestivalModel() {
    }

    public FestivalModel(String year, String pledgePrice, String festive_title, String place, int requestsCount, String start_date, String end_date) {
        this.year = year;
        this.pledgePrice = pledgePrice;
        this.festive_title = festive_title;
        this.place = place;
        this.requestsCount = requestsCount;
        this.start_date = start_date;
        this.end_date = end_date;

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
    }

    public String getFestive_title() {
        return festive_title;
    }

    public void setFestive_title(String festive_title) {
        this.festive_title = festive_title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public View.OnClickListener akgetRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestivalModel item = (FestivalModel) o;

        if (requestsCount != item.requestsCount) return false;
        if (year != null ? !year.equals(item.year) : item.year != null) return false;
        if (pledgePrice != null ? !pledgePrice.equals(item.pledgePrice) : item.pledgePrice != null)
            return false;
        if (festive_title != null ? !festive_title.equals(item.festive_title) : item.festive_title != null)
            return false;
        if (place != null ? !place.equals(item.place) : item.place != null)
            return false;
        if (start_date != null ? !start_date.equals(item.start_date) : item.start_date != null) return false;
        return !(end_date != null ? !end_date.equals(item.end_date) : item.end_date != null);

    }

    @Override
    public int hashCode() {
        int result = year != null ? year.hashCode() : 0;
        result = 31 * result + (pledgePrice != null ? pledgePrice.hashCode() : 0);
        result = 31 * result + (festive_title != null ? festive_title.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + requestsCount;
        result = 31 * result + (start_date != null ? start_date.hashCode() : 0);
        result = 31 * result + (end_date != null ? end_date.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<FestivalModel> getTestingList() {
        ArrayList<FestivalModel> items = new ArrayList<>();
        items.add(new FestivalModel("2016", "", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대", 7, "12월24일 \n~\n ", "12월26일"));
        items.add(new FestivalModel("2016", "", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대", 7, "12월24일 \n~\n ", "12월26일"));
        items.add(new FestivalModel("2016", "", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대", 7, "12월24일 \n~\n ", "12월26일"));
        items.add(new FestivalModel("2016", "", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대", 7, "12월24일 \n~\n ", "12월26일"));
        items.add(new FestivalModel("2016", "", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대", 7, "12월24일 \n~\n ", "12월26일"));
        return items;

    }

}
