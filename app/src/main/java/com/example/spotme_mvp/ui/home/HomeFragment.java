package com.example.spotme_mvp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.example.spotme_mvp.database.AppDatabase;
import com.example.spotme_mvp.databinding.FragmentHomeBinding;
import com.example.spotme_mvp.entities.Parking;
import com.example.spotme_mvp.utils.UserSession;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AppBarConfiguration appBarConfiguration;

    private UserSession userSession;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        userSession = UserSession.getInstance(requireContext());

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_content_main);
        setupSetMyCarImageClickListener(root, navController);
        setupFindMyCarImageClickListener(root, navController);

        return root;
    }

    private void setupSetMyCarImageClickListener(View root, NavController navController) {
        ImageView setMyCarImage = root.findViewById(R.id.setMyCarImage);
        if (setMyCarImage != null) {
            setMyCarImage.setOnClickListener(v -> navController.navigate(R.id.parkingFormFragment));
        }
    }

    private void setupFindMyCarImageClickListener(View root, NavController navController) {
        ImageView findMyCarImage = root.findViewById(R.id.pickMyCarImage);
        if (findMyCarImage != null) {
            findMyCarImage.setOnClickListener(v -> {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                Executors.newSingleThreadExecutor().execute(() -> {
                    Parking lastParking = db.parkingDao().getLastParkingByUserId(userSession.getUserId());
                    if (lastParking != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("parking", lastParking);
                        requireActivity().runOnUiThread(() -> navController.navigate(R.id.parkingDetailViewFragment, bundle));
                    } else {
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "No parking records found", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}