package io.cauliframes.masakbanyak_catering.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.cauliframes.masakbanyak_catering.model.Order;
import io.cauliframes.masakbanyak_catering.repository.OrderRepository;

public class OrderViewModel extends ViewModel {
  private OrderRepository repository;
  
  private LiveData<ArrayList<Order>> ordersLiveData;
  
  @Inject
  public OrderViewModel(OrderRepository repository) {
    this.repository = repository;
    this.ordersLiveData = repository.getOrdersLiveData();
  }
  
  public LiveData<ArrayList<Order>> getOrdersLiveData() {
    return ordersLiveData;
  }
  
  public void refreshOrders(){
    repository.refreshOrders();
  }
}