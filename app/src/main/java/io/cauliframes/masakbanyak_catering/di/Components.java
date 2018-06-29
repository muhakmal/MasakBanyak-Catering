package io.cauliframes.masakbanyak_catering.di;

public class Components {
  private static ApplicationComponent applicationComponent;
  private static SessionComponent sessionComponent;
  
  public static ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
  
  public static void setApplicationComponent(ApplicationComponent applicationComponent) {
    Components.applicationComponent = applicationComponent;
  }
  
  public static SessionComponent getSessionComponent() {
    return sessionComponent;
  }
  
  public static void setSessionComponent(SessionComponent sessionComponent) {
    Components.sessionComponent = sessionComponent;
  }
}
