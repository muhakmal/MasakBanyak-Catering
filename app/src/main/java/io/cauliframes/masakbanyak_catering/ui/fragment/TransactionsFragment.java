package io.cauliframes.masakbanyak_catering.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.di.Components;
import io.cauliframes.masakbanyak_catering.model.Order;
import io.cauliframes.masakbanyak_catering.ui.adapter.OrdersAdapter;
import io.cauliframes.masakbanyak_catering.viewmodel.OrderViewModel;
import io.cauliframes.masakbanyak_catering.viewmodel.ViewModelFactory;

public class TransactionsFragment extends Fragment {
  
  @Inject
  ViewModelFactory mViewModelFactory;
  
  private OrderViewModel mOrderViewModel;
  
  private ArrayList<Order> mOrders;
  
  private SwipeRefreshLayout mRefreshLayout;
  private RecyclerView mRecyclerView;
  
  private OrdersAdapter mAdapter;
  
  private OnFragmentInteractionListener mListener;
  
  public TransactionsFragment() {
  
  }
  
  public static TransactionsFragment newInstance() {
    TransactionsFragment fragment = new TransactionsFragment();
    return fragment;
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  
    Components.getSessionComponent().inject(this);
    
    mOrderViewModel = ViewModelProviders.of(this, mViewModelFactory).get(OrderViewModel.class);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_transactions, container, false);
    
    mRefreshLayout = view.findViewById(R.id.refreshLayout);
    mRecyclerView = view.findViewById(R.id.recyclerView);
    
    mAdapter = new OrdersAdapter(mListener);
    
    return view;
  }
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    mRefreshLayout.setRefreshing(true);
    mRefreshLayout.setOnRefreshListener(mOrderViewModel::refreshOrders);
    
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mRecyclerView.setAdapter(mAdapter);
  }
  
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    mOrderViewModel.getOrdersLiveData().observe(this, orders -> {
      mOrders = orders;
      mAdapter.setOrders(mOrders);
      
      mRefreshLayout.setRefreshing(false);
    });
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    
    mOrderViewModel.getOrdersLiveData().removeObservers(this);
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
    
    void onOrderClick(Order order);
  }
}
