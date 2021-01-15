package com.example.social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.time.Instant;

public class FacebookProfile extends AppCompatActivity {
    private FirebaseAuth mAuth =FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    TextView fbame,fbemail,sinfo,url;
    ImageView imageView;;
    Button fblogout;
    String signport,picUrl;
    private Instant Glide;
    private ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_profile);
        fbame=findViewById(R.id.fbname);
        fbemail=findViewById(R.id.fbemail);
        imageView=findViewById(R.id.facebookpic);
        fblogout=findViewById(R.id.fblogout);
        sinfo =findViewById(R.id.info);
        url =findViewById(R.id.url);

        if(user!=null){

            fbame.setText(user.getDisplayName());
            fbemail.setText(user.getEmail());

            picUrl = user.getPhotoUrl().toString();
            picUrl = picUrl + "?type=large";

            url.setText(picUrl);
            for (UserInfo info: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                if (info.getProviderId().equals("facebook.com")) {
                    int dimensionPixelSize = getResources().getDimensionPixelSize(com.facebook.R.dimen.com_facebook_profilepictureview_preset_size_large);
                    Uri profilePictureUri= Profile.getCurrentProfile().getProfilePictureUri(dimensionPixelSize , dimensionPixelSize);

//                    Glide.with((TemporalAdjuster) this).load(profilePictureUri).into(imageView);
                    Picasso.get().load(profilePictureUri).into(imageView);
                    signport = "User is signed in with Facebook";
                    sinfo.setText(signport);

                }
                else if(info.getProviderId().equals("google.com")){
                    signport = "User is signed in with google";
                    sinfo.setText(signport);

                }
            }
        }
        findViewById(R.id.fblogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                progressDialog=new ProgressDialog(FacebookProfile.this);
                progressDialog.setMessage("Log out");
                progressDialog.show();
                Intent intent = new Intent(getApplicationContext(),First.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user==null){
            Intent intent = new Intent(getApplicationContext(),First.class);
            startActivity(intent);

        }
    }
}
