package com.baraka.appe_waste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private androidx.appcompat.widget.Toolbar toolBar;
    private NavigationView navView;
    private CircleImageView nav_profile_image;
    ViewPager viewPager;
    AdapterPage adapter;
    TabLayout tabLayout;
    private TextView fullname,navemail,type;
    private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerlayout);
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);
        adapter=new AdapterPage(getSupportFragmentManager());
        adapter.add();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        toolBar=findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("E-Waste Collection App");
        navView=findViewById(R.id.navigationview);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        nav_profile_image=navView.getHeaderView(0).findViewById(R.id.nav_user_image);
        fullname=navView.getHeaderView(0).findViewById(R.id.nav_user_fulname);
        navemail=navView.getHeaderView(0).findViewById(R.id.nav_user_email);

        userRef= FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String name=snapshot.child("name").getValue().toString();
                    fullname.setText(name);
                    String email=snapshot.child("email").getValue().toString();
                    navemail.setText(email);
                    String imageUrl=snapshot.child("profilepicUrl").getValue().toString();
                    Glide.with(getApplicationContext()).load(imageUrl).into(nav_profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.profile:
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.donate:
                Intent intent2=new Intent(MainActivity.this,DonateActivity.class);
                startActivity(intent2);
                break;
            case R.id.collection:
                Intent intent3=new Intent(MainActivity.this,EMapsActivity.class);
                startActivity(intent3);
                break;
            case R.id.history:
                Intent intent4=new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent4);
                break;
            case R.id.about:
                Intent intent5=new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent5);
                break;
            case R.id.contact:
                Intent intent6=new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent6);
                break;
            case R.id.sendemail:
                Intent intent7=new Intent(MainActivity.this,Receive.class);
                startActivity(intent7);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent8 = new Intent(MainActivity.this, LoginActivity.class);
                intent8.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent8);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}