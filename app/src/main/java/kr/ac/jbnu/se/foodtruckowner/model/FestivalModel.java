package kr.ac.jbnu.se.foodtruckowner.model;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class FestivalModel {

    private String year;
    private String festive_title;
    private String place;
    private String start_date;
    private String end_date;

    private String title_recruitment_truck;
    private String title_cost;
    private String title_deadline;

    private String festive_content_view;
    private int recruitment_truck;
    private int request_truck;
    private String food_category;
    private String deadline;

    private View.OnClickListener requestBtnClickListener;

    public FestivalModel(String year,String festive_title, String place,  String end_date ,String start_date ,String festive_content_view,int recruitment_truck,int request_truck,String food_category,String deadline,String title_recruitment_truck,String title_cost,String title_deadline) {
        this.year = year;
        this.festive_title = festive_title;
        this.place = place;
        this.start_date = start_date;
        this.end_date = end_date;

        this.title_recruitment_truck = title_recruitment_truck;
        this.title_cost = title_cost;
        this.title_deadline= title_deadline;

        this.festive_content_view = festive_content_view;
        this.recruitment_truck = recruitment_truck;
        this.request_truck = request_truck;
        this.food_category = food_category;
        this.deadline = deadline;

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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


    public String getFestive_content_view() { return festive_content_view;}

    public void setFestive_content_view(String festive_content_view) {this.festive_content_view = festive_content_view;}

    public int getRecruitment_truck() {return recruitment_truck;}

    public void setRecruitment_truck(int recruitment_truck) {this.recruitment_truck = recruitment_truck;}
    public int getRequest_truck() {return request_truck;}

    public void setRequest_truck(int request_truck) {this.request_truck = request_truck;}

    public String getFood_category() {return food_category;}

    public void setFood_category(String food_category) {this.food_category = food_category;}

    public String getDeadline() {return deadline;}

    public void setDeadline(String deadline) {this.deadline = deadline;}

    public View.OnClickListener akgetRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {this.requestBtnClickListener = requestBtnClickListener;}
    public String getTitle_recruitment_truck() {return title_recruitment_truck;}

    public void setTitle_recruitment_truck(String title_recruitment_truck) {this.title_recruitment_truck = title_recruitment_truck;
    }
    public String getTitle_cost() {return title_cost;}

    public void setTitle_cost(String title_cost) {this.title_cost = title_cost;}

    public String getTitle_deadline() {return title_deadline;}

    public void setTitle_deadline(String title_deadline) {this.title_deadline = title_deadline;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestivalModel item = (FestivalModel) o;

        if (year != null ? !year.equals(item.year) : item.year != null) return false;
        if (festive_title != null ? !festive_title.equals(item.festive_title) : item.festive_title != null)
            return false;
        if (place != null ? !place.equals(item.place) : item.place != null)
            return false;
        if (start_date != null ? !start_date.equals(item.start_date) : item.start_date != null)
        return !(end_date != null ? !end_date.equals(item.end_date) : item.end_date != null);

        if (recruitment_truck!= item.recruitment_truck) return false;
        if (request_truck != item.request_truck) return false;
        if (festive_content_view != null ? !festive_content_view.equals(item.festive_content_view) : item.festive_content_view != null)
            return false;
        if (food_category != null ? !food_category.equals(item.food_category) : item.food_category != null)
            return false;
        if (deadline != null ? !deadline.equals(item.deadline) : item.deadline != null)
            return false;
        if (title_recruitment_truck != null ? !title_recruitment_truck.equals(item.title_recruitment_truck) : item.title_recruitment_truck != null)
            return false;
        if (title_cost!= null ? !title_cost.equals(item.title_cost) : item.title_cost != null)
            return false;
        if (title_deadline != null ? !title_deadline.equals(item.title_deadline) : item.title_deadline != null)
            return false;


        return  false;
    }

    @Override
    public int hashCode() {
        int result = year != null ? year.hashCode() : 0;
        result = 31 * result + (festive_title != null ? festive_title.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (start_date != null ? start_date.hashCode() : 0);
        result = 31 * result + (end_date != null ? end_date.hashCode() : 0);

        result = 31 * result + recruitment_truck;
        result = 31 * result + request_truck;
        result = 31 * result + (festive_content_view != null ? festive_content_view.hashCode() : 0);
        result = 31 * result + (food_category != null ? food_category.hashCode() : 0);
        result = 31 * result + (deadline!= null ? deadline.hashCode() : 0);

        result = 31 * result + (title_recruitment_truck != null ? title_recruitment_truck.hashCode() : 0);
        result = 31 * result + (title_cost != null ? title_cost.hashCode() : 0);
        result = 31 * result + (title_deadline != null ? title_deadline.hashCode() : 0);

        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<FestivalModel> getTestingList() {
        ArrayList<FestivalModel> items = new ArrayList<>();
        items.add(new FestivalModel("2016", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대", "12월24일 \n~ ", "12월26일","언제까지어깨춤을추게할거야 내어깨를봐 탈골됐잖아 탈골 탈골탈골탈고탈골 \n장소: 서울특별시 여의도 한강공원\n 날짜 2016년 12월24~12월26일까지  ",15,13,"곧","아사합니닿ㅎ","15","5만원","12월26일"));
        items.add(new FestivalModel("2016",  "서울 밤도깨비 야시장", "서울특별시 한강공원 일대",  "12월24일 \n~ ", "12월26일","",15,13,"","","15","5만원","12월26일"));
        items.add(new FestivalModel("2016",  "서울 밤도깨비 야시장", "서울특별시 한강공원 일대",  "12월24일 \n~ ", "12월26일","",15,13,"","","15","5만원","12월26일"));
        items.add(new FestivalModel("2016", "서울 밤도깨비 야시장", "서울특별시 한강공원 일대",  "12월24일 \n~", "12월26일","",15,13,"","","15","5만원","12월26일"));
        items.add(new FestivalModel("2016",  "서울 밤도깨비 야시장", "서울특별시 한강공원 일대",  "12월24일 \n~ ", "12월26일","",15,13,"","","15","5만원","12월26일"));
        return items;

    }

}
