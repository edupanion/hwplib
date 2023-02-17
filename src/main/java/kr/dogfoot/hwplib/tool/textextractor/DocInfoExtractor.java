package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.DocInfo;

import java.util.ArrayList;

public class DocInfoExtractor {
    private static final ArrayList<CharShape> charShapeList = new ArrayList<>();

    public static void extract(DocInfo doc) {
        charShapeList.addAll(doc.getCharShapeList());
    }

    public static boolean isUnderLine(long shapeId) {
        if (charShapeList.size() <= shapeId) {
            return false;
        }

        return charShapeList.get((int) shapeId).getProperty().getUnderLineSort().getValue() == 1;
    }
}
