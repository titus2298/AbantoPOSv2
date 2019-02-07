package jtabanto22.google.com.abantoposv2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {


    ImageView recipeDetails_imageView;
    String recipeID;
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;


    TextView recipeDetails_ingredients, recipeDetails_instructions, recipeDetails_mealType,recipeDetails_mealName,recipeDetails_cookingTime,recipeDetails_numServings,recipeDetails_totalCookTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        refs();

        Intent intent = getIntent();
        recipeID= intent.getStringExtra("recipeID");
        pullData();
    }
    private void pullData()
    {

        mStorageRef = FirebaseStorage.getInstance().getReference().child("images").child(recipeID);
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(recipeDetails_imageView);
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("Recipe").document("" + recipeID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        RecipeModel recipe = document.toObject(RecipeModel.class);
                        recipeDetails_mealType.setText(recipe.getMealType());
                        recipeDetails_mealName.setText(recipe.getRecipeName());
                        recipeDetails_cookingTime.setText(recipe.getCookingTime() + " minutes");
                        recipeDetails_totalCookTime.setText(recipe.getCookingTime()+ " minutes" );
                        recipeDetails_ingredients.setText(recipe.getIngredients());
                        recipeDetails_instructions.setText(recipe.getInstructions());
                        recipeDetails_numServings.setText("Serves " + recipe.getMealServing() + " people");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error in Cannot Retrieve Records!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void disable()
    {
        recipeDetails_ingredients.setEnabled(false);
        recipeDetails_instructions.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_start_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.startCooking )
        {
            View view = findViewById(R.id.recipeDetails_totalCookTime);
            Snackbar.make(view, "Enter Cooking Time", Snackbar.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);


    }
    public void refs()
    {
        recipeDetails_mealType=findViewById(R.id.recipeDetails_mealType);
        recipeDetails_mealName=findViewById(R.id.recipeDetails_mealName);
        recipeDetails_cookingTime=findViewById(R.id.recipeDetails_cookingTime);
        recipeDetails_totalCookTime=findViewById(R.id.recipeDetails_totalCookTime);
        recipeDetails_ingredients=findViewById(R.id.recipeDetails_ingredients);
        recipeDetails_instructions=findViewById(R.id.recipeDetails_instructions);
        recipeDetails_imageView=findViewById(R.id.recipeDetails_imageView);
        recipeDetails_numServings=findViewById(R.id.recipeDetails_numServings);

    }
}
