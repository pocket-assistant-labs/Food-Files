package keeper.recipe.recipekeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
* currently a small activity that provides a quick option for feedback thus allowing
* better improvement to the system in subsequent updates.
* */

public class InfoScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        Button feedback = (Button) findViewById(R.id.feedbackButton);
        feedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"pocketassistantlabs@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Pocket Recipe's");
                i.putExtra(Intent.EXTRA_TEXT   , "<--Please include feedback here-->");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(InfoScreen.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
