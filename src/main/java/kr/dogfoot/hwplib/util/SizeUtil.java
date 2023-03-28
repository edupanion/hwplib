package kr.dogfoot.hwplib.util;

public class SizeUtil {

    public static int marginToPx(int point) {
        return Math.round(point / 150.f);
    }

    public static long imageSizeToPx(double hwp) {
        return (long) (hwp / (72000.0f / 254.0f / 4.f) + 0.5f);
    }
}
