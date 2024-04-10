package com.example.ce216librarymanagementproject;

//In this class we will define all of our methods.
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
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
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private ListView<BookInformation> listView; // Kitapların listelendiği YER
    Gson gson = new Gson();
    //private EventObject event;


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


    public void switchToAddBookScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToListBookScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ListBook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





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
        // RESİM EKLEME VE KAYDETME
        String bookTitle = titleid.getText();
        String imagePath = "src/main/resources/Library Storage/images/" + bookTitle.replace(" ", "_") + ".png";

        if (bookImageView.getImage() != null) {
            File outputFile = new File(imagePath);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(bookImageView.getImage(), null), "png", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private ImageView bookImageView;

    @FXML
    private void handleSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Resim Seç");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedImage = fileChooser.showOpenDialog(stage);
        if (selectedImage != null) {
            bookImageView.setImage(new Image(selectedImage.toURI().toString()));
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
    @FXML
    private ImageView EditbookImageView;
    static String filePath;

    @FXML

    public void openFile(ActionEvent event) throws IOException { //objeyi alıp, filename alıp sonra bunu bir pathe çevirip readjsonfile ile book objesi döndürdük

        Stage stage =((Stage) ((Node) event.getSource()).getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("EditBook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);


        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            BookInformation selectedBook = listView.getItems().get(selectedIndex);
            String fileName = selectedBook.getTitle() + ".json";
            filePath = FinalPath + File.separator + fileName;

            BookInformation bookselected= readJsonFile(filePath);

            MainController mainController = fxmlLoader.getController();

            mainController.titleid.setText(bookselected.getTitle());
            mainController.subtitleid.setText(bookselected.getSubtitle());
            mainController.translatorid.setText(bookselected.getTranslators());
            mainController.authorsid.setText(bookselected.getAuthors());
            mainController.publisherid.setText(bookselected.getPublisher());
            mainController.tagsid.setText(bookselected.getTags());
            mainController.ratingid.setText(bookselected.getRating());
            mainController.editionid.setText(bookselected.getEdition());
            mainController.categoryid.setText(bookselected.getCategory());
            mainController.langid.setText(bookselected.getLanguage());
            mainController.dateid.setText(bookselected.getDate());
            mainController.isbnid.setText(bookselected.getIsbn());
            mainController.coverid.setText(bookselected.getCover());

            String imagePath = "src/main/resources/Library Storage/images/" + bookselected.getTitle().replace(" ", "_") + ".png";

            // Resmi ImageView üzerinde göstermekay
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                mainController.EditbookImageView.setImage(image);
            }
        }
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    public void editButton(ActionEvent event) throws IOException {

        File file=new File(filePath);
        file.delete();
        CreateNewBook();

    }


    @FXML
    private void updateListViewFromJson() {
        Gson gson = new Gson();
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
            protected void updateItem(BookInformation book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle());
                }
            }
        });
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            BookInformation selectedBook = listView.getItems().get(selectedIndex);
            String jsonFileName = selectedBook.getTitle() + ".json";
            String jsonFilePath = FinalPath + File.separator + jsonFileName;
            String imageFilePath = "src/main/resources/Library Storage/images/" + selectedBook.getTitle().replace(" ", "_") + ".png"; // Resim dosyası yolu


            try {
                // Delete the JSON file
                Files.deleteIfExists(Paths.get(jsonFilePath));

                // Delete the image file
                Files.deleteIfExists(Paths.get(imageFilePath));
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



    static String resourcesPath = "src/main/resources/"; // Projenin kaynak klasörüne işaret eder
    static String libraryStorageFolder = "Library Storage/";
    static String FinalPath = resourcesPath + libraryStorageFolder;
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

    @FXML
    private Button addbuttonMain;


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

}