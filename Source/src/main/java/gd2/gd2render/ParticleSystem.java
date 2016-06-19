package gd2.gd2render;

import java.util.Random;

/**
 * Created by Andy on 24/11/2015.
 */
public class ParticleSystem {

        private Particles[] p1;

    public ParticleSystem(int partNum1){
       p1 = new Particles[partNum1];

        Random arrayNum1 = new Random(System.currentTimeMillis());
        Random arrayNum2 = new Random(System.currentTimeMillis());
        Random arrayNum3 = new Random(System.currentTimeMillis());
        float num1 = arrayNum1.nextFloat();
        float num2 = arrayNum2.nextFloat();
        float num3 = arrayNum3.nextFloat();

        for(int i = 0; i< partNum1; i++){
            p1[i]= new Particles(num1,num2, num3);
        }

    }
}
