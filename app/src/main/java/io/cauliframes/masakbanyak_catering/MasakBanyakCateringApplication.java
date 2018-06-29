package io.cauliframes.masakbanyak_catering;

import android.app.Application;

import io.cauliframes.masakbanyak_catering.di.ApplicationComponent;
import io.cauliframes.masakbanyak_catering.di.ApplicationModule;
import io.cauliframes.masakbanyak_catering.di.Components;
import io.cauliframes.masakbanyak_catering.di.DaggerApplicationComponent;
import io.cauliframes.masakbanyak_catering.di.NetworkModule;
import io.cauliframes.masakbanyak_catering.di.StorageModule;

public class MasakBanyakCateringApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    
    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .storageModule(new StorageModule())
        .networkModule(new NetworkModule())
        .build();
  
    Components.setApplicationComponent(applicationComponent);
  }
  
}
