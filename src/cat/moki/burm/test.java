package cat.moki.burm;
import com.ibm.icu.text.CharsetDetector;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
//    String a = "";
//
//    public static void main(String[] args) {
//        Pattern p = Pattern.compile("\\{\\\\fn([^\\}]*.?)\\}", Pattern.MULTILINE);
//        Matcher matcher = p.matcher("Dialogue: 0,0:00:27.31,0:00:29.05,*Default,NTP,0,0,0,,太棒了  打中斯坦了\\N{\\fn微软雅黑}{\\fn微软雅黑}{\\fn微软}{\\fn微软黑}{\\fs17}{\\b0}{\\c&HFFFFFF&}{\\3c&H2F2F2F&}{\\4c&H000000&}{\\shad0.5}Yes! Stan shot!\n");
//
//        // check occurrence
//        while (matcher.find()) {
//            System.out.println(matcher.group(1).split("\n").length);
//        }
//
//    }

    public static void main(String[] args) throws IOException{
        String c = Main.subWithdraw("C:\\Users\\catmo\\Desktop\\Gravity.Falls.S01E16.Carpet.Diem.1080p.BluRay.REMUX.AVC.DTS-HD.MA.5.1-CTRmkv.ass");
        for (String i : Main.fontChecker(c)){
            System.out.println(i);
        }
    }

    }