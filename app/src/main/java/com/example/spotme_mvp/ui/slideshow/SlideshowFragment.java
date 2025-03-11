package com.example.spotme_mvp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotme_mvp.R;
import com.example.spotme_mvp.databinding.FragmentSlideshowBinding;
import com.example.spotme_mvp.utils.UserSession;

public class SlideshowFragment extends Fragment {

    private UserSession userSession;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.settings_main, container, false);
        userSession = UserSession.getInstance(requireContext());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}