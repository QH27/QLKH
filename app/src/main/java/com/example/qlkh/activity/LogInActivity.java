package com.example.qlkh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlkh.R;
import com.example.qlkh.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    public static boolean isOpen = false;
    User user;
    EditText name;
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
    }

    public void onButtonLogIn1Click(View view) {
        String myname = name.getText().toString();
        String mypass = password.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference accountRef = database.getReference("Account");
        accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isLoggedIn = false;

                for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                    String accountUsername = accountSnapshot.child("username").getValue(String.class);
                    String accountPassword = accountSnapshot.child("password").getValue(String.class);

                    if (accountUsername.equals(myname) && accountPassword.equals(mypass)) {
                        isLoggedIn = true;
                        break;
                    }
                }
                if (isLoggedIn) {
                    Intent intent = new Intent(LogInActivity.this, ProductActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LogInActivity.this, "Lỗi truy cập cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonSignUp1Click(View view){
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        isOpen = false;
    }
}
