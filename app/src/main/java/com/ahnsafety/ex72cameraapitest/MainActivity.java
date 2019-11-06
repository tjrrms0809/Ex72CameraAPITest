package com.ahnsafety.ex72cameraapitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyCameraView cv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv=findViewById(R.id.cv);
        iv=findViewById(R.id.iv);

        //동전퍼미션 작업
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.CAMERA);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }else {
                cv.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 10:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED || grantResults[1]==PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "이 앱 사용 불가", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    cv.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void clickCapture(View view) {

        cv.camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                //카메라가 취득한 사진을  byte[]로 줌
                //byte[]을 Bitmap객체로 생성
                Bitmap bm= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                iv.setImageBitmap(bm);

                //파일 입출력을 이용하기 byte[]을 파일로 저장하기
                //외부메모리 저장하기 예제를 참고하기 ....
            }
        });

    }
}
