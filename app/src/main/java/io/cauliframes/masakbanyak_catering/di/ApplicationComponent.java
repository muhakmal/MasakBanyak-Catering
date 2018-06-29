package io.cauliframes.masakbanyak_catering.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import io.cauliframes.masakbanyak_catering.ui.activity.LoginActivity;
import io.cauliframes.masakbanyak_catering.MasakBanyakCateringApplication;
import io.cauliframes.masakbanyak_catering.ui.activity.RegisterActivity;
import io.cauliframes.masakbanyak_catering.webservice.MasakBanyakWebService;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, StorageModule.class, NetworkModule.class})
public interface ApplicationComponent {
  void inject(MasakBanyakCateringApplication application);
  
  void inject(LoginActivity activity);
  
  void inject(RegisterActivity activity);
  
  Context applicationContext();
  
  SharedPreferences.Editor preferencesEditor();
  
  SharedPreferences preferences();
  
  Retrofit retrofit();
  
  MasakBanyakWebService webService();
}
