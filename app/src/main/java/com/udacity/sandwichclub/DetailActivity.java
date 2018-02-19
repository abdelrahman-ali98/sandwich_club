package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView alsoKnownAsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initUI();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        descriptionTextView.setText(sandwich.getDescription());
        Toast.makeText(this, sandwich.getIngredients().toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, sandwich.getAlsoKnownAs().toString(), Toast.LENGTH_LONG).show();

        try {
            if (sandwich.getAlsoKnownAs().size() > 0) {
                String alsoKnownAs = "";
                for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                    if(i==sandwich.getAlsoKnownAs().size()-1){
                        alsoKnownAs += sandwich.getAlsoKnownAs().get(i);
                    }
                    else if(i==sandwich.getAlsoKnownAs().size()-2){
                        alsoKnownAs += sandwich.getAlsoKnownAs().get(i) +" and ";
                    }
                    else{
                        alsoKnownAs += sandwich.getAlsoKnownAs().get(i) +", " ;
                    }

                }
                alsoKnownAsTextView.setText(alsoKnownAs);
            } else {
                alsoKnownAsTextView.setVisibility(View.GONE);
            }


            if (sandwich.getIngredients().size() > 0) {
                String ingredientsFor = "";
                for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                    if(i== sandwich.getIngredients().size()-1){
                        ingredientsFor += sandwich.getIngredients().get(i);
                    }
                    else if(i== sandwich.getIngredients().size()-2){
                        ingredientsFor += sandwich.getIngredients().get(i)+ " and ";
                    }
                    else{
                        ingredientsFor += sandwich.getIngredients().get(i)+ ", " ;
                    }
                }
                ingredientsTextView.setText(ingredientsFor);
            } else {
                ingredientsTextView.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initUI(){
        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_As);
        placeOfOriginTextView = findViewById(R.id.place_Of_Origin);
        descriptionTextView = findViewById(R.id.description);
        ingredientsTextView = findViewById(R.id.ingredients);

    }
}
