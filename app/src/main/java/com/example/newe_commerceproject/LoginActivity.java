package com.example.newe_commerceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newe_commerceproject.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText inputPhoneNumber, inputPassword;
    private ProgressDialog lodingBar;
    private String parentDbName ="NewUsers";
    private TextView adminLink,notAdminLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       lodingBar = new ProgressDialog(this);
        loginButton = findViewById(R.id.login_btn);
        inputPhoneNumber = findViewById(R.id.login_phone_number_input);
        inputPassword = findViewById(R.id.login_password_input);

        adminLink = findViewById(R.id.admin_penal_link);
        notAdminLink = findViewById(R.id.not_admin_penal_link);


        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Logmin Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "NewUsers";
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = inputPhoneNumber.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(phone))
                {
                    inputPhoneNumber.setError("Enter your Valid Phone Number");
                }
             else if (TextUtils.isEmpty(password))
                {
                    inputPassword.setError("Enter your valid password");
                }

             else {
                    lodingBar.setTitle("Login account");
                    lodingBar.setMessage("Please wite While we checking Creadintial");
                    lodingBar.setCanceledOnTouchOutside(false);
                    lodingBar.show();
                    loginAccount(phone,password);
                }
            }
        });


    }



    private void loginAccount(String phone, String password) {

        DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()){

                    final Users userData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                  if (userData.getPhone().equals(phone)){
                      if (userData.getPassword().equals(password)){
                          if (parentDbName.equals("Admins"))
                          {
                              Toast.makeText(LoginActivity.this, "Loggin successfully",Toast.LENGTH_SHORT).show();
                              lodingBar.dismiss();

                              Intent intent =  new Intent(LoginActivity.this, AdminCetagori.class);
                              startActivity(intent);
                          }
                          else
                              if (parentDbName.equals("NewUsers"))
                              {
                                  Toast.makeText(LoginActivity.this, "Loggin successfully",Toast.LENGTH_SHORT).show();
                                  lodingBar.dismiss();

                                  Intent intent =  new Intent(LoginActivity.this, HomeActivity.class);
                                  startActivity(intent);
                              }

                      }
                      else {

                          Toast.makeText(LoginActivity.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
                          lodingBar.dismiss();
                      }
                  }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Account with this"+phone+"is not match", Toast.LENGTH_SHORT).show();
                   lodingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}