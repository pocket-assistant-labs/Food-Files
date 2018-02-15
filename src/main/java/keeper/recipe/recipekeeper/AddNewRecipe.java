package keeper.recipe.recipekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNewRecipe extends AppCompatActivity {
    String previousIngredient = new String();
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String recipe = new String();
    FileSystemManager FSM = new FileSystemManager();

    int clickCount = 1;
    View view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);
        final LinearLayout main =(LinearLayout)findViewById(R.id.AddRecipeLayout);
        LayoutInflater inflater = (LayoutInflater)getBaseContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        clickCount = 1;
        final View view2 = inflater.inflate(R.layout.input_line_add_recipe, null);
        main.addView(view2);

        Button addIngredient = (Button) findViewById(R.id.AddIngredient);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recipe = recipe + clickCount + " " + addIngred() + "\n";
                if(clickCount == 1) {main.removeView(view2);}
                if(clickCount > 1) {main.removeView(view3);}
                inflationManager();
                adapter.notifyDataSetChanged();
                clickCount++;

            }
        });

        Button cancel = (Button) findViewById(R.id.AddRecipeCancelButton);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent mainMenu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenu);
                finish();
            }
        });

        Button save = (Button) findViewById(R.id.AddRecipeButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean contin = save();
                if(contin) {
                    Intent mainMenu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainMenu);
                    finish();
                }
            }
        });
    }

    //called when "add ingredient" is clicked. creates a string for display in the add recipe view and to be added to an array.
    public String addIngred(){

        Spinner measurementVolume = (Spinner) findViewById(R.id.AddRecipeMeasure);
        Spinner measurementType = (Spinner) findViewById(R.id.AddRecipeMeasureType);
        EditText ingredientName = (EditText) findViewById(R.id.AddRecipeIngredient);
        previousIngredient = (measurementVolume.getSelectedItem().toString() + " " +
                measurementType.getSelectedItem().toString() + " " + ingredientName.getText().toString());
        listItems.add(previousIngredient);

        String ingredient = "\u2022  " + measurementVolume.getSelectedItem().toString() + " " +
                measurementType.getSelectedItem().toString() + " " + ingredientName.getText().toString();

        return ingredient;
    }

    //inflates the internal views required for a repeated entry of ingredients in a dynamic method.
    private void inflationManager(){
        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        LinearLayout main =(LinearLayout)findViewById(R.id.AddRecipeLayout);
        ListView previousInput = (ListView) findViewById(R.id.AddRecipeLayoutListview);
        LayoutInflater inflater = (LayoutInflater)getBaseContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view3 = inflater.inflate(R.layout.input_line_add_recipe, null);
        previousInput.setAdapter(adapter);
        main.addView(view3);
    }

    //save function that builds the final resource to be written to the device memory.
    //Field checks are also used to ensure all required fields have been filled.
    //Sends final resource to filemanagement to be written to the system memory.
    public boolean save(){
        String prevMaster = FSM.readFromFile();
        EditText ingredientName = (EditText) findViewById(R.id.AddRecipeIngredient);
        EditText recipeName = (EditText) findViewById(R.id.AddRecipeName);
        EditText categoryName = (EditText) findViewById(R.id.CatagoryEditText);
        EditText detail = (EditText) findViewById(R.id.AddNewDetailBlock);
        if(!ingredientName.getText().toString().isEmpty()){
            addIngred();
        }
        if(recipeName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a recipe name!", Toast.LENGTH_LONG).show();
            if(Build.VERSION.SDK_INT >= 23) {
                recipeName.setBackgroundColor(getColor(android.R.color.holo_red_dark));
            }
            return false;
        }else if(categoryName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a category/folder name!", Toast.LENGTH_LONG).show();
            if(Build.VERSION.SDK_INT >= 23) {
                categoryName.setBackgroundColor(getColor(android.R.color.holo_red_dark));
            }
            return false;
        }else if(detail.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a details/instructions!", Toast.LENGTH_LONG).show();
            if(Build.VERSION.SDK_INT >= 23) {
                detail.setBackgroundColor(getColor(android.R.color.holo_red_dark));
            }
            return false;
        }else {
            String recipeComplete;
            recipeComplete = recipeName.getText().toString() + "{" + categoryName.getText().toString() + "}\n" +
                    "Ingredients\n" + recipe + "Details:" + detail.getText().toString() + "|";
            prevMaster = (prevMaster + recipeComplete);


            FSM.writeToFile(this, prevMaster);
            return true;
        }

    }
}
