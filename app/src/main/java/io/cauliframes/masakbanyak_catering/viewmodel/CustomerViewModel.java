package io.cauliframes.masakbanyak_catering.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.cauliframes.masakbanyak_catering.model.Customer;
import io.cauliframes.masakbanyak_catering.repository.CustomerRepository;
import io.cauliframes.masakbanyak_catering.util.Util;

public class CustomerViewModel extends ViewModel {
  private CustomerRepository repository;
  
  private LiveData<Customer> customerLiveData;
  private LiveData<Util.Event<String>> notificationEventLiveData;
  
  @Inject public CustomerViewModel(CustomerRepository repository) {
    this.repository = repository;
    this.customerLiveData = repository.getCustomerLiveData();
    this.notificationEventLiveData = repository.getNotificationEventLiveData();
  }
  
  public LiveData<Customer> getCustomerLiveData() {
    return customerLiveData;
  }
  
  public LiveData<Util.Event<String>> getNotificationEventLiveData() {
    return notificationEventLiveData;
  }
  
  public void refreshCustomer(){
    repository.refreshCustomer();
  }
  
  public void uploadProfileAvatar(Customer customer, String filename, byte[] file){
    repository.uploadCustomerAvatar(customer, filename, file);
  }
  
  public void updateProfile(Customer customer){
    repository.updateCustomer(customer);
  }
  
  public void logout(){
    repository.logout();
  }
}