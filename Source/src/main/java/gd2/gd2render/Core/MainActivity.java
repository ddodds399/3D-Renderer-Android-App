package gd2.gd2render.Core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import gd2.gd2render.Utils.AssetsManager;
import gd2.gd2render.R;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SurfaceViewObject surfaceView;
    static float density;
    private AssetsManager assetsManager;

    //Setting up our MainActivity, it is set to fullscreen mode in landscape.
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
        density = metrics.densityDpi;

        AssetManager assetManager = getAssets();
        GD2Renderer gd2Renderer = ((GD2Renderer) getApplicationContext());
        gd2Renderer.setAssetsManager(assetManager);
        assetsManager = gd2Renderer.getAssetsManager();
        try {
            assetsManager.StoreAsset("tile.bmp", "tiles");
        } catch (Exception e) {
            Log.e("Asset Loading", "Error loading assets");
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

    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        setupList();
    }

    public void setupList() {
        listView = (ListView) findViewById(R.id.listView);
        String[] shapes = new String[]{"Line", "Triangle", "Cube", "Simple Shader Demo", "Per Vertex Shader Demo", "Per Pixel Shader Demo", "Texture Demo", "Camera Control Demo Z *done but not fully tested* "};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shapes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
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
                    case 1:
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

                        //  }
                        //});
                        //b4.setOnClickListener(new View.OnClickListener() {
                        //  @Override
                        //public void onClick(View v) {
                        //test.getGL().glTranslatef(-1.0f,0.0f,0.0f);

                        //}
                        //});
                        break;
                    case 2:
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
                    case 3:
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 0, 1);
                        setContentView(surfaceView);
                        break;
                    case 4:
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 1, 1);
                        setContentView(surfaceView);
                        break;
                    case 5:
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 2, 1);
                        setContentView(surfaceView);
                        break;
                    case 6:
                        surfaceView = new SurfaceViewObject(getApplicationContext(), 3, 1);
                        setContentView(surfaceView);
                        break;

                    case 7:
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
                }
            }
        });

    }

    public static float getDensity() {
        return density;
    }
}
