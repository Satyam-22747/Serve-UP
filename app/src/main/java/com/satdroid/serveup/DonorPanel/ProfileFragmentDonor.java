package com.satdroid.serveup.DonorPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;

import com.satdroid.serveup.R;

public class ProfileFragmentDonor extends Fragment
{
    AppCompatButton postdish;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile_donor,null);
        getActivity().setTitle("Publish Item");
        postdish=v.findViewById(R.id.post_dish);
        postdish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Post_Dish.class));
            }
        });
        return v;
    }
}
