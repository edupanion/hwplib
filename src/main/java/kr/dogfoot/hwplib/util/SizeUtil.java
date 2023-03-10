package kr.dogfoot.hwplib.util;

public class SizeUtil {

    public static int pointToPixel(int point) {
        return Math.round(point / 150.f);
    }
}
