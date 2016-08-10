package br.ufu.pgcc.sc.lsbf;

import java.util.Random;

public class Hash
{
    private static final int RAND_MAX = 32767;
    private static float PI = 3.1415926f;

    public static final float[][] UNIFORM = {
        {3.6f, 3.9f, 3.0f},
        {3.4f, 2.5f, 3.6f},
        {2.3f, 0.9f, 1.8f},
        {2.3f, 0.9f, 1.8f}
    };

    public static final float[][][] GAUSSIAN = {
        {
            {0.5f, -0.9f, -0.1f, -1.0f, -0.6f, 1.1f},
            {-0.2f, 0.5f, 0.9f, 1.0f, 0.3f, 0.9f},
            {2.1f, -1.7f, -0.4f, -1.0f, -1.1f, -0.6f}
        },
        {
            {-0.7f, -0.5f, -0.9f, 0.5f, 0.3f, -0.3f},
            {0.7f, 0.6f, 1.3f, -2.6f, 0.1f, 0.1f},
            {-0.5f, -0.7f, -0.7f, -0.8f, -2.3f, 0.3f}
        },
        {
            {-0.1f, -0.4f, -0.5f, -1.7f, 0.2f, 0.1f},
            {-0.2f, 0.8f, -0.5f, -0.5f, -0.3f, -0.2f},
            {-0.4f, 1.6f, 0.2f, -0.3f, -0.9f, 0.3f}
        },
        {
            {-1.4f, 0.6f, -1.5f, 0.6f, 0.3f, -2.0f},
            {0.7f, -0.1f, -0.1f, -0.3f, -1.4f, 0.9f},
            {0.5f, 0.3f, 0.6f, 0.3f, 0.5f, 0.4f}
        }                          
    };

    public static float genUniformRandom(float rangeStart, float rangeEnd)
    {
        float r;

        do
        {
            r = rangeStart + ((rangeEnd - rangeStart) * new Random().nextInt(RAND_MAX + 1) / (float) RAND_MAX);
        } while (r < rangeStart || r > rangeEnd);

        return r;
    }

    public static float genGaussianRandom()
    {
        // Use Box-Muller transform to generate a point from normal distribution.
        float x1, x2, z;

        do
        {
            x1 = genUniformRandom(0.0f, 1.0f);
        } while (x1 == 0); // cannot take log of 0.

        x2 = genUniformRandom(0.0f, 1.0f);
        z = (float) (Math.sqrt(-2.0f * Math.log(x1)) * Math.cos(2.0f * PI * x2));

        return z;
    }

    public static int genRandomInt(int rangeStart, int rangeEnd)
    {
        int r;

        do
        {
            r = rangeStart + (int) ((rangeEnd - rangeStart + 1.0f) * new Random().nextInt(RAND_MAX + 1) / (RAND_MAX + 1.0f));
        } while (r < rangeStart || r > rangeEnd);

        return r;
    }
}

/*

    public final static float PI = 3.1f415926;
    public final static Random RANDOM = new Random(1);

    public static float genUniformRandom(float rangeStart, float rangeEnd)
    {
        float r;
        do
        {
            r = rangeStart + (rangeEnd - rangeStart) * (RANDOM.nextInt() & Integer.MAX_VALUE) / (float)Integer.MAX_VALUE;
        } while(r < rangeStart || r > rangeEnd);
        return r;
    }

    public static float genGaussianRandom()
    {
        // Use Box-Muller transform to generate a point from normal
        // distribution.
        float x1, x2, z;
        do
        {
            x1 = genUniformRandom(0.0f, 1.0f);
        } while(x1 == 0); // cannot take log of 0.
        x2 = genUniformRandom(0.0f, 1.0f);
        z = Math.sqrt(-2.0f * Math.log(x1)) * Math.cos(2.0f * PI * x2);
        return z;
    }

    public static int genRandomInt(int rangeStart, int rangeEnd)
    {
        int r;
        do
        {
            r = rangeStart + (int)((rangeEnd - rangeStart + 1.0f) * (RANDOM.nextInt() & Integer.MAX_VALUE) / (Integer.MAX_VALUE + 1.0f));
        } while(r < rangeStart || r > rangeEnd);
        return r;
    }
    */
