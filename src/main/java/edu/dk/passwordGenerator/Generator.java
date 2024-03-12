package edu.dk.passwordGenerator;

import java.io.*;
import java.util.*;

public class Generator {
    private String dictionaryPath;
    private int wordsNum;
    private boolean replaceLetters;
    private HashMap<Character, Character> replacingCharsMap = new HashMap<Character, Character>() {{
        put('a', '@');
        put('o', '0');
        put('b', '6');
        put('i', '1');
    }};

    public Generator(String dictionaryPath, int wordsNum, boolean replaceLetters) {
        this.dictionaryPath = dictionaryPath;
        this.wordsNum = wordsNum;
        this.replaceLetters = replaceLetters;
    }

    public String generate() throws IOException {
        StringBuilder resultPwd = new StringBuilder();
        List<String> stringList = getStringsListWithLength(wordsNum);
        for (String s : stringList) {
            resultPwd.append(s);
        }
        if (replaceLetters) {
            resultPwd = replaceLetters(resultPwd);
        }
        return resultPwd.toString();
    }

    private StringBuilder replaceLetters(StringBuilder sb) {
        StringBuilder resSb = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            char currChar = sb.charAt(i);
            if (replacingCharsMap.containsKey(currChar)) {
                resSb.append(replacingCharsMap.get(currChar));
            } else {
                resSb.append(currChar);
            }
        }
        return resSb;
    }

    private List<String> getStringsListWithLength(int length) throws IOException {
        BufferedReader reader = createReader();
        long dictLen = countDictionaryWords();
        Long[] wordsNum = getRandomWordsNum(dictLen, length);
        List<String> resultList = new ArrayList<>();
        for (long num : wordsNum) {
            resultList.add(getWordFromLineNumber(num));
        }
        reader.close();
        return resultList;
    }

    private Long[] getRandomWordsNum(long dictLen, int length) {
        Set<Long> nums = new HashSet<>();
        for (int i = 0; i < length; i++) {
            long n = (long) (Math.random() * (dictLen + 1));
            while (nums.contains(n)) {
                n = (long) (Math.random() * (dictLen + 1));
            }
            nums.add(n);
        }
        return nums.toArray(new Long[]{});
    }

    private long countDictionaryWords() throws IOException {
        BufferedReader reader = createReader();
        LineNumberReader lnr = new LineNumberReader(reader);
        while (lnr.readLine() != null) ;
        lnr.close();
        return lnr.getLineNumber();
    }

    private String getWordFromLineNumber(long lineNumber) throws IOException {
        BufferedReader reader = createReader();
        LineNumberReader lnr = new LineNumberReader(reader);
        String line = lnr.readLine();
        for (int i = 0; i < lineNumber; i++) {
            line = lnr.readLine();
        }
        lnr.close();
        return line;
    }

    private BufferedReader createReader() throws UnsupportedEncodingException {
        InputStream is = this.getClass().getResourceAsStream(dictionaryPath);
        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }
}
