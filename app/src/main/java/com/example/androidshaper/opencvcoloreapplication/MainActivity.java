package com.example.androidshaper.opencvcoloreapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.number.Scale;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2
{
    JavaCameraView javaCameraView;
    Scalar scaleLow,scaleHigh;
    Mat mat1,mat2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView=findViewById(R.id.myCameraView);
        javaCameraView.setCameraIndex(0);
        scaleLow=new Scalar(45,20,10);
        scaleHigh=new Scalar(75,255,255);
        javaCameraView.setCvCameraViewListener(MainActivity.this);
        javaCameraView.enableView();
    }

    @Override
    public void onCameraViewStarted(
            int width,
            int height)
    {
        mat1=new Mat(height,width, CvType.CV_16UC4);
        mat2=new Mat(height,width, CvType.CV_16UC4);

    }

    @Override
    public void onCameraViewStopped()
    {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame)
    {
        Imgproc.cvtColor(inputFrame.rgba(),mat1,Imgproc.COLOR_BGR2HSV);
        Core.inRange(mat1,scaleLow,scaleHigh,mat2);
        return mat2;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (javaCameraView!=null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (javaCameraView!=null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (OpenCVLoader.initDebug())
        {
            Log.d("Test", "static initializer: success");
            javaCameraView.enableView();
        }
        else
        {
            Log.d("Test", "static initializer: not success");

        }
    }
}