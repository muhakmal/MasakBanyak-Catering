package io.cauliframes.masakbanyak_catering.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.di.Components;
import io.cauliframes.masakbanyak_catering.di.DaggerSessionComponent;
import io.cauliframes.masakbanyak_catering.di.SessionComponent;
import io.cauliframes.masakbanyak_catering.di.SessionModule;
import io.cauliframes.masakbanyak_catering.model.Order;
import io.cauliframes.masakbanyak_catering.model.Packet;
import io.cauliframes.masakbanyak_catering.ui.fragment.PacketsFragment;
import io.cauliframes.masakbanyak_catering.ui.fragment.ProfileFragment;
import io.cauliframes.masakbanyak_catering.ui.fragment.TransactionsFragment;

public class MainActivity extends AppCompatActivity implements
    PacketsFragment.OnFragmentInteractionListener,
    TransactionsFragment.OnFragmentInteractionListener,
    ProfileFragment.OnFragmentInteractionListener {
  
  private CoordinatorLayout mCoordinatorLayout;
  private Toolbar mToolbar;
  private BottomNavigationView mNavigation;
  
  private PacketsFragment mPacketsFragment = PacketsFragment.newInstance();
  private TransactionsFragment mTransactionsFragment = TransactionsFragment.newInstance();
  private ProfileFragment mProfileFragment = ProfileFragment.newInstance();
  
  private BottomNavigationView.OnNavigationItemSelectedListener mNavigationListener = item -> {
    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    
    switch (item.getItemId()) {
      case R.id.navigation_packets:
        if (!(fragment instanceof PacketsFragment)) {
          transaction.replace(R.id.content, mPacketsFragment);
          transaction.addToBackStack(null);
          transaction.commit();
        }
        return true;
      
      case R.id.navigation_transactions:
        if (!(fragment instanceof TransactionsFragment)) {
          transaction.replace(R.id.content, mTransactionsFragment);
          transaction.addToBackStack(null);
          transaction.commit();
        }
        return true;
      
      case R.id.navigation_profile:
        if (!(fragment instanceof ProfileFragment)) {
          transaction.replace(R.id.content, mProfileFragment);
          transaction.addToBackStack(null);
          transaction.commit();
        }
        return true;
    }
    
    return false;
  };
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    SessionComponent sessionComponent = DaggerSessionComponent.builder()
        .sessionModule(new SessionModule())
        .applicationComponent(Components.getApplicationComponent())
        .build();
    Components.setSessionComponent(sessionComponent);
    Components.getSessionComponent().inject(this);
    
    mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
    
    mToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    
    mNavigation = findViewById(R.id.bottomNavigation);
    mNavigation.setOnNavigationItemSelectedListener(mNavigationListener);
    
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.content, PacketsFragment.newInstance());
    transaction.commit();
  }
  
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    
    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
    
    if (fragment instanceof PacketsFragment) {
      mNavigation.setSelectedItemId(R.id.navigation_packets);
    } else if (fragment instanceof TransactionsFragment) {
      mNavigation.setSelectedItemId(R.id.navigation_transactions);
    } else if (fragment instanceof ProfileFragment) {
      mNavigation.setSelectedItemId(R.id.navigation_profile);
    }
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    
    MenuItem item = menu.findItem(R.id.action_add_packet);
    item.setOnMenuItemClickListener(menuItem -> {
      Intent intent = new Intent(MainActivity.this, AddPacketActivity.class);
  
      startActivity(intent);
      
      return true;
    });
    
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public void onFragmentInteraction(Uri uri) {
  
  }
  
  @Override
  public void onPacketClick(Packet packet) {
    Intent intent = new Intent(this, EditPacketActivity.class);
    intent.putExtra("packet", packet);
    
    startActivity(intent);
  }
  
  @Override
  public void onOrderClick(Order order) {
    Intent intent = new Intent(this, TransactionActivity.class);
    intent.putExtra("order", order);
    
    startActivity(intent);
  }
  
  public void showResponse(String response){
    Snackbar.make(mCoordinatorLayout, response, Snackbar.LENGTH_SHORT).show();
  }
}