package emb.mobiliando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CabinetsActivity extends AppCompatActivity {

    ImageView img1, img2, img3, img4, img5, img6;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinets);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");

        img1 = (ImageView) findViewById(R.id.imageView1);
        img2 = (ImageView) findViewById(R.id.imageView2);
        img3 = (ImageView) findViewById(R.id.imageView3);
        img4 = (ImageView) findViewById(R.id.imageView4);
        img5 = (ImageView) findViewById(R.id.imageView5);
        img6 = (ImageView) findViewById(R.id.imageView6);

        img1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addCabinet("cabinet1");
            }
        });

        img2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addCabinet("cabinet2");
            }
        });

        img3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addCabinet("cabinet3");
            }
        });

        img4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addCabinet("cabinet4");
            }
        });

        img5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addCabinet("cabinet5");
            }
        });

        img6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addCabinet("cabinet6");
            }
        });
    }

    public void addCabinet(String selected_image) {
        Intent intent = new Intent(this, FurnishActivity.class);
        intent.putExtra("image_name", selected_image);
        intent.putExtra("command", path);
        startActivity(intent);
    }
}
