package com.example.social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;


public class Profile extends AppCompatActivity {


    TextView name , url;
    TextView mail,picurl;
    String photoUrl;
    String signport;
    ImageView imageView;
    Button logout;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        logout = findViewById(R.id.fblogout);
        name = findViewById(R.id.fbname);
        mail = findViewById(R.id.fbemail);
        picurl=findViewById(R.id.info);
        imageView=findViewById(R.id.facebookpic);
        url=findViewById(R.id.url);


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            mail.setText(signInAccount.getEmail());
            photoUrl = signInAccount.getPhotoUrl().toString();
            photoUrl = photoUrl + "?type=large";
            Picasso.get().load(photoUrl).into(imageView);
            url.setText(photoUrl);

            for (UserInfo info: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                if (info.getProviderId().equals("facebook.com")) {
                    signport = "User is signed in with Facebook";
                    picurl.setText(signport);

                }
                else if(info.getProviderId().equals("google.com")){
                    signport = "User is signed in with google";
                    picurl.setText(signport);

                }
            }
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(Profile.this);
                progressDialog.setMessage("Log out");
                progressDialog.show();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(),First.class);
                startActivity(intent);
            }
        });
    }
}
