package com.example.vukhachoi.chat2017;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class sign_in extends AppCompatActivity  {
    EditText edtEmail,edtPass,edtUserName;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtEmail=findViewById(R.id.edtEmail1);
        edtPass=findViewById(R.id.edtPass1);
        btnLogin=findViewById(R.id.btnLogin1);
        edtUserName=findViewById(R.id.edtUserName1);

        mAuth = FirebaseAuth.getInstance();
        addEvent();

    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dangky();

            }
        });
    }


    private void Dangky()
    {
        if (!validateForm()) {

            return;

        }
        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            DangNhap();
                        }
                        else
                        {
                            Toast.makeText(sign_in.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }
    private void DangNhap()
    {
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                         final   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(edtUserName.getText().toString())

                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent=new Intent();
                                                intent.putExtra("email",edtEmail.getText().toString());
                                                intent.putExtra("pass",edtPass.getText().toString());
                                                setResult(RESULT_OK,intent);
                                                String email=edtEmail.getText().toString();
                                                email=email.replaceAll("[.]","");
                                                Date currentTime = java.util.Calendar.getInstance().getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSSZ");
                                                mDatabase = FirebaseDatabase.getInstance().getReference();
//                                                mDatabase.child(email).child("Chat").child(sdf.format(currentTime).toString()).setValue(edtPass.getText().toString());
//                                                mDatabase.child(email).child("Friend").setValue(edtPass.getText().toString());
//                                                mDatabase.child(email).child("Mylocation").setValue(edtPass.getText().toString());
                                                mDatabase.child(email).child("Setting").child("Email").setValue(edtEmail.getText().toString());
                                                mDatabase.child(email).child("Setting").child("UserName").setValue(edtUserName.getText().toString());
                                             if(user.getPhotoUrl()!=null)
                                                mDatabase.child(email).child("Setting").child("avatar").setValue(user.getPhotoUrl().toString());

                                                mDatabase.child(email).child("Setting").child("Status").setValue("https://eyotdofe.files.wordpress.com/2016/09/red-dot.png?w=228");

                                                finish();



                                            }

                                        }
                                    });
                        }

                    }
                });

    }

    private boolean validateForm() {

        boolean valid = true;



        String email = edtEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {

            edtEmail.setError("Required.");

            valid = false;

        } else {

            edtEmail.setError(null);

        }
        String userName=edtUserName.getText().toString();
        if (TextUtils.isEmpty(userName)) {

            edtUserName.setError("Required.");

            valid = false;

        } else {

            edtUserName.setError(null);

        }


        String password = edtPass.getText().toString();

        if (TextUtils.isEmpty(password)) {

            edtPass.setError("Required.");

            valid = false;

        } else {

            edtPass.setError(null);

        }



        return valid;

    }

}
