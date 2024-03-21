package com.example.ce216librarymanagementproject;

//In this class we will define all of our methods.
import com.google.gson.Gson;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.io.*;


public class MainController {

    private static BookInformation readJsonFile(String filePath) {
        try (FileReader fileReader = new FileReader(filePath)) {
            return new Gson().fromJson(fileReader, BookInformation.class);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new object.");
            return new BookInformation();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*@FXML
    public void SaveAll(ActionEvent event) throws IOException {
        CreateNewBook();
        //swith scane satırı
    }*/

    public void CreateNewBook()throws IOException {
        Gson gson = new Gson();
        createdir.mkdirs();
        String title=titleID.getText().toUpperCase(); //titleID ile bahsedilen GUI'deki textfield kısmının id is olacak.
        String newFile;
        newFile= title+".json";

        BookInformation course=new BookInformation();
        fillBook(course); //fillBook will be a method
        String newJson = gson.toJson(course); //toJson will be a method

        //Create new directory according to course code
        File file = new File(FinalPath,newFile);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(newJson);
            System.out.println("JSON written to file successfully.");
        }
    }
    //Document address created
    static String userhome=System.getProperty("user.home");
    static String fileName="Libray Storage/";
    static String FinalPath=userhome+File.separator+fileName;
    static File createdir=new File(FinalPath);
}