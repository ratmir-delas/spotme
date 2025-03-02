package com.example.spotme_mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.spotme_mvp.ui.authentication.LoginActivity;
import com.example.spotme_mvp.ui.parking.ParkingDetailViewFragment;
import com.example.spotme_mvp.ui.parking.ParkingListViewFragment;
import com.example.spotme_mvp.utils.UserSession;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.spotme_mvp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuração da toolbar para habilitar o botão de menu
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setNavigationIcon(R.drawable.ic_menu);
        binding.appBarMain.toolbar.setNavigationOnClickListener(v -> {
            DrawerLayout drawer = binding.drawerLayout;
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        // Configuração do botão FAB
        binding.appBarMain.fab.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            if (navController.getCurrentDestination() != null
                    && navController.getCurrentDestination().getId() != R.id.parkingFormFragment) {
                navController.navigate(R.id.parkingFormFragment);
            }
        });

        // Configuração do DrawerLayout e NavigationView
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuração de navegação com AppBarConfiguration
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_parking_history, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Configuração do item selecionado no menu lateral

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_parking_history) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new ParkingListViewFragment()).commit();
            } else if (id == R.id.nav_gallery) {
                // Implemente a navegação para a tela desejada
            } else if (id == R.id.nav_slideshow) {
                // Implemente a navegação para a tela desejada
            } else if (id == R.id.nav_logout) {
                logout();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_parking_history);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new ParkingListViewFragment()).commit();
        }

        UserSession userSession = UserSession.getInstance(getApplicationContext());
        Log.d("UserSession", "User Name: " + userSession.getUserName());
        Log.d("UserSession", "User Email: " + userSession.getUserEmail());
        View headerView = navigationView.getHeaderView(0);
        TextView navHeaderName = headerView.findViewById(R.id.textViewName);
        TextView navHeaderEmail = headerView.findViewById(R.id.textViewEmail);
        navHeaderName.setText(userSession.getUserName());
        navHeaderEmail.setText(userSession.getUserEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
        return true;
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}