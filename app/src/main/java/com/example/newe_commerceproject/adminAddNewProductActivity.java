package com.example.newe_commerceproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class adminAddNewProductActivity extends AppCompatActivity {

    private String categoryName,description,Pprice, PName,savCurrentDate, saveCurrentTime;
    private Button addNewProductButton;
    private EditText productName,productDes,productPrice;
    private ImageView inputProductImgae;
    private static final int galleryPick = 1;
    private Uri imageUri;
    private String productRendomKey,downloadImageUrl;
    private ProgressDialog lodingBar;

    private StorageReference produtcImageRef;
    private DatabaseReference productRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        categoryName = getIntent().getExtras().get("category").toString();
        produtcImageRef = FirebaseStorage.getInstance().getReference().child("product image");
        productRef = FirebaseDatabase.getInstance().getReference().child("products");


        productName = findViewById(R.id.product_name);
        productDes = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        inputProductImgae = findViewById(R.id.select_product_image);
        addNewProductButton = findViewById(R.id.productAddButton);
        lodingBar = new ProgressDialog(this);


        inputProductImgae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
            inputProductImgae.setImageURI(imageUri);

        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);
    }

    private void ValidateProductData() {

        PName = productName.getText().toString();
        description = productDes.getText().toString();
        Pprice = productPrice.getText().toString();

        if (imageUri == null)
        {
            Toast.makeText(this, "product image is mandatory..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PName))
        {
            productName.setError("Enter your Product Name");
        }
        else if (TextUtils.isEmpty(description))
        {
            productName.setError("Enter your Product Description");
        }
        else if (TextUtils.isEmpty(Pprice))
        {
            productName.setError("Enter your Product Price");
        }
        else {
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        lodingBar.setTitle("Adding product");
        lodingBar.setMessage("Dear Admin please wite we are adding new product..");
        lodingBar.setCanceledOnTouchOutside(false);
        lodingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        savCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRendomKey = savCurrentDate + saveCurrentTime;
        StorageReference filePath = produtcImageRef.child(imageUri.getLastPathSegment() + productRendomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                final String message = e.toString();
                Toast.makeText(adminAddNewProductActivity.this, "Error :"+message,Toast.LENGTH_SHORT).show();
                lodingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl =filePath.getDownloadUrl().toString();
                     return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(adminAddNewProductActivity.this, "got the product url successfull...",Toast.LENGTH_SHORT).show();
                            saveProductInfoIoDatebase();
                        }
                    }
                });
            }
        });
    }

    private void saveProductInfoIoDatebase() {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pId", productRendomKey);
        hashMap.put("date", savCurrentDate);
        hashMap.put("time", saveCurrentTime);
        hashMap.put("description", description);
        hashMap.put("category", categoryName);
        hashMap.put("productName", PName);
        hashMap.put("productPrice", Pprice);
        hashMap.put("image", downloadImageUrl);

        productRef.child(productRendomKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {

                    Intent intent = new Intent(adminAddNewProductActivity.this, AdminCetagori.class);
                    startActivity(intent);
                    lodingBar.dismiss();
                    Toast.makeText(adminAddNewProductActivity.this, "Product is added successfully..",Toast.LENGTH_SHORT).show();
                    
                }
                else {
                    lodingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(adminAddNewProductActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}