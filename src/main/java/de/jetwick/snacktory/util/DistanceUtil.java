package de.jetwick.snacktory.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistanceUtil {

    static int count;

    public static int hammingDistance(int[] aList, int[] bList)
    {
        if(Arrays.equals(aList, bList))
            return 0;

        else
        {
            for(int i=0; i<aList.length; i++)
            {
                if (aList[i] != bList[i])
                    count++;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        //Scanner CONSOLE = new Scanner(System.in);

        String a1 = "Sungai Sekanak Dijadijan Pilot Project Wisata Air Palembang";
        String a2 = "rmolsumsel.com - Sungai Sekanak Dijadijan Pilot Project Wisata Air Palembang";
        
        System.out.println(longestSubstr(a1,a2));
    }
    
    public static int longestSubstr(String first, String second) {
        if (first == null || second == null || first.length() == 0 || second.length() == 0) {
            return 0;
        }

        int maxLen = 0;
        int fl = first.length();
        int sl = second.length();
        int[][] table = new int[fl][sl];

        for (int i = 0; i < fl; i++) {
            for (int j = 0; j < sl; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    if (i == 0 || j == 0) {
                        table[i][j] = 1;
                    }
                    else {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    }
                    if (table[i][j] > maxLen) {
                        maxLen = table[i][j];
                    }
                }
            }
        }
        return maxLen;
    }

}
