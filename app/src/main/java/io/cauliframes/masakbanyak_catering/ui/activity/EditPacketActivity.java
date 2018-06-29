package io.cauliframes.masakbanyak_catering.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import javax.inject.Inject;

import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.di.Components;
import io.cauliframes.masakbanyak_catering.model.Packet;
import io.cauliframes.masakbanyak_catering.viewmodel.CateringViewModel;
import io.cauliframes.masakbanyak_catering.viewmodel.ViewModelFactory;

public class EditPacketActivity extends AppCompatActivity {
  
  @Inject
  ViewModelFactory mViewModelFactory;
  
  CateringViewModel mCateringViewModel;
  
  private Packet mPacket;
  
  private CoordinatorLayout mCoordinatorLayout;
  private EditText mPacketNameInput;
  private EditText mPacketMinimumQuantityInput;
  private EditText mPacketPriceInput;
  private LinearLayout mPacketContentsLayout;
  private FloatingActionButton mUpdatePacketButton;
  private FloatingActionButton mMinusContentButton;
  private FloatingActionButton mPlusContentButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_packet);
  
    Components.getSessionComponent().inject(this);
  
    mCateringViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CateringViewModel.class);
  
    mPacket = (Packet) getIntent().getSerializableExtra("packet");
  
    mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
    mPacketNameInput = findViewById(R.id.packetNameEditText);
    mPacketMinimumQuantityInput = findViewById(R.id.minimumQuantityEditText);
    mPacketPriceInput = findViewById(R.id.priceEditText);
    mPacketContentsLayout = findViewById(R.id.packetContentsLayout);
    mUpdatePacketButton = findViewById(R.id.renewButton);
    mMinusContentButton = findViewById(R.id.minusButton);
    mPlusContentButton = findViewById(R.id.plusButton);
    
    mMinusContentButton.setOnClickListener(view -> {
      if(mPacketContentsLayout.getChildCount() != 0){
        mPacketContentsLayout.removeViewAt(mPacketContentsLayout.getChildCount()-1);
      }
    
      showResponse("Jumlah makanan/minuman: "+mPacketContentsLayout.getChildCount());
    });
  
    mPlusContentButton.setOnClickListener(view -> {
      View layoutView = getLayoutInflater().inflate(R.layout.itemview_packet_content, null, false);
      mPacketContentsLayout.addView(layoutView);
    
      showResponse("Jumlah makanan/minuman: "+mPacketContentsLayout.getChildCount());
    });
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    
    mPacketNameInput.setText(mPacket.getName());
    mPacketMinimumQuantityInput.setText(Integer.toString(mPacket.getMinimum_quantity()));
    mPacketPriceInput.setText(Integer.toString(mPacket.getPrice()));
    
    for(int i = 0; i < mPacket.getContents().size(); i++){
      View layoutView = getLayoutInflater().inflate(R.layout.itemview_packet_content, null, false);
      EditText contentInput = layoutView.findViewById(R.id.packetContentEditText);
      
      contentInput.setText(mPacket.getContents().get(i));
      
      mPacketContentsLayout.addView(layoutView);
    }
  }
  
  public void showResponse(String response){
    Snackbar.make(mCoordinatorLayout, response, Snackbar.LENGTH_SHORT).show();
  }
}
