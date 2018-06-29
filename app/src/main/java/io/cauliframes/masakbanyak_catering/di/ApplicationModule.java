package io.cauliframes.masakbanyak_catering.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.cauliframes.masakbanyak_catering.MasakBanyakCateringApplication;

@Module
public class ApplicationModule {
  private MasakBanyakCateringApplication application;
  
  public ApplicationModule(MasakBanyakCateringApplication application) {
    this.application = application;
  }
  
  @Provides
  @Singleton
  public Context provideApplicationContext(){
    return application;
  }
}
