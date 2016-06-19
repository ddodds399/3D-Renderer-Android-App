package gd2.gd2render;

import android.test.InstrumentationTestCase;
import android.util.Log;
import android.view.TextureView;

import gd2.gd2render.Core.MainActivity;
import gd2.gd2render.Utils.AppContext;
import gd2.gd2render.Utils.TextureBuilder;

public class TODOTextureTest extends InstrumentationTestCase
{
    /**
     * testTexture1 - simple texture creation
     * testTexture2 - mip map texture creation - TRUE
     * testTexture3 - mip map texture creation - FALSE
     */
        private int testTexture1;
        private int testTexture2;
        private int testTexture3;
        private TextureBuilder tb;

    public void TODOtestTextures(){


        //setting up a Gl context in order for the texture builder to run correctly
        MainActivity testAct = new MainActivity();
        TextureView testView = new TextureView(AppContext.getContext());
        tb = new TextureBuilder(testView.getContext());
        testTexture1 = tb.createTexture(R.drawable.tiles);
        testTexture2 = tb.createTexture(R.drawable.tiles, 1);
        testTexture3 = tb.createTexture(R.drawable.tiles, 0);

        final int[]texArray = new int[]{
        testTexture1,testTexture2,testTexture3
        };
        for(int element:texArray){
            assertNotNull(element);
            Log.d("TextureTest", Integer.toString(element));
        }
    }
}

