package kr.ac.jbnu.se.foodtruckowner.ui.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;

import kr.ac.jbnu.se.foodtruckowner.adapter.ReviewAnimator;
import kr.ac.jbnu.se.foodtruckowner.adapter.ReviewItemAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.ReviewItem;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hyunjung on 2016-11-25.
 */
public class FragmentTruckReview extends Fragment {

    private ArrayList<ReviewItem> reviewitems = new ArrayList<>();
    private ReviewItemAdapter reviewAdapter;
    private RecyclerView review_view;
    private LinearLayout clContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_truck_review, null);
        clContent = (LinearLayout) view.findViewById(R.id.content);

        initFT();
        review_view = (RecyclerView) view.findViewById(R.id.review_view);

        return view;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFT() {
        reviewitems.clear();
        requestReview(FoodTruckModel.getInstance().getFT_ID());
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }

    public void requestReview(int foodtruck_id) {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<ReviewItem>> call = service.request_review(foodtruck_id);
        call.enqueue(new Callback<ArrayList<ReviewItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewItem>> call, Response<ArrayList<ReviewItem>> response) {
                ArrayList<ReviewItem> reviewList = response.body();

                if(reviewList == null) {
                    Log.d("REVIEW", "No Review");
                } else {
                    for (ReviewItem review : reviewList) {
                        reviewitems.add(review);
                        Log.d("REVIEW", review.getTitle());
                    }
                    showViewList(reviewitems);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewItem>> call, Throwable t) {
                Log.d("REVIEW", t.toString());
            }
        });
    }

    private void showViewList(ArrayList<ReviewItem> listitems) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };

        review_view.setHasFixedSize(true);
        review_view.setLayoutManager(linearLayoutManager);
        reviewAdapter = new ReviewItemAdapter(getContext(), listitems);
        review_view.setAdapter(reviewAdapter);
        review_view.setItemAnimator(new ReviewAnimator());
    }
}
