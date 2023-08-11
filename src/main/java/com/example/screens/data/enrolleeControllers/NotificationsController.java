package com.example.screens.data.enrolleeControllers;

import com.example.screens.HelloController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;
import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
//import java.util.Collections;

public class NotificationsController extends HelloController {

    @FXML
    public VBox notificationsVbox = new VBox(); //главные vBox в котором все уведомления
    private HashMap<String, ArrayList<ArrayList<String>>> personalNotifications; //я в вострорге от таких хэшмепов
    private ArrayList<String> globalNotifications; //для глобальных пока что так

    public class OurNotification {
        public Separator separator; //просто для красоты и хайпа
        public Text adminId; // от: id админа
        public Text date; // дата отправки
        public TextArea textArea; //место для уведомление
        public Button OKButton; //кнопка о том, что прочитано
        public Text read; //если уведомление старое, то будет написано "прочитано" вместо кнопки
        public VBox littleVbox; //эта вся красота в маленьком vbox(отдельный для каждого уведомления)


        //этот конструктор для персональных уведомлений
        OurNotification(ArrayList<String> arr, String newOrOld, String IdText) //в конструктор кладем инфу из ArrayList
        {
            //String ourId,String ourDate,String ourText, String newOrOld
            String ourId = arr.get(0);
            String ourDate = arr.get(1);
            String ourText = arr.get(2);


            ///сепаратор///
            this.separator = new Separator();

            this.separator.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.separator.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.separator.setPrefWidth(800);
            this.separator.setPrefHeight(Region.USE_COMPUTED_SIZE);

            this.separator.setMaxWidth(Region.USE_PREF_SIZE);
            this.separator.setMaxHeight(Region.USE_COMPUTED_SIZE);

            ///от кого///
            this.adminId = new Text("От администратора №" + ourId);
            this.adminId.setFont(Font.font("system", FontWeight.THIN, FontPosture.REGULAR, 15));
            this.adminId.setStyle("-fx-text-fill: #545050;");
            ///дата///
            this.date = new Text("Дата: " + ourDate);
            this.date.setFont(Font.font("system", FontWeight.THIN, FontPosture.REGULAR, 15));
            this.date.setStyle("-fx-text-fill: #545050;");

            ///поле уведомлений///
            this.textArea = new TextArea(ourText);
            this.textArea.setEditable(false);
            this.textArea.setStyle("-fx-background-radius:  20;");
            this.textArea.setStyle("-fx-border-radius:  20;");
            this.textArea.setStyle("-fx-border-color:  #8d8b8b;");
            this.textArea.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));

            this.textArea.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.textArea.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.textArea.setPrefWidth(700);
            this.textArea.setPrefHeight(100);

            this.textArea.setMaxWidth(Region.USE_COMPUTED_SIZE);
            this.textArea.setMaxHeight(Region.USE_COMPUTED_SIZE);
            this.textArea.setWrapText(true); //проверяю

            ///кнопка/// -> если "newOrOld"=="new"
            this.OKButton = new Button("Ознакомлен(а)");
            this.OKButton.setFont(Font.font(13));
            this.OKButton.setAlignment(Pos.CENTER); //хз надо ли это

            this.OKButton.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.OKButton.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.OKButton.setPrefWidth(132);
            this.OKButton.setPrefHeight(35);

            this.OKButton.setMaxWidth(Region.USE_COMPUTED_SIZE);
            this.OKButton.setMaxHeight(Region.USE_COMPUTED_SIZE);

            ///прочитано///
            this.read = new Text("Прочитано");
            this.adminId.setFont(Font.font("system", FontWeight.THIN, FontPosture.REGULAR, 15));
            this.adminId.setStyle("-fx-text-fill: #545050;");

            ///vBox///
            littleVbox = new VBox(separator, adminId, date, textArea); //сначала добавляем базу
            //а потом смотрим прочитано или нет и добавляем либо кнопку либо текст что прочитано
            if (Objects.equals(newOrOld, "new")) {
                littleVbox.getChildren().add(OKButton);
            } else {
                littleVbox.getChildren().add(read);
            }

            this.littleVbox.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.littleVbox.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.littleVbox.setPrefWidth(700);
            this.littleVbox.setPrefHeight(Region.USE_COMPUTED_SIZE);

            this.littleVbox.setMaxWidth(Region.USE_PREF_SIZE);
            this.littleVbox.setMaxHeight(Region.USE_COMPUTED_SIZE);

            littleVbox.setSpacing(5);
            littleVbox.setAlignment(Pos.CENTER_LEFT);
            littleVbox.setId(IdText);
            OKButton.setOnAction(this::ClickToReadTheNotification);

        }

        //этот конструктор для общих
        OurNotification(String text) {

//            System.out.println("привет из конструктора: "+text); в конструктор заходит
            //from system
            this.date = new Text("System");
            this.date.setFont(Font.font("system", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            this.date.setStyle("-fx-text-fill: #545050; ");
            this.date.setStyle("-fx-opacity: 40%");

            ///сепаратор///
            this.separator = new Separator();

            this.separator.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.separator.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.separator.setPrefWidth(800);
            this.separator.setPrefHeight(Region.USE_COMPUTED_SIZE);

            this.separator.setMaxWidth(Region.USE_PREF_SIZE);
            this.separator.setMaxHeight(Region.USE_COMPUTED_SIZE);

            ///поле уведомлений///
            this.textArea = new TextArea(text);
            this.textArea.setEditable(false);
            this.textArea.setStyle("-fx-background-radius:  20;");
            this.textArea.setStyle("-fx-border-radius:  20;");
            this.textArea.setStyle("-fx-border-color:  #8d8b8b;");
            this.textArea.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));

            this.textArea.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.textArea.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.textArea.setPrefWidth(700);
            this.textArea.setPrefHeight(100);

            this.textArea.setMaxWidth(Region.USE_COMPUTED_SIZE);
            this.textArea.setMaxHeight(Region.USE_COMPUTED_SIZE);

            this.textArea.setWrapText(true);

            littleVbox = new VBox(date, separator, textArea);

            this.littleVbox.setMinWidth(Region.USE_COMPUTED_SIZE);
            this.littleVbox.setMinHeight(Region.USE_COMPUTED_SIZE);

            this.littleVbox.setPrefWidth(700);
            this.littleVbox.setPrefHeight(Region.USE_COMPUTED_SIZE);

            this.littleVbox.setMaxWidth(Region.USE_PREF_SIZE);
            this.littleVbox.setMaxHeight(Region.USE_COMPUTED_SIZE);

            littleVbox.setSpacing(5);
            littleVbox.setAlignment(Pos.CENTER_LEFT);

        }


        public void ClickToReadTheNotification(ActionEvent actionEvent) {
            littleVbox.getChildren().remove(OKButton);

            Text read = new Text("Прочитано");
            read.setFont(Font.font("system", FontWeight.THIN, FontPosture.REGULAR, 15));
            read.setStyle("-fx-text-fill: #545050;");

            littleVbox.getChildren().add(read);

            //но нам еще надо перенести в базе значение из "new" в "old"
            //возможно в массиве нужно искать по индексу vBox-a

            String someId = this.littleVbox.getId();
            System.out.println("----------------------------------------------");
            System.out.println("Буду менять позицию " + someId); //вот мы определили на какой позиции в массиве "new" будем менять
            System.out.println("-------------------вот массив который я хочу перенести---------------------------");
            ArrayList<String> newArr = new ArrayList<>();
            newArr = (personalNotifications.get("new")).get(Integer.parseInt(someId)); //копируем чтоб перенести
            (personalNotifications.get("new")).remove(Integer.parseInt(someId)); //и удаляем нахуй из "new"
            // и помещаем в "old"
            personalNotifications.get("old").add(newArr);

            //ух ёпта устала нахуй
            //я хочу уже пойти смотреть Сумерки или турецкий сериал про Серкана Болата

            //так собрались осталось просто засунуть в базу
            //я уже просто пишу комменты чтоб себя успокоить
            //так поехали

            try {
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();

                //сначала надо достать массив предыдущих уведомлений
                //поэтому начинаем играться с gson
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gsonObj = gsonBuilder.create();

                String JSONString = gsonObj.toJson(personalNotifications);

                statement.executeUpdate("UPDATE enrollees SET notifications=" + "'" + JSONString + "'" +
                        " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
                System.out.println("человечек прочитал уведомление!");

                // иставим 0 в таблице
                statement.executeUpdate("UPDATE enrollees SET hasNewPersonalNotification=" + "'" + 0 + "'" +
                        " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }

    }


    @FXML
    public void initialize() {

        notificationsVbox.setSpacing(15); //между уведомлениями расстояние 10

        //сначала чекаем че по персональным уведомлениям
        personalNotifications = notificationsFromTable(); //тут достается правильно

        //потом глобальные
        globalNotifications = globalNotificationsFromTable();

        //осталось запихнуть красивенько в vBox

        if(finalListExists())
        {
            Text finalLabel = new Text("Списки прошедших на направления");
            finalLabel.setFont(Font.font("system", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20));
            notificationsVbox.getChildren().add(finalLabel);


 //для бюджета
            Text budgetText = new Text("Список на бюджетную основу");
            budgetText.setFont(Font.font("system", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            budgetText.setWrappingWidth(291.6528015136719);

            Button openBudgetButton = new Button("Открыть");
            openBudgetButton.setMinWidth(Region.USE_COMPUTED_SIZE);
            openBudgetButton.setMinHeight(Region.USE_COMPUTED_SIZE);

            openBudgetButton.setPrefWidth(90);
            openBudgetButton.setPrefHeight(50);

            openBudgetButton.setMaxWidth(Region.USE_PREF_SIZE);
            openBudgetButton.setMaxHeight(Region.USE_COMPUTED_SIZE);
            openBudgetButton.setStyle("-fx-background-radius:  20;");

            openBudgetButton.setOnAction(this::ClickToButtonToOpenBudget);


            ToolBar budgetToolbar = new ToolBar(budgetText,openBudgetButton);

            budgetToolbar.setMinWidth(Region.USE_COMPUTED_SIZE);
            budgetToolbar.setMinHeight(Region.USE_COMPUTED_SIZE);

            budgetToolbar.setPrefWidth(400);
            budgetToolbar.setPrefHeight(60);

            budgetToolbar.setMaxWidth(Region.USE_PREF_SIZE);
            budgetToolbar.setMaxHeight(Region.USE_COMPUTED_SIZE);
            budgetToolbar.setStyle("-fx-background-radius:  20;");

//для платки

            Text paidText = new Text("Список на платную основу");
            paidText.setFont(Font.font("system", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            paidText.setWrappingWidth(291.6528015136719);

            Button openPaidButton = new Button("Открыть");
            openPaidButton.setMinWidth(Region.USE_COMPUTED_SIZE);
            openPaidButton.setMinHeight(Region.USE_COMPUTED_SIZE);

            openPaidButton.setPrefWidth(90);
            openPaidButton.setPrefHeight(50);

            openPaidButton.setMaxWidth(Region.USE_PREF_SIZE);
            openPaidButton.setMaxHeight(Region.USE_COMPUTED_SIZE);
            openPaidButton.setStyle("-fx-background-radius:  20;");

            openPaidButton.setOnAction(this::ClickToButtonToOpenPaid);

            ToolBar paidToolbar = new ToolBar(paidText,openPaidButton);

            paidToolbar.setMinWidth(Region.USE_COMPUTED_SIZE);
            paidToolbar.setMinHeight(Region.USE_COMPUTED_SIZE);

            paidToolbar.setPrefWidth(400);
            paidToolbar.setPrefHeight(60);

            paidToolbar.setMaxWidth(Region.USE_PREF_SIZE);
            paidToolbar.setMaxHeight(Region.USE_COMPUTED_SIZE);
            paidToolbar.setStyle("-fx-background-radius:  20;");

            VBox finalListVbox = new VBox(budgetToolbar,paidToolbar);

            finalListVbox.setMinWidth(Region.USE_COMPUTED_SIZE);
            finalListVbox.setMinHeight(Region.USE_COMPUTED_SIZE);

            finalListVbox.setPrefWidth(700);
            finalListVbox.setPrefHeight(Region.USE_COMPUTED_SIZE);

            finalListVbox.setMaxWidth(Region.USE_PREF_SIZE);
            finalListVbox.setMaxHeight(Region.USE_COMPUTED_SIZE);

            finalListVbox.setSpacing(5);
            finalListVbox.setAlignment(Pos.CENTER);

            notificationsVbox.getChildren().add(finalListVbox);
            notificationsVbox.setAlignment(Pos.CENTER);

        }



        //для дизайна: показываем пользователю, что есть персональные и общие уведомления
        Text personalLabel = new Text("Персональные уведомления");
        personalLabel.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Text globalLabel = new Text("Общие уведомления");
        globalLabel.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));


//        //создала еще один массив в котором будут массивы уведомлений (ну а че, я люблю массивы)
//        ArrayList<ArrayList<String>> arrayListOfArrayLists = new ArrayList<>();

        if (personalNotifications.get("new").size() != 0 || personalNotifications.get("old").size() != 0) {
            notificationsVbox.getChildren().add(personalLabel);
        }

        ArrayList<ArrayList<String>> newNotifications = new ArrayList<>();
        ArrayList<ArrayList<String>> oldNotifications = new ArrayList<>();

        //сначала смотрим есть ли ключ "new" и если есть то они идут первые в наш arrayListOfArrayLists чтобы первыми их достать (опять стек-vibes)
        if (personalNotifications.get("new").size() != 0) {
            //new распознает верно))

            newNotifications = personalNotifications.get("new"); //утт тоже все норм

            //типа я написала что теперь наш arrayListOfArrayLists это масисв из новых уведомлений

            //сначала добавим их в главный vBox
            for (int i = 0; i < newNotifications.size(); i++) //тут все нормально
            {
                OurNotification notification = new OurNotification(newNotifications.get(i), "new", String.valueOf(i));
                notificationsVbox.getChildren().add(notification.littleVbox);
            }

        }

        //а теперь туда добавляем в конец старые уведомления
        if (personalNotifications.get("old").size() != 0) {
            oldNotifications = personalNotifications.get("old");

            for (int i = 0; i < oldNotifications.size(); i++) {
                OurNotification notification = new OurNotification(oldNotifications.get(i), "old", "-1");
                notificationsVbox.getChildren().add(notification.littleVbox);
            }

        }

        //теперь добавляю глобальные
        //не забываем что их надо засовывать с конца чтоб новое было сверху

        //добавляю заголовок
        if (!hasReadglobal()) {
            globalLabel.setText("Общие (новое)");
            globalLabel.setStyle("-fx-text-fill: #f00;");
            Button globalOkButton = new Button("Ознакомлен(а)");
            notificationsVbox.getChildren().add(globalOkButton);
            globalOkButton.setOnAction(event ->
            {
                notificationsVbox.getChildren().remove(globalOkButton);
                try {
                    Connection conn = getDbConection();
                    Statement statement = conn.createStatement();
                    statement.executeUpdate("UPDATE enrollees SET hasReadGlobalNotifications=" + "'" + 1 + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'"); //ставится не та дата

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            });
        }

        notificationsVbox.getChildren().add(globalLabel);

        for (int i = globalNotifications.size() - 1; i >= 0; i--) {
            System.out.println("-----" + globalNotifications.get(i));
            OurNotification notification2 = new OurNotification(globalNotifications.get(i));
            notificationsVbox.getChildren().add(notification2.littleVbox);

        }





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

    public HashMap<String, ArrayList<ArrayList<String>>> notificationsFromTable() {
        HashMap<String, ArrayList<ArrayList<String>>> tableNotifications = new HashMap<>();

        Statement statement = null;
        String foundString = "";

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT notifications FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                foundString = resultSet.getString("notifications");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Type type = tableNotifications.getClass();

        GsonBuilder gsonBuilder = new GsonBuilder(); //мой любимый Гсон...
        Gson gsonObj = gsonBuilder.create(); //оаоао мммм ))000)))000

        System.out.println("Вот что пытаюсь вытащить " + foundString);

        tableNotifications = gsonObj.fromJson(foundString, type); //все супер все достали
        System.out.println("Вот они персональные вышли " + tableNotifications);

        return tableNotifications; //ну а если ничего не было то типа просто вернули пустой ArrayList

    }


    public ArrayList<String> globalNotificationsFromTable() {
        ArrayList<String> GlobalNotifications = new ArrayList<>();

        Statement statement = null;
        String foundString = "";

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT text FROM notificationsforall");
            while (resultSet.next()) {
                foundString = resultSet.getString("text");
                GlobalNotifications.add(foundString);

            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Вот они глобальные " + GlobalNotifications);


        //ну а если ничего не было то типа просто вернули пустой ArrayList
        //спойлер: там никогда не пусто потому что там уведомление "Добро пожаловать"

        return GlobalNotifications;

    }

    public boolean hasReadglobal() {
        Statement statement = null;
        String found1 = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT hasReadGlobalNotifications FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                found1 = resultSet.getString("hasReadGlobalNotifications");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Прочел глобальные = 1 / не прочел = 0 : " + found1);
        return Objects.equals(found1, "1");

    }
    public boolean finalListExists()
    {
        Statement statement = null;
        String found1 = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT finalList FROM notificationsForAll WHERE text='Добро пожаловать!'");
            if(resultSet.next())
            {
                found1 = resultSet.getString("finalList");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("есть финальные списки = 1 / нет = 0 : "+found1);
        return Objects.equals(found1, "1");

    }


}
