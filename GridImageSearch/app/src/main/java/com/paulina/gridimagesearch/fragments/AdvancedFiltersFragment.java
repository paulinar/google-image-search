package com.paulina.gridimagesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.paulina.gridimagesearch.R;
public class AdvancedFiltersFragment extends DialogFragment {

    private EditText mEditText;

    public AdvancedFiltersFragment() {
        // Empty constructor required for DialogFragment
    }

    public static AdvancedFiltersFragment newInstance(String title) {
        AdvancedFiltersFragment frag = new AdvancedFiltersFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_filters, container);
//        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
//        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle("Advanced Filters");
//        // Show soft keyboard automatically
//        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }
}