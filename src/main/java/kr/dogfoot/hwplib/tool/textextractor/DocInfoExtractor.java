package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.etc.Color4Byte;

import java.util.ArrayList;

public class DocInfoExtractor {
    private static final ArrayList<CharShape> charShapeList = new ArrayList<>();
    private static final ArrayList<BorderFill> borderFillList = new ArrayList<>();

    public static void extract(DocInfo doc) {
        charShapeList.addAll(doc.getCharShapeList());
        borderFillList.addAll(doc.getBorderFillList());
    }

    public static boolean isUnderLine(long shapeId) {
        if (charShapeList.size() <= shapeId) {
            return false;
        }

        return charShapeList.get((int) shapeId).getProperty().getUnderLineSort().getValue() == 1;
    }

    public static Color4Byte getCellBackgroundColor(long fillId) {
        if (borderFillList.size() < fillId) {
            return new Color4Byte(255, 255, 255);
        }

        final PatternFill fillInfo = borderFillList.get((int) fillId - 1).getFillInfo().getPatternFill();
        if (fillInfo == null) {
            return new Color4Byte(255, 255, 255);
        }
        return fillInfo.getBackColor();
    }
}
