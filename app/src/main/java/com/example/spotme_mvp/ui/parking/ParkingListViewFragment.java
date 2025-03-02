package com.example.spotme_mvp.ui.parking;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotme_mvp.R;
import com.example.spotme_mvp.adapters.ParkingListAdapter;
import com.example.spotme_mvp.database.AppDatabase;
import com.example.spotme_mvp.database.ParkingDao;
import com.example.spotme_mvp.entities.Parking;

import java.util.List;
import java.util.concurrent.Executors;

public class ParkingListViewFragment extends Fragment {

    private ParkingListViewViewModel mViewModel;
    private RecyclerView recyclerView;
    private ParkingListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parking_list_view, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewParkings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Carregar lista de parkings
        loadParkingList();

        return root;
    }

    private void loadParkingList() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = Room.databaseBuilder(requireContext(), AppDatabase.class, "database-name").build();
            ParkingDao parkingDao = db.parkingDao();
            List<Parking> parkings = parkingDao.getAll();

            requireActivity().runOnUiThread(() -> {
                adapter = new ParkingListAdapter(parkings, view -> {
                    int position = recyclerView.getChildLayoutPosition(view);
                    Parking parking = parkings.get(position);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    if (navController.getCurrentDestination() != null
                            && navController.getCurrentDestination().getId() != R.id.parkingFormFragment) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("parking", parking);
                        navController.navigate(R.id.parkingDetailViewFragment, bundle);
                    }
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }

}
