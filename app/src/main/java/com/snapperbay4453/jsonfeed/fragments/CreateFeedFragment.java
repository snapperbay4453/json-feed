package com.snapperbay4453.jsonfeed.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.snapperbay4453.jsonfeed.databinding.FragmentCreateFeedBinding;

public class CreateFeedFragment extends Fragment {

    private FragmentCreateFeedBinding binding;
    private Button submitButton;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentCreateFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        submitButton = binding.fragmentCreateFeedSubmitButton;

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
