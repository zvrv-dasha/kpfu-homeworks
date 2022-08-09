package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path1 = Paths.get("D:\\Java\\1.txt");
        List<String> list1 = Files.readAllLines(path1);
        System.out.println(list1);

        Pattern pattern = Pattern.compile("[.,!?-]");
        Matcher matcher = pattern.matcher(list1.get(0));
        String t = "";
        while (matcher.find())
            t = list1.get(0).replace(".", "");

        System.out.println(t);
        Path path2 = Paths.get("D:\\Java\\2.txt");
        List<String> list2 = Files.readAllLines(path2);
        System.out.println(list2);

        Map<String, String> map = new HashMap<String, String>();
        String[] temp;

        for (int i = 0; i < list2.size(); i++) {
            temp = list2.get(i).split(" = ");
            map.put(temp[0], temp[1]);
        }

        for (String s : t.split(" ")) {
            System.out.print(map.get(s) + " ");
        }
    }
}