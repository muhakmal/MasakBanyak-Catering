package io.cauliframes.masakbanyak_catering.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.di.Components;
import io.cauliframes.masakbanyak_catering.model.Catering;
import io.cauliframes.masakbanyak_catering.viewmodel.CateringViewModel;
import io.cauliframes.masakbanyak_catering.viewmodel.ViewModelFactory;
import me.itangqi.waveloadingview.WaveLoadingView;

import static io.cauliframes.masakbanyak_catering.Constants.MASAKBANYAK_URL;

public class ProfileFragment extends Fragment {
  
  @Inject
  ViewModelFactory mViewModelFactory;
  
  private CateringViewModel mCateringViewModel;
  
  private Catering mCatering;
  
  private CoordinatorLayout mCoordinatorLayout;
  private ConstraintLayout mParentLayout;
  private SwipeRefreshLayout mRefreshLayout;
  private CircleImageView mCateringAvatarImage;
  private WaveLoadingView mCateringRating;
  private EditText mCateringNameInput;
  private EditText mCateringAddressInput;
  private EditText mCateringPhoneInput;
  private TextView mCateringEmailText;
  private Button mUpdateCateringButton;
  private Button mLogoutButton;
  
  private OnFragmentInteractionListener mListener;
  
  public ProfileFragment() {
  
  }
  
  public static ProfileFragment newInstance() {
    ProfileFragment fragment = new ProfileFragment();
    return fragment;
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    Components.getSessionComponent().inject(this);
    
    mCateringViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CateringViewModel.class);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    
    mCoordinatorLayout = getActivity().findViewById(R.id.coordinatorLayout);
    mParentLayout = view.findViewById(R.id.constraintLayout);
    mRefreshLayout = view.findViewById(R.id.refreshLayout);
    mCateringAvatarImage = view.findViewById(R.id.cateringAvatarImageView);
    mCateringNameInput = view.findViewById(R.id.cateringName);
    mCateringAddressInput = view.findViewById(R.id.cateringAddress);
    mCateringPhoneInput = view.findViewById(R.id.cateringPhoneNumber);
    mCateringEmailText = view.findViewById(R.id.cateringEmail);
    mCateringRating = view.findViewById(R.id.cateringRating);
    mUpdateCateringButton = view.findViewById(R.id.updateCateringButton);
    mLogoutButton = view.findViewById(R.id.logoutButton);
    
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  
    mLogoutButton.setOnClickListener(view1 -> mCateringViewModel.logout(mCatering));
    
    mRefreshLayout.setRefreshing(true);
    mRefreshLayout.setOnRefreshListener(mCateringViewModel::refreshCatering);
  }
  
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    mCateringViewModel.getCateringLiveData().observe(this, catering -> {
      mCatering = catering;
      
      Picasso.get().load(MASAKBANYAK_URL + mCatering.getAvatar()).fit().centerCrop().into(mCateringAvatarImage);
  
      mCateringRating.setCenterTitle(Double.toString(mCatering.getTotalRating()));
      if(mCatering.getTotalRating() == 0){
        mCateringRating.setProgressValue(8);
        mCateringRating.setAmplitudeRatio(8);
      }else{
        mCateringRating.setProgressValue((int) mCatering.getTotalRating()*100/5-9);
        mCateringRating.setAmplitudeRatio((int) mCatering.getTotalRating()*100/5-9);
      }
      
      mCateringNameInput.setText(catering.getName());
      mCateringAddressInput.setText(catering.getAddress());
      mCateringPhoneInput.setText(catering.getPhone());
      mCateringEmailText.setText(catering.getEmail());
      
      mRefreshLayout.setRefreshing(false);
    });
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    
    mCateringViewModel.getCateringLiveData().removeObservers(this);
  }
  
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }
  
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
  }
  
  
}
