package kr.dogfoot.hwplib.tool.textextractor;

import org.junit.jupiter.api.Test;

class ExtractorHelperTest {
//    final String text = "LEFT | vec{rm PA it} BULLET vec{RMPB it} RIGHT |";
    final String text = "m <k <n ";
    final StringBuffer sb = new StringBuffer();

    @Test
    public void test() {
        ExtractorHelper.addEquation(sb, text);
        System.out.println(sb);
    }
}