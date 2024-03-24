package com.example.qlkh.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.qlkh.R;
import com.example.qlkh.adapter.ProductAdapter;
import com.example.qlkh.entity.Order;
import com.example.qlkh.entity.Product;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private ProductAdapter mProductAdapter;
    private List<Product> mListProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_logout = findViewById(R.id.button_exit);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        Button button_add = findViewById(R.id.btn_add_order);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference Product_reference = FirebaseDatabase.getInstance().getReference().child("Product");
                Product_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Product product = snapshot.getValue(Product.class);
                            Intent intent = new Intent(ProductActivity.this, OrderActivity.class);
                            intent.putExtra("product", product);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        initUi();
        getListProductFromDB();
    }
    private void initUi() {
        rcvProduct = findViewById(R.id.rcv_exercise);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(dividerItemDecoration);
        mListProduct = new ArrayList<>();
        mProductAdapter = new ProductAdapter(mListProduct, new ProductAdapter.IClickListener() {
            @Override
            public void onClickAddItem(Product product) {
                addProductToOrder(product);

            }
        });
        rcvProduct.setAdapter(mProductAdapter);
    }

    private void getListProductFromDB() {
        //khai bao csdl
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // chỉ tới nhánh product trong firebase
        DatabaseReference myRef = database.getReference("Product");
        // bắt sự kiện nhánh con trong product thay đổi
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // khai báo một biến kiểu Prodcut, sau đó truyền vào dữ liệu đã lấy đc trên firebase
                Product product = snapshot.getValue(Product.class);
                // kiểm tra nếu biên đo không rỗng
                if (product != null) {
                    // thêm vào 1 danh sách kiể product
                    mListProduct.add(product);
                    // thay đổi hoặc thêm vào rycleview
                    mProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void addProductToOrder(Product product) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Order").push();
        productRef.setValue(product);
        Toast.makeText(ProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
    }

}