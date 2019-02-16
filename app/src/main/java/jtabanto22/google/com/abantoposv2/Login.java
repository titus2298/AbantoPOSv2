package jtabanto22.google.com.abantoposv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView login_signUp;
    EditText login_username,login_password;
    Button loginButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        refs();
        mAuth = FirebaseAuth.getInstance();
        login_signUp.setOnClickListener(signUp);
        loginButton.setOnClickListener(login);
    }

    public View.OnClickListener signUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

        }
    };

    public View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isEmpty=false;


            if(login_username.getText().toString().isEmpty())
            {
                login_username.setError("Please Enter Username");
                isEmpty=true;

            }

            if(login_password.getText().toString().isEmpty())
            {
                login_password.setError("Please Enter Password");
                isEmpty=true;
            }

            if(isEmpty==false) {
                mAuth.signInWithEmailAndPassword(login_username.getText().toString(), login_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Login.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            Toast.makeText(getApplication(), "SUCCESSFULLY SIGNED IN!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), "Invalid Credentials ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }

    };


    public void refs()
    {
        login_signUp = findViewById(R.id.login_signUp);
        login_username=findViewById(R.id.login_username);
        login_password=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.loginButton);
    }
}
