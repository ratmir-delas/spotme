package com.example.spotme_mvp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spotme_mvp.databinding.ActivityMainBinding;
import com.example.spotme_mvp.ui.authentication.LoginActivity;
import com.example.spotme_mvp.utils.UserSession;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "SpotMeChannel";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            if (navController.getCurrentDestination() != null
                    && navController.getCurrentDestination().getId() != R.id.parkingFormFragment) {
                navController.navigate(R.id.parkingFormFragment);
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_parking_history, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.nav_home) {
                navController.navigate(R.id.nav_home);
            }else if (id == R.id.nav_parking_history) {
                navController.navigate(R.id.nav_parking_history);
            } else if (id == R.id.nav_gallery) {
                navController.navigate(R.id.nav_gallery);
            } else if (id == R.id.nav_slideshow) {
                navController.navigate(R.id.nav_slideshow);
            } else if (id == R.id.nav_logout) {
                logout();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_parking_history);
            navController.navigate(R.id.nav_parking_history);
        }

        UserSession userSession = UserSession.getInstance(getApplicationContext());
        View headerView = navigationView.getHeaderView(0);
        TextView navHeaderName = headerView.findViewById(R.id.textViewName);
        TextView navHeaderEmail = headerView.findViewById(R.id.textViewEmail);
        navHeaderName.setText(userSession.getUserName());
        navHeaderEmail.setText(userSession.getUserEmail());

        // Carregar a imagem do utilizador
        ImageView profileImageView = headerView.findViewById(R.id.imageView);
        String profileImagePath = userSession.getUserProfileImage();
        if (profileImagePath != null) {
            File imgFile = new File(profileImagePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profileImageView.setImageBitmap(myBitmap);
            } else {
                profileImageView.setImageResource(R.drawable.ic_default_profile);
            }
        } else {
            profileImageView.setImageResource(R.drawable.ic_default_profile);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Parking Timer Channel";
            String description = "Channel for parking timer notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}