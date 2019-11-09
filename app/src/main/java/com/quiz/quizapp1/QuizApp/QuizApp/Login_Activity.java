package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Login_Activity extends AppCompatActivity {


    Button mobilesignin,facebooksignin,googlesignin;
    FirebaseAuth firebaseAuth;
    public static  final  int GOOGLE_SIGNIN=2;

    GoogleSignInClient googleSignInClient;
    DatabaseReference databaseReference,databaseReference1;
    CallbackManager callbackManager;
    LoginButton loginButton;
    public static  final String MYPREFERENCES="MyPrefs";

    SharedPreferences sharedPreferences;
    List<Avtar_model> avtar;
    String  avtar_id, url;
    int avtarcount;
    int rank=1;
    int level=1;
    int xp=50;
    int star=4;
    int coin=0;
    int incr =50;
    int levelreached=150;
    int count=0;
    int points=0;
    int contiwin=0;
    int sheild=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login_);

        mobilesignin=findViewById(R.id.signinmobile);
        facebooksignin=findViewById(R.id.facebooksignin);
        googlesignin=findViewById(R.id.googlesignin);
        firebaseAuth=FirebaseAuth.getInstance();
//        Utils.hasInternetAccess(getApplicationContext());
 //     Utils.getDatabase();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference1=FirebaseDatabase.getInstance().getReference("Avtar");
       loginButton = findViewById(R.id.facebooklogin_button);
        sharedPreferences=getApplicationContext().getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        mobilesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Login_Activity.this,OTPactivity.class);
                startActivity(intent);
                SigninMobile();
            }
        });

        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });
        facebooksignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login_Activity.this,
                        Arrays.asList("email"));
                //SigninFacebook(view);
            }
        });
        getavtar();




    }

    private void getavtar()
    {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avtar=new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Avtar_model avtarModel=snapshot.getValue(Avtar_model.class);
                    avtar.add(avtarModel);
                }
                avtarcount=avtar.size();
                System.out.println("avtarcount------------"+avtarcount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void SigninFacebook(View view)
    {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



    }


        private void handleFacebookAccessToken(final AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            System.out.println("user------------->"+user.getDisplayName());
                            String name=user.getDisplayName();
                            String uid=user.getUid();
                            String id=token.getToken();

                            sharedPreferences.edit().putBoolean("islogin",true).commit();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void signInGoogle() {
        Intent signIntent=googleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,GOOGLE_SIGNIN);
    }



    private void SigninMobile() {
        //Toast.makeText(this, "hiii bro", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGNIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);

                Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show();



            } catch (ApiException e) {

                Toast.makeText(Login_Activity.this, "Auth went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createnewUser(String id,String name,String uid, String avtar_id, String url)
    {



        UserModel  userModel=new UserModel(id, name, uid,avtar_id,url);
        databaseReference.child("users").child(uid).child(id).setValue(userModel);
    }
    private void addExtras(int rank, int level, int xp, int star , int coin , int iswin ,int levelreached, int count, int points,
                           int contiwin, int sheild)
    {
        String uid= firebaseAuth.getUid();
        Extras_model extras_model=new Extras_model(rank,level,xp,star,coin, iswin, levelreached, count, points, contiwin, sheild);
        databaseReference.child("extras").child(uid).child(uid).setValue(extras_model);
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount account)
    {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("vani", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String name=user.getDisplayName();
                            String uid=user.getUid();
                            String email=user.getEmail();
                            String id1=account.getId();
                            avtar_id= avtar.get(0).getId();
                            url=avtar.get(0).getUrl();


                            String image= String.valueOf(user.getPhotoUrl());
                            Log.e("details",""+name+" "+uid+" "+email+" "+image);
                            createnewUser(id1,name,uid,avtar_id, url);
                            addExtras(rank,level,xp,star,coin, incr, levelreached, count, points, contiwin, sheild);
                            sharedPreferences.edit().putBoolean("islogin",true).commit();

                            Intent intent=new Intent(Login_Activity.this,Main_Screen.class);
                            startActivity(intent);
                         //   writenewuser(uid,name,email,image);




                        } else {


                            Toast.makeText(Login_Activity.this,"failed",Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });

    }
    public void onClickFacebookButton(View view) {
        if (view == facebooksignin) {
            loginButton.performClick();
        }
    }


}
