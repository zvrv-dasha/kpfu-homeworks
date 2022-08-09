package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(new BufferedReader(new InputStreamReader(System.in)).readLine());

        ArrayList<Integer> listData = new ArrayList<Integer>();
        while (inputStream.available() > 0) listData.add(inputStream.read());
        inputStream.close();
        ArrayList<Integer> result = new ArrayList<Integer>(new HashSet<Integer>(listData));
        Collections.sort(result);

        while (!result.isEmpty()) {
            System.out.print(result.get(result.size()-1) + " ");
            result.remove(result.get(result.size()-1));
        }
    }
}