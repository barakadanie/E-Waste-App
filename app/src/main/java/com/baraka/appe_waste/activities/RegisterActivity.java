package com.baraka.appe_waste.activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baraka.appe_waste.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,confirm,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fStore;
    DatabaseReference ref;
    String userID;
    private CircleImageView image;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        image=findViewById(R.id.profile_image);
        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn=findViewById(R.id.register);
        mLoginBtn = findViewById(R.id.login);
        confirm=findViewById(R.id.confirmPassword);
        fAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() !=null){
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            //finish();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                String password2 = confirm.getText().toString();
                String name = mFullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >=6 Characters");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            String error=task.getException().toString();
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String currentUserId=fAuth.getCurrentUser().getUid();
                            String dat="user";
                            ref=FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                            HashMap<String, Object> user = new HashMap<>();
                            user.put("accType",dat);
                            user.put("name", name);
                            user.put("email", email);
                            user.put("phone", phone);
                            ref.updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                }
                            });
                            if (resultUri!=null)
                            {
                                final StorageReference filePath= FirebaseStorage.getInstance().getReference().child("profile images").child(currentUserId);
                                Bitmap bitmap=null;
                                try {
                                    bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
                                byte[] data=byteArrayOutputStream.toByteArray();
                                UploadTask uploadTask=filePath.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "image uplad failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if (taskSnapshot.getMetadata()!=null&& taskSnapshot.getMetadata().getReference()!=null)
                                        {
                                            Task<Uri>  result=taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(uri -> {
                                                String imageUrl = uri.toString();
                                                Map newImageMap = new HashMap();
                                                newImageMap.put("profilepicUrl", imageUrl);
                                                ref.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegisterActivity.this, "image succesfully added", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                                finish();
                                            });
                                        }
                                    }
                                });

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==RESULT_OK&& data!=null)
        {
            resultUri=data.getData();
            image.setImageURI(resultUri);
        }
    }
}