package io.cauliframes.masakbanyak_catering.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import java.io.IOException;

import javax.inject.Inject;

import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.di.Components;
import io.cauliframes.masakbanyak_catering.webservice.MasakBanyakWebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
  @Inject
  SharedPreferences mPreferences;
  @Inject
  MasakBanyakWebService mWebService;
  
  private CoordinatorLayout mCoordinatorLayout;
  private EditText mEmailInput;
  private EditText mPasswordInput;
  private Button mLoginButton;
  private FloatingActionButton mToRegisterButton;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    
    Components.getApplicationComponent().inject(this);
  
    if (mPreferences.contains("access_token") && mPreferences.contains("refresh_token")) {
      Intent mainIntent = new Intent(this, MainActivity.class);
      startActivity(mainIntent);
      finish();
    }
    
    Toolbar toolbar = findViewById(R.id.toolbar);
    mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
    mEmailInput = findViewById(R.id.emailEditText);
    mPasswordInput = findViewById(R.id.passwordEditText);
    mLoginButton = findViewById(R.id.loginButton);
    mToRegisterButton = findViewById(R.id.fab);
    
    setSupportActionBar(toolbar);
    
    mLoginButton.setOnClickListener(view -> {
      String email = mEmailInput.getText().toString();
      String password = mPasswordInput.getText().toString();
      
      mWebService.login(email, password).enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          if (response.isSuccessful()) {
            SharedPreferences.Editor editor = mPreferences.edit();
  
            editor.putString("access_token", response.body().get("access_token").getAsString());
            editor.putString("refresh_token", response.body().get("refresh_token").getAsString());
            editor.apply();
  
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
          } else {
            try {
              showResponse(response.errorBody().string());
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
        
        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
          showResponse(t.toString());
        }
      });
    });
    
    mToRegisterButton.setOnClickListener(view -> Snackbar.make(view, "Klik disini untuk mendaftar.", Snackbar.LENGTH_LONG)
        .setAction("Register", view1 -> {
          Intent intent = new Intent(this, RegisterActivity.class);
          startActivity(intent);
        }).show());
  }
  
  private void showResponse(String response) {
    Snackbar.make(mCoordinatorLayout, response, Snackbar.LENGTH_SHORT).show();
  }
}
