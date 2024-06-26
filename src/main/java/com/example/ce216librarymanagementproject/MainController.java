package com.example.ce216librarymanagementproject;

//In this class we will define all of our methods.
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

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


import java.nio.file.*;
import java.util.*;




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
        activeFXML = "AddBook.fxml";
        root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Book Page");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

   public void switchToListBookScene(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "MainList.fxml";
        root = FXMLLoader.load(getClass().getResource("MainList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       stage.setTitle("List Book Page");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }





    public void switchToMainPage(ActionEvent event) throws IOException {//swith main page
        activeFXML = "MainPage.fxml";
        root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Library Management");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
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
        String imagePath = FinalPath+"images/" + bookTitle.replace(" ", "_") + ".png";
        File outputFile = new File(imagePath);
        if (bookImageView.getImage() != null) {

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(bookImageView.getImage(), null), "png", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {

                // Resmi dosyadan yükle
                Image defaultImage = new Image(getClass().getResource("Design Images/cover.png").openStream());
                // Default resmi dosyaya kaydet
                ImageIO.write(SwingFXUtils.fromFXImage(defaultImage, null), "png", outputFile);            } catch (IOException e) {
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
        if (ratingid.getText() == ""){
            book.setRating("0");
        }
        else book.setRating(ratingid.getText());
        try{
            Double rating = Double.parseDouble(ratingid.getText());
            if (rating <= 0){
                book.setRating("0");
            }
            else if (rating >= 5){
                book.setRating("5");
            }
            else book.setRating(ratingid.getText());

        }catch (Exception e){
            book.setRating("0");
        }
        book.setEdition(editionid.getText());
        book.setCategory(categoryid.getText());
        book.setLanguage(langid.getText());
        book.setDate(dateid.getText());
        book.setIsbn(isbnid.getText());

        //book.setPictures();

        String bookTitle = titleid.getText();
        String imagePath = FinalPath+"images/" + bookTitle.replace(" ", "_") + ".png";
        book.setPictures(imagePath);

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


            String imagePath = FinalPath + "images/" + bookselected.getTitle().replace(" ", "_") + ".png";

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
        filePath=filePath+".json";
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
        File folder = new File(FinalPath);
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

    private void updateUI() {
        // En yüksek dereceli kitapları tutacak liste
        List<BookInformation> topBooks = new ArrayList<>();

        // Tüm kitapları kendi ratinglerine göre sırala (yüksekten düşüğe)
        Collections.sort(bookList, (book1, book2) -> {
            double rating1 = Double.parseDouble(book1.getRating());
            double rating2 = Double.parseDouble(book2.getRating());

            return Double.compare(rating2, rating1); // Ters sıralama için rating2, rating1
        });

        // En yüksek dereceli 4 veya daha az kitabı topBooks listesine ekle
        int count = Math.min(8, bookList.size());
        for (int i = 0; i < count; i++) {
            topBooks.add(bookList.get(i));
        }



        if (topBooks.size() > 0) {
            updateBookUI(topBooks.get(0), bookImage1, bookTitle1, bookAuthor1);
        }
        if (topBooks.size() > 1) {
            updateBookUI(topBooks.get(1), bookImage2, bookTitle2, bookAuthor2);
        }
        if (topBooks.size() > 2) {
            updateBookUI(topBooks.get(2), bookImage3, bookTitle3, bookAuthor3);
        }
        if (topBooks.size() > 3) {
            updateBookUI(topBooks.get(3), bookImage4, bookTitle4, bookAuthor4);
        }
        if (topBooks.size() > 4) {
            updateBookUI(topBooks.get(4), bookImage5, bookTitle5, bookAuthor5);
        }
        if (topBooks.size() > 5) {
            updateBookUI(topBooks.get(5), bookImage6, bookTitle6, bookAuthor6);
        }
        if (topBooks.size() > 6) {
            updateBookUI(topBooks.get(6), bookImage7, bookTitle7, bookAuthor7);
        }
        if (topBooks.size() > 7) {
            updateBookUI(topBooks.get(7), bookImage8, bookTitle8, bookAuthor8);
        }
        topBooks.clear();
        bookList.clear();
    }

    private void updateBookUI(BookInformation book, ImageView imageView, Label titleLabel, Label authorLabel) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthors());
        String imagePath = book.getPictures();

        try {
            Image image = new Image(new File(imagePath).toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            System.err.println("Image load error for " + imagePath + ": " + e.getMessage());
            // Hata durumunda alternatif bir resim yükleyebilir veya resim yükleme hatasını görsel olarak işaretleyebilirsiniz.
            imageView.setImage(new Image("file:src/main/resources/Library Storage/images/default.png")); // Varsayılan resim yolu
        }
    }

    // Hangi FXML dosyasının aktif olduğunu belirleyen değişken





    private static String activeFXML = "MainPage.fxml";


    public void initialize() {
        if(activeFXML.equals("MainList.fxml")){
            FillTableView();
        }

        if (!activeFXML.equals("MainPage.fxml")) {
            return; // Eğer MainPage.fxml dışında bir sayfa yükleniyorsa, kitap yükleme ve UI güncellemesini atla
        }

        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                loadBooksFromJson(); // Kitapları JSON'dan yükleyen metod
                return null;
            }
            @Override
            protected void succeeded() {
                updateUI(); // UI güncelleme
                FillTableView();
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
            String imageFilePath = FinalPath + "images/" + selectedBook.getTitle().replace(" ", "_") + ".png"; // Resim dosyası yolu

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Book");
            alert.setContentText("Are you sure you want to delete the book '" + selectedBook.getTitle() + "'?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Delete the JSON file
                    Files.deleteIfExists(Paths.get(jsonFilePath));

                    // Delete the image file
                    Files.deleteIfExists(Paths.get(imageFilePath));
                } catch (IOException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Failed to delete file");
                    errorAlert.setContentText("An error occurred while deleting the file: " + e.getMessage());
                    errorAlert.showAndWait();
                    return;
                }

                // Remove the book from the list and update the JSON file
                tableView.getItems().remove(selectedIndex);
                updateJsonFile(new ArrayList<>(tableView.getItems()));
            }
        }
    }


    public static void createLibraryStorageFolder() {
        // Klasör zaten varsa, bir şey yapmaya gerek yok
        if (!createdir.exists()) {
            boolean result = createdir.mkdirs(); // Ebeveyn klasörler dahil gerekli tüm klasörleri oluşturur
            if (result) {
                System.out.println("Klasör başarıyla oluşturuldu: " + createdir);
            } else {
                System.out.println("Klasör oluşturulamadı.");
            }
        } else {
            System.out.println("Klasör zaten var: " + createdir);
        }
        if (!imagesDirectory.exists()) {
            if (imagesDirectory.mkdirs()) {
                System.out.println("Images klasörü başarıyla oluşturuldu: " + finalImagesPath);
            } else {
                System.out.println("Images klasörü oluşturulamadı.");
            }
        }
    }
    // Statik blok içinde klasör kontrolü ve oluşturma işlemi



    private  static String resourcesPath = System.getProperty("user.home"); // Projenin kaynak klasörüne işaret eder
    private  static String libraryStorageFolder = "/Library Storage/";
    private  static String FinalPath = resourcesPath + libraryStorageFolder;
    private  static File createdir = new File(FinalPath);
    private static String imagesFolder = "/images/"; // Images alt klasörüne işaret eder
    private static String finalImagesPath = FinalPath + imagesFolder; // Tam Images yolu
    private static File imagesDirectory = new File(finalImagesPath); // Images için File nesnesi
static {
    createLibraryStorageFolder();
}

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



    @FXML
    public void ImportBook(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        File selectedfile = fileChooser.showOpenDialog(null);
        String FilePath = selectedfile.getAbsolutePath();
        String FileName = selectedfile.getName();
        String regexPattern = "[_\\/,;|]";
        String formattedFileName = FileName.replaceAll(regexPattern, " ");


        Path in= Paths.get(FilePath);
        Path out = Paths.get(FinalPath+"\\"+formattedFileName);
        Files.copy(in,out);
    }

    @FXML
    public void ExportBook(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Book");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
        BookInformation selectedBook = tableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            String suggestedFileName = selectedBook.getTitle() + ".json";
            fileChooser.setInitialFileName(suggestedFileName);

            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(selectedBook);
                try (FileWriter writer = new FileWriter(selectedFile)) {
                    writer.write(json);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No book selected");
            alert.setContentText("Please select a book to export.");
            alert.showAndWait();
        }
    }



    @FXML
    public void FillTableView(){
        if (tableView == null)return;

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

    public void SearchAll() {
        FillTableView();
        ObservableList<BookInformation> filteredList = FXCollections.observableArrayList();

        String search = searchBox.getText();
        if (search == null || search.isEmpty()) {
            FillTableView();
            return;
        }

        for (BookInformation book : tableView.getItems()) {
            if (book.getTitle().toLowerCase().contains(search.toLowerCase())
                    || book.getSubtitle().toLowerCase().contains(search.toLowerCase())
                    || book.getTranslators().toLowerCase().contains(search.toLowerCase())
                    || book.getAuthors().toLowerCase().contains(search.toLowerCase())
                    || book.getPublisher().toLowerCase().contains(search.toLowerCase())
                    || book.getTags().toLowerCase().contains(search.toLowerCase())
                    || book.getRating().toLowerCase().contains(search.toLowerCase())
                    || book.getEdition().toLowerCase().contains(search.toLowerCase())
                    || book.getCategory().toLowerCase().contains(search.toLowerCase())
                    || book.getLanguage().toLowerCase().contains(search.toLowerCase())
                    || book.getDate().toLowerCase().contains(search.toLowerCase())
                    || (book.getIsbn().toLowerCase().contains(search.toLowerCase()))) {
                filteredList.add(book);
            }
        }
        tableView.setItems(filteredList);
    }


    public void FilterByTags() {
        FillTableView();
        ObservableList<BookInformation> filteredList = FXCollections.observableArrayList();

        String tags = searchBox.getText();
        if (tags == null || tags.isEmpty()) {
            FillTableView();
            return;
        }
        String[] tagArray = tags.split(",");
        for (BookInformation book : tableView.getItems()) {
            for (String tag : tagArray) {
                if (book.getTags().toLowerCase().contains(tag.trim().toLowerCase())) {
                    filteredList.add(book);
                    break;
                }
            }
        }
        tableView.setItems(filteredList);
    }

    //FXML BUTTON FUNCTIONS WILL BE BELLOW
    private boolean isAnyFieldEmpty() {
        return titleid.getText().isEmpty() || subtitleid.getText().isEmpty() ||
                translatorid.getText().isEmpty() || authorsid.getText().isEmpty() ||
                publisherid.getText().isEmpty() || dateid.getText().isEmpty() ||
                isbnid.getText().isEmpty() || langid.getText().isEmpty() ||
                categoryid.getText().isEmpty() || editionid.getText().isEmpty() ||
                tagsid.getText().isEmpty();
    }

    @FXML
    public void addButton(ActionEvent event) throws IOException {
        String ratingText = ratingid.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Rating alanı boş değilse devam et
        if (!ratingText.isEmpty()) {
            try {
                double rating = Double.parseDouble(ratingText);

                // Rating değeri 0 ile 5 arasında olmalı
                if (rating < 0 || rating > 5) {
                    alert.setTitle("Invalid Rating");
                    alert.setContentText("Please nter rating value between 0 and 5");
                    alert.showAndWait();
                } else if (isAnyFieldEmpty()) {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Error");
                    alert2.setContentText("You did not fill all the blanks.\nAre you sure for exit?");

                    Optional<ButtonType> result = alert2.showAndWait();

                    if (result.isPresent()) {
                        if (result.get() == ButtonType.OK) {
                            CreateNewBook();
                            switchToListBookScene(event);
                        } else if (result.get() == ButtonType.CANCEL) {
                            alert2.close();
                            switchToListBookScene(event);
                        }
                    }
                } else {
                    // Tüm alanlar doldurulmuş ve rating değeri geçerli
                    CreateNewBook();
                    switchToListBookScene(event);
                }

            } catch (NumberFormatException e) {
                // Sayısal olmayan bir değer girildiğinde hata mesajı
                alert.setTitle("Invalid Input");
                alert.setContentText("Please enter numerical value for rating");
                alert.showAndWait();
            }

        } else {
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            alert3.setTitle("Error");
            alert3.setContentText("You did not fill all the blanks.\nAre you sure for exit?");

            Optional<ButtonType> result = alert3.showAndWait();

            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    CreateNewBook();
                    switchToListBookScene(event);
                } else if (result.get() == ButtonType.CANCEL) {
                    alert3.close();
                }
            }
        }
    }

    @FXML
    private void helpMenu() throws IOException {
        activeFXML = "HelpOne.fxml";
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpOne.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Help");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
    public void switchToHelp2(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "HelpOne.fxml";
        root = FXMLLoader.load(getClass().getResource("HelpTwo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Help");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void switchToHelp1(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "HelpTwo.fxml";
        root = FXMLLoader.load(getClass().getResource("HelpOne.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Help");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void switchToHelp3(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "HelpTwo.fxml";
        root = FXMLLoader.load(getClass().getResource("HelpThree.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Help");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void switchToHelp4(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "HelpThree.fxml";
        root = FXMLLoader.load(getClass().getResource("HelpFour.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Help");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void switchToHelp5(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "HelpFour.fxml";
        root = FXMLLoader.load(getClass().getResource("HelpFifth.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Help");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void switchToHelp6(ActionEvent event) throws IOException {//switch ege's list book scene

        activeFXML = "HelpFifth.fxml";
        root = FXMLLoader.load(getClass().getResource("HelpSix.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Help");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    @FXML
    public void saveEdit(ActionEvent event) throws IOException{
        editButton(event);
        switchToListBookScene(event);
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
    @FXML
    private Button exportid;

    @FXML
    private Button helpAddMenu;
    @FXML
    private Button helpMainPage;
    @FXML
    private Button helpEditMenu;
    @FXML
    private Button helpMainList;

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
    @FXML
    private ImageView bookImage5;
    @FXML
    private Label bookTitle5;
    @FXML
    private Label bookAuthor5;
    @FXML
    private ImageView bookImage6;
    @FXML
    private Label bookTitle6;
    @FXML
    private Label bookAuthor6;
    @FXML
    private ImageView bookImage7;
    @FXML
    private Label bookTitle7;
    @FXML
    private Label bookAuthor7;
    @FXML
    private ImageView bookImage8;
    @FXML
    private Label bookTitle8;
    @FXML
    private Label bookAuthor8;

}