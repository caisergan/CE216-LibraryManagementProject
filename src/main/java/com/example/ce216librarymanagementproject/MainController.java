package com.example.ce216librarymanagementproject;

//In this class we will define all of our methods.
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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


    public void switchToAddBookScene(ActionEvent event) throws IOException {//switch add book scene
        root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToListBookScene(ActionEvent event) throws IOException {//switch ege's list book scene
        root = FXMLLoader.load(getClass().getResource("MainList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMain(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("MainList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToTableView(ActionEvent event) throws IOException { //use in add button
        root = FXMLLoader.load(getClass().getResource("MainList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMainPage(ActionEvent event) throws IOException {//swith main page
        root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





    public void CreateNewBook()throws IOException {

        createdir.mkdirs();
        String title=titleid.getText().toLowerCase(); //titleID ile bahsedilen GUI'deki textfield kısmının id is olacak.
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
    public void editFile(ActionEvent event) throws IOException { //objeyi alıp, filename alıp sonra bunu bir pathe çevirip readjsonfile ile book objesi döndürdük

        Stage stage =((Stage) ((Node) event.getSource()).getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("EditBook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);


        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            filePath=FinalPath+tableView.getItems().get(selectedIndex).title;
            BookInformation bookselected = tableView.getItems().get(selectedIndex);

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
                mainController.bookImageView.setImage(image);
            }
            stage.setScene(scene);
            stage.show();
        }

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
    private static ObservableList<BookInformation> bookList = FXCollections.observableArrayList();

    private void loadBooksFromJson() {
        File folder = new File("src/main/resources/Library Storage");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    // Her bir dosya için JSON içeriğini bir BookInformation nesnesine dönüştür
                    try (Reader reader = new FileReader(file)) {
                        BookInformation bookInfo = new Gson().fromJson(reader, BookInformation.class);
                        bookList.add(bookInfo);
                    } catch (FileNotFoundException e) {
                        System.err.println("The JSON file was not found: " + file.getPath());
                    } catch (IOException e) {
                        System.err.println("An error occurred while reading the JSON file: " + file.getPath());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.err.println("The directory was not found: " + folder.getPath());
        }
    }

    private void updateUIWithBooks() {

        if (bookList.size() > 0) {
            BookInformation firstBook = bookList.get(0);
            bookTitle1.setText(firstBook.getTitle());
            bookAuthor1.setText(firstBook.getAuthors());
            String imagePath = "src/main/resources/Library Storage/images/" + firstBook.title.replace(" ", "_").toUpperCase() + ".png";
            Image image = new Image(new File(imagePath).toURI().toString());
            bookImage1.setImage(image);
        }

        if (bookList.size() > 1) {
            BookInformation secondBook = bookList.get(1);
            bookTitle2.setText(secondBook.getTitle());
            bookAuthor2.setText(secondBook.getAuthors());
            String imagePath2 = "src/main/resources/Library Storage/images/" + secondBook.title.replace(" ", "_").toUpperCase() + ".png";
            Image image2 = new Image(new File(imagePath2).toURI().toString());
            bookImage2.setImage(image2);
        }

        if (bookList.size() > 2) {
            BookInformation thirdBook = bookList.get(2);
            bookTitle3.setText(thirdBook.getTitle());
            bookAuthor3.setText(thirdBook.getAuthors());
            String imagePath3 = "src/main/resources/Library Storage/images/" + thirdBook.title.replace(" ", "_").toUpperCase() + ".png";
            Image image3 = new Image(new File(imagePath3).toURI().toString());
            bookImage3.setImage(image3);
        }

        if (bookList.size() > 3) {
            BookInformation fourthBook = bookList.get(3);
            bookTitle4.setText(fourthBook.getTitle());
            bookAuthor4.setText(fourthBook.getAuthors());
            String imagePath4 = "src/main/resources/Library Storage/images/" + fourthBook.title.replace(" ", "_").toUpperCase() + ".png";
            Image image4 = new Image(new File(imagePath4).toURI().toString());
            bookImage4.setImage(image4);
        }

    }
    @FXML
    public void initialize() {
        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                loadBooksFromJson(); // Kitapları yükleyen metod
                return null;
            }

            @Override
            protected void succeeded() {
                updateUIWithBooks(); // UI güncelleme
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
            }
        };

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            BookInformation selectedBook = tableView.getItems().get(selectedIndex);
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
            tableView.getItems().remove(selectedIndex);
            updateJsonFile(new ArrayList<>(tableView.getItems()));
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
        switchToTableView(event);


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

    @FXML
    public void FillTableView(){
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
        tableView.setItems(items);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        subtitleCol.setCellValueFactory(new PropertyValueFactory<>("subtitle"));
        translatorCol.setCellValueFactory(new PropertyValueFactory<>("translators"));
        authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        languageCol.setCellValueFactory(new PropertyValueFactory<>("language"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        editionCol.setCellValueFactory(new PropertyValueFactory<>("edition"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tagsCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
    }

    public void FilterByTags() {
        FillTableView();
        ObservableList<BookInformation> filteredList = FXCollections.observableArrayList();

        String tags = searchBox.getText();
        if (tags == null || tags.isEmpty()) {
            FillTableView();
            return;
        }
        String[] tagArray = tags.toLowerCase().split(",");
        for (BookInformation book : tableView.getItems()) {
            for (String tag : tagArray) {
                if (book.getTags().contains(tag.trim())) {
                    filteredList.add(book);
                    break;
                }
            }
        }
        tableView.setItems(filteredList);
    }

    @FXML
    private TextField searchBox;
    @FXML
    private TableView<BookInformation> tableView;
    @FXML
    private TableColumn<BookInformation, String> titleCol;
    @FXML
    private TableColumn<BookInformation, String> subtitleCol;
    @FXML
    private TableColumn<BookInformation, String> translatorCol;
    @FXML
    private TableColumn<BookInformation, String> authorsCol;
    @FXML
    private TableColumn<BookInformation, String> publisherCol;
    @FXML
    private TableColumn<BookInformation, String> dateCol;
    @FXML
    private TableColumn<BookInformation, String> isbnCol;
    @FXML
    private TableColumn<BookInformation, String> languageCol;
    @FXML
    private TableColumn<BookInformation, String> categoryCol;
    @FXML
    private TableColumn<BookInformation, String> editionCol;
    @FXML
    private TableColumn<BookInformation, String> ratingCol;
    @FXML
    private TableColumn<BookInformation, String> tagsCol;



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
    @FXML
    private ImageView bookImage1;
    @FXML
    private Label bookTitle1;
    @FXML
    private Label bookAuthor1;
    @FXML
    private ImageView bookImage2;
    @FXML
    private Label bookTitle2;
    @FXML
    private Label bookAuthor2;
    @FXML
    private ImageView bookImage3;
    @FXML
    private Label bookTitle3;
    @FXML
    private Label bookAuthor3;
    @FXML
    private ImageView bookImage4;
    @FXML
    private Label bookTitle4;
    @FXML
    private Label bookAuthor4;
}