package br.ufu.pgcc.sc.lsbf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Lsbf
{
    public boolean[] execute(int nDatasetPoints, int nQueryPoints, float R, String datasetFilename, String querysetFilename)
    {
        boolean[] result = new boolean[nQueryPoints];

        float[][] dataset = readFile(nDatasetPoints, datasetFilename);
        float[][] queryset = readFile(nQueryPoints, querysetFilename);
        Bloom bloom = new Bloom(5000);

        bloom.set(dataset, nDatasetPoints, R);

//        String str;
        for (int i = 0; i < nQueryPoints; i++)
        {
//            result[i] = bloom.checkSimilar(queryset[i], R);
//            System.out.println(result[i]);
//            if(bloom.checkSimilar(queryset[i], R))
//                str = "Yes";
//            else
//                str = "No";
//            System.out.println(String.format("Query Point %d:%s", i, str));
        }

        return result;
    }

    private float[][] readFile(int qtd, String filename)
    {
        float[][] set = new float[qtd][Bloom.dimention];

        File file = new File(filename);
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            for (int i = 0; i < qtd; i++)
            {
                String[] s = reader.readLine().split(" ");
                for (int j = 0; j < Bloom.dimention; j++)
                {
                    set[i][j] = Float.parseFloat(s[j]);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return set;
    }
}
