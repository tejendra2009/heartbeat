package com.oppoindia.billionbeats;

import android.content.Context;
import android.content.Intent;

import android.hardware.Camera;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.hardware.Camera.PreviewCallback;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.oppoindia.billionbeats.data.HeartShape;


public class Measure extends AppCompatActivity implements SurfaceHolder.Callback {
    private int SH = 0, SW = 0;
    SurfaceHolder SurfaceHolder;
    SurfaceView SurfaceView;
    public Camera mCamera;
    boolean mPreviewRunning;
    public static final AtomicInteger progressStatus =new AtomicInteger(0);
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    public static Context context;
    private boolean loop = false;
    private static  Handler handler = new Handler();
    private static final AtomicBoolean processing = new AtomicBoolean(false);

    private static double beats = 0;
    private static long startTime = 0;
    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];



    public enum TYPE {
        GREEN, RED
    };

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        HeartShape heart = new HeartShape(this);
       SurfaceView = (SurfaceView) findViewById(R.id.preview);
       // final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);

        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        SurfaceHolder = SurfaceView.getHolder();
        SurfaceHolder.addCallback(this);
        showProgressBar(pb);
       // SurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        context = ApplicationInit.getAppContext();

    }



    /*private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


            mCamera = Camera.open();
            Camera.Parameters parameters = mCamera.getParameters();
            if(parameters.getMaxExposureCompensation() != parameters.getMinExposureCompensation()){
                parameters.setExposureCompensation(0);
            }
            if(parameters.isAutoExposureLockSupported()){
                parameters.setAutoExposureLock(true);
            }
            if(parameters.isAutoWhiteBalanceLockSupported()){
                parameters.setAutoWhiteBalanceLock(true);
            }
            mCamera.setParameters(parameters);
            mCamera.setPreviewCallback(previewCallback);
        progressStatus.set(0);
            startTime = System.currentTimeMillis();



    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }
        Camera.Parameters p = mCamera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(p);
        try {
            mCamera.setPreviewDisplay(arg0);
        } catch (IOException e) {
            progressStatus.set(0);
            e.printStackTrace();
        }
        setCameraDisplayOrientation(mCamera);
        progressStatus.set(0);
        mCamera.startPreview();
        mPreviewRunning = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        progressStatus.set(0);
        mCamera.release();

    }


    public void setCameraDisplayOrientation(android.hardware.Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        android.hardware.Camera.CameraInfo camInfo = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(0, camInfo);

        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (camInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (camInfo.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);

    }


    private static PreviewCallback previewCallback = new PreviewCallback() {


        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (data == null) throw new NullPointerException();
            Camera.Size size = camera.getParameters().getPreviewSize();
            if (size == null) throw new NullPointerException();

            if (!processing.compareAndSet(false, true)) return;

            int width = size.width;
            int height = size.height;

            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            // Log.e("BitAvg ", "imgAvg="+imgAvg);

            if (imgAvg == 0 || imgAvg == 255 ) {
                processing.set(false);

                return;
            }


if(imgAvg< 220){
    processing.set(false);
    progressStatus.getAndSet(0);
    //Toast t = Toast.makeText(context, String.valueOf("Please Place finger on camera!!!"), Toast.LENGTH_SHORT);
   // t.show();
}



            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;

            TYPE newType = currentType;

            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    // Log.e("BEATS", "BEAT!! beats="+beats);
                   
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize) averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
            }

            long endTime = System.currentTimeMillis();

            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if ( progressStatus.get()==100) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 10 || dpm > 220) {
                    startTime = System.currentTimeMillis();
                    beats = 0;

                    processing.set(false);
                    return;
                }

              // Log.e("TIME", "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);*//*

                if (beatsIndex == beatsArraySize) beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);

               /* Toast t = Toast.makeText(context, String.valueOf(beatsAvg), Toast.LENGTH_LONG);
                t.show();*/
              //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString("LAST_MEASURE", String.valueOf(beatsAvg));
               // editor.commit();
                //progressStatus=100;

                startTime = System.currentTimeMillis();
                beats = 0;


                Intent intent = new Intent(context, MainActivity.class);

                intent.putExtra("fragment_tag", "ShareFragment");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);

               // camera.release();

            }
            processing.set(false);

            //intent.
        }

    } ;

private static void showProgressBar(final ProgressBar pb){


    // Start the lengthy operation in a background thread
    new Thread(new Runnable() {
        @Override
        public void run() {
            while(progressStatus.get()<100 ){
                // Update the progress status
              progressStatus.addAndGet(5);

                // Try to sleep the thread for 20 milliseconds
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                  progressStatus.set(0);
                    processing.set(false);
                }

                // Update the progress bar
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pb.setProgress(progressStatus.get());
                        if(progressStatus.get()==100){

processing.set(false);

                        }

                    }
                });
            }
        }
    }).start(); // Start the operation

}
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mCamera.release();
        progressStatus.set(0);
        processing.set(false);
        SurfaceView.getHolder().removeCallback(this);
        finish();

    }
@Override
    public void onPause() {
        super.onPause();
    progressStatus.set(0);
    processing.set(false);

        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            progressStatus.set(0);

            SurfaceView.getHolder().removeCallback(this);
            mCamera.release();
            finish();
        }

    }

    @Override
    protected void onResume() {

        super.onResume();
        progressStatus.set(0);
        //processing.set(false);

    }
    @Override
    public void onStop(){
        super.onStop();
        progressStatus.set(0);
       // processing.set(false);

        /*if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            progressStatus.set(0);

            SurfaceView.getHolder().removeCallback(this);
            mCamera.release();
            finish();
        }*/
    }

}
