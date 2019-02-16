package jtabanto22.google.com.abantoposv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    Button registerButton;
    int numDigits=0,numUpperCase=0,numPunctuations=0,numLowerCase=0;
    TextInputEditText register_usernameUser,register_passwordUser,register_firstNameUser,register_confPasswordUser;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        refs();
        mAuth = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(register);
    }

    public View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checkIfEmpty=false;
            String username="",password="";
            username=register_usernameUser.getText().toString();
            password=register_passwordUser.getText().toString();

            if(username.isEmpty())
            {
                register_usernameUser.setError("Username is Required");

                checkIfEmpty=true;
            }

            if(password.isEmpty())
            {
                register_passwordUser.setError("Password is Required");

                checkIfEmpty=true;
            }

            if(register_firstNameUser.getText().toString().isEmpty())
            {
                register_firstNameUser.setError("Name is Required");
                checkIfEmpty=true;
            }


            if(register_confPasswordUser.getText().toString().isEmpty())
            {
                register_confPasswordUser.setError("Please confirm your password!");
                checkIfEmpty=true;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(register_usernameUser.getText().toString()).matches())
            {
                register_usernameUser.setError("Please enter valid email");
                checkIfEmpty=true;
            }

            if(register_passwordUser.length()<8)
            {
                Toast.makeText(getApplication(), "PASSWORD MUST BE AT LEAST 8 CHARACTERS!", Toast.LENGTH_SHORT).show();
                checkIfEmpty=true;
            }

            if(!register_passwordUser.getText().toString().equals(register_confPasswordUser.getText().toString())) {
                Toast.makeText(getApplication(), "PASSWORDS DO NOT MATCH!", Toast.LENGTH_SHORT).show();
                checkIfEmpty=true;
            }

       /*     //  if(register_passwordUser.getText().toString().contains())
            String wordToBeExamined = register_passwordUser.getText().toString();
            for(int innerCounter=0;innerCounter<wordToBeExamined.length();innerCounter++)
            {
                if(Character.isDigit(wordToBeExamined.charAt(innerCounter)))
                {
                    numDigits++;
                }
                else if(Character.isLetter(wordToBeExamined.charAt(innerCounter)))
                {
                    // Toast.makeText(getApplication(), "I GOT IN LETTER CASE! ", Toast.LENGTH_SHORT).show();
                    int AsciiValue=(int)wordToBeExamined.charAt(innerCounter);
                    if(AsciiValue>=65 && AsciiValue<=90)
                    {
                        numUpperCase++;
                        // Toast.makeText(getApplication(), "UPPER CASE! " + AsciiValue, Toast.LENGTH_SHORT).show();
                    }
                    else if(AsciiValue>=97 && AsciiValue<=122)
                    {
                        numLowerCase++;
                        // Toast.makeText(getApplication(), "LOWER CASE! " + AsciiValue, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplication(), "NO CASE!", Toast.LENGTH_SHORT).show();
                    }
                }


                if(checkPunctuation(wordToBeExamined.charAt(innerCounter)))
                {
                    numPunctuations++;
                }

            }


            if(numDigits==0)
            {
                Toast.makeText(getApplication(), "Does not Contain Digits!", Toast.LENGTH_SHORT).show();
                checkIfEmpty=true;
            }
            else if(numUpperCase==0)
            {
                Toast.makeText(getApplication(), "Does not Contain Upper Case!", Toast.LENGTH_SHORT).show();
                checkIfEmpty=true;
            }
            else if(numPunctuations==0){
                Toast.makeText(getApplication(), "Does not Contain a Special Character!", Toast.LENGTH_SHORT).show();
                checkIfEmpty=true;
            }
            else if(numLowerCase==0)
            {
                Toast.makeText(getApplication(), "Does not Contain Lower Case!", Toast.LENGTH_SHORT).show();
                checkIfEmpty=true;
            }*/



            if(checkIfEmpty==false) {

                mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {


                            Intent intent = new Intent(Register.this, HomeActivity.class);
                            User user = new User(register_firstNameUser.getText().toString(),"","",register_usernameUser.getText().toString());

                            db = FirebaseFirestore.getInstance();

                            FirebaseUser userRegistered = mAuth.getCurrentUser();
                            db.collection("User").document("" +userRegistered.getUid()).set(user)
                                    .addOnSuccessListener(new OnSuccessListener< Void >() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "User Registered",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Invalid Credentials!",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                    }
                });

            }
            numDigits=0;
            numUpperCase=0;
            numPunctuations=0;
            numLowerCase=0;
        }

    };


    public boolean checkPunctuation(Character checkPunctuationChar)
    {
        //  List<Character> punctuationList = Arrays.asList(';',',','.',':','"','!','?','(',')','{','}','[',']','-','\'','/');
        if(checkPunctuationChar==';' || checkPunctuationChar==',' || checkPunctuationChar=='.' || checkPunctuationChar==':' ||
                checkPunctuationChar=='"' || checkPunctuationChar=='!' || checkPunctuationChar=='?' || checkPunctuationChar=='(' ||
                checkPunctuationChar==')' ||checkPunctuationChar=='{' ||checkPunctuationChar=='}' ||checkPunctuationChar=='[' ||
                checkPunctuationChar==']' || checkPunctuationChar=='-' || checkPunctuationChar=='\'' || checkPunctuationChar=='/' )
        {
            return true;
        }else
        {
            return false;
        }
    }

    public void refs()
    {
        registerButton=findViewById(R.id.registerButton);
        register_usernameUser=findViewById(R.id.register_usernameUser);
        register_passwordUser=findViewById(R.id.register_passwordUser);
        register_firstNameUser=findViewById(R.id.register_firstNameUser);
        register_confPasswordUser=findViewById(R.id.register_confPasswordUser);

    }
}

