package kr.ac.jbnu.se.foodtruckowner.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.ui.modi_dialog_Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;

    public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

        private ArrayList<MenuModel> listitems;
        private Context context;
        private float imageWidth;
        private float imageHeight;
        private MenuViewHolder holder;
        private String call;
        private FragmentManager fm;

    String Url="https://server-blackdog11.c9users.io/";


    public MenuAdapter(Context context, ArrayList<MenuModel> listitems, String Call,FragmentManager fm) {
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


//        Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());
//        setBitmapImage(image);
        holder.title.setText(model.getTitle()+"");
        Log.d("TAG", "메뉴이름넘어오니? :" + model.getTitle());
        holder.price.setText(model.getPrice() + "원");
        Picasso.with(context).load(Url + listitems.get(position).getImage().getUrl()).into(holder.imageview);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "해당 아이템 번호 = " + position);
                //dialog 띄우기
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")

                        .setContentText("Won't be able to recover this file!")
                        //수정버튼 text
                        .setCancelText("수정")
                        //삭제버튼 text
                        .setConfirmText("삭제")
                        .showCancelButton(true)
                        //수정버튼 리스너
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override

                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                modifyMenu(position);

                            }

                        })
                        // 삭제버튼 리스너
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

                        })
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != listitems ? listitems.size() : 0);
    }

    //메뉴 삭제
    public void deleteMenu(int pos) {
        listitems.remove(pos);
        notifyDataSetChanged();
        return;
    }

    //메뉴 추가
    public void addMenu(MenuModel menu) {
        listitems.add(menu);
        notifyDataSetChanged();
    }

    //메뉴수정
    public void modifyMenu(int pos) {
        MenuModel tempModel = listitems.get(pos);
        show_modi_dialog_Fragment();
        notifyDataSetChanged();
    }

    //수정 다이얼로그 띄우기
    private void show_modi_dialog_Fragment() {
        modi_dialog_Fragment inputDialog = new modi_dialog_Fragment();
        inputDialog.setDialogTitle("Enter Name");
        inputDialog.show(fm, "Input Dialog");
    }

    private void setBitmapImage(Bitmap image) {
        float width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int) convertDpToPixel(10f, (Activity) context);
        // two images, three margins of 10dips
        imageWidth = ((width - (margin)) / 2);
        System.out.println(imageWidth);
        if (image != null) {
            float i = ((float) imageWidth) / ((float) image.getWidth());
            imageHeight = i * (image.getHeight());
            holder.imageview.setImageBitmap(Bitmap.createScaledBitmap(image, (int) imageWidth, (int) imageHeight, false));
        } else {
            holder.imageview.setImageBitmap(image);
        }
    }


    private float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
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