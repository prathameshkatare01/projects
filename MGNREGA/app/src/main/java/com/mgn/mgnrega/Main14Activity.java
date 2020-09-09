package com.mgn.mgnrega;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;



    public class Main14Activity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener {

        private CameraBridgeViewBase openCvCameraView;
        private CascadeClassifier cascadeClassifier;
        private Mat grayscaleImage;
        private int absoluteFaceSize;
        int j = 0;
        private String filename;
        int s1;


        private void initializeOpenCVDependencies() {

            try {
                // Copy the resource into a temp file so OpenCV can load it
                InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_default);
                File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_default.xml");
                FileOutputStream os = new FileOutputStream(mCascadeFile);


                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                is.close();
                os.close();

                // Load the cascade classifier
                cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            } catch (Exception e) {
                Log.e("OpenCVActivity", "Error loading cascade", e);
            }

            // And we are ready to go

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            if (!OpenCVLoader.initDebug())
                Log.e("OpenCv", "Unable to load OpenCV");
            else {
                Log.d("OpenCv", "OpenCV loaded");
                initializeOpenCVDependencies();
            }
            Intent intent = getIntent();
            s1 = intent.getIntExtra("jbint",0);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            openCvCameraView = new JavaCameraView(this, -1);
            setContentView(openCvCameraView);
            openCvCameraView.setCvCameraViewListener(this);
            openCvCameraView.setCameraIndex(1);
            openCvCameraView.setMaxFrameSize(1000, 800);
            openCvCameraView.enableView();
        }


        @Override
        public void onCameraViewStarted(int width, int height) {
            grayscaleImage = new Mat(height, width, CvType.CV_8UC4);

            // The faces will be a 20% of the height of the screen
            absoluteFaceSize = (int) (height * 0.2);
        }

        @Override
        public void onCameraViewStopped() {

        }


        @Override
        public Mat onCameraFrame(Mat aInputFrame) {
            // Create a grayscale image
            Mat mRgbaT = aInputFrame.t();
            Core.flip(aInputFrame.t(), mRgbaT, 0);
            Imgproc.resize(mRgbaT, mRgbaT, aInputFrame.size());
            Imgproc.cvtColor(mRgbaT, grayscaleImage, Imgproc.COLOR_RGBA2RGB);

            MatOfRect faces = new MatOfRect();

            // Use the classifier to detect faces
            if (cascadeClassifier != null) {
                cascadeClassifier.detectMultiScale(grayscaleImage, faces, 1.1, 2, 2,
                        new Size(absoluteFaceSize, absoluteFaceSize), new Size());
            }

            // If there are any faces found, draw a rectangle around it
            Rect[] facesArray = faces.toArray();
            for (int i = 0; i < facesArray.length; i++) {
                Imgproc.rectangle(mRgbaT, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);

            }
            if (j <= 10 && facesArray != null && facesArray.length > 0) {
                Mat m = new Mat();
                Rect r = facesArray[0];
                m = mRgbaT.submat(r);
                saveimage(m, j);
                j++;
            }
            if (j > 10) {

           finish();
            }
            return mRgbaT;
        }

       public void onDestroy() {
           super.onDestroy();
           Intent intent = new Intent(this, Main8Activity.class);
           startActivity(intent);

       }

        private void saveimage(Mat aInputFrame, int i) {
            Mat im = new Mat();
            Imgproc.cvtColor(aInputFrame, im, Imgproc.COLOR_BGR2GRAY, 3);
            File fp = new File(Environment.getExternalStorageDirectory() + "/face/");
            fp.mkdirs();
            File f = new File(fp, String.valueOf(s1) + "-" + String.valueOf(i) + ".png");
            filename = f.toString();
            imwrite(filename, im);
        }


    }
