package com.satdroid.serveup.DonorPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.satdroid.serveup.R;

import java.util.zip.Inflater;

public class PendingRequestFragmentDonor extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_pending_request_donor,null);
        getActivity().setTitle("Pending Requests");
        return v;
    }
}
