package com.satdroid.serveup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.satdroid.serveup.DonorPanel.CompletedOrderFragmentDonor;
import com.satdroid.serveup.DonorPanel.HomeFragmentDonor;
import com.satdroid.serveup.DonorPanel.PendingRequestFragmentDonor;
import com.satdroid.serveup.DonorPanel.ProfileFragmentDonor;

public class Donor_panel_bottomNavigationview extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_panel_bottom_navigationview);

        BottomNavigationView navigationView=findViewById(R.id.donor_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
          if(item.getItemId()==R.id.Home)
              fragment=new HomeFragmentDonor();
        if(item.getItemId()==R.id.Pending_Request)
              fragment=new PendingRequestFragmentDonor();

        if(item.getItemId()==R.id.completed)
              fragment=new CompletedOrderFragmentDonor();

        if(item.getItemId()==R.id.Publish_Item)
              fragment=new ProfileFragmentDonor();

      return loadDonorFragment(fragment);
        }

    private boolean loadDonorFragment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}
