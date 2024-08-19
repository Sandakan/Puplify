package com.adsandakannipunajith.puplify.activities.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.adapters.EducationalItemAdapter;
import com.adsandakannipunajith.puplify.dao.EducationalContentDAO;
import com.adsandakannipunajith.puplify.databinding.FragmentInfoBinding;
import com.adsandakannipunajith.puplify.models.EducationalItemModel;

import java.util.ArrayList;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);

        RecyclerView productRecyclerView = binding.educationalContentRecyclerView;

        EducationalContentDAO educationalContentDAO = new EducationalContentDAO();
        ArrayList<EducationalItemModel> educationalItems = educationalContentDAO.getEducationItems();
        EducationalItemAdapter educationalItemAdapter = new EducationalItemAdapter(getContext(), educationalItems);

        productRecyclerView.setAdapter(educationalItemAdapter);
        productRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}