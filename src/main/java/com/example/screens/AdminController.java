package com.example.screens;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import com.example.screens.data.DatabaseHandler;
import com.example.screens.finalThings.finalDocument;
import com.google.gson.Gson; //саня это пиздец а не библиотека
import com.example.screens.data.Enrollee;
import com.google.gson.GsonBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
//import tableView.TableViewController;


public class AdminController extends HelloController {


    Stage window;

///экран таблицы абитуриентов

    //крч тут начал
    @FXML
    private TableView<Enrollee> enrolleeTable; //таблица студентов главная

    @FXML
    private TableColumn<Enrollee, Integer> enrolleeNumberTable = new TableColumn<>(); //колонка с id студента

    @FXML
    private TableColumn<Enrollee, String> enrolleeStatusTable = new TableColumn<>(); //колонка со статусом студетна

    @FXML
    private TableColumn<Enrollee, String> enrolleeFIOTable = new TableColumn<>();

    @FXML
    private TableColumn<Enrollee, String> editCol = new TableColumn<>();

    //колонка с фио студента

    @FXML
    ObservableList<Enrollee> Enrolleelist = FXCollections.observableArrayList();


    // тут закончил

    @FXML
    private Button findButton; //кнопка поиска студента по главной таблице

    @FXML
    private TextField findField = new TextField(); //то поле где вбивается id или ФИО (в зависимости от того что в ChoiseBox)

    private final String[] massiveOfChoices = new String[]{"id", "ФИО"};
    @FXML
    private ChoiceBox<String> findPicker = new ChoiceBox<String>(); //тут типа два выбора: поиск по id или по ФИО
    // (хотя мне кажется надо сделать так, чтобы поск сам определял число или срока в него
    // вводиться и типа если число то искал по айдишникам, а если строка то по фио )


///экран обращений

//    @FXML
//    private Tab create_new_notification;

    @FXML
    private DatePicker dateDocumentsAdmin;

    @FXML
    private DatePicker dateNowAdmin;

    @FXML
    private Button saveDateAdmin;


    //экран создания уведомления
    @FXML
    private CheckBox forAll = new CheckBox();

    //    private Button findButton = new Button();
    @FXML
    private TextField forSpecificEnrollee = new TextField();



////////экран списков/////
    @FXML
    private ToolBar BudgetToolBar;

    @FXML
    private Button ButtonToGenerateLists;

    @FXML
    private Button ButtonToOpenBudget;

    @FXML
    private Button ButtonToOpenPaid;

    @FXML
    private Text PaidToolbar;

    @FXML
    private VBox ListsVbox;
    @FXML
    void clickToGenerateLists(ActionEvent event)
    {

        finalDocument finalDocs = new finalDocument(); //запускаем адский генератор списков
        ButtonToGenerateLists.setText("Готово");
        ButtonToGenerateLists.setDisable(true);

    }

    @FXML
    void ClickToButtonToOpenBudget(ActionEvent event)
    {
        String name = "Budget.txt";
        File selectedFile = new File("src/main/java/com/example/screens/finalThings/"+name);
        Desktop desktop =Desktop.getDesktop();
        try {
            desktop.open(selectedFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @FXML
    void ClickToButtonToOpenPaid(ActionEvent event)
    {
        String name = "Paid.txt";
        File selectedFile = new File("src/main/java/com/example/screens/finalThings/"+name);
        Desktop desktop =Desktop.getDesktop();
        try {
            desktop.open(selectedFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    ///////////

    @FXML
    private TextField notificationTextAdmin1 = new TextField();

    @FXML
    private Button exitButton = new Button();

    @FXML
    private void exit() {
        exitButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/screens/Welcome.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 900, 600));
        stage.showAndWait();
    }

    @FXML
    private Button sentNotificationAdmin1 = new Button();

    @FXML
    private void ClickToSearchButton() {
        System.out.println("Button works successfully");
        if (Objects.equals(findField.getText(), "")) {
            refreshTable();
        } else if (Objects.equals(findPicker.getValue(), "id")) {
            getEnrolleeListById();
        } else if (Objects.equals(findPicker.getValue(), "ФИО")) {
            getEnrolleeListByFio();
        }
    }

    public void UserScene(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/com/example/screens/admin.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    } //не помню зачем это


    private void loadDate() {

        refreshTable();
        enrolleeNumberTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        enrolleeFIOTable.setCellValueFactory(new PropertyValueFactory<>("fio"));
        enrolleeStatusTable.setCellValueFactory(new PropertyValueFactory<>("status"));

        //add cell of button edit
        Callback<TableColumn<Enrollee, String>, TableCell<Enrollee, String>> cellFoctory = (TableColumn<Enrollee, String> param) -> {
            // make cell containing buttons
            final TableCell<Enrollee, String> cell = new TableCell<Enrollee, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Button editButton = new Button("Просмотр");
                        editButton.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-background-color:#6495ED;"
                        );


                        editButton.setOnAction((event) -> {

                            Enrollee enrollee2 = getTableRow().getItem();
                            // нужно выбрать этого enrollee из таблицы

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("Untitled1122.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            EnrolleeInfoController enrolleeInfoController = loader.getController();

                            // здесь заполнить поля
                            enrolleeInfoController.setId(enrollee2.getId());

                            enrolleeInfoController.setFio(enrollee2.getFio());
                            enrolleeInfoController.setEmail(enrollee2.getEmail());
                            enrolleeInfoController.setStatus(enrollee2.getStatus());
                            //жоска вытаскивает экзамены из базы и сразу преобразовывает в хэшмэп

                            Statement statement = null;
                            String foundString = "";

                            HashMap<String, Double> hashMapFromBase = new HashMap<>();

                            try {
                                statement = getDbConection().createStatement();

                                ResultSet resultSet = statement.executeQuery("SELECT exams FROM enrollees WHERE idEnrollee='" + enrollee2.getIdEnrollee() + "'");
                                if (resultSet.next()) {
                                    foundString = resultSet.getString("exams");
                                }

                            } catch (SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
//                            System.out.println("Вот наши BTS из базы слева направо... " + foundString);
                            //а так обратно в хешмэп

                            Type type = hashMapFromBase.getClass();

                            GsonBuilder gsonBuilder = new GsonBuilder(); //какие-то заебы той библиотеки гугла
                            Gson gsonObj = gsonBuilder.create(); //это тоже

                            hashMapFromBase = gsonObj.fromJson(foundString, type); //все супер все достали и оно норм хешмеп
                            //System.out.println("new hashMap: "+ newHashMap);
                            System.out.println("Вот они причёсанные " + hashMapFromBase);


                            if (hashMapFromBase != null) {
                                enrolleeInfoController.setExams(hashMapFromBase.toString().replace("{", "").replace("}", "").replace(".0", ""));
                            }
                            statement = null;
                            foundString = "";
                            ArrayList<String> arrayListFromBase = new ArrayList<>();

                            try {
                                statement = getDbConection().createStatement();

                                ResultSet resultSet = statement.executeQuery("SELECT directions FROM enrollees WHERE idEnrollee='" + enrollee2.getIdEnrollee() + "'");
                                if (resultSet.next()) {
                                    foundString = resultSet.getString("directions");
                                }

                            } catch (SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
//                            System.out.println("Вот наши BTS из базы слева направо... "+foundString);
                            //а так обратно в хешмэп

                            type = arrayListFromBase.getClass();

                            gsonBuilder = new GsonBuilder(); //какие-то заебы той библиотеки гугла
                            gsonObj = gsonBuilder.create(); //это тоже

                            arrayListFromBase = gsonObj.fromJson(foundString, type); //все супер все достали и оно норм хешмеп
                            //System.out.println("new hashMap: "+ newHashMap);
                            System.out.println("Вот они причёсанные " + arrayListFromBase);
                            if (arrayListFromBase != null) {
                                enrolleeInfoController.setDirections(arrayListFromBase.toString());

                            }
                            enrolleeInfoController.setWhyus(enrollee2.getWhyUs());


                            enrolleeInfoController.setDate(enrollee2.getDateOfBirth().toString());
                            System.out.println(enrollee2.toString());
                            //                        enrolleeInfoController.setDirections(enrollee./);

                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();


                        });
                        HBox managebtn = new HBox(editButton);

                        //                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        //                        managebtn.setStyle("-fx-alignment:center");
                        //                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        //                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        //
                        setGraphic(managebtn);

                        setText(null);
                    }


                }

            };

            return cell;
        };
        enrolleeTable.setItems(Enrolleelist);
        editCol.setCellFactory(cellFoctory);

    }

    @FXML
    @Override
    public void initialize() {
        // working days week by week
        // that me makes me wanna drink
        // every fridays I got drink
        // maybe should I really quit

 //САНЕК ПЕРЕСТАНЬ УДАЛЯТЬ ДАТЫ АХАХА ЭТО НУЖНЫЙ КОД
        dateNowAdmin.setValue(dateNowFromTable()); //ставим дату которая щас
        dateDocumentsAdmin.setValue(lastDateFromTable()); //ставим последнюю дату при которой можно отправлять и редактировать документы
        dateNowAdmin.setDisable(true);
        dateDocumentsAdmin.setDisable(true);


        if(Date.valueOf(String.valueOf(dateNowAdmin.getValue())).before(Date.valueOf(String.valueOf(dateDocumentsAdmin.getValue())))) //если дата норм то генерировать низя
        {
            ButtonToGenerateLists.setDisable(true);
            ButtonToOpenBudget.setDisable(true);
            ButtonToOpenPaid.setDisable(true);
        }
        else{
            ButtonToGenerateLists.setDisable(false);
            ButtonToOpenBudget.setDisable(false);
            ButtonToOpenPaid.setDisable(false);
        }


        findPicker.getItems().addAll(massiveOfChoices);
        loadDate();
    }

    @FXML
    private void refreshTable() {

        try {
            Enrolleelist.clear();

            String query = "SELECT * FROM enrollees";
            PreparedStatement preparedStatement = getDbConection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
//            Statement statement = getDbConection().createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM enrollees");
            System.out.println("refreshTable used");
            while (resultSet.next()) {
                int id1 = resultSet.getInt(1);
                String firstName1 = resultSet.getString(2);
                String lastName1 = resultSet.getString(3);
                LocalDate date1 = resultSet.getDate(4).toLocalDate();
                String whyUs1 = resultSet.getString(5);
                String email1 = resultSet.getString(6);
                String status1 = resultSet.getString(7);
                Enrolleelist.add(new Enrollee(id1, firstName1, lastName1, date1, email1, status1, whyUs1));
                System.out.println("id - " + id1);
                enrolleeTable.setItems(Enrolleelist);


                //тут не все поля из таблицы без хешмепов всяких

//                    String exams1 = resultSet.getString(8);

//                    HashMap<Integer, String> examsHashMap1 = new HashMap<>();
//                    GsonBuilder gsonBuilder = new GsonBuilder();
//                    Gson gsonObj = gsonBuilder.create();
//                    String JSONString = gsonObj.toJson(exams1);


//                    String notifications1 = resultSet.getString(9);
//                    String directions1 = resultSet.getString(10);

//                Enrollee enrollee = new Enrollee(id1, firstName1, lastName1, date1, email1, status1, whyUs1);
//                System.out.println(enrollee);
//                Enrolleelist.add(enrollee);
//                System.out.println(Enrolleelist.get(0));


            }
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("tut");
        }
    }

    private void getEnrolleeListById() {
        ObservableList<Enrollee> list = FXCollections.observableArrayList();
        Statement statement = null;

        try {

            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM enrollees");
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt(1);
                String firstName1 = resultSet.getString(2);
                String lastName1 = resultSet.getString(3);
                LocalDate date1 = resultSet.getDate(4).toLocalDate();
                String whyUs1 = resultSet.getString(5);
                String email1 = resultSet.getString(6);
                String status1 = resultSet.getString(7);
                //тут не все поля из таблицы без хешмепов всяких

//                    String exams1 = resultSet.getString(8);

//                    HashMap<Integer, String> examsHashMap1 = new HashMap<>();
//                    GsonBuilder gsonBuilder = new GsonBuilder();
//                    Gson gsonObj = gsonBuilder.create();
//                    String JSONString = gsonObj.toJson(exams1);


//                    String notifications1 = resultSet.getString(9);
//                    String directions1 = resultSet.getString(10);

                Enrollee enrollee = new Enrollee(id1, firstName1, lastName1, date1, email1, status1, whyUs1);
//                System.out.println(enrollee);
                if (Integer.parseInt(findField.getText()) == enrollee.getId()) {
                    list.add(enrollee);
                }
                enrolleeTable.setItems(list);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println("tut");
        }
    }

    private ObservableList<Enrollee> getEnrolleeListByFio() {
        ObservableList<Enrollee> list = FXCollections.observableArrayList();
        Statement statement = null;

        try {

            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM enrollees");
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt(1);
                String firstName1 = resultSet.getString(2);
                String lastName1 = resultSet.getString(3);
                LocalDate date1 = resultSet.getDate(4).toLocalDate();
                String whyUs1 = resultSet.getString(5);
                String email1 = resultSet.getString(6);
                String status1 = resultSet.getString(7);
                //тут не все поля из таблицы без хешмепов всяких

//                    String exams1 = resultSet.getString(8);

//                    HashMap<Integer, String> examsHashMap1 = new HashMap<>();
//                    GsonBuilder gsonBuilder = new GsonBuilder();
//                    Gson gsonObj = gsonBuilder.create();
//                    String JSONString = gsonObj.toJson(exams1);


//                    String notifications1 = resultSet.getString(9);
//                    String directions1 = resultSet.getString(10);

                Enrollee enrollee = new Enrollee(id1, firstName1, lastName1, date1, email1, status1, whyUs1);
//                System.out.println(enrollee);
                if (enrollee.getFio().contains(findField.getText())) {
                    list.add(enrollee);
                }
                enrolleeTable.setItems(list);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println("tut");
        }
        return list;
    }


    public void ClickToSendNotification(ActionEvent actionEvent) {
        String notificationText = notificationTextAdmin1.getText();
        if (forAll.isSelected()) {
            //тут еще надо прописать типа стираем то что написано в поле № если пользователь там чёта написал

            //тогда отправляем текст в очень хайповую таблицу "notificationsForAll"
            try {
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();
//                System.out.println("пытаюсь положить "+notificationText); //тут все норм

                statement.executeUpdate("INSERT INTO notificationsforall(text) VALUES" + "(" + "'" + notificationText + "'" + ")");
                statement.executeUpdate("UPDATE enrollees SET hasReadGlobalNotifications=" + "'" + 0 + "'");
                statement.executeUpdate("INSERT INTO notificationsForAll(text) VALUES" + "(" + "'" + notificationText + "'" + ")");

                System.out.println("записали уведомление в хайповую таблицу для уведомленийДляВсех");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else //если галочка не поставлена то считываем поле с № и !отправляем уже в таблицу enrollees!!!
        {
            int specialEnrolleeId = Integer.parseInt(forSpecificEnrollee.getText());

            try {
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();

                //сначала надо достать массив предыдущих уведомлений
                //поэтому начинаем играться с gson
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gsonObj = gsonBuilder.create();

                HashMap<String, ArrayList<ArrayList<String>>> TableNotifications = notificationsFromTable(specialEnrolleeId);


                //вот что тут происходит:
                //{"new":[ ["Имя админа/id",дата,"new_text1"], ["Имя админа/id",дата,"new_text2"] , и тд ],
                //типа  HashMap<String, ArrayList>, но этот ArrayList<ArrayList<String>>


                if (NullInNotifcations(specialEnrolleeId)) //если еще не отправляли ничего лИчнОгО раньше
                {
                    HashMap<String, ArrayList<ArrayList<String>>> personalNotifications = new HashMap<>();

                    ArrayList<ArrayList<String>> arrayOfArrays = new ArrayList<>();
                    ArrayList<String> arr = new ArrayList<>();

                    arr.add(String.valueOf(admin.getidAdmin()));
                    arr.add(String.valueOf(dateNowAdmin.getValue()));
                    arr.add(notificationText);

                    arrayOfArrays.add(arr);

                    personalNotifications.put("new", arrayOfArrays);

                    String JSONString = gsonObj.toJson(TableNotifications);

                }
                //проверяю есть лю ключ "new"
                else if (TableNotifications.containsKey("new")) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(String.valueOf(admin.getidAdmin()));
                    arrayList.add(String.valueOf(dateNowAdmin.getValue()));
                    arrayList.add(notificationText);

                    TableNotifications.get("new").add(arrayList);
                } else {
                    //еще раз показываю:
                    //{"new":[ ["Имя админа/id",дата,"new_text1"], ["Имя админа/id",дата,"new_text2"] , и тд ],
                    //типа  HashMap<String, ArrayList>, но этот ArrayList<ArrayList<String>>

                    ArrayList<ArrayList<String>> massiveOfmassives = new ArrayList<>();

                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(String.valueOf(admin.getidAdmin()));
                    arr.add(String.valueOf(dateNowAdmin.getValue()));
                    arr.add(notificationText);

                    massiveOfmassives.add(arr);

                    TableNotifications.put("new", massiveOfmassives);
                }

                //и уже потом обратно переписанный массив положить в базу

                String JSONString = gsonObj.toJson(TableNotifications);

                statement.executeUpdate("UPDATE enrollees SET notifications=" + "'" + JSONString + "'" +
                        " WHERE idEnrollee='" + specialEnrolleeId + "'");
                System.out.println("Отправили новое персональное уведомление для человечка №" + forSpecificEnrollee.getText() + JSONString);


                //а потом ставим 1 в колонке  hasNewPersonalNotification
                statement.executeUpdate("UPDATE enrollees SET hasNewPersonalNotification=" + "'" + 1 + "'" +
                        " WHERE idEnrollee='" + specialEnrolleeId + "'");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            notificationTextAdmin1.clear(); //типа убираем текст
            forSpecificEnrollee.clear();
            forAll.setSelected(false);
//            Alert a = new Alert();
            //надо как-то поприветствовать аДМиНа по имени и фамилии, не зря же я класс сделала новый то бля

            //сюда бы какое-нибудь что-то что показывает что уведомление успешно отправлено но я пока не придумала

        }
        notificationTextAdmin1.clear(); //типа убираем текст
        forSpecificEnrollee.clear();
        forAll.setSelected(false);


    }

    public HashMap<String, ArrayList<ArrayList<String>>> notificationsFromTable(int id) {
        HashMap<String, ArrayList<ArrayList<String>>> OldNotifications = new HashMap<>();
        if (NullInNotifcations(id) == false) //если там чета было то заполняем
        {
            Statement statement = null;
            String foundString = "";

            try {
                statement = getDbConection().createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT notifications FROM enrollees WHERE idEnrollee='" + id + "'");
                if (resultSet.next()) {
                    foundString = resultSet.getString("notifications");
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Type type = OldNotifications.getClass();

            GsonBuilder gsonBuilder = new GsonBuilder(); //мой любимый Гсон...
            Gson gsonObj = gsonBuilder.create(); //оаоао мммм ))000)))000

            OldNotifications = gsonObj.fromJson(foundString, type); //все супер все достали
            System.out.println("Вот они причёсанные " + OldNotifications);
        }

        return OldNotifications; //ну а если ничего не было то типа просто вернули пустой ArrayList


    }

    public boolean NullInNotifcations(int id) {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT notifications FROM enrollees WHERE idEnrollee='" + id + "'");
            if (resultSet.next()) {
                foundNULL = resultSet.getString("notifications");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут в notifications: " + foundNULL);
        return Objects.equals(foundNULL, "");

    }


    public LocalDate dateNowFromTable() {

        Statement statement = null;
        LocalDate foundDate = null;
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT datenow FROM notificationsForAll WHERE text = 'Добро пожаловать!'");
            if (resultSet.next()) {
                foundDate = resultSet.getDate("datenow").toLocalDate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Вот наша воображаемая дата сейчас: " + foundDate);

        return foundDate;
    }

    public LocalDate lastDateFromTable() {

        Statement statement = null;
        LocalDate foundDate = null;
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT lastdate FROM notificationsForAll WHERE text = 'Добро пожаловать!'");
            if (resultSet.next()) {
                foundDate = resultSet.getDate("lastdate").toLocalDate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Абитура может редактировать инфу до: " + foundDate);

        return foundDate;
    }

    public void СlickToEditSaveDate(ActionEvent actionEvent) {

        dateNowAdmin.setDisable(false);
        dateDocumentsAdmin.setDisable(false);
        saveDateAdmin.setText("Сохранить");

        saveDateAdmin.setOnAction(event -> {

            LocalDate newNowDate = dateNowAdmin.getValue();
            LocalDate newLastDate = dateDocumentsAdmin.getValue();

            //сначала заносим в базу эти даты
            try {
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE notificationsforall SET datenow=" + "'" + newNowDate + "'");

                statement.executeUpdate("UPDATE notificationsforall SET lastdate=" + "'" + newLastDate + "'");

                System.out.println("в таблице новые даты: " + newNowDate + " и " + newLastDate);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //а потом нам надо отправить всем абитуриентам уведомление, что дата поменялась
            try {
                String notificationText = "Внимание! Изменился сок подачи документов: редактирование информации доступно до " + String.valueOf(newLastDate);
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();

                statement.executeUpdate("INSERT INTO notificationsforall(text) VALUES" + "(" + "'" + notificationText + "'" + ")");

                System.out.println("записали уведомление о сроках подачи документов в хайповую таблицу для уведомленийДляВсех");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            dateNowAdmin.setDisable(true);
            dateDocumentsAdmin.setDisable(true);
            saveDateAdmin.setText("Редактировать");
            saveDateAdmin.setOnAction(this::СlickToEditSaveDate); //ЗаКолЬцоВыВаЕм
        });


    }


}


