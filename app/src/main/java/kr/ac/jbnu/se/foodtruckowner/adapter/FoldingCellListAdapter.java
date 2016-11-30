package kr.ac.jbnu.se.foodtruckowner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
 import java.util.List;

 import kr.ac.jbnu.se.foodtruckowner.R;
 import kr.ac.jbnu.se.foodtruckowner.model.FestivalModel;


/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<FestivalModel> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
   private View.OnClickListener defaultRequestBtnClickListener;

    final int REQ_CODE_SELECT_IMAGE=100;

    public FoldingCellListAdapter(Context context, List<FestivalModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // get item for selected view
        FestivalModel item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.year = (TextView) cell.findViewById(R.id.title_date_year);
            viewHolder.end_date = (TextView) cell.findViewById(R.id.title_end_date);
            viewHolder.start_date = (TextView) cell.findViewById(R.id.title_start_date);
            viewHolder.festive = (TextView) cell.findViewById(R.id.title_festive);
            viewHolder.place = (TextView) cell.findViewById(R.id.title_place);
            viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.request_btn);
            viewHolder.folding = (TextView) cell.findViewById(R.id.folding);
            viewHolder.headImage = (ImageView) cell.findViewById(R.id.festive_image);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.year.setText(item.getYear());
        viewHolder.end_date.setText(item.getStart_date());
        viewHolder.start_date.setText(item.getEnd_date());
        viewHolder.festive.setText(item.getFestive_title());
        viewHolder.place.setText(item.getPlace());
        viewHolder.requestsCount.setText(String.valueOf(item.getRequestsCount()));

        viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
/*
     dn
*/
                // set custom btn handler for list item from that item
        if (item.akgetRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.akgetRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;

    }


    // View lookup cache
    private static class ViewHolder {
    TextView year;
    TextView contentRequestBtn;
    TextView festive;
    TextView place;
    TextView requestsCount;
    TextView start_date;
    TextView end_date;
    TextView folding;
    ImageView headImage;
}
}
