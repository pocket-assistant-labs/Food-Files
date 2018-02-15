package keeper.recipe.recipekeeper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* This is the main activity which loads the main view of the app as well as controls data flow to other classes.*/

public class MainActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 201;
    boolean check = true;
    ArrayList<String> tempList = new ArrayList<String>();
    ArrayList<String> tempList2 = new ArrayList<String>();
    ArrayList<String> fullRecipes = new ArrayList<>();
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    boolean isFABOpen = false;
    CommonDataContainer CDC = new CommonDataContainer();

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting permissions required by the app to function properly.
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);}
        }else{

            createRecipeList();
        }

        //opens the 'add recipe' view and starts the relevant class.
        Button newRecipe = (Button) findViewById(R.id.NewRecipe);
        newRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newRec = new Intent(getApplicationContext(), AddNewRecipe.class);
                startActivity(newRec);
            }
        });

        //sets the floating action button menu items and handles clicks.
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonMain);
        fab1 = (FloatingActionButton) findViewById(R.id.floatingActionMenuPref);
        fab2 = (FloatingActionButton) findViewById(R.id.floatingActionMenuShare);
        fab3 = (FloatingActionButton) findViewById(R.id.floatingActionMenuInfo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_down));
                }else{
                    closeFABMenu();
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_up));
                }
            }
        });
        fab3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent infoPane = new Intent(getApplicationContext(), InfoScreen.class);
                startActivity(infoPane);
            }
        });
    }

    //opens floating action menu
    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    //collapses floating action menu
    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }

    //part of require permissions check function.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createRecipeList();
                    check = true;

                } else {

                    check = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    //a linked boolean to facilitate disabling functions if permission denied.
    public boolean checker(){
        return check;
    }

    //searches an arrayList for the specified name and category and sets the item to common data container class
    public void findRecipeByName(String nameCat){
        for (String recipe : fullRecipes) {
            if (recipe.substring(0, recipe.indexOf('}')).toString().compareTo(nameCat) == 0) {
                String display = recipe;
                CDC.setCurrentRecipeIngredients(display.substring(display.indexOf('}') + 1, display.indexOf(':') - 7));
                CDC.setCurrentRecipeDetails(display.substring(display.indexOf(':') + 1, display.length()));
            }
        }
    }

    //takes a String and creates the formatted views required for the expandable list adapter.
    public void createRecipeList() {
        FileSystemManager FSM = new FileSystemManager();
        String savedMaster = FSM.readFromFile();
        ArrayList<String> master = new ArrayList<>();
        String temp;

        if (savedMaster != "") {
            int x = savedMaster.length();
            for (int i = 0; i < x; i++) {
                if(savedMaster.substring(0, 1).compareTo("\n") == 0)
                    savedMaster = savedMaster.substring(1, savedMaster.length());
                master.add(savedMaster.substring(0, savedMaster.indexOf('|')));
                if (master.get(i).length() < savedMaster.length() - 1) {
                    savedMaster = savedMaster.substring(savedMaster.indexOf('|') + 1, savedMaster.length());
                    if (savedMaster.length() < 10) {
                        break;
                    }
                }
            }
            fullRecipes = master;
            CDC.setRecipeList(master);
            for (int i = 0; i < master.size(); i++) {

                temp = master.get(i).substring(master.get(i).indexOf('{') + 1, master.get(i).indexOf('}'));
                if (!tempList.contains(temp)) {
                    tempList.add(temp);
                }
                temp = temp + ":" + master.get(i).substring(0, master.get(i).indexOf('{'));
                tempList2.add(temp);

            }
        } else {
            tempList.add("");
            tempList2.add(" : ");
        }
        buildList();
    }

    //sets the expandable list and listens for clicks. upon click opens recipe viewer activity.
    public void buildList() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(tempList, tempList2);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        for(int x = 0; x < tempList.size(); x++){
            expandableListView.expandGroup(x);
        }
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String Cat = expandableListTitle.get(groupPosition);
                String name = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition);
                findRecipeByName(name + "{" + Cat);
                Intent recipeDisplay = new Intent(getApplicationContext(), RecipeViewer.class);
                startActivity(recipeDisplay);
                return false;
            }
        });
    }
}
