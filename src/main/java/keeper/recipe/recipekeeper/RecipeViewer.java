package keeper.recipe.recipekeeper;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* This class handles the viewing of individual recipes.
* Creates and manages 2 popups that offer additional functionality
* and their calls to other classes and displays
* */

public class RecipeViewer extends AppCompatActivity {

    CommonDataContainer CDC = new CommonDataContainer();

    Button Close;
    Button Close2;
    FloatingActionButton eggTimer;
    FloatingActionButton calc;

    ConversionCalculator CC = new ConversionCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_viewer2);

        TextView ingred = (TextView) findViewById(R.id.IngredientView);
        TextView detail = (TextView) findViewById(R.id.DetailView);
        Button home = (Button) findViewById(R.id.recipeViewHomeButton);
        ingred.setText(CDC.getCurrentRecipeIngredients());
        detail.setText(CDC.getCurrentRecipeDetails() + "\n\n\n");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeScreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeScreen);
            }
        });

        final Spinner recipeConverter = (Spinner) findViewById(R.id.recipe_view_spinner);
        recipeConverter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = recipeConverter.getSelectedItem().toString();
                ArrayList<String> ingredientListPreConvert = createSpinnerData();
                switch(selected) {
                    case ("original recipe"): {
                        CC.packageArrayForDisplay(ingredientListPreConvert);
                        break;
                    }
                    case("Half recipe"):{

                        break;
                    }
                    case("1.5 times recipe"):{

                        break;
                    }
                    case("Double recipe"):{

                        break;
                    }
                    default:{

                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        eggTimer = (FloatingActionButton) findViewById(R.id.EggTimerFAB);
        eggTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });

        calc = (FloatingActionButton) findViewById(R.id.CalculatorFAB);
        calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showPopup2();
            }
        });

    }

    //first popup window that will contain a timer for use while cooking
    private PopupWindow pw;
    private void showPopup() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_1));
            pw = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            pw.showAtLocation(layout, Gravity.TOP, 10, 10);
            Close = (Button) layout.findViewById(R.id.close_popup);
            Close.setOnClickListener(cancel_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //click listener for first popup internal button
    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {
            pw.dismiss();
        }
    };

    //second popup window which contains a conversion calculator and all relevant click listeners.
    private PopupWindow pw2;
    private void showPopup2() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup2,
                    (ViewGroup) findViewById(R.id.popup_2));
            pw2 = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            pw2.showAtLocation(layout, Gravity.BOTTOM, 10, 30);

            List<String> spinner1 = createSpinnerData();

            final Spinner toConvert = (Spinner) layout.findViewById(R.id.calc_from_spinner);
            final Spinner convertTo = (Spinner) layout.findViewById(R.id.calc_to_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinner1);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            toConvert.setAdapter(adapter);
            toConvert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    List<String> spinner2;
                    String selected = toConvert.getSelectedItem().toString();
                    int y = selected.indexOf(' ') + 1;
                    selected = selected.substring(y, selected.indexOf(' ', y));
                    switch(selected) {
                        case ("tsp"): {
                            spinner2 = Arrays.asList("tbsp", "floz", "cup", "pnt", "qrt");
                            break;
                        }
                        case("tbsp"):{
                            spinner2 = Arrays.asList("tsp", "floz", "cup", "pnt", "qrt");
                            break;
                        }
                        case("floz"):{
                            spinner2 = Arrays.asList("tsp", "tbsp", "cup", "pnt", "qrt");
                            break;
                        }
                        case("cup"):{
                            spinner2 = Arrays.asList("tsp", "tbsp", "floz", "pnt", "qrt");
                            break;
                        }
                        case("pnt"):{
                            spinner2 = Arrays.asList("tsp", "tbsp", "floz", "cup", "qrt");
                            break;
                        }
                        case("qrt"):{
                            spinner2 = Arrays.asList("tsp", "tbsp", "floz", "cup", "pnt");
                            break;
                        }
                        case("gm"):{
                            spinner2 = Arrays.asList("oz", "lbs");
                            break;
                        }
                        case("oz"):{
                            spinner2 = Arrays.asList("gm", "lbs");
                            break;
                        }
                        case("lbs"):{
                            spinner2 = Arrays.asList("gm", "oz");
                            break;
                        }
                        default:{
                            spinner2 = Arrays.asList("nothing selected");
                            break;
                        }
                    }
                    try {
                        ArrayAdapter<String> adapterInner = new ArrayAdapter<>(
                                RecipeViewer.this, android.R.layout.simple_spinner_item, spinner2);

                        adapterInner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        convertTo.setAdapter(adapterInner);
                    }catch(Exception j){j.printStackTrace();}

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final TextView result = (TextView) layout.findViewById(R.id.result_space);
            Button compute = (Button) layout.findViewById(R.id.calculate);
            compute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String convertable = toConvert.getSelectedItem().toString();
                    String toMeasure = convertTo.getSelectedItem().toString();
                    CC.popupCalc(convertable, toMeasure);
                    result.setText(CDC.getPopupConversionValue());
                }
            });

            Close2 = (Button) layout.findViewById(R.id.calc_close);
            Close2.setOnClickListener(cancel_button2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //uses the string value of the recipe field to generate an arrayList to
    // facilitate data manipulation within the calculator functions.
    private ArrayList<String> createSpinnerData(){
        ArrayList<String> ingredsToConv = new ArrayList<>();
        String ingred = CDC.getCurrentRecipeIngredients().substring(CDC.getCurrentRecipeIngredients().indexOf('1'), CDC.getCurrentRecipeIngredients().length());
        if (!ingred.equals("")) {
            int x = ingred.length();
            for (int i = 0; i < x; i++) {
                ingredsToConv.add(ingred.substring(ingred.indexOf('\u2022') + 3, ingred.indexOf('\n')));
                ingred = ingred.substring(ingred.indexOf('\n') + 1, ingred.length());
                if (ingred.length() <= 7) {
                    break;
                }
            }
        }else{ingredsToConv.add("");}
        return ingredsToConv;
    }

    private View.OnClickListener cancel_button2 = new View.OnClickListener() {
        public void onClick(View v) {
            pw2.dismiss();
        }
    };
}

