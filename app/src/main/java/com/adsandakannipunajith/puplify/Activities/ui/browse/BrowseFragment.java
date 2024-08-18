package com.adsandakannipunajith.puplify.Activities.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.adsandakannipunajith.puplify.Adapters.ProductAdapter;
import com.adsandakannipunajith.puplify.DAO.ProductDAO;
import com.adsandakannipunajith.puplify.Models.ProductModel;
import com.adsandakannipunajith.puplify.databinding.FragmentBrowseBinding;

import java.util.ArrayList;

public class BrowseFragment extends Fragment {

    private FragmentBrowseBinding binding;
    private SearchView searchView;
    private TextView searchViewLabel;
    private TextView noSearchResultsText;
    private Spinner productBrandFiltersSpinner, productTypeFiltersSpinner, productAgeGroupFiltersSpinner;
    private ProductAdapter productAdapter;

    private ArrayList<String> productBrandFilters, productTypeFilters, productAgeGroupFilters;
    private String searchText = "", selectedProductBrandFilter = "", selectedProductTypeFilter = "", selectedProductAgeGroupFilter = "";
    private ArrayList<ProductModel> products;
    private ProductDAO productDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        searchView = binding.searchView;
        searchViewLabel = binding.searchViewLabel;
        noSearchResultsText = binding.noSearchResultsText;

        productBrandFiltersSpinner = binding.searchBrandFiltersSpinner;
        productTypeFiltersSpinner = binding.searchFoodTypeFiltersSpinner;
        productAgeGroupFiltersSpinner = binding.searchAgeGroupFiltersSpinner;

        productBrandFilters = new ArrayList<>();
        productTypeFilters = new ArrayList<>();
        productAgeGroupFilters = new ArrayList<>();

        productBrandFilters.add("Any Brand");
        productTypeFilters.add("Any Type");
        productAgeGroupFilters.add("Any Age Group");

        RecyclerView productRecyclerView = binding.searchProductItemsRecyclerView;
        productDAO = new ProductDAO();
        products = productDAO.getProducts();
        productAdapter = new ProductAdapter(getContext(), products);
        handleNoSearchResults(products);

        productBrandFilters.addAll(productDAO.getProductBrands());
        productTypeFilters.addAll(productDAO.getProductTypes());
        productAgeGroupFilters.addAll(productDAO.getProductAgeGroups());


        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));


        productBrandFiltersSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, productBrandFilters));
        productTypeFiltersSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, productTypeFilters));
        productAgeGroupFiltersSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, productAgeGroupFilters));

        searchView.setOnSearchClickListener(view -> searchViewLabel.setVisibility(View.GONE));
        searchView.setOnCloseListener(() -> {
            searchViewLabel.setVisibility(View.VISIBLE);
            return false;
        });
        searchView.setOnClickListener(view -> searchView.setEnabled(true));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchText = s.trim();
                updateProducts();
                return false;
            }
        });

        productBrandFiltersSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedProductBrandFilter = "";
                } else {
                    selectedProductBrandFilter = productBrandFilters.get(position);
                }
                updateProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        productTypeFiltersSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedProductTypeFilter = "";
                } else {
                    selectedProductTypeFilter = productTypeFilters.get(position);
                }
                updateProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        productAgeGroupFiltersSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedProductAgeGroupFilter = "";
                } else {
                    selectedProductAgeGroupFilter = productAgeGroupFilters.get(position);
                }
                updateProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        return binding.getRoot();
    }

    public void handleNoSearchResults(ArrayList<ProductModel> products) {
        if (products.isEmpty()) {
            noSearchResultsText.setVisibility(View.VISIBLE);
        } else {
            noSearchResultsText.setVisibility(View.GONE);
        }
    }

    public void updateProducts() {
        products = productDAO.searchProducts(searchText, selectedProductBrandFilter, selectedProductTypeFilter, selectedProductAgeGroupFilter);
        productAdapter.updateProducts(products);
        handleNoSearchResults(products);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}