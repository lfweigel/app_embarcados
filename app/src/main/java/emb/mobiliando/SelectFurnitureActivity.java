package emb.mobiliando;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.io.File;

public class SelectFurnitureActivity extends AppCompatActivity {

    ImageView furnitureIcon, decorationIcon, furnishedImg, saveIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfurniture);

        Intent intent = getIntent();
        final String  photo_path = intent.getStringExtra(NewProjectActivity.EXTRA_MESSAGE);

        Bitmap imgBitmap = BitmapFactory.decodeFile(photo_path);
        ImageView imgView = (ImageView) findViewById(R.id.imageView1);
        imgView.setImageBitmap(imgBitmap);

        furnitureIcon = (ImageView) findViewById(R.id.imageView2);
        decorationIcon = (ImageView) findViewById(R.id.imageView3);
        saveIcon = (ImageView) findViewById(R.id.saveImage);

        saveIcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                saveFurnishedImage();
            }
        });

        furnitureIcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectFurniture();
            }
        });

        decorationIcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectDecoration();
            }
        });

    }

    private void saveFurnishedImage() {
        /*
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(image);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        */
    }

    public void selectFurniture(){

        PopupMenu popup = new PopupMenu(SelectFurnitureActivity.this, findViewById(R.id.imageView2));
        popup.getMenuInflater().inflate(R.menu.furniture_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Do something
                Intent objectIntent = new Intent(); // pra ele não reclamar
                switch(item.getItemId()){
                    case R.id.chairs:
                        objectIntent = new Intent(SelectFurnitureActivity.this, ChairsActivity.class);
                        break;

                    case R.id.armchairs:
                        objectIntent = new Intent(SelectFurnitureActivity.this, ArmchairsActivity.class);
                        break;

                    case R.id.sofas:
                       objectIntent = new Intent(SelectFurnitureActivity.this, SofasActivity.class);
                        break;

                    case R.id.beds:
                        objectIntent = new Intent(SelectFurnitureActivity.this, BedsActivity.class);
                        break;

                    case R.id.tables:
                        objectIntent = new Intent(SelectFurnitureActivity.this, TablesActivity.class);
                        break;

                    case R.id.shelves:
                        objectIntent = new Intent(SelectFurnitureActivity.this, ShelvesActivity.class);
                        break;

                    case R.id.cabinets:
                        objectIntent = new Intent(SelectFurnitureActivity.this, CabinetsActivity.class);
                        break;
                }
                startActivity(objectIntent);
                return true;
            }
        });

        popup.show();//showing popup menu
    }


    public void selectDecoration(){

        PopupMenu popup = new PopupMenu(SelectFurnitureActivity.this, findViewById(R.id.imageView3));
        popup.getMenuInflater().inflate(R.menu.decoration_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent objectIntent = new Intent();
                // Do something
                    switch(item.getItemId()){

                        case R.id.portraits:
                            objectIntent = new Intent(SelectFurnitureActivity.this, PortraitsActivity.class);
                            break;

                        case R.id.vases:
                            objectIntent = new Intent(SelectFurnitureActivity.this, VasesActivity.class);
                            break;

                        case R.id.mats:
                            objectIntent = new Intent(SelectFurnitureActivity.this, MatsActivity.class);
                            break;

                        case R.id.curtains:
                            objectIntent = new Intent(SelectFurnitureActivity.this, CurtainsActivity.class);
                            break;

                        case R.id.lights:
                            objectIntent = new Intent(SelectFurnitureActivity.this, LightsActivity.class);
                            break;
                    }
                startActivity(objectIntent);
                return true;
                }
            });

        popup.show();//showing popup menu

    }

}

