package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.util.Pair;
import kr.dogfoot.hwplib.util.SizeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractorHelper {
    private static Pattern ltPattern = Pattern.compile("<( *)?([^-<])");
    private static Pattern gtPattern = Pattern.compile("([^->])( *)?>");
    private static Pattern leqTextPattern = Pattern.compile("leq", Pattern.CASE_INSENSITIVE);
    private static Pattern leTextPattern = Pattern.compile("(ang|bul|pi)?(le)(ft|t)?", Pattern.CASE_INSENSITIVE);
    private static Pattern ltTextPattern = Pattern.compile("(de)?lt");
    private static Pattern geTextPattern = Pattern.compile("ge");
    private static Pattern geqTextPattern = Pattern.compile("geq", Pattern.CASE_INSENSITIVE);
    private static Pattern gtTextPattern = Pattern.compile("gt");

    public static void appendSpanStartTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "<span>");
    }

    public static void appendSpanEndTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "</span>");
    }

    public static void appendEquationTag(TextExtractOption option, StringBuffer sb, String data) {
        String[] equations = data.split("\uDB80\uDC40");
        for (int i = 0; i < equations.length; i++) {
            if (i > 0) {
                sb.append("<img src=\"https://img-wonriedu.cceapi.com/q/d1/52/q.53586/104.jpg\" style=\"width:18px; height:9px;\">");
            }
            insertTag(option, sb, "<span class=\"equation\">");
            addEquation(sb, equations[i]);
            insertTag(option, sb, "</span>");
        }
    }

    public static void appendTableTag(TextExtractOption option, StringBuffer sb, String data, int totalCellWidth, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        insertTag(option, sb, "<table"
                + " width=" + SizeUtil.tablePercent(totalCellWidth) + "%"
                + " style=\"border-collapse: collapse;"
                + " margin-left:" + SizeUtil.imageSizeToPx(marginLeft) + "px;"
                + " margin-top:" + SizeUtil.imageSizeToPx(marginTop) + "px;"
                + " margin-right:" + SizeUtil.imageSizeToPx(marginRight) + "px;"
                + " margin-bottom:" + SizeUtil.imageSizeToPx(marginBottom) + "px;>");
        sb.append(data);
        insertTag(option, sb, "</table>");
    }

    public static void appendImageTag(TextExtractOption option, StringBuffer sb, int data, long width, long height) {
        insertTag(option, sb, "<img width=\""
                + SizeUtil.imageSizeToPx(width) + "\" height=\""
                + SizeUtil.imageSizeToPx(height) + "\"");
        insertTag(option, sb, " src=");
        sb.append(data);
        insertTag(option, sb, ">");
    }

    public static void insertTag(TextExtractOption option, StringBuffer sb, String tag) {
        if (option.isInsertTag()) {
            sb.append(tag);
        }
    }

    public static void addEquation(StringBuffer sb, String data) {
        String convertedText = data;
        convertedText = leqTextPattern.matcher(convertedText).replaceAll("le");
        Matcher leMatcher = leTextPattern.matcher(convertedText);

        final List<Pair> lePositions = new ArrayList<>();
        while (leMatcher.find()) {
            if (leMatcher.group(1) == null && leMatcher.group(3) == null) {
                lePositions.add(new Pair(leMatcher.start(), leMatcher.end()));
            }
        }
        Collections.reverse(lePositions);
        for (Pair position : lePositions) {
            convertedText = convertedText.substring(0, position.getFirst()) + "&le;" + convertedText.substring(position.getSecond());
        }

        Matcher ltTextMatcher = ltTextPattern.matcher(convertedText);
        final List<Pair> ltPositions = new ArrayList<>();
        while (ltTextMatcher.find()) {
            if (ltTextMatcher.group(1) == null) {
                ltPositions.add(new Pair(ltTextMatcher.start(), ltTextMatcher.end()));
            }
        }
        Collections.reverse(ltPositions);
        for (Pair position : ltPositions) {
            convertedText = convertedText.substring(0, position.getFirst()) + "&lt;" + convertedText.substring(position.getSecond());
        }

        convertedText = geqTextPattern.matcher(convertedText).replaceAll("ge");
        convertedText = geTextPattern.matcher(convertedText).replaceAll("&ge;");
        convertedText = gtTextPattern.matcher(convertedText).replaceAll("&gt;");

        Matcher ltMatcher = ltPattern.matcher(convertedText);
        while (ltMatcher.find()) {
            final String matcherText = ltMatcher.group();
            convertedText = convertedText.replace(matcherText, "&lt; " + ltMatcher.group(2));
        }
        Matcher gtMatcher = gtPattern.matcher(convertedText);
        while (gtMatcher.find()) {
            final String matcherText = gtMatcher.group();
            convertedText = convertedText.replace(matcherText, gtMatcher.group(1) + "&gt;");
        }
        sb.append(convertedText);
    }
}