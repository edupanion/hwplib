package kr.dogfoot.hwplib.tool.textextractor;

public class TextExtractOption {
    private TextExtractMethod method;

    private boolean withControlChar;
    private boolean appendEndingLF;
    private boolean insertParaHead;
    private boolean insertTag;

    public TextExtractOption() {
        method = TextExtractMethod.InsertControlTextBetweenParagraphText;
        withControlChar = true;
        appendEndingLF = true;
        insertParaHead = true;
        insertTag = true;
    }

    public TextExtractOption(TextExtractMethod method) {
        this.method = method;
        withControlChar = true;
        appendEndingLF = true;
        insertParaHead = true;
    }

    public TextExtractOption(TextExtractOption that) {
        this.method = that.method;
        this.withControlChar = that.withControlChar;
        this.appendEndingLF = that.appendEndingLF;
        this.insertTag = that.insertTag;
    }

    public TextExtractMethod getMethod() {
        return method;
    }

    public void setMethod(TextExtractMethod method) {
        this.method = method;
    }

    public boolean isWithControlChar() {
        return withControlChar;
    }

    public void setWithControlChar(boolean withControlChar) {
        this.withControlChar = withControlChar;
    }

    public boolean isAppendEndingLF() {
        return appendEndingLF;
    }

    public void setAppendEndingLF(boolean appendEndingLF) {
        this.appendEndingLF = appendEndingLF;
    }

    public boolean isInsertParaHead() {
        return insertParaHead;
    }

    public void setInsertParaHead(boolean insertParaHead) {
        this.insertParaHead = insertParaHead;
    }

    public boolean isInsertTag() {
        return insertTag;
    }

    public void setInsertTag(boolean insertTag) {
        this.insertTag = insertTag;
    }
}
