package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;
import kr.dogfoot.hwplib.object.etc.Color4Byte;

import java.util.ArrayList;

public class DocInfoExtractor {
    private static final ArrayList<CharShape> charShapeList = new ArrayList<>();
    private static final ArrayList<BorderFill> borderFillList = new ArrayList<>();
    private static final ArrayList<ParaShape> paraShapeList = new ArrayList<>();

    public static void extract(DocInfo doc) {
        charShapeList.addAll(doc.getCharShapeList());
        borderFillList.addAll(doc.getBorderFillList());
        paraShapeList.addAll(doc.getParaShapeList());
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

    public static boolean hasSlash(long fillId) {
        if (borderFillList.size() < fillId) {
            return false;
        }
        return borderFillList.get((int) fillId - 1).getProperty().getSlashDiagonalShape() == SlashDiagonalShape.Slash;
    }

    public static boolean hasBackSlash(long fillId) {
        if (borderFillList.size() < fillId) {
            return false;
        }
        return borderFillList.get((int) fillId - 1).getProperty().getBackSlashDiagonalShape() == BackSlashDiagonalShape.BackSlash;
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

    public static boolean isAlignLeft(int paraShapeId) {
        return paraShapeList.get(paraShapeId).getProperty1().getAlignment() == Alignment.Left;
    }

    public static boolean isAlignRight(int paraShapeId) {
        return paraShapeList.get(paraShapeId).getProperty1().getAlignment() == Alignment.Right;
    }

    public static boolean isAlignCenter(int paraShapeId) {
        return paraShapeList.get(paraShapeId).getProperty1().getAlignment() == Alignment.Center;
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
