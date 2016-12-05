package emb.mobiliando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AddFurnitureActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfurniture);

        Intent intent = getIntent();
        String image_to_load = intent.getStringExtra("image_name");

        int res = getResources().getIdentifier(image_to_load, "drawable", this.getPackageName());
        img = (ImageView)findViewById(R.id.selected_furniture);
        img.setImageResource(res);

    }
}
