package net.lab0.tools;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class MyString
{
    public static String getHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    /**
     * Removes the diacritics from a string (accents).
     * 
     * @param str
     * @return A string without the diacritics.
     */
    public static String deAccent(String str)
    {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    
    public static void main(String[] args)
    {
        byte[] array = new byte[4];
        array[0] = 0;
        array[1] = (byte) 255;
        array[2] = 15;
        array[3] = (byte) (255 - 15);
        String out = "00ff0ff0";
        System.out.println(getHexString(array));
        System.out.println(out);
    }
}
