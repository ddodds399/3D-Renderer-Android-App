package gd2.gd2render.Core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import gd2.gd2render.Primitives.CubeGL2;
import gd2.gd2render.Shadows.ShadowActivity;
import gd2.gd2render.UserInterface.UserInterfaceRenderer;
import gd2.gd2render.Utils.AssetsManager;

import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

import gd2.gd2render.R;
import gd2.gd2render.Utils.FPSCounter;
import gd2.gd2render.Utils.MemoryUsage;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SurfaceViewObject surfaceView;
    private AssetsManager assetsManager;
    private static Handler hnd;
    /**
     * Variables for use in performance metrics
     */
    FPSCounter fpsCounter = new FPSCounter();
    MemoryUsage memUse = new MemoryUsage();

    /**
     * Variables for use in conifg options
     * window
     */
    public int click = 0;
    ImageButton config1;
    RelativeLayout relative;
    LayoutInflater layoutInflater, inflater;
    PopupWindow popup;
    View popUpWindow;
    public static int colour_value;
    public static int cube_value;
    public static boolean small_size= false;
    public static boolean medium_size = false;
    public static boolean large_size = false;
    public static boolean remove_one = false;
    public static boolean remove_two = false;
    public static boolean remove_three = false;
    Spinner spinner, spinner2;
    ArrayAdapter<CharSequence> adapter1, adapter2;
    Button wireframeB;
    String[] colour1 = {"Red", "Green", "Blue", "Black", "Grey"};
    
    Button button1,button2, button3, button4, button5, button6,button7,button8,button9,button10,button11,button12,button13, button14, button15,button16;
    public TextView fpsText, memoryText;


    static float sDensity;
    int spinner_value = 0;
    int spinner_value2 = 0;

    /**
     * Setting up our MainActivity, it is set to fullscreen mode in landscape.
     *
     * @param savedInstanceState - the instance of the render that will be used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //initialising the surface view and renderer objects
        boolean supportGLES = (info.reqGlEsVersion >= 0x20000);
        //checking that the device supports GLES version 2 or above
        if (supportGLES) {
            setupList();
        } else {
            Log.e("OpenGLES Req", "Your device does not support OpenGLES Version 2 or above. (" + info.reqGlEsVersion + ")");
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        sDensity = metrics.densityDpi;

        AssetManager assetManager = getAssets();
        GD2Renderer gd2Renderer = ((GD2Renderer) getApplicationContext());
        gd2Renderer.setAssetsManager(assetManager);
        assetsManager = gd2Renderer.getAssetsManager();
        try {
            assetsManager.StoreAsset("img/tile.bmp", "tiles");
            assetsManager.StoreAsset("img/brickbm.bmp", "bricks");
            assetsManager.StoreAsset("img/bubble.bmp", "bubble");
            assetsManager.StoreAsset("img/bumpmap.bmp", "bumpmap");
            assetsManager.StoreAsset("img/skybox.bmp", "skybox");
            assetsManager.StoreAsset("img/smoke.bmp", "smoke");
            assetsManager.StoreAsset("img/craterlake_bk.bmp", "skyboxback");
            assetsManager.StoreAsset("img/craterlake_dn.bmp", "skyboxbottom");
            assetsManager.StoreAsset("img/craterlake_ft.bmp", "skyboxfront");
            assetsManager.StoreAsset("img/craterlake_lf.bmp", "skyboxleft");
            assetsManager.StoreAsset("img/craterlake_rt.bmp", "skyboxright");
            assetsManager.StoreAsset("img/craterlake_up.bmp", "skyboxtop");
            assetsManager.StoreAsset("img/bullet.bmp", "bullet");
            assetsManager.StoreAsset("img/target.bmp", "target");
        } catch (Exception e) {
            Log.e("Asset Storing", "Error storing assets");
        }
    }

    //This method makes sure the app stays fullscreen when focus is changed.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * what to do when the back button on the device is pressed
     */
    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        setupList();
    }

    /**
     * method that creates the list for all of the demos and sets them up ready to be drawn
     */
    public void setupList() {
        listView = (ListView) findViewById(R.id.listView);
        String[] shapes = new String[]{"Line", "Triangle", "Cube", "Simple Shader Demo", "Per Vertex Shader Demo", "Per Pixel Shader Demo", "Texture Demo", "Camera Control Demo", "Bump Map Demo","Smoke Particles", "Bubble Particles","SkyBox Demo", "Particles GLES20", "Model Demo" ,"User Interface", "OBJ Model GL10", "Shadow", "Decals", "Static Light"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shapes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // simple line demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 0, 0);
                        setContentView(surfaceView);
                        LinearLayout test = new LinearLayout(getApplicationContext());
                        Button lineX1 = new Button(getApplicationContext());
                        Button lineX2 = new Button(getApplicationContext());
                        Button lineY1 = new Button(getApplicationContext());
                        Button lineY2 = new Button(getApplicationContext());
                        Button lineZ1 = new Button(getApplicationContext());
                        Button lineZ2 = new Button(getApplicationContext());
                        lineX1.setText("X-");
                        lineX2.setText("X+");
                        lineY1.setText("Y-");
                        lineY2.setText("Y+");
                        lineZ1.setText("Z-");
                        lineZ2.setText("Z+");
                        test.addView(lineX1);
                        test.addView(lineX2);
                        test.addView(lineY1);
                        test.addView(lineY2);
                        test.addView(lineZ1);
                        test.addView(lineZ2);
                        test.setGravity(Gravity.LEFT);
                        ViewGroup.LayoutParams lay1;
                        lay1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(test, lay1);

                        //final GLGraphics test = new GLGraphics(surfaceView);
                        lineX1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getLineCamera().translateXMinus();

                            }
                        });
                        lineX2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getLineCamera().translateXPlus();

                            }
                        });
                        lineY1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getLineCamera().translateZMinus();
                            }
                        });
                        lineY2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getLineCamera().translateZPlus();
                            }
                        });
                        lineZ1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getLineCamera().translateYMinus();
                            }
                        });
                        lineZ2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getLineCamera().translateYPlus();
                            }
                        });

                        break;
                    case 1://simple triangle demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 1, 0);
                        setContentView(surfaceView);
                        LinearLayout test2 = new LinearLayout(getApplicationContext());
                        Button triX1 = new Button(getApplicationContext());
                        Button triX2 = new Button(getApplicationContext());
                        Button triY1 = new Button(getApplicationContext());
                        Button triY2 = new Button(getApplicationContext());
                        Button triZ1 = new Button(getApplicationContext());
                        Button triZ2 = new Button(getApplicationContext());
                        triX1.setText("X-");
                        triX2.setText("X+");
                        triY1.setText("Y-");
                        triY2.setText("Y+");
                        triZ1.setText("Z-");
                        triZ2.setText("Z+");
                        test2.addView(triX1);
                        test2.addView(triX2);
                        test2.addView(triY1);
                        test2.addView(triY2);
                        test2.addView(triZ1);
                        test2.addView(triZ2);
                        test2.setGravity(Gravity.LEFT);
                        ViewGroup.LayoutParams lay2;
                        lay2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(test2, lay2);

                        triX1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateXMinus();
                            }
                        });
                        triX2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateXPlus();
                            }

                        });
                        triY1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateZMinus();
                            }
                        });
                        triY2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateZPlus();
                            }
                        });
                        triZ1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateYMinus();
                            }
                        });
                        triZ2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateYPlus();
                            }
                        });

                        break;
                    case 2://simple cube demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 2, 0);
                        setContentView(surfaceView);

                        LinearLayout test3 = new LinearLayout(getApplicationContext());
                        Button cubeX1 = new Button(getApplicationContext());
                        Button cubeX2 = new Button(getApplicationContext());
                        Button cubeY1 = new Button(getApplicationContext());
                        Button cubeY2 = new Button(getApplicationContext());
                        Button cubeZ1 = new Button(getApplicationContext());
                        Button cubeZ2 = new Button(getApplicationContext());
                        cubeX1.setText("X-");
                        cubeX2.setText("X+");
                        cubeY1.setText("Y-");
                        cubeY2.setText("Y+");
                        cubeZ1.setText("Z-");
                        cubeZ2.setText("Z+");
                        test3.addView(cubeX1);
                        test3.addView(cubeX2);
                        test3.addView(cubeY1);
                        test3.addView(cubeY2);
                        test3.addView(cubeZ1);
                        test3.addView(cubeZ2);
                        test3.setGravity(Gravity.LEFT);
                        ViewGroup.LayoutParams lay3;
                        lay3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(test3, lay3);

                        cubeX1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getCubeCamera().translateXMinus();
                                Log.d("test", Float.toString(surfaceView.getSurfaceRenderer().getCubeCamera().getX()));

                            }
                        });
                        cubeX2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getCubeCamera().translateXPlus();
                                Log.d("test", Float.toString(surfaceView.getSurfaceRenderer().getCubeCamera().getX()));

                            }
                        });
                        cubeY1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getCubeCamera().translateZMinus();
                                Log.d("test", Float.toString(surfaceView.getSurfaceRenderer().getCubeCamera().getY()));
                            }
                        });
                        cubeY2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getCubeCamera().translateZPlus();
                                Log.d("test", Float.toString(surfaceView.getSurfaceRenderer().getCubeCamera().getY()));
                            }
                        });
                        cubeZ1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getCubeCamera().translateYMinus();
                                Log.d("test", Float.toString(surfaceView.getSurfaceRenderer().getCubeCamera().getZ()));
                            }
                        });
                        cubeZ2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getCubeCamera().translateYPlus();
                                Log.d("test", Float.toString(surfaceView.getSurfaceRenderer().getCubeCamera().getZ()));
                            }
                        });
                        break;
                    case 3://simple shader demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 0, 1);
                        setContentView(surfaceView);
                        break;
                    case 4://per vertex demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 1, 1);
                        setContentView(surfaceView);
                        break;
                    case 5://per pixel demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 2, 1);
                        setContentView(surfaceView);
                        break;
                    case 6://texture demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 3, 1);
                        setContentView(surfaceView);


                        fpsText = new TextView(getApplicationContext());
                        memoryText = new TextView(getApplicationContext());
                        LinearLayout statsLayout = new LinearLayout(getApplicationContext());
                        fpsText.setText("FPS: " + fpsCounter.getFrameRate());
                        final DecimalFormat df = new DecimalFormat("#.00");
                        memoryText.setText("  RAM Used: " + df.format((MemoryUsage.getUsed() / 1000000.00)) + "MB");

                        hnd = new Handler() {
                            public void handleMessage(Message msg) {
                                if (msg.what == 1) {
                                    fpsText.setText("FPS: " + fpsCounter.getFrameRate());
                                    memoryText.setText("  RAM Used: " + df.format((memUse.getUsed() / 1000000.00)) + "MB");
                                }
                            }
                        };
                        statsLayout.addView(fpsText);
                        statsLayout.addView(memoryText);
                        ViewGroup.LayoutParams lay10;
                        lay10 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(statsLayout, lay10);

                        break;

                    case 7://camera control demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 4, 1);
                        setContentView(surfaceView);
                        LinearLayout test4 = new LinearLayout(getApplicationContext());
                        Button x71 = new Button(getApplicationContext());
                        Button x72 = new Button(getApplicationContext());
                        Button y71 = new Button(getApplicationContext());
                        Button y72 = new Button(getApplicationContext());
                        Button z71 = new Button(getApplicationContext());
                        Button z72 = new Button(getApplicationContext());
                        Button rY71 = new Button(getApplicationContext());
                        Button rY72 = new Button(getApplicationContext());
                        Button rX71 = new Button(getApplicationContext());
                        Button rX72 = new Button(getApplicationContext());
                        y71.setText("Y-");
                        y72.setText("Y+");
                        x71.setText("X-");
                        x72.setText("X+");
                        z71.setText("Z-");
                        z72.setText("Z+");
                        rY71.setText("Turn Right");
                        rY72.setText("Turn Left");
                        rX71.setText("Look Up");
                        rX72.setText("Look Down");
                        test4.addView(x71);
                        test4.addView(x72);
                        test4.addView(y71);
                        test4.addView(y72);
                        test4.addView(z71);
                        test4.addView(z72);
                        test4.addView(rY71);
                        test4.addView(rY72);
                        test4.addView(rX71);
                        test4.addView(rX72);
                        test4.setGravity(Gravity.LEFT);
                        ViewGroup.LayoutParams lay4;
                        lay4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(test4, lay4);

                        x71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateXMinus();
                                Log.d("test x -", Float.toString(surfaceView.getRendererMK2().getCam().getX()));

                            }
                        });
                        x72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateXPlus();
                                Log.d("test x +", Float.toString(surfaceView.getRendererMK2().getCam().getX()));


                            }
                        });
                        y71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateYMinus();
                                Log.d("test y-", Float.toString(surfaceView.getRendererMK2().getCam().getY()));

                            }
                        });
                        y72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateYPlus();
                                Log.d("test y +", Float.toString(surfaceView.getRendererMK2().getCam().getY()));

                            }
                        });
                        z71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateZMinus();
                                Log.d("test z- +", Float.toString(surfaceView.getRendererMK2().getCam().getZ()));
                                //surfaceView.getRendererMK2().setCameraZ(-1.0f);

                            }
                        });
                        z72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateZPlus();
                                Log.d("test z +", Float.toString(surfaceView.getRendererMK2().getCam().getZ()));
                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rY71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraLeft();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rY72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraRight();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rX71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraUp();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rX72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraDown();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });

                        break;
                    case 8://bumpmap demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 5, 1);
                        setContentView(surfaceView);
                        LinearLayout FOV = new LinearLayout(getApplicationContext());
                        Button fovminus = new Button(getApplicationContext());
                        Button fovplus = new Button(getApplicationContext());
                        fovplus.setText("FOV +");
                        fovminus.setText("FOV -");
                        FOV.addView(fovminus);
                        FOV.addView(fovplus);
                        FOV.setGravity(Gravity.LEFT);
                        ViewGroup.LayoutParams fovlay;
                        fovlay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(FOV, fovlay);
                        fovplus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().setFov(+10.0f);
                            }
                        });
                        fovminus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().setFov(-10.0f);
                            }
                        });
                        break;
                    case 9://smoke particle demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 0, 2);
                        setContentView(surfaceView);
                        break;
                    case 10://bubble particle demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 1, 2);
                        setContentView(surfaceView);
                        break;

                    case 11://skybox demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 7, 1);
                        setContentView(surfaceView);
                        test4 = new LinearLayout(getApplicationContext());
                        x71 = new Button(getApplicationContext());
                        x72 = new Button(getApplicationContext());
                        y71 = new Button(getApplicationContext());
                        y72 = new Button(getApplicationContext());
                        z71 = new Button(getApplicationContext());
                        z72 = new Button(getApplicationContext());
                        rY71 = new Button(getApplicationContext());
                        rY72 = new Button(getApplicationContext());
                        rX71 = new Button(getApplicationContext());
                        rX72 = new Button(getApplicationContext());
                        y71.setText("Y-");
                        y72.setText("Y+");
                        x71.setText("X-");
                        x72.setText("X+");
                        z71.setText("Z-");
                        z72.setText("Z+");
                        rY71.setText("Turn Right");
                        rY72.setText("Turn Left");
                        rX71.setText("Look Up");
                        rX72.setText("Look Down");
                        test4.addView(x71);
                        test4.addView(x72);
                        test4.addView(y71);
                        test4.addView(y72);
                        test4.addView(z71);
                        test4.addView(z72);
                        test4.addView(rY71);
                        test4.addView(rY72);
                        test4.addView(rX71);
                        test4.addView(rX72);
                        test4.setGravity(Gravity.LEFT);
                        lay4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(test4, lay4);

                        x71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateXMinus();
                                Log.d("test x -", Float.toString(surfaceView.getRendererMK2().getCam().getX()));

                            }
                        });
                        x72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateXPlus();
                                Log.d("test x +", Float.toString(surfaceView.getRendererMK2().getCam().getX()));


                            }
                        });
                        y71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateYMinus();
                                Log.d("test y-", Float.toString(surfaceView.getRendererMK2().getCam().getY()));

                            }
                        });
                        y72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateYPlus();
                                Log.d("test y +", Float.toString(surfaceView.getRendererMK2().getCam().getY()));

                            }
                        });
                        z71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateZMinus();
                                Log.d("test z- +", Float.toString(surfaceView.getRendererMK2().getCam().getZ()));
                                //surfaceView.getRendererMK2().setCameraZ(-1.0f);

                            }
                        });
                        z72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().translateZPlus();
                                Log.d("test z +", Float.toString(surfaceView.getRendererMK2().getCam().getZ()));
                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rY71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraLeft();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rY72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraRight();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rX71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraUp();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        rX72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getRendererMK2().getCam().rotateCameraDown();
                                surfaceView.getRendererMK2().setCamBol();

                                //surfaceView.getRendererMK2().setCameraZ(1.0f);

                            }
                        });
                        break;
                    case 12://particle GLES20
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 6, 1);
                        setContentView(surfaceView);
                        break;
                    case 13://model demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 8, 1);
                        setContentView(surfaceView);
                        break;
                    case 14://UI demo
                        //UserInterfaceRenderer View
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 0, 4);
                        setContentView(surfaceView);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        final View secondLayerView = inflater.from(getApplicationContext()).inflate(R.layout.user_interface_layout, null, false);
                        addContentView(secondLayerView, lp);
                        //Config image button in bottom left
                        config1 = (ImageButton) findViewById(R.id.imageButton);
                        //Layout of MainActivity
                        relative = (RelativeLayout) findViewById(R.id.rootView);


                        //onclick listener for when image button is clicked to open pop up window
                        config1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                click += 1;
                                Log.d("test", Float.toString(click));


                                Display display = getWindowManager().getDefaultDisplay();
                                Point size = new Point();
                                display.getSize(size);
                                int width = size.x;
                                int height = size.y;
                                layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                popUpWindow = layoutInflater.inflate(R.layout.pop_up_layout, null);

                                popup = new PopupWindow(popUpWindow, (width / 4) * 3, (height / 5) * 4);
                                popup.setBackgroundDrawable(new ColorDrawable());
                                //closes popupwindow when touched outside
                                popup.setOutsideTouchable(true);

                                //popup.setContentView(popUpWindow);
                                //spinner to set background colour
                                spinner = (Spinner) popUpWindow.findViewById(R.id.spinner);
                                adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.colours, android.R.layout.simple_spinner_item);
                                spinner.setAdapter(adapter1);
                                spinner.setSelection(spinner_value);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                        colour_value = spinner.getSelectedItemPosition();
                                        spinner_value = colour_value;
                                        //backgroundColour = colour1[colour_value];

                                    }

                                    //popup.showAtLocation(secondLayerView, Gravity.CENTER, 0, 0);

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                //spinner to set type of cube
                                spinner2 = (Spinner) popUpWindow.findViewById(R.id.spinner2);
                                adapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.cubes, android.R.layout.simple_spinner_item);
                                spinner2.setAdapter(adapter2);
                                spinner2.setSelection(spinner_value2);
                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                        cube_value = spinner2.getSelectedItemPosition();
                                        spinner_value2 = cube_value;
                                        //buttons to move cube
                                        if (spinner_value2 == 0) {
                                            button7.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.smallCubeX -= 1;
                                                }
                                            });
                                            button8.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.smallCubeX += 1;
                                                }
                                            });
                                            button9.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.smallCubeY -= 1;
                                                }
                                            });
                                            button10.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.smallCubeY += 1;
                                                }
                                            });
                                            button11.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.smallCubeZ -= 1;
                                                }
                                            });
                                            button12.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.smallCubeZ += 1;

                                                }
                                            });

                                        } else if (spinner_value2 == 1) {
                                            button7.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.mediumCubeX -= 1;
                                                }
                                            });
                                            button8.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.mediumCubeX += 1;
                                                }
                                            });
                                            button9.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.mediumCubeY -= 1;
                                                }
                                            });
                                            button10.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.mediumCubeY += 1;
                                                }
                                            });
                                            button11.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.mediumCubeZ -= 1;
                                                }
                                            });
                                            button12.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.mediumCubeZ += 1;

                                                }
                                            });

                                        } else if (spinner_value2 == 2) {
                                            button7.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.largeCubeX -= 1;
                                                }
                                            });
                                            button8.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.largeCubeX += 1;
                                                }
                                            });
                                            button9.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.largeCubeY -= 1;
                                                }
                                            });
                                            button10.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.largeCubeY += 1;
                                                }
                                            });
                                            button11.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.largeCubeZ -= 1;
                                                }
                                            });
                                            button12.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    UserInterfaceRenderer.largeCubeZ += 1;

                                                }
                                            });
                                        }

                                        //backgroundColour = colour1[colour_value];

                                    }

                                    //popup.showAtLocation(secondLayerView, Gravity.CENTER, 0, 0);

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                //button1 adds small cube to screen
                                button1 = (Button) popUpWindow.findViewById(R.id.button);
                                if (small_size == true) {
                                    button1.setEnabled(false);
                                }

                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        small_size = true;
                                        button1.setEnabled(false);
                                        remove_one = false;
                                        button4.setEnabled(true);
                                        button7.setEnabled(true);
                                        button8.setEnabled(true);
                                        button9.setEnabled(true);
                                        button10.setEnabled(true);
                                        button11.setEnabled(true);
                                        button12.setEnabled(true);

                                    }
                                });
                                //button2 adds medium cube to screen
                                button2 = (Button) popUpWindow.findViewById(R.id.button2);
                                if (medium_size == true) {
                                    button2.setEnabled(false);
                                }

                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        medium_size = true;
                                        button2.setEnabled(false);
                                        remove_two = false;
                                        button5.setEnabled(true);
                                        button7.setEnabled(true);
                                        button8.setEnabled(true);
                                        button9.setEnabled(true);
                                        button10.setEnabled(true);
                                        button11.setEnabled(true);
                                        button12.setEnabled(true);

                                    }
                                });
                                //button3 adds large cube to screen
                                button3 = (Button) popUpWindow.findViewById(R.id.button3);
                                if (large_size == true) {
                                    button3.setEnabled(false);
                                }

                                button3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        large_size = true;
                                        button3.setEnabled(false);
                                        remove_three = false;
                                        button6.setEnabled(true);
                                        button7.setEnabled(true);
                                        button8.setEnabled(true);
                                        button9.setEnabled(true);
                                        button10.setEnabled(true);
                                        button11.setEnabled(true);
                                        button12.setEnabled(true);

                                    }
                                });
                                //button 4 removes small cube from screen
                                button4 = (Button) popUpWindow.findViewById(R.id.button4);
                                if (remove_one == true) {
                                    button4.setEnabled(false);
                                }
                                button4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        remove_one = true;
                                        button4.setEnabled(false);
                                        button1.setEnabled(true);

                                    }
                                });
                                //button 5 remove medium cube from screen
                                button5 = (Button) popUpWindow.findViewById(R.id.button5);
                                if (remove_two == true) {
                                    button5.setEnabled(false);
                                }
                                button5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        remove_two = true;
                                        button5.setEnabled(false);
                                        button2.setEnabled(true);

                                    }
                                });
                                //button 6 removes large cube from screen
                                button6 = (Button) popUpWindow.findViewById(R.id.button6);
                                if (remove_three == true) {
                                    button6.setEnabled(false);
                                }
                                button6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        remove_three = true;
                                        button6.setEnabled(false);
                                        button3.setEnabled(true);

                                    }
                                });
                                //movement buttons
                                button7 = (Button) popUpWindow.findViewById(R.id.button7);
                                button8 = (Button) popUpWindow.findViewById(R.id.button8);
                                button9 = (Button) popUpWindow.findViewById(R.id.button9);
                                button10 = (Button) popUpWindow.findViewById(R.id.button10);
                                button11 = (Button) popUpWindow.findViewById(R.id.button11);
                                button12 = (Button) popUpWindow.findViewById(R.id.button12);
                                //disables movement buttons if no cube on screen
                                if (small_size == false && medium_size == false && large_size == false) {
                                    button7.setEnabled(false);
                                    button8.setEnabled(false);
                                    button9.setEnabled(false);
                                    button10.setEnabled(false);
                                    button11.setEnabled(false);
                                    button12.setEnabled(false);
                                }


                                if (spinner_value2 == 0) {
                                    button7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.smallCubeX -= 1;
                                        }
                                    });
                                    button8.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.smallCubeX += 1;
                                        }
                                    });
                                    button9.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.smallCubeY -= 1;
                                        }
                                    });
                                    button10.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.smallCubeY += 1;
                                        }
                                    });
                                    button11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.smallCubeZ -= 1;
                                        }
                                    });
                                    button12.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.smallCubeZ += 1;

                                        }
                                    });

                                } else if (spinner_value2 == 1) {
                                    button7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.mediumCubeX -= 1;
                                        }
                                    });
                                    button8.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.mediumCubeX += 1;
                                        }
                                    });
                                    button9.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.mediumCubeY -= 1;
                                        }
                                    });
                                    button10.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.mediumCubeY += 1;
                                        }
                                    });
                                    button11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.mediumCubeZ -= 1;
                                        }
                                    });
                                    button12.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.mediumCubeZ += 1;

                                        }
                                    });

                                } else if (spinner_value2 == 2) {
                                    button7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.largeCubeX -= 1;
                                        }
                                    });
                                    button8.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.largeCubeX += 1;
                                        }
                                    });
                                    button9.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.largeCubeY -= 1;
                                        }
                                    });
                                    button10.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.largeCubeY += 1;
                                        }
                                    });
                                    button11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.largeCubeZ -= 1;
                                        }
                                    });
                                    button12.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UserInterfaceRenderer.largeCubeZ += 1;

                                        }
                                    });

                                }
                                //Reset button to reset cubes to original position
                                button13 = (Button) popUpWindow.findViewById(R.id.button13);
                                button13.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UserInterfaceRenderer.smallCubeX = 0.0f;
                                        UserInterfaceRenderer.smallCubeY = 0.0f;
                                        UserInterfaceRenderer.smallCubeZ = -5f;
                                        UserInterfaceRenderer.mediumCubeX = 5.0f;
                                        UserInterfaceRenderer.mediumCubeY = 0.0f;
                                        UserInterfaceRenderer.mediumCubeZ = -5.0f;
                                        UserInterfaceRenderer.largeCubeX = -5.0f;
                                        UserInterfaceRenderer.largeCubeY = 0.0f;
                                        UserInterfaceRenderer.largeCubeZ = -5.0f;
                                        UserInterfaceRenderer.fov.resetFov();

                                    }
                                });

                                button14 = (Button) popUpWindow.findViewById(R.id.button14);
                                button14.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UserInterfaceRenderer.fov.setFieldOfView(-10.0f);

                                    }
                                });

                                button15 = (Button)popUpWindow.findViewById(R.id.button14);
                                button15.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UserInterfaceRenderer.fov.setFieldOfView(10.0f);

                                    }
                                });

                                button16 = (Button)popUpWindow.findViewById(R.id.button16);
                                button16.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CubeGL2.setMesh();
                                    }
                                });

                                //if(click % 2 ==0){
                                // Log.d("test",Float.toString(click));
                                //popup.dismiss();
                                //}



                                /*Log.d("test" , Float.toString(click));
                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                View thirdLayerView = inflater.from(getApplicationContext()).inflate(R.layout.pop_up_layout,null );

                                //if(click % 2 == 1) {

                                addContentView(thirdLayerView, lp2);
                                Log.d("test" , "here2nd");*/

                                //}
                                //if(click%2 ==0) {
                                //Log.d("test", Float.toString(click));
                                //popup.dismiss();
                                //}
                                popup.showAtLocation(secondLayerView, Gravity.CENTER, 0, 0);
                            }
                        });

                        break;
                    case 15://OBJGL10 demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 3, 5);
                        setContentView(surfaceView);
                        LinearLayout test5 = new LinearLayout(getApplicationContext());
                        triX1 = new Button(getApplicationContext());
                        triX2 = new Button(getApplicationContext());
                        triY1 = new Button(getApplicationContext());
                        triY2 = new Button(getApplicationContext());
                        triZ1 = new Button(getApplicationContext());
                        triZ2 = new Button(getApplicationContext());
                        triX1.setText("X-");
                        triX2.setText("X+");
                        triY1.setText("Y-");
                        triY2.setText("Y+");
                        triZ1.setText("Z-");
                        triZ2.setText("Z+");
                        test5.addView(triX1);
                        test5.addView(triX2);
                        test5.addView(triY1);
                        test5.addView(triY2);
                        test5.addView(triZ1);
                        test5.addView(triZ2);
                        test5.setGravity(Gravity.LEFT);
                        ViewGroup.LayoutParams lay5;
                        lay5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        addContentView(test5, lay5);

                        triX1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateXMinus();
                            }
                        });
                        triX2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateXPlus();
                            }

                        });
                        triY1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateZMinus();
                            }
                        });
                        triY2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateZPlus();
                            }
                        });
                        triZ1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateYMinus();
                            }
                        });
                        triZ2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                surfaceView.getSurfaceRenderer().getTriCamera().translateYPlus();
                            }
                        });

                        break;
                    case 16://Shadow demo
                        Intent i = new Intent(getApplicationContext(), ShadowActivity.class);
                        view.getContext().startActivity(i);
                        break;
                    case 17://Decal Demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 9, 1);
                        setContentView(surfaceView);
                        break;
                    case 18://Static Light Demo
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 10, 1);
                        setContentView(surfaceView);
                        break;
                }

            }
        });
    }

    /**
     * returns the density
     *
     * @return
     */
    public static float getsDensity() {
        return sDensity;
    }

    /**
     * returns the handler object
     *
     * @return
     */
    public static Handler getHandler() {
        return hnd;
    }
}

