package br.ufu.pgcc.sc.lsbf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LsbfTest
extends TestCase
{
    /*private static boolean[] expected = {
        false, false, true, true, false, true, false, true, true, false, false, false, false, false, true, false, true, true, true, false,
        false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true,
        false, false, false, true, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false,
        true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false,
        true, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, true, true, true, false,
        false, false, false, false, false, true, false, true, true, false, false, false, true, false, false, false, false, false, true, false,
        true, false, false, false, false, false, false, false, true, false, false, false, false, false, true, true, false, false, false, false,
        true, false, false, false, false, true, false, false, false, false, false, true, true, true, true, true, true, true, false, false,
        false, false, false, true, false, false, true, true, false, false, false, false, true, false, true, false, false, false, false, false,
        false, true, true, true, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false
    };*/

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LsbfTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(LsbfTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testLsbf()
    {
        String dataset = "/home/likewise-open/NETWORK/mourao/Workspace/projects/mestrado/lsbf-java/src/main/resources/200_data";
        String queryset = "/home/likewise-open/NETWORK/mourao/Workspace/projects/mestrado/lsbf-java/src/main/resources/200_query";
        Lsbf lsbf = new Lsbf();
        boolean[] result = lsbf.execute(200, 200, 2f, dataset, queryset);

        /*int it = 0;
        for (int i = 0; i < expected.length; i++)
        {
            if (expected[i] != result[i])
            {
                it++;
                System.out.println(i + ": " + expected[i] + "\t" + result[i]);
            }
        }
        System.out.println();
        System.out.println(it);

        assertTrue(Arrays.equals(expected, result));*/
    }
}
