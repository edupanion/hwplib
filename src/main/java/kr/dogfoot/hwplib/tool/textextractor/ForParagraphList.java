package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.bodytext.ParagraphListInterface;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.CharPositionShapeIdPair;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharNormal;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharType;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.tool.textextractor.paraHead.ParaHeadMaker;
import kr.dogfoot.hwplib.util.SizeUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 문단 리스트를 위한 텍스트 추출기 객체
 *
 * @author neolord
 */
public class ForParagraphList {
    /**
     * 문단 리스트에서 텍스트를 추출한다.
     *
     * @param paragraphList 문단 리스트
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(ParagraphListInterface paragraphList,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(paragraphList,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * 문단 리스트에서 텍스트를 추출한다.
     *
     * @param paragraphList 문단 리스트
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(ParagraphListInterface paragraphList,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        if (paragraphList == null) {
            return;
        }
        for (Paragraph p : paragraphList) {
            paragraph(p,
                    option,
                    paraHeadMaker,
                    sb);
        }
    }


    public static void extract(ParagraphListInterface paragraphList,
                               int startParaIndex,
                               int startCharIndex,
                               int endParaIndex,
                               int endCharIndex,
                               TextExtractOption option,
                               StringBuffer sb) throws UnsupportedEncodingException {
        if (startParaIndex == endParaIndex) {
            ForParagraphList.extract(paragraphList.getParagraph(startParaIndex),
                    startCharIndex, endCharIndex, option, null, sb);
        } else {
            ForParagraphList.extract(paragraphList.getParagraph(startParaIndex),
                    startCharIndex, option, null, sb);
            if (startParaIndex + 1 < endParaIndex) {
                for (int paraIndex = startParaIndex + 1; paraIndex <= endParaIndex - 1; paraIndex++) {
                    ForParagraphList.extract(paragraphList.getParagraph(paraIndex), -1, 0xffff, option, null, sb);
                }
            }

            ForParagraphList.extract(paragraphList.getParagraph(endParaIndex),
                    -1, endCharIndex, option, null, sb);
        }
    }

    public static void extract(ParagraphListInterface paragraphList,
                               int startParaIndex,
                               int startCharIndex,
                               int endParaIndex,
                               int endCharIndex,
                               TextExtractMethod tem,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(paragraphList,
                startParaIndex,
                startCharIndex,
                endParaIndex,
                endCharIndex,
                new TextExtractOption(tem),
                sb);
    }

    /**
     * startIndex 순번 부터 끝 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {

        extract(p,
                startIndex,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * startIndex 순번 부터 끝 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        ParaText pt = p.getText();
        if (pt != null) {
            extract(p,
                    startIndex,
                    pt.getCharList().size() - 1,
                    option,
                    paraHeadMaker,
                    sb);
        }
    }

    /**
     * startIndex 순번 부터 endIndex 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param endIndex      끝 순번
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               int endIndex,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(p,
                startIndex,
                endIndex,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * startIndex 순번 부터 endIndex 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param endIndex      끝 순번
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               int endIndex,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {

        ArrayList<Control> controlList = new ArrayList<Control>();
        ParaText pt = p.getText();
        if (pt != null) {
            int controlIndex = 0;

            int charCount = pt.getCharList().size();
            for (int charIndex = 0; charIndex < charCount; charIndex++) {
                HWPChar ch = pt.getCharList().get(charIndex);
                switch (ch.getType()) {
                    case Normal:
                        if (startIndex <= charIndex && charIndex <= endIndex) {
                            normalText(ch, sb);
                        }
                        break;
                    case ControlChar:
                    case ControlInline:
                        if (option.isWithControlChar()) {
                            controlText(ch, option, sb);
                        }
                        break;
                    case ControlExtend:
                        if (startIndex <= charIndex && charIndex <= endIndex) {
                            if (option.getMethod() == TextExtractMethod.InsertControlTextBetweenParagraphText) {
                                ForControl.extract(p.getControlList().get(controlIndex),
                                        option,
                                        paraHeadMaker,
                                        sb);
                            } else {
                                controlList.add(p.getControlList()
                                        .get(controlIndex));
                            }
                        }
                        controlIndex++;
                        break;
                    default:
                        break;
                }
            }
        }

        if (option.getMethod() == TextExtractMethod.AppendControlTextAfterParagraphText) {
            controls(controlList, option, paraHeadMaker, sb);
        }
    }

    /**
     * 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void paragraph(Paragraph p,
                                 TextExtractMethod tem,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {
        paragraph(p,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void paragraph(Paragraph p,
                                 TextExtractOption option,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {
        if (option.isInsertParaHead() && paraHeadMaker != null) {
            String headString = paraHeadMaker.paraHeadString(p);
            sb.append(headString);
        }

        ParaText pt = p.getText();
        if (pt != null) {
            final int shapeId = p.getHeader().getParaShapeId();
            final int indent = DocInfoExtractor.getIndent(shapeId);
            final int leftMargin = DocInfoExtractor.getLeftMargin(shapeId);
            final boolean isAlignRight = DocInfoExtractor.isAlignRight(shapeId);
            final boolean isAlignCenter = DocInfoExtractor.isAlignCenter(shapeId);
            sb.append("<p style=\"line-height: 1.6;");
            if (isAlignRight) {
                sb.append(" text-align: right;");
            } else if (isAlignCenter) {
                sb.append(" text-align: center;");
            }
            sb.append("\">");
            final ArrayList<CharPositionShapeIdPair> charShapeList = p.getCharShape().getPositonShapeIdPairList();
            HWPCharType lastType = null;
            int controlIndex = 0;
            int lineIndex = 0;
            int lineFirstCharIndex = 0;
            int marginLeft = 0;
            boolean underline = false;
            boolean isFirstChildOfLine = true;
            for (int i = 0; i < pt.getCharList().size(); i++) {
                final HWPChar ch = pt.getCharList().get(i);
                if (isFirstChildOfLine) {
                    lineFirstCharIndex = sb.length();
                    marginLeft = SizeUtil.marginToPx(lineIndex == 0 ? leftMargin : leftMargin - indent);
                    isFirstChildOfLine = false;
                }
                switch (ch.getType()) {
                    case Normal:
                        if (lastType != HWPCharType.Normal) {
                            ExtractorHelper.appendSpanStartTag(option, sb);
                        }
                        final int index = i;
                        final List<CharPositionShapeIdPair> shapeList = charShapeList.stream().filter((charShape) -> charShape.getPosition() == index).collect(Collectors.toList());
                        if (!shapeList.isEmpty()) {
                            final CharPositionShapeIdPair shape = shapeList.get(0);
                            final boolean isUnderLine = DocInfoExtractor.isUnderLine(shape.getShapeId());
                            if (underline != isUnderLine) {
                                underline = isUnderLine;
                                if (underline) {
                                    ExtractorHelper.insertTag(option, sb, "<u>");
                                } else {
                                    ExtractorHelper.insertTag(option, sb, "</u>");
                                }
                            }
                        }
                        normalText(ch, sb);
                        break;
                    case ControlChar:
                    case ControlInline:
                        if (option.isWithControlChar()) {
                            if (lastType == HWPCharType.Normal) {
                                addUnderLineEndTag(option, sb, underline);
                            }
                            if (ch.isLineBreak()) {
                                lineIndex++;
                                isFirstChildOfLine = true;
                            }
                            String lineText = sb.substring(lineFirstCharIndex, sb.length());
                            if (!lineText.contains("img") || lineText.contains("span")) {
                                String span = "<span style=\"";
                                boolean wrap = false;
                                if (lineText.contains("img")) {
                                    wrap = true;
                                    span += "display: flex; ";
                                }
                                if (marginLeft > 0) {
                                    wrap = true;
                                    span += "margin-left: " + marginLeft + "px;";
                                }
                                span += " align-items: center;\">";

                                if (wrap) {
                                    sb.delete(lineFirstCharIndex, sb.length());
                                    ExtractorHelper.insertTag(option, sb, span);
                                    sb.append(lineText);
                                    ExtractorHelper.appendSpanEndTag(option, sb);
                                }
                            }

                            controlText(ch, option, sb);
                        }
                        break;
                    case ControlExtend:
                        if (lastType == HWPCharType.Normal) {
                            addUnderLineEndTag(option, sb, underline);
                        }
                        if (option.getMethod() == TextExtractMethod.InsertControlTextBetweenParagraphText) {
                            ForControl.extract(p.getControlList().get(controlIndex),
                                    option,
                                    paraHeadMaker,
                                    sb);
                            controlIndex++;
                        }
                        break;
                    default:
                        break;
                }
                lastType = ch.getType();
            }
        } else {
            addNewLineTag(option, sb);
        }
        if (option.getMethod() == TextExtractMethod.AppendControlTextAfterParagraphText) {
            controls(p.getControlList(), option, paraHeadMaker, sb);
        }
    }

    private static void addNewLineTag(TextExtractOption option, StringBuffer sb) {
        if (option.isInsertTag()) {
            sb.append("<br>");
        }
    }

    private static void addParaEndTag(TextExtractOption option, StringBuffer sb) {
        if (option.isInsertTag()) {
            sb.append("</p>");
        }
    }

    private static void addUnderLineEndTag(TextExtractOption option, StringBuffer sb, boolean underline) {
        if (underline) {
            ExtractorHelper.insertTag(option, sb, "</u>");
        }
        ExtractorHelper.appendSpanEndTag(option, sb);
    }

    /**
     * 일반 문자에서 문자를 추출한다.
     *
     * @param ch 한글 문자
     * @param sb 추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void normalText(HWPChar ch, StringBuffer sb) throws UnsupportedEncodingException {
        final String character = ((HWPCharNormal) ch).getCh();
        if (Objects.equals(character, " ")) {
            sb.append("&nbsp;");
        } else if (Objects.equals(character, "<")) {
            sb.append("&lt;");
        } else if (Objects.equals(character, ">")) {
            sb.append("&gt;");
        } else {
            sb.append(character);
        }
    }

    private static void controlText(HWPChar ch, TextExtractOption option, StringBuffer sb) {
        switch (ch.getCode()) {
            case 9:
                ExtractorHelper.appendSpanStartTag(option, sb);
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                ExtractorHelper.appendSpanEndTag(option, sb);
                break;
            case 10:
                addNewLineTag(option, sb);
                break;
            case 13:
                addParaEndTag(option, sb);
                break;
            case 24:
                sb.append("_");
                break;
        }
    }

    /**
     * 컨트롤 리스트에 포함된 컨트롤에서 텍스트를 추출한다.
     *
     * @param controlList   컨트롤 리스트
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void controls(ArrayList<Control> controlList,
                                 TextExtractOption option,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {

        if (controlList != null) {
            for (Control c : controlList) {
                ForControl.extract(c, option, paraHeadMaker, sb);
            }
        }
    }

}
