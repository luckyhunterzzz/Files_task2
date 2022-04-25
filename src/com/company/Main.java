package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String wayToSave = "F:\\Programms\\IDEA-projects\\homeprojects\\files\\files-installed\\Games\\savegames\\";
    private static List<String> listSaves = new ArrayList<String>();

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(wayToSave + path)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameProgress);

            listSaves.add(wayToSave + path);
            System.out.println("Файл " + path + " добавлен в список сохранений.");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> listSaves) {
        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream(wayToSave + "gameProgress.zip"))) {
            for (String currentSave : listSaves) {
                File fileToZip = new File(currentSave);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    System.out.println("Файл " + currentSave + " добавлен в архив.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delSavesFiles(List<String> listSaves) {
        for (int i = 0; i < listSaves.size(); i++) {
            String saveFilePath = listSaves.get(i);
            File fileToDel = new File(saveFilePath);
            if (fileToDel.delete()) {
                System.out.println("Файл " + saveFilePath + " удален!");
            } else {
                System.out.println("Файл " + saveFilePath + " не удален!");
            }
        }
    }

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(14, 31, 62, 1231.05);
        GameProgress gameProgress3 = new GameProgress(73, 229, 143, 15183.86);

        saveGame("save1.date", gameProgress1);
        saveGame("save2.date", gameProgress2);
        saveGame("save3.date", gameProgress3);


        zipFiles(wayToSave, listSaves);


        delSavesFiles(listSaves);

    }
}
