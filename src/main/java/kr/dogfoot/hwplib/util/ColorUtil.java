package kr.dogfoot.hwplib.util;

import kr.dogfoot.hwplib.object.etc.Color4Byte;

public class ColorUtil {

    public static String convertToString(Color4Byte color) {
        return "#" + String.format("%02X", color.getR()) + String.format("%02X", color.getG()) + String.format("%02X", color.getB());
    }

}
