package com.example.vukhachoi.chat2017;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AddStatus extends AppCompatActivity {
    private DatabaseReference mDatabase;
    FirebaseUser user;
    Button btnchonhinh,btnDang;
    EditText edtInput;
    Spinner spnStatus;
    ImageView imgtest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnchonhinh = findViewById(R.id.btnchonhinh);
        btnDang = findViewById(R.id.btnDang);

        edtInput = findViewById(R.id.edtInput);
        imgtest = findViewById(R.id.imgtest);
        spnStatus = findViewById(R.id.spnStatus);

        AddEvent();

    }






    private void AddEvent() {

        btnchonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        btnDang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
try {

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Bitmap bitmap = ((BitmapDrawable) imgtest.getDrawable()).getBitmap();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
    byte[] byteArray = byteArrayOutputStream.toByteArray();
    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
    String email=user.getEmail().toString();
    email=email.replaceAll("[.]","");
    mDatabase.child(email).child("Status").child("hinhanh").setValue(encoded);
    mDatabase.child(email).child("Status").child("regime").setValue(spnStatus.getSelectedItem().toString());
    mDatabase.child(email).child("Status").child("status").setValue(edtInput.getText().toString());
    mDatabase.child("Status").child(email).child("hinhanh").setValue(encoded);
    mDatabase.child("Status").child(email).child("regime").setValue(spnStatus.getSelectedItem().toString());
    mDatabase.child("Status").child(email).child("status").setValue(edtInput.getText().toString());
    mDatabase.child("Status").child(email).child("UserName").setValue(user.getDisplayName().toString());
    mDatabase.child("Status").child(email).child("avatar").setValue(user.getPhotoUrl().toString());
   finish();

}catch (Exception e)
{

}



            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imgtest.setVisibility(View.VISIBLE);
                imgtest.setImageURI(data.getData());
                return;
            }


        }
    }
}

