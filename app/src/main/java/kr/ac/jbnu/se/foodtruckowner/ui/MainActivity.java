package kr.ac.jbnu.se.foodtruckowner.ui;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.preference.PreferenceFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import kr.ac.jbnu.se.foodtruckowner.R;
public class MainActivity extends BaseDrawerActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        ReviewFragment reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.reviewListFragment);
//        reviewFragment.addItem(ContextCompat.getDrawable(this, R.drawable.profle),
//                "New Box", "New Account Box Black 36dp");
//        @Override
//        protected void onCreate (Bundle savedInstanceState){
//            ReviewFragment reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.reviewListFragment);
//            reviewFragment.addItem(ContextCompat.getDrawable(this, R.drawable.profle),
//                    "New Box", "New Account Box Black 36dp");
//        }


        /**
         *Setup the DrawerLayout and NavigationView
         */

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */
        /**
         * Setup Drawer Toggle of the Toolbar
         */


    }
}