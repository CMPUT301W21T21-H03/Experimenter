package com.DivineInspiration.experimenter.Activity.UI.QRBarCode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.R;

public class BarCodeListFragment extends Fragment {

    RecyclerView recyclerView;

    /**
     * When creating view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.barcode_list, container, false);
    }

    /**
     * When view is created
     * @param view
     * view itself
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.barCodeList);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
    }

    /**
     * When the dialog fragment is paused
     */
    @Override
        public void onResume() {
            super.onResume();
            recyclerView.setAdapter(new BarcodeListAdapter(getContext()));
    }
}
