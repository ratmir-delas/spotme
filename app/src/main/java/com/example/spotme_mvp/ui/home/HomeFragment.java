package com.example.spotme_mvp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spotme_mvp.R;
import com.example.spotme_mvp.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupSetMyCarImageClickListener(root, Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_content_main));

        return root;
    }

    private void setupSetMyCarImageClickListener(View root, NavController navController) {
        ImageView setMyCarImage = root.findViewById(R.id.setMyCarImage);
        if (setMyCarImage != null) {
            setMyCarImage.setOnClickListener(v -> navController.navigate(R.id.parkingFormFragment));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}