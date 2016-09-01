package br.ufu.pgcc.sc.lsbf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bloom
{
    public static final int dimention = 6;    // dimension
    private static final int BLOOM_L = 4;    // the number of bloom(parameter L in lsh)
    private static final int K_hfun = 3;    // the number of h_function(parameter K in lsh)
    private static final int W = 4;        // The default value for lsh algorithm parameter W

    private static final int CHAR_BIT = 8;  // 4294967291 = 2^32-5
    private static final long UH_PRIME_DEFAULT = 4294967291L & 0xffffffff;   // 2^29
    private static final long MAX_HASH_RND = 536870912L & 0xffffffff;     // lsh_r(1,MAX_HASH_RND)

    private static long lsh_r[] = { (451072346 & 0xffffffff), (211732722 & 0xffffffff), (420423195 & 0xffffffff) };
//    private long lsh_r[];    //used to calculate the gindex of lsh, gindex=((lsh_r*a)mod prime)mod tableSize

    private static int globCounter = 0;

    private BloomStruct bloom[];

    public Bloom(int size)
    {
//        System.out.println("---------------------------------------------------------------------------------------");
//        System.out.println("--- CREATE ----------------------------------------------------------------------------");
//        System.out.println("---------------------------------------------------------------------------------------");
        // init g(v) functions
//        lsh_r = new long[K_hfun];
//        System.out.print("\nlsh_r: ");
//        for (int k = 0; k < K_hfun; k++)
//        {
////            lsh_r[k] = Hash.genRandomInt(1, MAX_HASH_RND);
//            System.out.print(lsh_r[k] + " ");
//        }
//        System.out.println();

        // init L bloom filters
        bloom = new BloomStruct[BLOOM_L];
        for (int i = 0; i < BLOOM_L; i++)
        {
            bloom[i] = new BloomStruct();
            bloom[i].a = new short[(size + CHAR_BIT - 1) / CHAR_BIT];
            bloom[i].nFuncs = K_hfun;
            bloom[i].aSize = size;

            // init h(v) functions
            bloom[i].para_a = new float[K_hfun][dimention];
            bloom[i].para_b = new float[K_hfun];

            for (int l = 0; l < bloom[i].nFuncs; l++)
            {
                for (int k = 0; k < dimention; k++)
                {
                    bloom[i].para_a[l][k] = Hash.GAUSSIAN[i][l][k];
//                    bloom[i].para_a[l][k] = Hash.genGaussianRandom();
                }
                bloom[i].para_b[l] = Hash.UNIFORM[i][l];
//                bloom[i].para_b[l] = Hash.genUniformRandom(0, W);
            }

//            System.out.println(String.format("\nBLOOM %d:", i));
//            System.out.println(String.format("\ta[%d]:\t%s", ((size + CHAR_BIT - 1) / CHAR_BIT), Arrays.toString(bloom[i].a)));
//            System.out.print("\tpara_a:");
//            for (int l = 0; l < bloom[i].nFuncs; l++)
//            {
//                System.out.print("\n\t");
//                for (int k = 0; k < dimention; k++)
//                {
//                    System.out.print(String.format("\t%f", bloom[i].para_a[l][k]));
//                }
//            }
//            System.out.print("\n\tpara_b:\t");
//            for (int l = 0; l < bloom[i].nFuncs; l++)
//            {
//                System.out.print(String.format("\t%f", bloom[i].para_b[l]));
//            }
        }
    }

    private static final void setBit(short[] a, long n)
    {
        int pos = (int) (n / CHAR_BIT);
        int posUnsigned = (pos < 0) ? (pos * -1) : pos;
        a[posUnsigned] = (short) (a[posUnsigned] | (1 << (n % CHAR_BIT)));
    }

    private static final boolean getBit(short[] a, long n)
    {
        int pos = (int) (n / CHAR_BIT);
        int posUnsigned = (pos < 0) ? (pos * -1) : pos;
        int result = a[posUnsigned] & (1 << (n % CHAR_BIT));
        return result != 0;
    }

    public void set(float[][] dataset, int dataNum, float R)
    {
//        System.out.println("\n\n---------------------------------------------------------------------------------------");
//        System.out.println("--- SET -------------------------------------------------------------------------------");
//        System.out.println("---------------------------------------------------------------------------------------");
        long index, temp[];
        for (int i = 0; i < BLOOM_L; i++)
        {
            System.out.print(String.format("\n\nBLOOM %d:", i));
            for (int j = 0; j < dataNum; j++)
            {
//                System.out.print(String.format("\n\tdata %d: ", j));
                temp = getVector(bloom[i], dataset[j], R);
                index = getIndex(bloom[i], temp);
                setBit(bloom[i].a, index);
//                System.out.print(String.format("a[%d]=%d ", index/CHAR_BIT, bloom[i].a[(int)(index/CHAR_BIT)]));
//                System.out.print(String.format("\n%d - a[%d] ", index, index/CHAR_BIT));
            }
        }
    }

    private long getIndex(BloomStruct bloom, long[] temp)
    {
        long index = 0;
        for (int i = 0; i < bloom.nFuncs; i++)
        {
            index += temp[i] * lsh_r[i];
            index %= 2L<<32;
        }
        index %= UH_PRIME_DEFAULT;
        index %= bloom.aSize;
        return index;    //gIndex = g(v) =((lsh_r*h(v))mod prime)mod tableSize
    }

    private long[] getVector(BloomStruct bloom, float[] f, float R)
    {
//        if(globCounter == 128)
//            System.out.println("aqui");

//        System.out.println();
        long[] temp = new long[bloom.nFuncs];
        float result;
        for (int i = 0; i < bloom.nFuncs; i++)
        {
            globCounter++;
//            if(globCounter == 128 && i == 2)
//                System.out.println("aqui");
            result = bloom.para_b[i];
            for (int k = 0; k < dimention; k++)
            {
                result += f[k] * (bloom.para_a[i][k] / R);
//                if(globCounter == 128 && i == 2)
                if(globCounter == 384 || globCounter == 447 || globCounter == 926 || globCounter == 1399 || globCounter == 2285)
                    System.out.print(String.format("\nk: %d, f[k]: %f, bloom.para_a[i][k]: %f, R: %f, result: %f", k, f[k], bloom.para_a[i][k], R, result));
            }
//            System.out.print(result +"\n");
            if(globCounter == 384 || globCounter == 447 || globCounter == 926 || globCounter == 1399 || globCounter == 2285)
                System.out.print(String.format("\nresult =  %f", result));
            result /= W;
            if(globCounter == 384 || globCounter == 447 || globCounter == 926 || globCounter == 1399 || globCounter == 2285)
                System.out.print(String.format("\nresult/W =  %f", result));
            temp[i] = (long) Math.floor(result); // h(v) = (a.v+b)/w
            if(globCounter == 384 || globCounter == 447 || globCounter == 926 || globCounter == 1399 || globCounter == 2285)
                System.out.print("\nfloor(result): " + temp[i]);
            temp[i] = unsignedRepresentation(temp[i]);
            if(globCounter == 384 || globCounter == 447 || globCounter == 926 || globCounter == 1399 || globCounter == 2285)
                System.out.print("\nRESULTADO unsigned: " + temp[i] + "\n");
//            System.out.print("\n" + globCounter + ": " + temp[i] + " ");
        }

        return temp;
    }

    private long unsignedRepresentation(long l)
    {
        long uMax = 1l<<32;

        l %= uMax;
        l += uMax;
        l %= uMax;

        return l;
    }

//    public boolean checkSimilar(float[] s, int R)
//    {
//        // check whether a point is similar to a Set
//        // for one bloom, if the point's	index +1 or -1 bit is 1,then we can say this bloom is true
//        // if all bloom are true,then reutrn 1
//        long temp[], index;
//        int j;
//
//        for (int i = 0; i < BLOOM_L; i++)
//        {
//            temp = getVector(bloom[i], s, R);
//            index = getIndex(bloom[i], temp);
//
//            if (!getBit(bloom[i].a, index))
//            {
//                continue;
//            }
//            else
//            {
//                for (j = 0; j < bloom[i].nFuncs; j++)
//                {
//                    temp[j] -= 1;
//                    index = getIndex(bloom[i], temp);
//                    if (getBit(bloom[i].a, index))
//                    {
//                        break;
//                    }
//                    temp[j] += 2;
//                    index = getIndex(bloom[i], temp);
//                    if (getBit(bloom[i].a, index))
//                    {
//                        break;
//                    }
//                    temp[j] -= 1;
//                }
//                if (j == bloom[i].nFuncs)
//                {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }

    private class BloomStruct
    {
        int aSize;          // the size of a
        short a[];
        int nFuncs;         // there are nFuncs function in a bloom(= K)
        float para_a[][];  // parameter a for lsh
        float para_b[];    // parameter b for lsh

        private String para_aToString()
        {
            StringBuilder builder = new StringBuilder();
            for (float[] l : para_a)
            {
                builder.append("\n\t       ");
                List<Float> list = new ArrayList<Float>();
                for (float c : l)
                {
                    list.add(c);
                }
                builder.append(list);
            }
            return  builder.toString();
        }

        @Override
        public String toString()
        {
            return "BloomStruct{" +
                   "\n\taSize=" + aSize +
                   "\n\ta=" + Arrays.toString(a) +
                   "\n\tnFuncs=" + nFuncs +
                   "\n\tpara_a=" + para_aToString() +
                   "\n\tpara_b=" + Arrays.toString(para_b) +
                   "\n}\n";
        }
    }
}