package iss.nus.edu.medipalappln.Encryption;

/**
 * Created by root on 19/3/17.
 */

public class CeaserCipher {

    public CeaserCipher() {
    }

    public  String encode(String num, int x)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : num.toCharArray()) {
            sb.append((char) (c + x));
        }
        return sb.toString();
    }

    public  String decode(String num, int x)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : num.toCharArray()) {
            sb.append((char) (c - x));
        }
        return sb.toString();
    }



}