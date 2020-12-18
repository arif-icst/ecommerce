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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButtonButton;
    private EditText inputName,inputPhoneNumber,inputPassword;
    private ProgressDialog lodingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButtonButton = findViewById(R.id.register_btn);
        inputName = findViewById(R.id.register_user_name);
        inputPhoneNumber = findViewById(R.id.register_login_phone_number_input);
        inputPassword = findViewById(R.id.register_login_password_input);
        lodingBar = new ProgressDialog(this);

        createAccountButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString();
                final String phone = inputPhoneNumber.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(name))
                {
                    inputName.setError("Enter your Name");
                }
              else   if (TextUtils.isEmpty(phone))
                {
                    inputName.setError("Enter your Phone Number");
                }
                else if (TextUtils.isEmpty(password))
                {
                    inputName.setError("Enter your Password");
                }


                // Creating Check Creadintial Dialog Box
                else {

                    lodingBar.setTitle("Create Account");
                    lodingBar.setMessage("Please wite While we checking Creadintial");
                    lodingBar.setCanceledOnTouchOutside(false);
                    lodingBar.show();


                    //this method adding name,phone,passowrd and check Validity
                    validatePhoneNumger(name, phone,password);
                }
            }
        });
    }

    private void  validatePhoneNumger(String name, String phone, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("NewUsers").child(phone).exists()))
                {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name", name);

                    RootRef.child("NewUsers").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Successfull", Toast.LENGTH_SHORT).show();
                                        lodingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        lodingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network error. Please try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                // if all ready create account so throw the message
                else {
                    Toast.makeText(RegisterActivity.this,"This"+ phone +"alreaddy exits",Toast.LENGTH_SHORT).show();
                    lodingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number.",Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}