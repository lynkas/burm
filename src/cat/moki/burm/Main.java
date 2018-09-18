package cat.moki.burm;
import com.alee.laf.WebLookAndFeel;
import com.bulenkov.darcula.DarculaLaf;
import com.ibm.icu.text.CharsetDetector;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alee.managers.language.data.TooltipType.weblaf;

public class Main {
    public static void main(String[] a) {
        BasicLookAndFeel d = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(d);
        }catch (UnsupportedLookAndFeelException e){
            System.out.println("fuck");
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        frame.setMinimumSize(new Dimension(600,300));

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);

//        frame.pack();
        frame.setVisible(true);
    }


    public static String chooseFile(String title){
        FileDialog dialog = new FileDialog((Frame)null, title);
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory()+dialog.getFile();
        System.out.println(file + " chosen.");
        return file;
    }

    public static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel videoLabel = new JLabel("视频");
        videoLabel.setBounds(10,20,50,25);
        panel.add(videoLabel);

        JTextField videoPath = new JTextField(20);
        videoPath.setBounds(60,20,400,25);
        panel.add(videoPath);

        JLabel subtitleLabel = new JLabel("字幕");
        subtitleLabel.setBounds(10,40,50,25);
        panel.add(subtitleLabel);

        JTextField subtitlePath = new JTextField(20);
        subtitlePath.setBounds(60,40,400,25);
        panel.add(subtitlePath);

        JButton choose = new JButton("选择");
        choose.setBounds(470,18,80,50);
        panel.add(choose );
        choose .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String videoLocation =chooseFile("选择视频文件");
                if (!videoLocation.equals("nullnull"))
                    videoPath.setText(videoLocation);
                String subtitleLocation =chooseFile("选择字幕文件");
                if (!subtitleLocation .equals("nullnull"))
                    subtitlePath.setText(subtitleLocation );


            }
        });

    }


    static String subWithdraw(String subtitleFile){
        if (!subtitleFile.endsWith(".ass"))return null;

        File subFile = new File(subtitleFile);
        if (!subFile.exists())return null;

        System.out.println(subFile.exists());
        CharsetDetector detector;
        String encoding;
        String ass;
        try{
            byte[] bytes = Files.readAllBytes(subFile.toPath());
            detector = new CharsetDetector();
            detector.setText(bytes);
            encoding=detector.detect().getName();
            ass=FileUtils.readFileToString(subFile,encoding);
        }catch (IOException e){
            return null;
        }
        System.out.println(encoding);

        return ass;
    }

    static String[] fontChecker(String ass){
        ArrayList<String> need = new ArrayList<>();
        HashSet<String> fontList = new HashSet<>();
        String[] lines = ass.split("(\\r\\n|\\r|\\n)");
        int styleFontPosition=0;
        outter:
        for (String s : lines)
            if (s.startsWith("Format:"))
                for (String p : s.split(":")[1].split(","))
                    if (p.trim().toLowerCase().equals("fontname")){
                        break outter;
                    }else {
                        styleFontPosition++;
                    }
        for (String s : lines)
            if (s.startsWith("Style:"))
                fontList.add(s.split(":")[1].split(",")[styleFontPosition]);

        for (String s :lines) {
            Pattern p = Pattern.compile("\\{\\\\fn([^[\\},\\\\]]*.?)\\}", Pattern.MULTILINE);
            Matcher matcher = p.matcher(s);
            while (matcher.find()) {
                fontList.add(matcher.group(1).replace("{\\fn", "").replace("}", ""));
            }
        }

        for (String s : fontList){
            Font font = new Font(s,0,1);
            if (font.getFamily().equals("Dialog"))
                need.add(s);
        }

        return need.toArray(new String[need.size()]);
    }


}