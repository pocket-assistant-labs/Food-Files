package keeper.recipe.recipekeeper;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
* File management system that writes and reads data.
* */

public class FileSystemManager extends Activity {

    //write to file is only called by the save function at this time.
    //A delete function will be added in the future requiring an additional call of this feature
    //to update the recorded values.
    public void writeToFile(Context context, String data) {

        MainActivity M = new MainActivity();
        if(M.checker()) {
            try {
                File root = new File(Environment.getExternalStorageDirectory(), "/Recipe_Keeper/");
                if (!root.exists()) {
                    root.mkdirs();
                }
                if (root.exists()) {
                    File recipeKeeper = new File(root, "RecipeMaster.txt");
                    FileWriter writer = new FileWriter(recipeKeeper);
                    writer.append(data);
                    writer.flush();
                    writer.close();
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Not Saved", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    //read from file is only accessed once by main activity due to the hit to performance
    // if accessed to frequently. values are stored in the common data container after being read
    //to make common data more easily accessible to all components of the app.
    public String readFromFile() {
        String myData = "";
        String outputData = null;
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "/Recipe_Keeper/");
            if (!root.exists()) {
                root.mkdirs();
            }
            File FileName = new File(Environment.getExternalStorageDirectory(), "/Recipe_Keeper/RecipeMaster.txt");
            if(!FileName.exists()){
                File recipeKeeper = new File(root, "RecipeMaster.txt");
                FileWriter writer = new FileWriter(recipeKeeper);
                writer.append("");
                writer.flush();
                writer.close();
            }
            InputStream fis = new FileInputStream(FileName);
            //DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(fis));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine + "\n";
            }
            //fis.close();
            if (myData != null) {
                outputData = (myData);
            } else {
                outputData = "";
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return outputData;
    }


}
