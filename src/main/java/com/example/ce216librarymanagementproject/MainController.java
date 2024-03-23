package com.example.ce216librarymanagementproject;

//In this class we will define all of our methods.
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.lang.reflect.Modifier;


public class MainController {
    Gson gson = new Gson();


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


    //METHODS WILL BE BELLOW
    public void CreateNewBook()throws IOException {

        createdir.mkdirs();
        String title=titleid.getText().toUpperCase(); //titleID ile bahsedilen GUI'deki textfield kısmının id is olacak.
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
    public void fillBook (BookInformation book){

        book.setTitle(titleid.getText());
        book.setSubtitle(subtitleid.getText());
        book.setTranslators(translatorid.getText());
        book.setAuthors(authorsid.getText());
        book.setPublisher(publisherid.getText());
        book.setTags(tagsid.getText());
        book.setRating(ratingid.getText());
        book.setEdition(editionid.getText());
        book.setCategory(categoryid.getText());
        book.setLanguage(langid.getText());
        book.setDate(dateid.getText());
        book.setIsbn(isbnid.getText());
        book.setCover(coverid.getText());

        //book.setPictures();

    }

    public void openFile(String fineName){ //objeyi alıp, filename alıp sonra bunu bir pathe çevirip readjsonfile ile book objesi döndürdük
        String selected=fineName+".json";
        String path=FinalPath+"/"+selected;
        BookInformation bookselected= readJsonFile(path);
        titleid.setText(bookselected.getTitle());
        subtitleid.setText(bookselected.getSubtitle());
        translatorid.setText(bookselected.getTranslators());
        authorsid.setText(bookselected.getAuthors());
        publisherid.setText(bookselected.getPublisher());
        tagsid.setText(bookselected.getTags());
        ratingid.setText(bookselected.getRating());
        editionid.setText(bookselected.getEdition());
        categoryid.setText(bookselected.getCategory());
        langid.setText(bookselected.getLanguage());
        dateid.setText(bookselected.getDate());
        isbnid.setText(bookselected.getIsbn());
        coverid.setText(bookselected.getCover());
        editFile(bookselected,selected);
    }



    public void editFile(BookInformation book,String newFile){
        fillBook(book);
        BookInformation course=new BookInformation();
        fillBook(course); //fillBook will be a method
        String newJson = gson.toJson(course); //toJson will be a method
        File file = new File(FinalPath,newFile);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(newJson);
            System.out.println("JSON written to file successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    //FXML BUTTON FUNCTIONS WILL BE BELLOW
    @FXML
    public void addButton(ActionEvent event) throws IOException {
        CreateNewBook();
        //swith scane satırı
    }

    @FXML
    public void editButton(ActionEvent event) throws IOException {
        openFile("DENEME ADI"); //openfile düğmeye tıklanarak çağırıldığı için anca sonra çağırılıyor. start gui bittikten sonra ara sahne eklenerek düzeltilecek bu da

        //swith scane satırı
    }
    //FXML BUTTON CODES WILL BE BELLOW
    @FXML
    private Button addbutton;

    @FXML
    private Button editbutton;


    //FXML FIELD ID WILL BE BELLOW
    @FXML
    private TextField editionid;

    @FXML
    private TextField authorsid;

    @FXML
    private TextField categoryid;

    @FXML
    private TextField dateid;

    @FXML
    private TextField isbnid;

    @FXML
    private TextField langid;

    @FXML
    private TextField publisherid;

    @FXML
    private TextField ratingid;

    @FXML
    private TextField subtitleid;

    @FXML
    private TextField tagsid;

    @FXML
    private TextField titleid;

    @FXML
    private TextField translatorid;

    @FXML
    private TextField coverid;







    //Document address created
    static String userhome=System.getProperty("user.home");
    static String fileName="Libray Storage/";
    static String FinalPath=userhome+File.separator+fileName;
    static File createdir=new File(FinalPath);
}