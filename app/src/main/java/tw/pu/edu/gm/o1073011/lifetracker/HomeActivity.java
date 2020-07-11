package tw.pu.edu.gm.o1073011.lifetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    private DashBoardFragment dashBoardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;

    private FirebaseAuth mAuth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Life Tracker");
        setSupportActionBar(toolbar);
        getActionBar();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigaton_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.naView);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        email = mUser.getEmail();

        View headView = navigationView.getHeaderView(0);
        TextView emailtxt = (TextView) headView.findViewById(R.id.userDisplayEmail);

        emailtxt.setText(email);

//        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        FrameLayout frameLayout = findViewById(R.id.main_frame);

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.dashboard:
//                        setFragment(dashBoardFragment);
//                        //bottomNavigationView.setItemBackgroundResource(R.color.dashboard_color);
//                        return true;
//
//                    case R.id.income:
//                        setFragment(incomeFragment);
//                        //bottomNavigationView.setItemBackgroundResource(R.color.income_color);
//                        return true;
//
//                    case R.id.expense:
//                        setFragment(expenseFragment);
//                        //bottomNavigationView.setItemBackgroundResource(R.color.expense_color);
//                        return true;
//                }
//
//                return false;
//            }
//        });

        dashBoardFragment = new DashBoardFragment();
        incomeFragment = new IncomeFragment();
        expenseFragment = new ExpenseFragment();

        setFragment(dashBoardFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
//
//    @Override
//    public void onBackPressed() {
//
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//
//        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
//            drawerLayout.closeDrawer(GravityCompat.END);
//        } else {
//            super.onBackPressed();
//        }
//
//    }
//
    public void displaySelectedListener(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.dashboard:
                fragment = new DashBoardFragment();
                break;

            case R.id.income:
                fragment = new IncomeFragment();
                break;

            case R.id.expense:
                fragment = new ExpenseFragment();
                break;

            case R.id.notes:
                fragment = new NoteFragment();
                break;

            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }
//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());
        return true;
    }
}