package emb.mobiliando;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class FurnishActivity extends AppCompatActivity {

    Bitmap imgBitmap;
    ImageView furnishedImg;
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
        if (intent.hasExtra("path")) { // "Novo projeto", intent vem com um extra indicando o path absoluto
            photo_path = intent.getStringExtra("path");
            ((GlobalVariableHelper) this.getApplication()).setGlobalVariable(photo_path);

            // Bitmap imgBitmap = BitmapFactory.decodeFile(photo_path);
            imgBitmap = decodeSampledBitmapFromFile(photo_path, 600, 600);
            ((GlobalVariableHelper) this.getApplication()).setAmbientBitmap(imgBitmap);
            furnishedImg.setImageBitmap(imgBitmap);

            absolutePath = photo_path;
        } else if (intent.hasExtra("uri")) { // "Editar projeto", intent vem com uma Uri indicando a imagem selecionada da galeria
            photo_path = intent.getStringExtra("uri");
            Uri photo_uri = Uri.parse(photo_path);
            String abs_path = getPath(photo_uri);
            ((GlobalVariableHelper) this.getApplication()).setGlobalVariable(abs_path);
            imgBitmap = decodeSampledBitmapFromFile(abs_path, 600, 600);
            ((GlobalVariableHelper) this.getApplication()).setAmbientBitmap(imgBitmap);
            furnishedImg.setImageBitmap(imgBitmap);


        } else if (intent.hasExtra("command")) // vem de alguma activity de móveis
        {
            boolean has_previous_objects = ((GlobalVariableHelper) this.getApplication()).getHasPreviousObjects();
            if (has_previous_objects == FALSE) {

                String global_path = ((GlobalVariableHelper) this.getApplication()).getGlobalVariable();
                Bitmap imgBitmap = decodeSampledBitmapFromFile(global_path, 600, 600);
                furnishedImg.setImageBitmap(imgBitmap);
                ((GlobalVariableHelper) this.getApplication()).setHasPreviousObjects(TRUE);

            } else {
                Bitmap last_bitmap = ((GlobalVariableHelper) this.getApplication()).getLastBitmap();;

                furnishedImg.setImageBitmap(last_bitmap);
            }


            String imageName = intent.getStringExtra("image_name");
            ((GlobalVariableHelper) this.getApplication()).setFurniturePath(imageName);
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
        } else
            Toast.makeText(getBaseContext(), "Algo bem errado, nao deveria acontecer", Toast.LENGTH_LONG).show();


    }


    String absolutePath;

    public void selectFurniture() {

        PopupMenu popup = new PopupMenu(FurnishActivity.this, findViewById(R.id.add_furniture));
        popup.getMenuInflater().inflate(R.menu.furniture_popup_menu, popup.getMenu());

        ((GlobalVariableHelper) this.getApplication()).setLasBitmap(getBitmap(findViewById(R.id.ambient_frame)));

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent objectIntent = new Intent(); // pra ele não reclamar

                switch (item.getItemId()) {
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

        popup.show();
    }


    public void selectDecoration() {

        PopupMenu popup = new PopupMenu(FurnishActivity.this, findViewById(R.id.add_decoration));
        popup.getMenuInflater().inflate(R.menu.decoration_popup_menu, popup.getMenu());

        ((GlobalVariableHelper) this.getApplication()).setLasBitmap(getBitmap(findViewById(R.id.ambient_frame)));

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent objectIntent = new Intent();
                // Do something
                switch (item.getItemId()) {

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

        popup.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_furniture:
                selectFurniture();
                return true;

            case R.id.add_decoration:
                selectDecoration();
                return true;

            case R.id.save_image:
                saveFurnishedImage();
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String result = cursor.getString(idx);
        cursor.close();
        return result;

    }

    private void saveFurnishedImage() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFileFolder = new File(Environment.getExternalStorageDirectory(),
                "FOLDER_PHOTOS");
        imageFileFolder.mkdir();
        FileOutputStream out1 = null;

        File imageFileName = new File(imageFileFolder, timeStamp +".jpg");
        try {
            out1 = new FileOutputStream(imageFileName);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap final_image = getBitmap(findViewById(R.id.ambient_frame));
        final_image.compress(Bitmap.CompressFormat.JPEG, 100, out1);
        try {
            out1.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out1.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getBaseContext(), "Projeto salvo em " + imageFileName.toString(), Toast.LENGTH_LONG).show();

    }
    private Bitmap getBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Toast.makeText(getBaseContext(), "Erro em getBitmap()",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path,
                                                     int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
