package com.baraka.appe_waste.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baraka.appe_waste.R;
import com.baraka.appe_waste.admin.AdminMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mRegisterBtn,forgot;
    FirebaseAuth fAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.register);
        mLoginBtn = findViewById(R.id.login);
        forgot=findViewById(R.id.forgotPass);
        fAuth=FirebaseAuth.getInstance();
        //getSupportActionBar().hide();
        ref = FirebaseDatabase.getInstance().getReference("users");

        if(fAuth.getCurrentUser() !=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is Required.");

                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is Required.");

                }

                if(password.length() < 6)
                {
                    mPassword.setError("Password Must be >=6 Characters");
                }

                else {
                    //binding.loginProgressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Successfully login",Toast.LENGTH_SHORT).show();
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot i : snapshot.getChildren()){
                                            User user = i.getValue(User.class);
                                            assert user != null;
                                            Log.d(TAG, "onDataChange: "+user.getAccType());
                                            if (email.equals(user.getEmail())){
                                                if (user.getAccType().equals("admin"))
                                                {
                                                    Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else if (user.getAccType().equals("user"))
                                                {
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(),"An error : "+error.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                //binding.loginProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}