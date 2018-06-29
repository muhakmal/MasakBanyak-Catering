package io.cauliframes.masakbanyak_catering.di;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.cauliframes.masakbanyak_catering.viewmodel.CateringViewModel;
import io.cauliframes.masakbanyak_catering.viewmodel.CustomerViewModel;
import io.cauliframes.masakbanyak_catering.viewmodel.OrderViewModel;

@Module
public abstract class ViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(CateringViewModel.class)
  public abstract ViewModel cateringViewModel(CateringViewModel cateringViewModel);
  
  @Binds
  @IntoMap
  @ViewModelKey(OrderViewModel.class)
  public abstract ViewModel transactionViewModel(OrderViewModel orderViewModel);
  
  @Binds
  @IntoMap
  @ViewModelKey(CustomerViewModel.class)
  public abstract ViewModel profileViewModel(CustomerViewModel customerViewModel);
  
  //The annotation to define the type of key used in the map.
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.CLASS)
  @MapKey
  @interface ViewModelKey{
    Class<? extends ViewModel> value();
  }
}