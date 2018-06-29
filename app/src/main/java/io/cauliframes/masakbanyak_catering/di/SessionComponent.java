package io.cauliframes.masakbanyak_catering.di;

import dagger.Component;
import io.cauliframes.masakbanyak_catering.ui.activity.AddPacketActivity;
import io.cauliframes.masakbanyak_catering.ui.activity.EditPacketActivity;
import io.cauliframes.masakbanyak_catering.ui.activity.MainActivity;
import io.cauliframes.masakbanyak_catering.ui.activity.TransactionActivity;
import io.cauliframes.masakbanyak_catering.ui.fragment.PacketsFragment;
import io.cauliframes.masakbanyak_catering.ui.fragment.ProfileFragment;
import io.cauliframes.masakbanyak_catering.ui.fragment.TransactionsFragment;

@SessionScope
@Component(modules = {SessionModule.class, ViewModelModule.class}, dependencies = {ApplicationComponent.class})
public interface SessionComponent {
  void inject(MainActivity activity);
  
  void inject(AddPacketActivity activity);
  
  void inject(EditPacketActivity activity);
  
  void inject(TransactionActivity activity);
  
  void inject(PacketsFragment fragment);
  
  void inject(TransactionsFragment fragment);
  
  void inject(ProfileFragment fragment);
}
