package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternType;
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
        final PatternFill fillInfo = getPatternFill(fillId);
        if (fillInfo == null) {
            return new Color4Byte(255, 255, 255);
        }
        return fillInfo.getBackColor();
    }

    public static boolean hasVerticalLine(long fillId) {
        final PatternFill fillInfo = getPatternFill(fillId);
        if (fillInfo == null) {
            return false;
        }
        return fillInfo.getPatternType() == PatternType.VerticalLine;
    }

    public static boolean isLeftBorderEmpty(long fillId) {
        final BorderFill borderFill = getBorderFill(fillId);
        if (borderFill == null) {
            return false;
        }
        return borderFill.getLeftBorder().getType() == BorderType.None;
    }

    public static boolean isRightBorderEmpty(long fillId) {
        final BorderFill borderFill = getBorderFill(fillId);
        if (borderFill == null) {
            return false;
        }
        return borderFill.getRightBorder().getType() == BorderType.None;
    }

    public static boolean isTopBorderEmpty(long fillId) {
        final BorderFill borderFill = getBorderFill(fillId);
        if (borderFill == null) {
            return false;
        }
        return borderFill.getTopBorder().getType() == BorderType.None;
    }

    public static boolean isBottomBorderEmpty(long fillId) {
        final BorderFill borderFill = getBorderFill(fillId);
        if (borderFill == null) {
            return false;
        }
        return borderFill.getBottomBorder().getType() == BorderType.None;
    }

    private static PatternFill getPatternFill(long fillId) {
        if (borderFillList.size() < fillId) {
            return null;
        }

        return borderFillList.get((int) fillId - 1).getFillInfo().getPatternFill();
    }

    private static BorderFill getBorderFill(long fillId) {
        if (borderFillList.size() < fillId) {
            return null;
        }

        return borderFillList.get((int) fillId - 1);
    }
}
