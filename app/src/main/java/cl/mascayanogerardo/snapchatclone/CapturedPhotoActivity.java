package cl.mascayanogerardo.snapchatclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CapturedPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_photo);

        ImageView ivPhoto = findViewById(R.id.iv_photo_captured);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            byte[] bytesPhoto = bundle.getByteArray("photo");

            if (bytesPhoto != null) {
                Bitmap decodeBitmap = BitmapFactory.decodeByteArray(bytesPhoto,0,bytesPhoto.length);

                Bitmap rotatedBitmap = rotateBitmap(decodeBitmap);

                ivPhoto.setImageBitmap(rotatedBitmap);
            }

        }




    }

    private Bitmap rotateBitmap(Bitmap decodeBitmap) {
        int width = decodeBitmap.getWidth();
        int height = decodeBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setRotate(90);

        return Bitmap.createBitmap(decodeBitmap,0,0,width,height,matrix,true);
    }
}
