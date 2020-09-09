package com.mgn.mgnrega;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;

import static android.text.TextUtils.split;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;
import static org.opencv.imgproc.Imgproc.putText;

public class Main3Activity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener {
    private CameraBridgeViewBase openCvCameraView;
    private CascadeClassifier cascadeClassifier;
    private Mat grayscaleImage;
    private int absoluteFaceSize;
    int j=0;
    private String filename;
    LBPHFaceRecognizer rec;
    int flag=0,flag1=0,lab,b,w;
    String s;
    private static final String TAG = "Main3Activity";
    DataBase d;
    Database1 db1;
    Database2 db2;
    statedata db3;

    //For looking logs
    ArrayAdapter adapter;
    ArrayList<String> list = new ArrayList<>();

    CameraSource cameraSource;
    private int c=0;
    private int o=0;
    private String user;

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
            rec=LBPHFaceRecognizer.create();
        } catch (Exception e) {
            Log.e("OpenCVActivity", "Error loading cascade", e);
        }

        // And we are ready to go

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!OpenCVLoader.initDebug())
            Log.e("OpenCv", "Unable to load OpenCV");
        else {
            Log.d("OpenCv", "OpenCV loaded");
            initializeOpenCVDependencies();
        }
        Intent intent=getIntent();
        db1=new Database1(this);
        db2=new Database2(this);
        db3=new statedata(this);
        d=new DataBase(this);
        s=intent.getStringExtra("s");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        openCvCameraView = new JavaCameraView(this, -1);
        setContentView(openCvCameraView);
        openCvCameraView.setCvCameraViewListener(this);
        openCvCameraView.setCameraIndex(1);
        openCvCameraView.setMaxFrameSize(1000,800);
        openCvCameraView.enableView();
        train();
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        grayscaleImage = new Mat(height, width, CvType.CV_8UC4);
        absoluteFaceSize = (int) (height * 0.2);
    }
    @Override
    public void onCameraViewStopped() {
        finish();
    }
    @Override
    public Mat onCameraFrame(Mat aInputFrame) {
        // Create a grayscale image
        Mat mRgbaT = aInputFrame.t();
        Core.flip(aInputFrame.t(), mRgbaT, 0);
        Imgproc.resize(mRgbaT, mRgbaT,aInputFrame.size());
        Imgproc.cvtColor(mRgbaT, grayscaleImage,Imgproc.COLOR_BGR2GRAY);
        MatOfRect faces = new MatOfRect();
        if (cascadeClassifier != null) {
            cascadeClassifier.detectMultiScale(grayscaleImage, faces, 1.1, 2, 2,
                    new Size(absoluteFaceSize, absoluteFaceSize), new Size());
        }
        // If there are any faces found, draw a rectangle around it
        Rect[] facesArray = faces.toArray();
        for (int i = 0; i <facesArray.length; i++) {
            Imgproc.rectangle(mRgbaT, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
        }
        recognize(mRgbaT);
        return mRgbaT;
    }

    public void train() {
        ArrayList<Mat> mats = new ArrayList<Mat>();
        ArrayList<Integer> l = new ArrayList<Integer>();
        File root=new File(Environment.getExternalStorageDirectory()+"/face/");
        File[] files= root.listFiles();
        for(File file:files) {
            String src = file.getAbsolutePath();
            String name = file.getName();
            Mat imgread = new Mat();
            imgread = Imgcodecs.imread(src, IMREAD_GRAYSCALE);
            mats.add(imgread);
            String[] s = name.split("-");
            if (s.length > 1) {
                int i = Integer.parseInt(s[0]);
                l.add(i);
            }
        }
        MatOfInt label = new MatOfInt(new int[l.size()]);
        for (int j = 0; j < l.size(); j++) {
            label.put(j, 0, l.get(j));
            System.out.println(label.get(j,0));
        }
        rec.train(mats, label);


    }
    public void recognize(Mat m) {
        Mat g = new Mat();
        Imgproc.cvtColor(m, g, Imgproc.COLOR_BGR2GRAY);
        int[] label = {0};
        double[] confidence = {0.0};
        rec.predict(g, label, confidence);

        if (confidence[0] < 45.00) {

            System.out.println(String.valueOf(label[0]) + " " + String.valueOf(confidence[0]));
            putText(m,String.valueOf(label[0]), new Point(100, 100), 2, 3, new Scalar(0, 255, 0, 255),8);
            lab=label[0];
            flag = 1;
        } else {
            System.out.println("unknown");
            putText(m, "unknown", new Point(100, 100), 2, 3, new Scalar(0, 255, 0, 255), 8);
            lab=1;
        }
    }
    @Override
    public boolean onTouchEvent (MotionEvent event){

        if(lab!=1) {
            String jbcno = db1.jb(lab);

            if (s.equals("checkin")) {
                String t = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                System.out.println(t);
                db1.setcheckin(t, jbcno);
                flag1 = 1;
                Toast.makeText(getApplicationContext(), String.valueOf(lab) + "Checked in", Toast.LENGTH_LONG).show();
            } else if (s.equals("checkout")) {
                String t = db1.getcheckin(jbcno);
                System.out.println(t);
                String[] t1 = t.split("\\.");
                String[] t2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()).split("\\.");
                if (Integer.parseInt(t2[3]) > Integer.parseInt(t1[3]) && (Integer.parseInt(t2[2]) == Integer.parseInt(t1[2]))) {
                    int a = Integer.parseInt(t2[3]) - Integer.parseInt(t1[3]);
                    if (a >= 8) {
                        b = db1.getday(jbcno);
                        b++;
                        w=db3.getweek();
                        System.out.println(String.valueOf(w)+"*******");
                        if (w == 0) {
                            db2.setday1(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        } else if (w == 1) {
                            db2.setday2(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        } else if (w == 2) {
                            db2.setday3(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        } else if (w == 3) {
                            db2.setday4(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        } else if (w == 4) {
                            db2.setday5(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        } else if (w == 5) {
                            db2.setday6(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        } else if (w == 6) {
                            db2.setday7(1, jbcno);
                            db1.setday(b, jbcno);
                            db1.setcheckin("00.00.00.00.00.00", jbcno);
                        }

                        db2.setdaypresent(b, jbcno);
                        double sal = (Double.parseDouble(db3.getsalary()) * b);
                        db2.setsalary(sal, jbcno);
                        Toast.makeText(getApplicationContext(), String.valueOf(lab) + "Checked out", Toast.LENGTH_LONG).show();
                        flag1 = 1;
                    }

                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Worker not present",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Main6Activity.class);
            startActivity(intent);
        }


        if(flag1==1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Too Early Try Later",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Main6Activity.class);
            startActivity(intent);
        }
        return  true;
    }


}
