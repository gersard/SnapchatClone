package cl.mascayanogerardo.snapchatclone.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import cl.mascayanogerardo.snapchatclone.LoginActivity;
import cl.mascayanogerardo.snapchatclone.R;
import cl.mascayanogerardo.snapchatclone.SplashScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment implements SurfaceHolder.Callback {

    private final int CAMERA_REQUEST_CODE = 100;

    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    public static CameraFragment newInstance() {
        CameraFragment cameraFragment = new CameraFragment();
        return cameraFragment;
    }

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);

        surfaceView = root.findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }else{
           initSurfaceHolder();
        }

        root.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        return root;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();

        Camera.Parameters parameters = camera.getParameters();
        camera.setDisplayOrientation(90);

        parameters.setPreviewFrameRate(30);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters );
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initSurfaceHolder();
                }else{
                    Toast.makeText(getContext(), "Necesias dar el permiso de camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initSurfaceHolder(){
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
}
