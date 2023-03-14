package kr.dogfoot.hwplib.tool.textextractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractorHelper {
    private static Pattern ltPattern = Pattern.compile("<( *)?([^-<])");
    private static Pattern gtPattern = Pattern.compile("([^->])( *)?>");

    public static void appendMarginTag(TextExtractOption option, StringBuffer sb, int marginLeft) {
        insertTag(option, sb, "<span style=\"margin-left:" + marginLeft + "px;\"/>");
    }
    public static void appendNormalStartTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "<span>");
    }

    public static void appendNormalEndTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "</span>");
    }

    public static void appendEquationTag(TextExtractOption option, StringBuffer sb, String data) {
        insertTag(option, sb, "<span id=\"equation\">");
        String convertedText = data;
        Matcher ltMatcher = ltPattern.matcher(data);
        while (ltMatcher.find()) {
            final String matcherText = ltMatcher.group();
            convertedText = convertedText.replace(matcherText, "&lt; " + ltMatcher.group(2));
        }
        Matcher gtMatcher = gtPattern.matcher(data);
        while (gtMatcher.find()) {
            final String matcherText = gtMatcher.group();
            convertedText = convertedText.replace(matcherText, gtMatcher.group(1) + "&gt;");
        }
        sb.append(convertedText);
        insertTag(option, sb, "</span>");
    }

    public static void appendTableTag(TextExtractOption option, StringBuffer sb, String data) {
        insertTag(option, sb, "<table>");
        sb.append(data);
        insertTag(option, sb, "</table>");
    }

    public static void appendImageTag(TextExtractOption option, StringBuffer sb, int data) {
        insertTag(option, sb, "<img src=");
        sb.append(data);
        insertTag(option, sb, ">");
    }

    public static void insertTag(TextExtractOption option, StringBuffer sb, String tag) {
        if (option.isInsertTag()) {
            sb.append(tag);
        }
    }
}