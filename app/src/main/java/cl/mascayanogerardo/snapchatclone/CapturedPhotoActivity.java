package cl.mascayanogerardo.snapchatclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CapturedPhotoActivity extends AppCompatActivity {

    private String uid;
    Bitmap rotatedBitmap
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

                rotatedBitmap = rotateBitmap(decodeBitmap);

                ivPhoto.setImageBitmap(rotatedBitmap);
            }

        }

        uid = FirebaseAuth.getInstance().getUid();

        findViewById(R.id.save_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToStories();
            }
        });


    }

    private void saveToStories() {
        final DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("story");
        final String key = userDbRef.push().getKey();

        StorageReference filePath = FirebaseStorage.getInstance().getReference().child("photos").child(key);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] dataToUplado = baos.toByteArray();
        UploadTask uploadTask = filePath.putBytes(dataToUplado);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    Uri imageUrl = task.getResult().getDownloadUrl();
                    long currentTimestamp = System.currentTimeMillis();
                    long endTimestamp = currentTimestamp + (24*60*60*100);

                    Map<String, Object> mapToUpload = new HashMap<>();
                    mapToUpload.put("imageUrl",imageUrl.toString());
                    mapToUpload.put("timestampBeg",currentTimestamp);
                    mapToUpload.put("timestampEnd",endTimestamp);

                    userDbRef.child(key).setValue(mapToUpload);

                    finish();

                }else{
                    // Failure
                    finish();
                }
            }
        });
    }

    private Bitmap rotateBitmap(Bitmap decodeBitmap) {
        int width = decodeBitmap.getWidth();
        int height = decodeBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setRotate(90);

        return Bitmap.createBitmap(decodeBitmap,0,0,width,height,matrix,true);
    }
}
