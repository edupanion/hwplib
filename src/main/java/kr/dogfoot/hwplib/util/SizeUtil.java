package kr.dogfoot.hwplib.util;

public class SizeUtil {

    public static int marginToPx(int point) {
        return Math.round(point / 150.f);
    }

    public static long imageSizeToPx(double point) {
        return (long) (point / (72000.0f / 254.0f / 4.f) + 0.5f);
    }

    public static float getMm(int point) {
        return point / (72000.0f / 254.0f) + 0.5f;
    }

    public static int tablePercent(int point) {
        return Math.min((int) (getMm(point) / 0.8), 100);
    }
}
