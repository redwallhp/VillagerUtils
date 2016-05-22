package io.github.redwallhp.villagerutils.helpers;


public class StringHelper {


    public static String joinArray(String separator, String[] arr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String s : arr) {
            sb.append(s);
            if (i < (arr.length - 1)) {
                sb.append(separator);
            }
            i++;
        }
        return sb.toString().trim();
    }


}
