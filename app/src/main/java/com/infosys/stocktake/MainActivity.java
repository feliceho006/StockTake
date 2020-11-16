package com.infosys.stocktake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.infosys.stocktake.auth.GoogleLoginActivity;
import com.infosys.stocktake.auth.LoginActivity;
import com.infosys.stocktake.inventory.InventoryActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button testingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Check if user is logged in
                if(firebaseAuth.getCurrentUser() != null) {
                    Intent loginIntent = new Intent(MainActivity.this, GoogleLoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // prevent user from accessing previous activity
                    startActivity(loginIntent);
                }
            }
        };

        testingBtn = findViewById(R.id.testingBtn);
        testingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add the listener to auth instance
        mAuth.addAuthStateListener(mAuthListener);
    }
}