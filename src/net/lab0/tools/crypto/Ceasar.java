package net.lab0.tools.crypto;

import java.util.ArrayList;
import java.util.List;

public class Ceasar
{
    /**
     * Transform the input string with a Ceasar code. The string will be converted to lower case for the transformation.
     * 
     * @param input
     *            The String to transform.
     * @param offset
     *            The offset to apply for this conversion. The offset must be between 0 and 25 included.
     * @throws IllegalArgumentException
     *             if the offset is invalid.
     * @return A list of the 26 transformed strings.
     */
    public static String standardCeasarTransform(String input, int offset)
    {
        if (offset < 0 || offset > 25)
        {
            throw new IllegalArgumentException("The offset must be between 0 and 25 included, not " + offset);
        }
        List<Character> alphabet = new ArrayList<>(26);
        for (int i = 0; i < 26; ++i)
        {
            alphabet.add((char) ('a' + i));
        }
        
        StringBuilder stringBuilder = new StringBuilder(input.length());
        for (Character c : input.toLowerCase().toCharArray())
        {
            if (c >= 'a' && c <= 'z')
            {
                stringBuilder.append(alphabet.get((c - 'a' + offset) % 26));
            }else{
                stringBuilder.append(c);
            }
        }
        
        return stringBuilder.toString();
    }
    
    public static void main(String[] args)
    {
        System.out
        .println(standardCeasarTransform(
        "map",
        2));
    }
}
