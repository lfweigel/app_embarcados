package emb.mobiliando;


import android.app.Application;
import android.graphics.Bitmap;

import static java.lang.Boolean.FALSE;

public class GlobalVariableHelper extends Application {

        private String globalVariable;
        private Bitmap ambientBitmap;
        private Bitmap last_bitmap;
        private String furniturePath;
        private boolean has_previous_objects = FALSE;

        public String getGlobalVariable() {

            return globalVariable;

        }

        public void setGlobalVariable(String globalVariable) {

            this.globalVariable = globalVariable;

        }

    public boolean getHasPreviousObjects() {

        return has_previous_objects;

    }

    public void setHasPreviousObjects(boolean bool) {

        this.has_previous_objects = bool;

    }

     public Bitmap getAmbientBitmap() {

         return ambientBitmap;

     }

     public void setLasBitmap(Bitmap bitmap) {

         this.last_bitmap = bitmap;

     }

    public Bitmap getLastBitmap() {

        return last_bitmap;

    }

    public void setAmbientBitmap(Bitmap bitmap) {

        this.ambientBitmap = bitmap;

    }

    public String getFurniturePath() {

        return furniturePath;

    }

    public void setFurniturePath(String path) {

        this.furniturePath = path;

    }


    }

