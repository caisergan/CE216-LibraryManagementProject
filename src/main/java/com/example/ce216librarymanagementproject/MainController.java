package com.example.ce216librarymanagementproject;

//In this class we will define all of our methods.
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.*;

import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class MainController {
    public Label label1;
    @FXML
    private Button listButton;

    @FXML
    private ListView<BookInformation> listView; // Kitapların listelendiği YER
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


    @FXML
    private void initialize() {
        listButton.setOnAction(event -> updateListViewFromJson());
    }

    private void updateListViewFromJson() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<BookInformation>>(){}.getType();
        List<BookInformation> kitapList = new ArrayList<>();

        Path path = Paths.get(FinalPath);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    BookInformation book = readJsonFile(filePath.toString());
                    kitapList.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken bir hata oluştu: " + e.getMessage());
            return;
        }

        ObservableList<BookInformation> items = FXCollections.observableArrayList(kitapList);
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell<BookInformation>() {
            @Override
            protected void updateItem(BookInformation kitap, boolean empty) {
                super.updateItem(kitap, empty);

                if (empty || kitap == null) {
                    setText(null);
                } else {
                    setText(kitap.getTitle());
                }
            }
        });
    }


    @FXML
    private void handleDeleteAction(ActionEvent event) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            BookInformation selectedBook = listView.getItems().get(selectedIndex);
            String fileName = selectedBook.getTitle() + ".json";
            String filePath = FinalPath + File.separator + fileName;

            // Delete the JSON file
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to delete file");
                alert.setContentText("An error occurred while deleting the file: " + e.getMessage());
                alert.showAndWait();
                return;
            }

            // Remove the book from the list and update the JSON file
            listView.getItems().remove(selectedIndex);
            updateJsonFile(new ArrayList<>(listView.getItems()));
        }
    }



    static String userhome = System.getProperty("user.dir"); // C'de çalıştırmak için .home yazınız
    static String fileName = "Library Storage/";
    static String FinalPath = userhome + File.separator + fileName;
    static File createdir = new File(FinalPath);

    private void updateJsonFile(List<BookInformation> kitapList) {
        Gson gson = new Gson();
        for (BookInformation book : kitapList) {
            String fileName = book.getTitle() + ".json";
            String filePath = FinalPath + File.separator + fileName;
            String json = gson.toJson(book);

            Path path = Paths.get(filePath);
            try {
                Files.writeString(path, json, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Dosya Güncellenemedi");
                alert.setContentText("Dosya yazılırken bir hata oluştu: " + e.getMessage());
                alert.showAndWait();
            }
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

    @FXML
    public void ImportBook(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        File selectedfile = fileChooser.showOpenDialog(null);
        String FilePath = selectedfile.getAbsolutePath();
        String FileName = selectedfile.getName();

        Path in= Paths.get(FilePath);
        Path out = Paths.get(FinalPath+"\\"+FileName);
        Files.copy(in,out);
    }



    //FXML BUTTON CODES WILL BE BELLOW
    @FXML
    private Button addbutton;

    @FXML
    private Button editbutton;

    @FXML
    private Button importbutton;



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
    /*static String userhome=System.getProperty("user.home");
    static String fileName="Libray Storage/";
    static String FinalPath=userhome+File.separator+fileName;
    static File createdir=new File(FinalPath);*/
}