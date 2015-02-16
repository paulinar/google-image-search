package com.paulina.gridimagesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.paulina.gridimagesearch.R;
import com.paulina.gridimagesearch.models.Filters;

public class AdvancedFiltersFragment extends DialogFragment {

    private Spinner mSpinnerImageSize;
    private Spinner mSpinnerColorFilter;
    private Spinner mSpinnerImageType;
    private EditText mSiteName;
    private String mImageSize;
    private String mImageColor;
    private String mImageType;
    private String mSite;
    private Filters mFilters;
    private Button mBtnSave;
    private Button mBtnCancel;

    public AdvancedFiltersFragment() {
        // Empty constructor required for DialogFragment
    }

    // http://stackoverflow.com/questions/15459209/passing-argument-to-dialogfragment
    public static AdvancedFiltersFragment newInstance(Filters filters) {
        AdvancedFiltersFragment frag = new AdvancedFiltersFragment();
        Bundle args = new Bundle();
        args.putSerializable("filters", filters);
        frag.setArguments(args);
        return frag;
    }

    public interface AdvancedFiltersFragmentDialogListener {
        void getDataFromDialog(Bundle result);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_filters, container);

        getDialog().setTitle("Advanced Filters");
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mSpinnerImageSize = (Spinner) view.findViewById(R.id.spinnerImageSize);
        mSpinnerColorFilter = (Spinner) view.findViewById(R.id.spinnerColorFilter);
        mSpinnerImageType = (Spinner) view.findViewById(R.id.spinnerImageType);
        mSiteName = (EditText) view.findViewById(R.id.etSiteName);
        mBtnSave = (Button) view.findViewById(R.id.btnSave);
        mBtnCancel = (Button) view.findViewById(R.id.btnCancel);

        mFilters = (Filters) getArguments().getSerializable("filter");
        if (mFilters == null)
            mFilters = new Filters();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSetting();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void saveSetting() {

        mImageSize = mSpinnerImageSize.getSelectedItem().toString();
        mImageColor = mSpinnerColorFilter.getSelectedItem().toString();
        mImageType = mSpinnerImageType.getSelectedItem().toString();
        mSite = mSiteName.getText().toString();

        mFilters.imageSize = mImageSize;
        mFilters.colorFilter = mImageColor;
        mFilters.imageType = mImageType;
        mFilters.siteFilter = mSite;

        int savedImageSizePosition = mSpinnerImageSize.getSelectedItemPosition();
        mSpinnerImageSize.setSelection(savedImageSizePosition);

        AdvancedFiltersFragmentDialogListener advancedFiltersFragmentDialogListener = (AdvancedFiltersFragmentDialogListener) getActivity();
        Bundle bundle = new Bundle();
        bundle.putSerializable("filters", mFilters);
        advancedFiltersFragmentDialogListener.getDataFromDialog(bundle); // storing result into this activity
        dismiss();
    }

}