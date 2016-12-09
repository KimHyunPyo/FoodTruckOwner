package kr.ac.jbnu.se.foodtruckowner.ui.navimenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.FoldingCellListAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.FestivalModel;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ratan on 7/29/2015.
 */
public class FestiveFragment extends Fragment {
    private ArrayList<FestivalModel> items = new ArrayList<>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festive, null);

        listView = (ListView) view.findViewById(R.id.mainListView);



        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<FestivalModel>> call = service.festival_info();
        call.enqueue(new Callback<ArrayList<FestivalModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FestivalModel>> call, Response<ArrayList<FestivalModel>> response) {
                items = response.body();

                if(items == null) {
                    return;
                }

                Log.d("FESTIVAL", items.get(0).getFestive_title());

                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getContext(), items);

                // add default btn handler for each request btn on each item if custom handler not found
                adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                    }
                });

                // set elements to adapter
                listView.setAdapter(adapter);

                // set on click event listener to list view
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        adapter.registerToggle(pos);
                    }
                });


            }

            @Override
            public void onFailure(Call<ArrayList<FestivalModel>> call, Throwable t) {
                Log.d("FESTIVAL", t.toString());
            }
        });



        return view;

    }
}
