package kr.ac.jbnu.se.foodtruckowner.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private ArrayList<MenuModel> listitems;
    private Context context;
    private float imageWidth;
    private float imageHeight;
    private MenuViewHolder holder;
    private String call;
    private FragmentManager fm;

    public MenuAdapter(Context context, ArrayList<MenuModel> listitems, String Call, FragmentManager fm) {
        this.context = context;
        this.listitems = listitems;
        this.call = Call;
        this.fm = fm;
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

        holder.title.setText(model.getTitle() + "");
        holder.price.setText(model.getPrice() + "원");

        // TODO: 2016-11-30  높이도 리사이징 해줘야함 http://stackoverflow.com/questions/25799967/android-get-drawable-image-after-picasso-loaded
        //이미지 리사이징
        float width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int) convertDpToPixel(10f, (Activity) context);
        imageWidth = ((width - (margin)) / 2);
        imageHeight = (float) (imageWidth * 1.1); //나중에..

        Picasso.with(context)
                .load(ServiceGenerator.API_BASE_URL + listitems.get(position).getImage().getUrl())
                .resize((int) imageWidth, (int) imageHeight)
                .into(holder.imageview);
        if(listitems.get(position).getImage().getUrl()==null){
            Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_images);
            setBitmapImage(image);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "해당 아이템 번호 = " + position);
                //dialog 띄우기
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("메뉴 삭제")
                        .setContentText("해당 메뉴의 삭제를 원하시면 삭제 버튼을 눌러주세요.")
                        .setConfirmText("삭제")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("삭제되었습니다.")
                                        .setConfirmText("확인")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                deleteMenu(position);
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != listitems ? listitems.size() : 0);
    }

    //메뉴 삭제
    public void deleteMenu(final int pos) {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Boolean> call = service.delete_menu(listitems.get(pos).getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean result = response.body();
                if(result == true) {
                    listitems.remove(pos);
                    notifyDataSetChanged();
                } else {

                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("MENU", "메뉴 삭제 오류");
                Log.d("MENU", t.toString());
            }
        });
        return;
    }

    private float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    private void setBitmapImage(Bitmap image) {
        float width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int) convertDpToPixel(10f, (Activity) context);
        // two images, three margins of 10dips
        imageWidth = ((width - (margin)) / 2);
        if (image != null) {
            float i = ((float) imageWidth) / ((float) image.getWidth());
            if (call.equals("AcitivityTruckMenu"))
                imageHeight = i * (image.getHeight());
            else if (call.equals("AcitivityTruckDetail"))
                imageHeight = imageWidth;
            holder.imageview.setImageBitmap(Bitmap.createScaledBitmap(image, (int) imageWidth, (int) imageHeight, false));
        } else {
            holder.imageview.setImageBitmap(image);
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        // View holder for griddview recycler view as we used in listview
        public TextView title;
        public TextView price;
        public ImageView imageview;

        public MenuViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.menu_title);
            imageview = (ImageView) view.findViewById(R.id.image);
            price = (TextView) view.findViewById(R.id.price);
        }
    }
}