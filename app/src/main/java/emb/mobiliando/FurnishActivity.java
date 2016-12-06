package emb.mobiliando;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FurnishActivity extends AppCompatActivity {

    ImageView furnitureIcon, decorationIcon, furnishedImg, saveIcon;

    float dX, dY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnish);
        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.toolbar);




         furnishedImg = (ImageView) findViewById(R.id.imageView1);
         final String photo_path;
         if(intent.hasExtra("path")) { // "Novo projeto", intent vem com um extra indicando o path absoluto
            photo_path = intent.getStringExtra("path");
            Bitmap imgBitmap = BitmapFactory.decodeFile(photo_path);
            furnishedImg.setImageBitmap(imgBitmap);

             absolutePath = photo_path;
         }
         else if(intent.hasExtra("uri")) { // "Editar projeto", intent vem com uma Uri indicando a imagem selecionada da galeria
            photo_path = intent.getStringExtra("uri");
            Uri photo_uri = Uri.parse(photo_path);
            furnishedImg.setImageURI(photo_uri);
        }
         else if(intent.hasExtra("command")) // vem de alguma activity de móveis
         {
             /*int res = getResources().getIdentifier("living_room2", "drawable", this.getPackageName());
             furnishedImg.setImageResource(res);*/
             //String picturePath = "/storage/emulated/0/DCIM/Camera/"

             final String path = intent.getStringExtra("command");
             Uri photo_uri = Uri.parse(path);
             furnishedImg.setImageURI(photo_uri);

             absolutePath = path;

             //Toast.makeText(getBaseContext(), "Absolute Path " + absolutePath , Toast.LENGTH_LONG).show();

             String imageName = intent.getStringExtra("image_name");
             int res2 = getResources().getIdentifier(imageName, "drawable", this.getPackageName());

             ImageView iv = (ImageView) findViewById(R.id.iv);

             iv.setImageResource(res2);

             iv.setOnTouchListener(new View.OnTouchListener() {

                 @Override
                 public boolean onTouch(View view, MotionEvent event) {

                     switch (event.getAction()) {

                         case MotionEvent.ACTION_DOWN:

                             dX = view.getX() - event.getRawX();
                             dY = view.getY() - event.getRawY();
                             break;

                         case MotionEvent.ACTION_MOVE:

                             view.animate()
                                     .x(event.getRawX() + dX)
                                     .y(event.getRawY() + dY)
                                     .setDuration(0)
                                     .start();
                             break;
                         default:
                             return false;
                     }
                     return true;
                 }

             });
         }
         else
             Toast.makeText(getBaseContext(), "Algo bem errado, nao deveria acontecer", Toast.LENGTH_LONG).show();


        //furnishedImg.setRotation(90.0f);

       // furnitureIcon = (ImageView) findViewById(R.id.imageView2);
        //decorationIcon = (ImageView) findViewById(R.id.imageView3);
       // saveIcon = (ImageView) findViewById(R.id.saveImage);

       /* saveIcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //saveFurnishedImage();
                createDirectoryAndSaveFile();
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
*/
    }
/*
    private Bitmap uriToBitmap(Uri selectedFileUri) {
        Bitmap image = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);


            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }*/
    private void saveFurnishedImage() {
/*
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(getBaseContext(), "Project saved!\nPath: "+ path, Toast.LENGTH_LONG).show();
*/
        /*
        furnishedImg.buildDrawingCache();
        Bitmap bitmap =furnishedImg.getDrawingCache();
        OutputStream fOut = null;
        Uri outputFileUri;
        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "folder_name" + File.separator);
            root.mkdirs();
            File sdImageMainDirectory = new File(root, "myPicName.jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
        }
        */
        furnishedImg.buildDrawingCache();
        Bitmap bitmap = furnishedImg.getDrawingCache();
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/directoryName";
        Toast.makeText(getBaseContext(), "Trying to savd!\nPath: "+ fullPath, Toast.LENGTH_LONG).show();
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            OutputStream fOut = null;
            File file = new File(fullPath, "image.png");
            if (file.exists())
                file.delete();
            file.createNewFile();
            fOut = new FileOutputStream(file);
            // 100 means no compression, the lower you go, the stronger the compression
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Toast.makeText(getBaseContext(), "Project saved!\nPath: "+ fullPath, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
        }
    }

        /*
        furnishedImg.setDrawingCacheEnabled(true);
        Bitmap b = furnishedImg.getDrawingCache();
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), b, "imgtest", description);
        */

    private void createDirectoryAndSaveFile() {
        String fileName = "test";
        furnishedImg.buildDrawingCache();
        Bitmap bitmap = furnishedImg.getDrawingCache();

        //String path =  Environment.getExternalStorageDirectory() + "/" + "mobiliando_projects/";
        //String path =  Environment.getExternalStorageDirectory() + "/" + "mobiliando_projects/";
        String path = ("/SDCARD/mobiliando_projects/");
        File dir = new File(path);

        if (!dir.exists()) {
            //File wallpaperDirectory = new File("/sdcard/DirName/");
            dir.mkdirs();
            Toast.makeText(getBaseContext(), "Directory" + path + " created!", Toast.LENGTH_LONG).show();
        }

        String image_destination = path + "image_test";
        File file = new File(image_destination);
        if (file.exists()) {
            file.delete();
        }
        Toast.makeText(getBaseContext(), "Project saved at " + image_destination + "!", Toast.LENGTH_LONG).show();
       try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(getBaseContext(), "Project saved!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String absolutePath;

    public void selectFurniture(){

        PopupMenu popup = new PopupMenu(FurnishActivity.this, findViewById(R.id.add_furniture));
        popup.getMenuInflater().inflate(R.menu.furniture_popup_menu, popup.getMenu());


       // final String path;
        //path = absolutePath;

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Do something
                //Toast.makeText(getBaseContext(), "Absolute Path Furniture " + absolutePath , Toast.LENGTH_LONG).show();
                Intent objectIntent = new Intent(); // pra ele não reclamar

                switch(item.getItemId()){
                    case R.id.chairs:
                        objectIntent = new Intent(FurnishActivity.this, ChairsActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;

                    case R.id.armchairs:
                        objectIntent = new Intent(FurnishActivity.this, ArmchairsActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;

                    case R.id.sofas:
                       objectIntent = new Intent(FurnishActivity.this, SofasActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;

                    case R.id.beds:
                        objectIntent = new Intent(FurnishActivity.this, BedsActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;

                    case R.id.tables:
                        objectIntent = new Intent(FurnishActivity.this, TablesActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;

                    case R.id.shelves:
                        objectIntent = new Intent(FurnishActivity.this, ShelvesActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;

                    case R.id.cabinets:
                        objectIntent = new Intent(FurnishActivity.this, CabinetsActivity.class);
                        objectIntent.putExtra("path", absolutePath);
                        break;
                }
                startActivity(objectIntent);
                return true;
            }
        });

        popup.show();//showing popup menu
    }


    public void selectDecoration(){

        PopupMenu popup = new PopupMenu(FurnishActivity.this, findViewById(R.id.add_decoration));
        popup.getMenuInflater().inflate(R.menu.decoration_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent objectIntent = new Intent();
                // Do something
                    switch(item.getItemId()){

                        case R.id.portraits:
                            objectIntent = new Intent(FurnishActivity.this, PortraitsActivity.class);
                            objectIntent.putExtra("path", absolutePath);
                            break;

                        case R.id.vases:
                            objectIntent = new Intent(FurnishActivity.this, VasesActivity.class);
                            objectIntent.putExtra("path", absolutePath);
                            break;

                        case R.id.mats:
                            objectIntent = new Intent(FurnishActivity.this, MatsActivity.class);
                            objectIntent.putExtra("path", absolutePath);
                            break;

                        case R.id.lights:
                            objectIntent = new Intent(FurnishActivity.this, LightsActivity.class);
                            objectIntent.putExtra("path", absolutePath);
                            break;
                    }
                startActivity(objectIntent);
                return true;
                }
            });

        popup.show();//showing popup menu

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_furniture:
                selectFurniture();
                return true;

            case R.id.add_decoration:
                selectDecoration();
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.save_image:
                //createDirectoryAndSaveFile()


            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

}

