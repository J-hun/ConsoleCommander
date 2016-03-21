/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolcommander;

import java.io.File;

/**
 *
 * @author Joco
 */
public class ConsolCommander {

    private boolean isEnd = false;
    private Window window1;//, window2;

    public static void main(String[] args) {
        new ConsolCommander();
    }

    public ConsolCommander() {
        window1 = new Window(new File("E:\\"));
        while (!isEnd) {
            System.out.println(window1);
            input();
        }

    }


    private void input() {

        boolean done = false;
        int input = 0;

        do {
            input = ConsoleIOHelper.inputInt("Melyik mappába szeretnél belépni?");

            if (input >= -1 && input <= window1.getNumberOfDirs()) {
                done = true;
            } else {
                System.out.println(ConsoleIOHelper.messageBox("Nem létezik ilyen sorszámú mappa!"));
            }

        } while (!done);
        if (input == 0) {
            if (window1.getFile().getParentFile() != null) {
                window1.setFile(window1.getFile().getParentFile());
            } else {
                System.out.println(ConsoleIOHelper.messageBox("Nem lehet ennél feljebb lépni.."));
            }

        } else if (input == -1) {
            isEnd = true;
        } else {
            window1.setFile(window1.getFullList().get(input - 1));
        }

    }
}
