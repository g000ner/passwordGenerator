package edu.dk.passwordGenerator;

import org.apache.commons.cli.*;

import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws IOException {
        Options options = new Options();
        options.addOption("n", "num", true, "количество слов в пароле");
        options.addOption("r", "replace", true, "замена символов похожими");

        CommandLineParser parser = new DefaultParser();
        int wordsNum = 4;
        boolean replaceLetters = false;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("n")) {
                wordsNum = Integer.parseInt(cmd.getOptionValue('n'));
            }
            if (cmd.hasOption("r")) {
                replaceLetters = Boolean.parseBoolean(cmd.getOptionValue('r'));
            }
        } catch (ParseException | NumberFormatException  e) {
            e.printStackTrace();
        }
        Generator g = new Generator("words.txt", wordsNum, replaceLetters);
        System.out.println(g.generate());
    }
}
