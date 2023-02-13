package kr.dogfoot.hwplib.tool.textextractor;

public class ExtractorHelper {

    public static void appendNormalStartTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "<span>");
    }

    public static void appendNormalEndTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "</span>");
    }

    public static void appendEquationTag(TextExtractOption option, StringBuffer sb, String data) {
        insertTag(option, sb, "<equation>");
        sb.append(data);
        insertTag(option, sb, "</equation>");
    }

    public static void appendTableTag(TextExtractOption option, StringBuffer sb, String data) {
        insertTag(option, sb, "\n<br>\n<table border=\"1\" cellpadding=\"20px\">\n");
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