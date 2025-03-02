package com.example.spotme_mvp.ui.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotme_mvp.R;
import com.example.spotme_mvp.entities.Parking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParkingDetailViewFragment extends Fragment {

    private ParkingDetailViewViewModel mViewModel;
    private TextView titleTextView, descriptionTextView, startTimeTextView, endTimeTextView;
    private Button buttonOpenMaps;

    private Parking parking;

    public static ParkingDetailViewFragment newInstance() {
        return new ParkingDetailViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parking_detail_view, container, false);

        titleTextView = root.findViewById(R.id.textViewTitle);
        descriptionTextView = root.findViewById(R.id.textViewDescription);
        startTimeTextView = root.findViewById(R.id.textViewStartTime);
        endTimeTextView = root.findViewById(R.id.textViewEndTime);
        buttonOpenMaps = root.findViewById(R.id.buttonOpenMaps);

        // Obter dados do estacionamento (deve ser passado via argumentos)
        if (getArguments() != null) {
            parking = (Parking) getArguments().getSerializable("parking");
            if (parking != null) {
                preencherDetalhes(parking);
            }
        }

        return root;
    }

    private void preencherDetalhes(Parking parking) {
        titleTextView.setText(parking.getTitle());
        descriptionTextView.setText(parking.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        // Converter e exibir Start Time
        String startTimeStr = sdf.format(new Date(parking.getStartTime()));
        startTimeTextView.setText("Início: " + startTimeStr);

        // Verificar se End Time é válido
        if (parking.getEndTime() == 0) {
            endTimeTextView.setText("Fim: Ainda em andamento");
        } else {
            String endTimeStr = sdf.format(new Date(parking.getEndTime()));
            endTimeTextView.setText("Fim: " + endTimeStr);
        }

        buttonOpenMaps.setOnClickListener(v -> {
            openInMaps(requireContext(), parking.getLatitude(), parking.getLongitude());
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void openInMaps(Context context, double latitude, double longitude) {
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(null); // Forçar a escolha de um app de mapas
        startActivity(mapIntent);
    }

}
