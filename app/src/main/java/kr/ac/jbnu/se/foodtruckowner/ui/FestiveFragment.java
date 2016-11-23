package kr.ac.jbnu.se.foodtruckowner.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.adapter.FoldingCellListAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.Item;

/**
 * Created by Ratan on 7/29/2015.
 */
public class FestiveFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festive,null);


        view.findViewById(R.id.imageView1).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                    }
                }
        );
        // get our list view
        ListView theListView = (ListView)view.findViewById(R.id.mainListView);

        // prepare elements to display
        final ArrayList<Item> items = Item.getTestingList();

        // add custom btn handler to first list item
        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getContext(),items);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });



        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

        return view;

    }
}
