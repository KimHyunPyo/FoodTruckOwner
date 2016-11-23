package kr.ac.jbnu.se.foodtruckowner.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private ArrayList<MenuModel> listitems;
    private Context context;
    private float imageWidth;
    private float imageHeight;
    private MenuViewHolder holder;
    private String call;

    public MenuAdapter(Context context, ArrayList<MenuModel> listitems, String Call) {
        this.context = context;
        this.listitems = listitems;
        this.call = Call;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup v = (ViewGroup) mInflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        final MenuModel model = listitems.get(position);
        this.holder = holder;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());
        setBitmapImage(image);
        holder.title.setText(model.getTitle());
        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "해당 아이템 번호 = "+position);

            }
        });

    }
    @Override
    public int getItemCount() {
        return (null != listitems ? listitems.size() : 0);
    }

    private void setBitmapImage(Bitmap image){
        float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int)convertDpToPixel(10f, (Activity)context);
        // two images, three margins of 10dips
        imageWidth = ((width - (margin)) / 2);
        System.out.println(imageWidth);
        if(image != null){
            float i = ((float) imageWidth) / ((float) image.getWidth());
                imageHeight = i * (image.getHeight());

             holder.imageview.setImageBitmap(Bitmap.createScaledBitmap(image, (int)imageWidth, (int)imageHeight,false));
        }
        else{
            holder.imageview.setImageBitmap(image);
        }
    }
    private float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder {
        // View holder for griddview recycler view as we used in listview
        public TextView title;
        public TextView nexttitle;
        public ImageView imageview;

        public MenuViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            nexttitle = (TextView) view.findViewById(R.id.nextimage);
            imageview = (ImageView) view.findViewById(R.id.image);
        }
    }
}