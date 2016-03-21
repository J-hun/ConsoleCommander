/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolcommander;

import java.util.Scanner;

/**
 *
 * @author Joco
 */
public final class ConsoleIOHelper {

    public static String inputString(String question, String regex) {
        return inputString(question, regex, "");
    }

    public static String inputString(String question, String regex, String help) {
        String answer = "";
        boolean matches = false;
        System.out.print(messageBox(question));
        while (!matches) {
            answer = new Scanner(System.in).next();
            if (answer.matches(regex)) {
                matches = true;
            } else {
                System.out.print(messageBox(help, question));
            }
        }

        return question;
    }

    public static int inputInt(String question) {
        int answer = -1;
        boolean matches = false;
        System.out.print(messageBox(question));
        while (!matches) {

            try {

                answer = new Scanner(System.in).nextInt();
                matches = true;
            } catch (Exception e) {
                System.out.println(messageBox("Csak számokat írhatsz be...!", question));
            }
        }

        return answer;
    }

    public static String messageBox(String... messages) {

        if (messages.length > 0) {
            int maxLength = messages[0].length();
            for (int i = 1; i < messages.length; i++) {
                if (maxLength < messages[i].length()) {
                    maxLength = messages[i].length();
                }
            }
            if (maxLength > 0) {
                StringBuilder sb = new StringBuilder("");
                sb.append(String.format("%s%-" + maxLength + "s%s", "+ ", " ", " +\n").replace(" ", "-"));
                for (String message : messages) {
                    if (message.length() > 0) {
                        sb.append("| ").append(String.format("%-" + maxLength + "s", message)).append(" |\n");
                    }
                }
                sb.append(String.format("%s%-" + maxLength + "s%s", "+ ", " ", " +").replace(" ", "-"));
                return sb.toString();
            }
        }
        return "+-+\n"
                + "| |\n"
                + "+-+";
    }

    public static String organizeStrings(int size, String... columns) {
        int remainder = size % columns.length;
        size = size / columns.length;
        System.out.println(size);
        StringBuilder sb = new StringBuilder();
        for (String column : columns) {
            if (remainder > 0) {
                sb.append(allignToCenter(column, size + 1));
            } else {
                sb.append(allignToCenter(column, size));
            }
        }
        return sb.toString();
    }

    public static String allignToCenter(String s, int size) {
        if (s.length() < size - 1) {
            int halfSize = (size - s.length()) / 2;
            int remainder = (size - s.length()) % 2;
            String pattern = "%-" + halfSize + "s%s%" + (halfSize + remainder) + "s";
            return String.format(pattern, "|", s, "|").replace(" ", "-");
        }

        return String.format("%-" + size + "s", s).replace(" ", "-");

    }

}
