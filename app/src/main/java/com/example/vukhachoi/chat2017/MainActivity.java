package com.example.vukhachoi.chat2017;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener
{

    private DatabaseReference mDatabase;
    EditText edtEmail,edtPass;
    TextView txtforgot;
    Button btnLogin,btngetUser,btngetSignUp;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    @Override
    public void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UI accordingly.
//FirebaseUser currentUser = mAuth.getCurrentUser();
//updateUI(currentUser);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");








        txtforgot=findViewById(R.id.txtforgot);
        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "dm", Toast.LENGTH_SHORT).show();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)

                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)

                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)

                .build();
        mAuth = FirebaseAuth.getInstance();
        btngetUser=findViewById(R.id.btngetUser);
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        btnLogin=findViewById(R.id.btnLogin);
        btngetSignUp=findViewById(R.id.btngetSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();

            }
        });
        btngetSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,sign_in.class);
                startActivityForResult(intent,1);
            }
        });

        btngetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });




        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("Lớp").setValue("KTPM 2015");
//        mDatabase.child("trường").setValue("UIT", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                // trang thai put
//            }
//        });
//        mDatabase.child("Lớp").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//// change data
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//



    }
    private void DangNhap()
    {

        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser  user = FirebaseAuth.getInstance().getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            String email=user.getEmail().toString();
                            email=email.replaceAll("[.]","");
                            mDatabase.child(email).child("Setting").child("Status").setValue("https://vignette1.wikia.nocookie.net/animal-jam-clans-1/images/1/10/Neon-green-dot-md.png/revision/latest?cb=20160630184443");

                            Intent  intent1=new Intent(MainActivity.this,InfoUser.class);
                            startActivity(intent1);


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 11) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);


            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
         if(resultCode==RESULT_OK&&requestCode==1)
        {
            edtEmail.setText(data.getStringExtra("email"));
            edtPass.setText(data.getStringExtra("pass"));

        }
    }
    private void signIn() {

        signOut();


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 11);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("xxxx", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email=user.getEmail().toString();
                            email=email.replaceAll("[.]","");
//                                                Date currentTime = java.util.Calendar.getInstance().getTime();
//                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSSZ");
                                                mDatabase = FirebaseDatabase.getInstance().getReference();
//                                                mDatabase.child(email).child("Chat").child(sdf.format(currentTime).toString()).setValue(edtPass.getText().toString());
//                                                mDatabase.child(email).child("Friend").setValue(edtPass.getText().toString());
//                                                mDatabase.child(email).child("Mylocation").setValue(edtPass.getText().toString());

                            mDatabase.child(email).child("Setting").child("Email").setValue(user.getEmail().toString());
                            mDatabase.child(email).child("Setting").child("UserName").setValue(user.getDisplayName().toString());
                            mDatabase.child(email).child("Setting").child("avatar").setValue(user.getPhotoUrl().toString());




                            mDatabase.child(email).child("Setting").child("Status").setValue("https://vignette1.wikia.nocookie.net/animal-jam-clans-1/images/1/10/Neon-green-dot-md.png/revision/latest?cb=20160630184443");


                            Intent  intent1=new Intent(MainActivity.this,InfoUser.class);
                            startActivity(intent1);
                            Toast.makeText(MainActivity.this, "Authentication true.",
                                    Toast.LENGTH_SHORT).show();
                            // FirebaseUser user = mAuth.getCurrentUser();
// updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("xxxxxx", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("xx", "onConnectionFailed:" + connectionResult);

        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void signOut() {

        // Firebase sign out
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            String email = user.getEmail().toString();
            email = email.replaceAll("[.]", "");
            mDatabase.child(email).child("Setting").child("Status").setValue("https://eyotdofe.files.wordpress.com/2016/09/red-dot.png?w=228");

        mAuth.signOut();



        // Google sign out

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(

                new ResultCallback<Status>() {

                    @Override

                    public void onResult(@NonNull Status status) {



                    }

                });

        }
        catch (Exception e){}
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        signOut();
    }
}
