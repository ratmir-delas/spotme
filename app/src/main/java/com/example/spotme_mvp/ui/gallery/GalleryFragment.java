package com.example.spotme_mvp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spotme_mvp.R;
import com.example.spotme_mvp.database.AppDatabase;
import com.example.spotme_mvp.utils.UserSession;

// TO:DO - Implement the GalleryFragment class

public class GalleryFragment extends Fragment {

    private UserSession userSession;
    private AppDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.profile_main_view, container, false);

        // Initialize UI components
        TextView tvAccountTitle = root.findViewById(R.id.tvAccountTitle);
        ImageView profileImage = root.findViewById(R.id.profileImage);
        TextView tvNomeLabel = root.findViewById(R.id.tvNomeLabel);
        TextView tvNome = root.findViewById(R.id.tvNome);
        ImageView ivEditNome = root.findViewById(R.id.ivEditNome);
        TextView tvEmailLabel = root.findViewById(R.id.tvEmailLabel);
        TextView tvEmail = root.findViewById(R.id.tvEmail);
        ImageView ivEditEmail = root.findViewById(R.id.ivEditEmail);
        TextView tvChangePassword = root.findViewById(R.id.tvChangePassword);
        ImageView ivArrowPassword = root.findViewById(R.id.ivArrowPassword);
        TextView tvPersonalStats = root.findViewById(R.id.tvPersonalStats);
        ImageView ivArrowStats = root.findViewById(R.id.ivArrowStats);

        userSession = UserSession.getInstance(requireContext());
        db = AppDatabase.getInstance(requireContext());

        // Set initial values or listeners if needed
        tvNome.setText(userSession.getUserName());
        tvEmail.setText(userSession.getUserEmail());

        // Add any additional setup or listeners here

        return root;
    }
}