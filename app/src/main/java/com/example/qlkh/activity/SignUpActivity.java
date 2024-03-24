package com.example.qlkh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlkh.R;
import com.example.qlkh.entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;


public class SignUpActivity extends AppCompatActivity {
    User user;
    EditText name;
    EditText password;
    EditText staffName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accountRef = database.getReference("Account");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SignUpActivity.isOpen = true;
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        staffName = findViewById(R.id.staffname1);
    }

    public void onButtonSignUp2Click(View view) {
        DatabaseReference newAccountRef = accountRef.push();
        String myname = name.getText().toString();
        String mypass = password.getText().toString();
        String staffname = staffName.getText().toString();

        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        String createDate = String.valueOf(day + "-" +month + "-" +year);

        User createUser = new User(myname,mypass,staffname,createDate);
        newAccountRef.setValue(createUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Lỗi đăng ký tài khoản: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onButtonLogIn2Click(View view){
        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOpen = false;
    }
}
