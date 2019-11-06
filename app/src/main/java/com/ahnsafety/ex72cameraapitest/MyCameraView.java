package com.ahnsafety.ex72cameraapitest;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MyCameraView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;//Surface를 관리하는 공장장 객체

    //카메라 관리자 객체 참조변수
    Camera camera;

    public MyCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder= getHolder();
        holder.addCallback(this);

    }//MyCameraView..

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //카메라뷰가 화면에 그려낼 준비가 될때

        //카메라 객체 열기
        camera= Camera.open(0);//[0:back, 1:front]

        //카메라의 미리보기를 실행 전에 몇가지 설정
        try {
            camera.setPreviewDisplay(holder);

            //카메라는 무조건 가로방향임
            //프리뷰를 90도 회전
            camera.setDisplayOrientation(90);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //surfaceCreated()메소드가 종료된 후
        //자동으로 실행되는 메소드

        //카메라 미리보기 시작
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //미리보기 종료
        camera.stopPreview();

        //카메라 닫기
        camera.release();
        camera=null;
    }
}//class..
