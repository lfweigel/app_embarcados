package emb.mobiliando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
    }

    public void newProject(View view){

        Intent newProjectIntent = new Intent(this, NewProjectActivity.class);
        startActivity(newProjectIntent);

    }

    public void editProject(View view){

        Intent editProjectIntent = new Intent(this, EditProjectActivity.class);
        startActivity(editProjectIntent);
    }
}
