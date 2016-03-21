/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolcommander;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Joco
 */
public class Window {

    private File file;
    private List<File> dirList;
    private List<File> fileList;
    private List<File> fullList;

    public Window(File file) {

        setFile(file);

    }

    public static void printMoreWindows(Window... windows) {
        //todo

    }

    private List<File> setDirList(File[] files) {
        dirList = new ArrayList();
        for (File file : files) {
            if (file.isDirectory()) {
                dirList.add(file);
            }
        }

        return dirList;
    }

    private List<File> setFileList(File[] files) {
        fileList = new ArrayList();
        for (File file : files) {
            if (!file.isDirectory()) {
                fileList.add(file);
            }
        }
        return fileList;

    }

    private static class SortByDate implements Comparator<File> {

        @Override
        public int compare(File o1, File o2) {
            return ((Long) o1.lastModified()).compareTo(o2.lastModified());
        }

    }

    private class SortByType implements Comparator<File> {

        @Override
        public int compare(File o1, File o2) {

            String type1 = o1.getName();
            type1 = type1.substring(type1.length() - 3);
            String type2 = o2.getName();
            type2 = type2.substring(type2.length() - 3);
            return type1.compareTo(type2);
        }

    }

    protected void sortByDate() {
        Collections.sort(dirList, new SortByDate());
        Collections.sort(fileList, new SortByDate());

    }

    private static class SortBySize implements Comparator<File> {

        @Override
        public int compare(File o1, File o2) {
            Long datum = o1.length();
            return datum.compareTo(o2.length());
        }

    }

    @Override
    public String toString() {
        return file + "\n" + listDirs(fullList, file);
    }

    private void setFullList() {
        fullList.clear();
        fullList.addAll(dirList);
        fullList.addAll(fileList);
    }

    private void setFullList(List<File> dirList, List<File> fileList) {

        fullList = new ArrayList();
        sortByDate();
        fullList.addAll(dirList);
        fullList.addAll(fileList);
    }

    public List<File> getFullList() {
        return fullList;
    }

    public File getFile() {
        return file;
    }

    public final void setFile(File file) {

        if (file.exists() && file.isDirectory() && file.listFiles() != null) {
            this.file = file;
            setFullList(setDirList(file.listFiles()), setFileList(file.listFiles()));
        } else {
            System.out.println(ConsoleIOHelper.messageBox("Nem lehet elérni a mappát"));
        }

    }

    public static String formatDate(long lastModified) {
        if (lastModified == 0) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        return dateFormat.format(new Date(lastModified));
    }

    private static String bytesToSize(long size) {
        double normal = size;
        String unit = "bytes";
        if (normal > 1023) {
            normal = size / 1024;
            unit = "Kb";
        }
        if (normal > 1023) {
            normal = normal / 1024;
            unit = "MB";
        }
        if (normal > 1023) {
            normal = normal / 1024;
            unit = "Gb";
        }

        String number = new DecimalFormat("#.#").format(normal);
        return (number + " " + unit);
    }

    public static String listDir(File[] fileList, File root) {
        return listDirs(Arrays.asList(fileList), root);
    }

    public static String listDirs(List<File> fileList, File root) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s%85s", "+", "+\n").replace(" ", "-"));
        sb.append(String.format("%-84s", "|0.  [...]")).append("|\n");
        for (int i = 0; i < fileList.size(); i++) {
            sb.append(fileToString(fileList.get(i), i + 1));
        }
        sb.append(String.format("%s%85s", "+", "+\n").replace(" ", "-"));
        return sb.toString();
    }
    protected static String fileToString(File file, int index) {
        String starting = "|" + index + ".";
        String type = " ";
        String size = "<DIR>";
        String name = "";
        if (!file.isDirectory()) {
            type = file.getName().substring(file.getName().length() - 3);
            size = file.isDirectory() ? "<DIR>" : bytesToSize(file.length());
            name += (file.getName().length() > 30 ? (file.getName().substring(0, 28) + "...") : file.getName());
        } else {
            name = "[" + (file.getName().length() > 30 ? (file.getName().substring(1, 28) + "...") : file.getName()) + "]";
        }
        return String.format("%-5s%-35s%-5s%-20s%-19s", starting, name, type, size, formatDate(file.lastModified())) + "|\n";
    }

    public int getNumberOfDirs() {
        return dirList.size();
    }
}
