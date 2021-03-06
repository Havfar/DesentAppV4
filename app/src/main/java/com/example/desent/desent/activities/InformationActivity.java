package com.example.desent.desent.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.desent.desent.R;
import com.example.desent.desent.utils.SessionManagement;
import com.example.desent.desent.utils.Utility;

import java.io.FileNotFoundException;

public class InformationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button btnNext, btnRules;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setUpNavigationView();

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnNext = findViewById(R.id.btn_next);

        //Display rules
        btnRules = findViewById(R.id.btnRules);

        //dialog box that displays rules
        final AlertDialog.Builder builder = new AlertDialog.Builder(InformationActivity.this);
        builder.setTitle("You receive Earth Coins (EC) in the following situations:")
                .setMessage("Stay on top of the leaderboard for a week: 2 EC" + "\n" + "\n" +
                        "Add a new friend: 1 EC" + "\n" + "\n" +
                        "Reach the goal of 30 active minutes in a day: 1 EC" + "\n" + "\n" +
                        "Cycles: " + "\n" +
                        "3-5 km in a day: 1 EC" + "\n" +
                        "5-7 km in a day: 2 EC" + "\n" +
                        "over 7 km in a day: 3 EC" + "\n" + "\n" +
                        "Walks: " + "\n" +
                        "3-5 km km in a day : 1 EC" + "\n" +
                        "5-7 km km in a day : 2 EC" + "\n" +
                        "over 7 km in a day : 3 EC" + "\n" + "\n" +
                        "Completing challenges: various amount of EC" + "\n" + "\n" +
                        "Gifting: when giving Earth Coins to another player, receive a bonus of one EC per ten EC gifted." + "\n" + "\n" +
                        "Carbon footprint is below 4 kgCO2 during a day: 1 EC")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.create().show();
            }
        });

        // layouts with the relevant welcome slides
        layouts = new int[]{
                //R.layout.slide_welcome0,
                R.layout.slide_welcome1,
                R.layout.slide_welcome2,
                R.layout.slide_welcome3,
                R.layout.slide_welcome4,
                R.layout.slide_welcome5,
                R.layout.slide_welcome6,
                R.layout.slide_welcome7,
                R.layout.slide_welcome8,
                R.layout.slide_welcome9
                //R.layout.slide_welcome10,
        };
        System.out.println("Layouts: " + layouts.length);

        // adding bottom dots
        addBottomDots(0);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchLoginScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[layouts.length];
        System.out.println("Dots: " + dots.length);

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchLoginScreen() {
        startActivity(new Intent(InformationActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener;

    {
        viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);

                // changing the next button text 'NEXT' / 'GOT IT'
                if (position == layouts.length - 1) {
                    // last page. make button text to GOT IT
                    btnNext.setText(getString(R.string.start));
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
    }

    protected void setUpNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.title_nav_header)).setText(PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_personal_name", ""));
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.subtitle_nav_header)).setText(PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_personal_email", ""));
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewNumEarthCoins)).setText(String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getInt("pref_key_personal_score", 0)));

        ImageView profilePicture = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Uri imageUri = null;
        try {
            imageUri = Uri.parse(PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_profile_picture", "android.resource://com.example.desent.desent/drawable/earth"));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(imageUri));
            profilePicture.setImageBitmap(Utility.getCroppedBitmap(bitmap));
            bitmap.recycle();
            bitmap = null;
            System.gc();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(InformationActivity.this, MainActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(InformationActivity.this, HistoryActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(InformationActivity.this, SettingsActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_user_profile) {
            startActivity(new Intent(InformationActivity.this, ProfileActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(InformationActivity.this, FriendsActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_call_to_arms) {
            startActivity(new Intent(InformationActivity.this, CallToArmsActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_leaderboard) {
            startActivity(new Intent(InformationActivity.this, LeaderboardActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_information) {
            //startActivity(new Intent(InformationActivity.this, InformationActivity.class));
            drawer.closeDrawers();
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(InformationActivity.this, AboutUsActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_log_out){
            SessionManagement session = new SessionManagement(getApplicationContext());
            session.logoutUser();
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
