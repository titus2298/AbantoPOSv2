package jtabanto22.google.com.abantoposv2;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder>{
    private ArrayList<RecipeModel> recipes;
    private Context mContext;
    private StorageReference mStorageRef;

    public RecipeRecyclerAdapter(ArrayList<RecipeModel> recipes, Context mContext) {
        this.recipes = recipes;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe_layout, parent, false);
        ViewHolder holder = new ViewHolder(view,mContext,recipes);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecipeRecyclerAdapter.ViewHolder holder, int position) {

        mStorageRef = FirebaseStorage.getInstance().getReference().child("images/").child(recipes.get(position).getRecipeID()+"");

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(holder.rowRecipe_ImageView);
            }
        });


        holder.rowRecipe_mealTypeTxtView.setText(recipes.get(position).getRecipeName());
        holder.rowRecipe_NumServingsTxtView.setText(recipes.get(position).getMealServing() + " Servings");
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView rowRecipe_mealTypeTxtView;
        TextView rowRecipe_NumServingsTxtView;
        ImageView rowRecipe_ImageView;
        ConstraintLayout parentLayout;
        private ArrayList<RecipeModel> recipes;
        private Context mContext;



        public ViewHolder(View itemView, Context mContext,ArrayList<RecipeModel> recipes) {
            super(itemView);
            this.recipes=recipes;
            this.mContext=mContext;
            itemView.setOnClickListener(this);
            rowRecipe_mealTypeTxtView = itemView.findViewById(R.id.rowRecipe_mealTypeTxtView);
            rowRecipe_NumServingsTxtView = itemView.findViewById(R.id.rowRecipe_NumServingsTxtView);
            rowRecipe_ImageView = itemView.findViewById(R.id.rowRecipe_ImageView);
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(mContext,RecipeDetailsActivity.class);
            intent.putExtra("recipeID",recipes.get(position).getRecipeID());
            this.mContext.startActivity(intent);
        }
    }

}
