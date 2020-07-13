package com.meteor.stream.create;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class StreamFileBytesCreateTest {
    @Test
    void bytesReadLineStreamTest() {
        StringBuilder stb = new StringBuilder("line0");
        for (int i = 1; i < 10; i++) {
            stb.append(System.lineSeparator());
            stb.append("line").append(i);
        }
        {
            CharArrayReader charArrayReader = new CharArrayReader(stb.toString().toCharArray());
            try (BufferedReader bufferedReader = new BufferedReader(charArrayReader)) {
                bufferedReader.lines().forEach(s -> System.out.println("line : " + s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        {
            CharArrayReader charArrayReader = new CharArrayReader(stb.toString().toCharArray());

            try (LineNumberReader lineNumberReader = new LineNumberReader(charArrayReader)) {
                System.out.println("lineNumberReader.getLineNumber() : " + lineNumberReader.getLineNumber());
                lineNumberReader.lines()
                        .forEach(s -> System.out.println("lineNumberReader : " + s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        {
            CharArrayReader charArrayReader = new CharArrayReader(stb.toString().toCharArray());
            try (LineNumberReader lineNumberReader = new LineNumberReader(charArrayReader)) {
                String line;
                while ((line = lineNumberReader.readLine()) != null) {
                    System.out.println(lineNumberReader.getLineNumber() + "]lineNumberReader : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}