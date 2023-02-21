package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.bodytext.control.*;
import kr.dogfoot.hwplib.object.bodytext.control.gso.GsoControl;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.etc.Color4Byte;
import kr.dogfoot.hwplib.tool.textextractor.paraHead.ParaHeadMaker;
import kr.dogfoot.hwplib.util.ColorUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 컨트롤을 위한 텍스트 추출기 객체
 *
 * @author neolord
 */
public class ForControl {
    /**
     * 컨트롤에서 텍스트를 추출한다.
     *
     * @param c             컨트롤
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Control c,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(c, new TextExtractOption(tem), paraHeadMaker, sb);
    }

    /**
     * 컨트롤에서 텍스트를 추출한다.
     *
     * @param c             컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Control c,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        if (c.isField()) {
        } else {
            switch (c.getType()) {
                case Table:
                    table((ControlTable) c, option, paraHeadMaker, sb);
                    break;
                case Gso:
                    ForGso.extract((GsoControl) c, option, paraHeadMaker, sb);
                    break;
                case Equation:
                    equation((ControlEquation) c, option, sb);
                    break;
                case SectionDefine:
                    break;
                case ColumnDefine:
                    break;
                case Header:
//                    header((ControlHeader) c, option, paraHeadMaker, sb);
                    break;
                case Footer:
//                    footer((ControlFooter) c, option, paraHeadMaker, sb);
                    break;
                case Footnote:
//                    footnote((ControlFootnote) c, option, paraHeadMaker, sb);
                    break;
                case Endnote:
//                    endnote((ControlEndnote) c, option, paraHeadMaker, sb);
                    break;
                case AutoNumber:
                    break;
                case NewNumber:
                    break;
                case PageHide:
                    break;
                case PageOddEvenAdjust:
                    break;
                case PageNumberPosition:
                    break;
                case IndexMark:
                    break;
                case Bookmark:
                    break;
                case OverlappingLetter:
                    break;
                case AdditionalText:
                    additionalText((ControlAdditionalText) c, sb);
                    break;
                case HiddenComment:
                    hiddenComment((ControlHiddenComment) c, option, paraHeadMaker, sb);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 표 컨트롤에서 텍스트를 추출한다
     *
     * @param table         표 컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void table(ControlTable table,
                              TextExtractOption option,
                              ParaHeadMaker paraHeadMaker,
                              StringBuffer sb) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        for (Row r : table.getRowList()) {
            ExtractorHelper.insertTag(option, stringBuffer, "<tr>\n");
            for (Cell c : r.getCellList()) {
                boolean alignCenter = false;
                final int lines = c.getParagraphList().getParagraphs().length;
                if (lines == 1) {
                    final ParaText paraText = c.getParagraphList().getParagraphs()[0].getText();
                    if (paraText != null) {
                        alignCenter = paraText.getCharList().stream().noneMatch(HWPChar::isHangul);
                    }
                }
                Color4Byte color = DocInfoExtractor.getCellBackgroundColor(c.getListHeader().getBorderFillId());
                final boolean isLeftBorderEmpty = DocInfoExtractor.isLeftBorderEmpty(c.getListHeader().getBorderFillId());
                final boolean isRightBorderEmpty = DocInfoExtractor.isRightBorderEmpty(c.getListHeader().getBorderFillId());
                final boolean isTopBorderEmpty = DocInfoExtractor.isTopBorderEmpty(c.getListHeader().getBorderFillId());
                final boolean isBottomBorderEmpty = DocInfoExtractor.isBottomBorderEmpty(c.getListHeader().getBorderFillId());
                final StringBuilder tabBuilder = new StringBuilder();
                tabBuilder.append("<td style=\"")
                        .append("background-color: ").append(ColorUtil.convertToString(color)).append(";")
                        .append(" border-left: ").append((isLeftBorderEmpty ? 0 : "1px solid #000000")).append(";")
                        .append(" border-top: ").append((isTopBorderEmpty ? 0 : "1px solid #000000")).append(";")
                        .append(" border-right: ").append((isRightBorderEmpty ? 0 : "1px solid #000000")).append(";")
                        .append(" border-bottom: ").append((isBottomBorderEmpty ? 0 : "1px solid #000000")).append(";");
                if (alignCenter) {
                    tabBuilder.append(" text-align: center;");
                }
                tabBuilder.append("\" colspan=\"").append(c.getListHeader().getColSpan())
                        .append("\" rowspan=\"").append(c.getListHeader().getRowSpan())
                        .append("\">\n");
                ExtractorHelper.insertTag(option, stringBuffer, tabBuilder.toString());
                ForParagraphList.extract(c.getParagraphList(), option, paraHeadMaker, stringBuffer);
                ExtractorHelper.insertTag(option, stringBuffer, "</td>\n");
            }
            ExtractorHelper.insertTag(option, stringBuffer, "</tr>\n");
        }
        ExtractorHelper.appendTableTag(option, sb, stringBuffer.toString());
    }

    /**
     * 수식 컨트롤에서 텍스트를 추출한다
     *
     * @param equation 수식 컨트롤 객체
     * @param sb       추출된 텍스트를 저정할 StringBuffer 객체
     */
    private static void equation(ControlEquation equation, TextExtractOption option, StringBuffer sb) {
        ExtractorHelper.appendEquationTag(option, sb, equation.getEQEdit().getScript().toUTF16LEString());
    }

    /**
     * 머리말 컨트롤에서 텍스트를 추출한다.
     *
     * @param header        머리말 컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void header(ControlHeader header,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        ForParagraphList.extract(header.getParagraphList(), option, paraHeadMaker, sb);
    }

    /**
     * 꼬리말 컨트롤에서 텍스트를 추출한다.
     *
     * @param footer        꼬리말 컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void footer(ControlFooter footer,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        ForParagraphList.extract(footer.getParagraphList(), option, paraHeadMaker, sb);
    }

    /**
     * 각주 컨트롤에서 텍스트를 추출한다.
     *
     * @param footnote      각주 컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void footnote(ControlFootnote footnote,
                                 TextExtractOption option,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {
        ForParagraphList.extract(footnote.getParagraphList(), option, paraHeadMaker, sb);
    }

    /**
     * 미주 컨트롤에서 텍스트를 추출한다.
     *
     * @param endnote       미주 컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void endnote(ControlEndnote endnote,
                                TextExtractOption option,
                                ParaHeadMaker paraHeadMaker,
                                StringBuffer sb) throws UnsupportedEncodingException {
        ForParagraphList.extract(endnote.getParagraphList(), option, paraHeadMaker, sb);
    }

    /**
     * 덧말 컨트롤에서 텍스트를 추출한다.
     *
     * @param additionalText 덧말 컨트롤
     * @param sb             추출된 텍스트를 저정할 StringBuffer 객체
     */
    private static void additionalText(ControlAdditionalText additionalText,
                                       StringBuffer sb) {
        sb.append(additionalText.getHeader().getMainText()).append("\n");
        sb.append(additionalText.getHeader().getSubText()).append("\n");
    }

    /**
     * 숨은 설명 컨트롤에서 텍스트를 추출한다.
     *
     * @param hiddenComment 숨은 설명 컨트롤
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void hiddenComment(ControlHiddenComment hiddenComment,
                                      TextExtractOption option,
                                      ParaHeadMaker paraHeadMaker,
                                      StringBuffer sb) throws UnsupportedEncodingException {
        ForParagraphList.extract(hiddenComment.getParagraphList(), option, paraHeadMaker, sb);
    }
}
