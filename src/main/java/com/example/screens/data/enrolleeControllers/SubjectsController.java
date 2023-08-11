package com.example.screens.data.enrolleeControllers;

import java.lang.reflect.Type;
import java.sql.*;


import com.example.screens.HelloController;
import com.google.gson.Gson; //саня это пиздец а не библиотека

import java.sql.Date;
import java.util.*;


import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;



public class SubjectsController extends HelloController {
    @FXML
    private ChoiceBox<String> subject1 = new ChoiceBox<>();

    @FXML
    private TextField score1 = new TextField();

    @FXML
    private VBox mainVbox = new VBox();

    @FXML
    private ToolBar toolBar1 = new ToolBar();

    @FXML
    private Button moreButton1 = new Button();

    @FXML
    private Button deliteButton1 = new Button();

    @FXML
    private Button editSaveSubjectButton = new Button();

    @FXML
    private VBox vbox;

    /////////

    private Integer numberOfSubject = (int) mainVbox.getChildren().stream().count(); //для того чтобы выбрали не более 4 предметов(русский/матеша/физика/инфа)

    private HashMap<String, Double> listOfToolBars; //тут чилят все предметы и баллы которые указал человечек
    private final String[] massiveOfSubjects = new String[]{"Математика", "Физика", "Информатика"};

    private OurToolBar obj2 = new OurToolBar();
    private OurToolBar obj3 = new OurToolBar();
    private OurToolBar obj4 = new OurToolBar();

    @FXML
    public void ClickToEditSaveSubject(ActionEvent event) {


        editSaveSubjectButton.setText("Сохранить");
        moreButton1.setDisable(false);
        subject1.setDisable(false);
        score1.setDisable(false);
//            score1.setTextFormatter(new );
        obj2.newSubject.setDisable(false);
        obj2.newMoreButton.setDisable(false);
        obj2.newDeleteButton.setDisable(false);
        obj2.newScore.setDisable(false);

        obj3.newSubject.setDisable(false);
        obj3.newMoreButton.setDisable(false);
        obj3.newDeleteButton.setDisable(false);
        obj3.newScore.setDisable(false);

        obj4.newSubject.setDisable(false);
        obj4.newMoreButton.setDisable(false);
        obj4.newDeleteButton.setDisable(false);
        obj4.newScore.setDisable(false);

        editSaveSubjectButton.setOnAction(event2 ->
        {
            try {
                // проверяем значения
                System.out.println(score1.getText() + " - 1\n" + obj2.newScore.getText() + " - 2\n" + obj3.newScore.getText() + " - 3\n" + obj4.newScore.getText() + " - 4");

                int mark = Integer.parseInt(score1.getText());
                if (mark > 100 | mark < 0) {
                    throw new NumberFormatException();
                }
                if (!Objects.equals(obj2.newScore.getText(), "") & obj2.newScore.getText() != null) {

                    mark = Integer.parseInt(obj2.newScore.getText());
                    if (mark > 100 | mark < 0) {
                        throw new NumberFormatException();
                    }

                }
                if (!Objects.equals(obj3.newScore.getText(), "") & obj3.newScore.getText() != null) {

                    mark = Integer.parseInt(obj3.newScore.getText());
                    if (mark > 100 | mark < 0) {
                        throw new NumberFormatException();
                    }
                }
                if (!Objects.equals(obj4.newScore.getText(), "") & obj4.newScore.getText() != null) {


                    mark = Integer.parseInt(obj4.newScore.getText());
                    if (mark > 100 | mark < 0) {
                        System.out.println("err in value 4");
                        throw new NumberFormatException();
                    }
                }
//
                System.out.println("nachiaetsya proverka na sovpadenie");

                // Проверка на null и совпадение названия предметов
                if ((Objects.equals(obj2.newSubject.getValue(), obj3.newSubject.getValue()) & !Objects.equals(obj2.newSubject.getValue(), "") & obj2.newSubject.getValue() != null & !Objects.equals(obj3.newSubject.getValue(), "") & obj3.newSubject.getValue() != null) |
                        (Objects.equals(obj2.newSubject.getValue(), obj4.newSubject.getValue()) & !Objects.equals(obj2.newSubject.getValue(), "") & !Objects.equals(obj4.newSubject.getValue(), "") & obj2.newSubject.getValue() != null & obj4.newSubject.getValue() != null) |
                        (Objects.equals(obj3.newSubject.getValue(), obj4.newSubject.getValue()) & !Objects.equals(obj3.newSubject.getValue(), "") & !Objects.equals(obj4.newSubject.getValue(), "") & obj3.newSubject.getValue() != null & obj4.newSubject.getValue() != null)) {
                    System.out.println("ya tut");
                    throw new Exception();
                }
                HashMap<String, Double> EditedListOfToolBars = new HashMap<>();

                try {
                    System.out.println(score1.getText() + " \n" + obj2.newScore.getText() + "=\n " + obj3.newScore.getText() + " " + obj4.newScore.getText());
                    //                Integer.parseInt(score1.getText());
                    //                if (!Objects.equals(obj2.newScore.getText(), "")){
                    //                    Integer.parseInt(obj2.newScore.getText());
                    //                }
                    //                if (!Objects.equals(obj3.newScore.getText(), "")){
                    //                    Integer.parseInt(obj3.newScore.getText());
                    //                }
                    //                if (!Objects.equals(obj4.newScore.getText(), "")){
                    //                    Integer.parseInt(obj4.newScore.getText());
                    //                }


                    editSaveSubjectButton.setText("Редактировать");
                    //убираем возможность редактирования после нажатия на кнопку сохранить
                    moreButton1.setDisable(true);
                    subject1.setDisable(true);

                    score1.setDisable(true);

                    obj2.newSubject.setDisable(true);
                    obj2.newMoreButton.setDisable(true);
                    obj2.newDeleteButton.setDisable(true);
                    obj2.newScore.setDisable(true);

                    obj3.newSubject.setDisable(true);
                    obj3.newMoreButton.setDisable(true);
                    obj3.newDeleteButton.setDisable(true);
                    obj3.newScore.setDisable(true);

                    obj4.newSubject.setDisable(true);
                    obj4.newMoreButton.setDisable(true);
                    obj4.newDeleteButton.setDisable(true);
                    obj4.newScore.setDisable(true);


                    //                try {

                    //int countToolbars = (int) mainVbox.getChildren().stream().count(); //тут узнаем сколько у нас тулбаров на экране потому что счетчик пиздит жоска

                    try {
                        obj2.setToolbarSubject(obj2.newSubject.getValue());
                        obj2.setToolbarScore(Integer.parseInt(obj2.newScore.getText()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "obj2 пустой");
                    } finally {
                        System.out.println(obj2.info());
                    }

                    try {
                        obj3.setToolbarSubject(obj3.newSubject.getValue());
                        obj3.setToolbarScore(Integer.parseInt(obj3.newScore.getText()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "obj3 пустой");
                    } finally {
                        System.out.println(obj3.info());
                    }

                    try {
                        obj4.setToolbarSubject(obj4.newSubject.getValue());
                        obj4.setToolbarScore(Integer.parseInt(obj4.newScore.getText()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "obj4 пустой");
                    } finally {
                        System.out.println(obj4.info());
                    }

                    EditedListOfToolBars.put(subject1.getValue(), Double.parseDouble(score1.getText()));
                    EditedListOfToolBars.put(obj2.toolbarSubject, Double.parseDouble(String.valueOf(obj2.toolbarScore)));
                    EditedListOfToolBars.put(obj3.toolbarSubject, Double.parseDouble(String.valueOf(obj3.toolbarScore)));
                    EditedListOfToolBars.put(obj4.toolbarSubject, Double.parseDouble(String.valueOf(obj4.toolbarScore)));
                    System.out.println("Вот что попало в черновик: ");
                    System.out.println(EditedListOfToolBars);

                } catch (Exception e) {
                    System.out.println(e.getCause());
                }

                //
                // идем по баллам предметов и если встречаем название по уполчанию 1 то удаляем
                // ну типа не может же быть 1 балл за егэ, а если есть то удаляем  нам такой студент не нужен
                try {
                    for (String i : EditedListOfToolBars.keySet()) { //ну вообще тут по хорошему и нули надо проверять и названия тоже
                        if (i == null) {
                            EditedListOfToolBars.remove(i); //удаляем по ключу который и нашли!
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                listOfToolBars = EditedListOfToolBars; //перезаписываем значения списка на новый, зная, что там всё
                System.out.println("Вот что осталось в черновике: " + EditedListOfToolBars);
                System.out.println("Вот что сохранилось в чистовик: " + listOfToolBars);

                //теперь немного штучек с dataBase

                try {
                    Connection conn = getDbConection();
                    Statement statement = conn.createStatement();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gsonObj = gsonBuilder.create();
                    String JSONString = gsonObj.toJson(listOfToolBars);

                    System.out.println("вот такой айдишник у " + enrollee.getEnrolleeFirstname() + enrollee.getIdEnrollee());

                    statement.executeUpdate("UPDATE enrollees SET exams=" + "'" + JSONString + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

                    System.out.println("Вика господи ты такая молодец спустя столько лет у тея получилось засунуть это говно ");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


                editSaveSubjectButton.setOnAction(this::ClickToEditSaveSubject);//все поехали заново
            } catch (NumberFormatException e) {
                System.out.println("Non-numeric character exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("smth go wrong");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Ошибка при введении поля оценки!");
                alert.showAndWait();

            } catch (Exception e) {
                System.out.println("Values are the same");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("smth go wrong");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Ошибка при выборе предмета!");
                alert.showAndWait();
            }


        });

    }

    @FXML
    public void ClickToAddSubject(ActionEvent event) {


        try {

            if (numberOfSubject == 1) //создаем второй обьект класса тулбара
            {

                mainVbox.getChildren().add(obj2.newToolBar);
                obj2.newMoreButton.setOnAction(this::ClickToAddSubject);
                obj2.newDeleteButton.setOnAction(event1 ->
                {
                    mainVbox.getChildren().remove(obj2.newToolBar); //удаляем сначала из видимости
//                    obj2.newSubject.setValue(null);//а потом типа ставлю дефолтные значения чтобы оно убралось из хэшмепа
//                    obj2.newScore.setText(null);
                    obj2 = new OurToolBar();


                    numberOfSubject -= 1;
                    System.out.println("Убрался второй тулбар. Типа щас на экране у нас " + numberOfSubject + " тулбара");
                });
                System.out.println("Создался второй тулбар Типа кроме первого у нас еще " + numberOfSubject + " тулбара ");
            }
            if (numberOfSubject == 2) //создаем третий тулбар
            {

                mainVbox.getChildren().add(obj3.newToolBar);

                obj3.newMoreButton.setOnAction(this::ClickToAddSubject);
                obj3.newDeleteButton.setOnAction(event2 ->
                {
                    mainVbox.getChildren().remove(obj3.newToolBar);
//                    obj3.newSubject.setValue(null);
//                    obj3.newScore.setText(null);
                    obj3 = new OurToolBar();

                    numberOfSubject -= 1;
                    System.out.println("Убрался третий тулбар. Типа на экране у нас " + numberOfSubject + " тулбара");
                });
                System.out.println("Создался третий тулбар Типа кроме первого у нас еще " + numberOfSubject + " тулбара ");

            }
            if (numberOfSubject == 3) //создаем четвертый тулбар
            {
                //obj3.newMoreButton.setDisable(true);
                //obj4=new OurToolBar();
                mainVbox.getChildren().add(obj4.newToolBar);
                obj4.newMoreButton.setOnAction(this::ClickToAddSubject);
                obj4.newDeleteButton.setOnAction(event3 ->
                {
                    mainVbox.getChildren().remove(obj4.newToolBar);
                    //(Node.toFront() / Node.toBack()),
                    //mainVbox.toBack(obj4.newToolBar);
//                    obj4.newSubject.setValue(null);
//                    obj4.newScore.setText(null);
                    obj4 = new OurToolBar();


                    numberOfSubject -= 1;
                    System.out.println("Убрался четвертый тулбар. Типа на экране щас " + numberOfSubject + " тулбара ");
                });
                System.out.println("Создался четвертый тулбар Типа кроме первого у нас еще " + numberOfSubject + " тулбара ");
            }
            if (numberOfSubject < 4) {
                numberOfSubject += 1;
            }
//            if(numberOfSubject==4 ) {obj4.newMoreButton.setDisable(true);}


        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getCause());
        }


    }

    private class OurToolBar {
        private Integer toolbarScore = null; //надо не ноль (не помню почему, возможно, можно и 0)))
        private String toolbarSubject = null; //надо не нулевую строку (не помню почему)
        protected ChoiceBox<String> newSubject;
        protected TextField newScore;
        protected Button newMoreButton;
        protected Button newDeleteButton;
        protected ToolBar newToolBar;

        public Boolean IAmInVBox; //это решение проблемы но я еще не дошла главное не забыть сделать это

        public OurToolBar()  //тут у нас конструктор
        {
            this.newSubject = new ChoiceBox<String>();
            this.newSubject.getItems().addAll(massiveOfSubjects);
            //this.newSubject.setValue("Предмет");
            this.newSubject.setLayoutX(3);
            this.newSubject.setLayoutY(3);
            this.newSubject.setMinWidth(250);
            this.newSubject.setMinHeight(Region.USE_COMPUTED_SIZE);
            this.newSubject.setPrefWidth(250);
            this.newSubject.setPrefHeight(26);
            this.newSubject.setMaxWidth(260);
            this.newSubject.setMaxHeight(Region.USE_COMPUTED_SIZE);
            this.newSubject.setStyle("-fx-background-radius:  20;");

            this.newScore = new TextField();
            this.newScore.setAlignment(Pos.BASELINE_CENTER);
            this.newScore.setLayoutX(190);
            this.newScore.setLayoutY(0);
            this.newScore.setMinWidth(80);
            this.newScore.setMinHeight(Region.USE_COMPUTED_SIZE);
            this.newScore.setPrefWidth(70);
            this.newScore.setPrefHeight(26);
            this.newScore.setMaxWidth(90);
            this.newScore.setMaxHeight(Region.USE_COMPUTED_SIZE);
            this.newScore.setStyle("-fx-background-radius:  20;");

            this.newMoreButton = new Button("+");
            this.newMoreButton.setMinWidth(25);
            this.newMoreButton.setMinHeight(25);
            this.newMoreButton.setPrefWidth(25);
            this.newMoreButton.setPrefHeight(25);
            this.newMoreButton.setMaxWidth(25);
            this.newMoreButton.setMaxHeight(25);
            this.newMoreButton.setStyle("-fx-background-radius:  20;");
//            this.newMoreButton.setOnAction(this::ClickToAddSubject); //типа так не работает

            this.newDeleteButton = new Button("-");
            this.newDeleteButton.setMinWidth(25);
            this.newDeleteButton.setMinHeight(25);
            this.newDeleteButton.setPrefWidth(25);
            this.newDeleteButton.setPrefHeight(25);
            this.newDeleteButton.setMaxWidth(25);
            this.newDeleteButton.setMaxHeight(25);
            this.newDeleteButton.setStyle("-fx-background-radius:  20;");
//            this.newDeleteButton.setOnAction(this::ClickToDeliteSubject); //типа так не работает

            this.newToolBar = new ToolBar();
            this.newToolBar.setLayoutX(0);
            this.newToolBar.setLayoutY(0);
            this.newToolBar.setMinWidth(410);
            this.newToolBar.setMinHeight(Region.USE_COMPUTED_SIZE);
            this.newToolBar.setPrefWidth(384);
            this.newToolBar.setPrefHeight(37);
            this.newToolBar.setMaxWidth(420);
            this.newToolBar.setMaxHeight(Region.USE_COMPUTED_SIZE);
            this.newToolBar.setStyle("-fx-background-radius:  20;");
            this.newToolBar.getItems().addAll(newDeleteButton, newSubject, newScore, newMoreButton);


        }


        public Integer getToolbarScore() {
            return Integer.parseInt(String.valueOf(toolbarScore));
        }

        public void setToolbarScore(Integer toolbarScore1) {
            this.toolbarScore = toolbarScore1;
        }

        public String getToolbarSubject() {
            return toolbarSubject;
        }

        public void setToolbarSubject(String toolbarSubject1) {
            this.toolbarSubject = Objects.requireNonNullElse(toolbarSubject1, null);

        }

        public String info() {
            if (toolbarSubject == null || toolbarScore == null) {
                return "Empty";
            }
            return "Вот что в тулбаре: " + toolbarScore + " " + toolbarSubject;

        }

    }

    public void initialize()
    {
        if(java.sql.Date.valueOf(String.valueOf(enrollee.dateNow)).before(Date.valueOf(String.valueOf(enrollee.lastDate))))
        {
            mainVbox.setSpacing(10);
            //так ну все поехали
            //если в клеточке ноль(нет информации) то....(узнать продолжение в коде...)
            System.out.println("Пустые экзамены? " + NullInExams());
            if (NullInExams() == true) {

                deliteButton1.setDisable(true);//это всегда нельзя трогать, потому что русский язык нельзя убрать

                moreButton1.setDisable(true);
                subject1.setDisable(true);
                score1.setDisable(true);

                obj2.newSubject.setDisable(true);
                obj2.newMoreButton.setDisable(true);
                obj2.newDeleteButton.setDisable(true);
                obj2.newScore.setDisable(true);

                obj3.newSubject.setDisable(true);
                obj3.newMoreButton.setDisable(true);
                obj3.newDeleteButton.setDisable(true);
                obj3.newScore.setDisable(true);

                obj4.newSubject.setDisable(true);
                obj4.newMoreButton.setDisable(true);
                obj4.newDeleteButton.setDisable(true);
                obj4.newScore.setDisable(true);
                subject1.getItems().add("Русский язык"); //ну типа первый предмет русский язык(его сдают ВСЕ)
                moreButton1.setOnAction(this::ClickToAddSubject);
            } else {


                // и тут сначала расчехляем строку из базы в хешмеп и засовываем в наш чистовик
                listOfToolBars = HashMapInExams();
                // потом считаем сколько там наших любимых BTS (Ви, Чонгук, реп монстер, все на месте?)
                //это встроенная  которая listOfToolBars.size()

                //System.out.println(listOfToolBars.size());
                numberOfSubject = listOfToolBars.size(); //счетчику говорим сколько
                System.out.println("У нас BTS в количестве " + numberOfSubject);

                // и в таком составе пихаем в наш mainvbox
                //помним, что русский всегда первый
                double weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Русский язык")).setScale(3, RoundingMode.DOWN).doubleValue();
                int neededScore = (int) weNeedThisStepInCode;
                score1.setText(String.valueOf(neededScore));
                subject1.setValue("Русский язык");
                moreButton1.setOnAction(this::ClickToAddSubject); //не забываем про кнопОчку типа

                //воу спокойно, сначала просто добавим в mainVbox
                try {
                    if (listOfToolBars.get("Математика") != null) {
                        obj2.newSubject.setValue("Математика");
                        weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Математика")).setScale(3, RoundingMode.DOWN).doubleValue();
                        neededScore = (int) weNeedThisStepInCode;
                        obj2.newScore.setText(String.valueOf(neededScore));
                        mainVbox.getChildren().add(obj2.newToolBar);

                        obj2.newMoreButton.setOnAction(this::ClickToAddSubject); //не забываем сказать что делать кнопкам!
                        obj2.newDeleteButton.setOnAction(event2 ->
                        {
                            mainVbox.getChildren().remove(obj2.newToolBar);
                            obj2.newSubject.setValue(null);
                            obj2.newScore.setText(null);
                            numberOfSubject -= 1;
                            System.out.println("Убрался второй тулбар" + numberOfSubject);
                        });
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    if (listOfToolBars.get("Физика") != null) {
                        obj3.newSubject.setValue("Физика");
                        weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Физика")).setScale(3, RoundingMode.DOWN).doubleValue();
                        neededScore = (int) weNeedThisStepInCode;
                        obj3.newScore.setText(String.valueOf(neededScore));
                        mainVbox.getChildren().add(obj3.newToolBar);

                        obj3.newMoreButton.setOnAction(this::ClickToAddSubject); //не забываем сказать что делать кнопкам!
                        obj3.newDeleteButton.setOnAction(event2 ->
                        {
                            mainVbox.getChildren().remove(obj3.newToolBar);
                            //obj3=null;
                            obj3.newSubject.setValue(null);
                            obj3.newScore.setText(null);
                            numberOfSubject -= 1;
                            System.out.println("Убрался третий тулбар" + numberOfSubject);
                        });
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    if (listOfToolBars.get("Информатика") != null) {
                        obj4.newSubject.setValue("Информатика");
                        weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Информатика")).setScale(3, RoundingMode.DOWN).doubleValue();
                        neededScore = (int) weNeedThisStepInCode;
                        obj4.newScore.setText(String.valueOf(neededScore));
                        mainVbox.getChildren().add(obj4.newToolBar);

                        obj4.newMoreButton.setOnAction(this::ClickToAddSubject); //не забываем сказать что делать кнопкам!
                        obj4.newDeleteButton.setOnAction(event2 ->
                        {
                            mainVbox.getChildren().remove(obj4.newToolBar);
                            obj4.newSubject.setValue(null);
                            obj4.newScore.setText(null);
                            numberOfSubject -= 1;
                            System.out.println("Убрался четвёртый тулбар" + numberOfSubject);
                        });
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //а теперь

                //и запрещаем редактирование наших BTS

                deliteButton1.setDisable(true);//это всегда нельзя трогать, потому что русский язык нельзя убрать

                moreButton1.setDisable(true);
                subject1.setDisable(true);
                score1.setDisable(true);

                obj2.newSubject.setDisable(true);
                obj2.newMoreButton.setDisable(true);
                obj2.newDeleteButton.setDisable(true);
                obj2.newScore.setDisable(true);

                obj3.newSubject.setDisable(true);
                obj3.newMoreButton.setDisable(true);
                obj3.newDeleteButton.setDisable(true);
                obj3.newScore.setDisable(true);

                obj4.newSubject.setDisable(true);
                obj4.newMoreButton.setDisable(true);
                obj4.newDeleteButton.setDisable(true);
                obj4.newScore.setDisable(true);
            }

        }
        else
        {
            mainVbox.setSpacing(10);
            //так ну все поехали
            //если в клеточке ноль(нет информации) то....(узнать продолжение в коде...)
            System.out.println("Пустые экзамены? " + NullInExams());
            if (NullInExams() == true) {

                deliteButton1.setDisable(true);//это всегда нельзя трогать, потому что русский язык нельзя убрать

                moreButton1.setDisable(true);
                subject1.setDisable(true);
                score1.setDisable(true);

                obj2.newSubject.setDisable(true);
                obj2.newMoreButton.setDisable(true);
                obj2.newDeleteButton.setDisable(true);
                obj2.newScore.setDisable(true);

                obj3.newSubject.setDisable(true);
                obj3.newMoreButton.setDisable(true);
                obj3.newDeleteButton.setDisable(true);
                obj3.newScore.setDisable(true);

                obj4.newSubject.setDisable(true);
                obj4.newMoreButton.setDisable(true);
                obj4.newDeleteButton.setDisable(true);
                obj4.newScore.setDisable(true);
                subject1.getItems().add("Русский язык"); //ну типа первый предмет русский язык(его сдают ВСЕ)
                moreButton1.setOnAction(this::ClickToAddSubject);
            } else {


                // и тут сначала расчехляем строку из  базы в хешмеп и засовываем в наш чистовик
                listOfToolBars = HashMapInExams();
                // потом считаем сколько там наших любимых BTS (Ви, Чонгук, реп монстер, все на месте?)
                //это встроенная которая listOfToolBars.size()

                //System.out.println(listOfToolBars.size());
                numberOfSubject = listOfToolBars.size(); //счетчику говорим сколько
                System.out.println("У нас BTS в количестве " + numberOfSubject);

                // и в таком составе пихаем в наш mainvbox

                double weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Русский язык")).setScale(3, RoundingMode.DOWN).doubleValue();
                int neededScore = (int) weNeedThisStepInCode;
                score1.setText(String.valueOf(neededScore));
                subject1.setValue("Русский язык");
                moreButton1.setOnAction(this::ClickToAddSubject); //не забываем про кнопОчку типа

                //воу спокойно, сначала просто добавим в mainVbox
                try {
                    if (listOfToolBars.get("Математика") != null) {
                        obj2.newSubject.setValue("Математика");
                        weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Математика")).setScale(3, RoundingMode.DOWN).doubleValue();
                        neededScore = (int) weNeedThisStepInCode;
                        obj2.newScore.setText(String.valueOf(neededScore));
                        mainVbox.getChildren().add(obj2.newToolBar);

                        obj2.newMoreButton.setOnAction(this::ClickToAddSubject); //не забываем сказать что делать кнопкам!
                        obj2.newDeleteButton.setOnAction(event2 ->
                        {
                            mainVbox.getChildren().remove(obj2.newToolBar);
                            obj2.newSubject.setValue(null);
                            obj2.newScore.setText(null);
                            numberOfSubject -= 1;
                            System.out.println("Убрался второй тулбар" + numberOfSubject);
                        });
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    if (listOfToolBars.get("Физика") != null) {
                        obj3.newSubject.setValue("Физика");
                        weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Физика")).setScale(3, RoundingMode.DOWN).doubleValue();
                        neededScore = (int) weNeedThisStepInCode;
                        obj3.newScore.setText(String.valueOf(neededScore));
                        mainVbox.getChildren().add(obj3.newToolBar);

                        obj3.newMoreButton.setOnAction(this::ClickToAddSubject); //не забываем сказать что делать кнопкам!
                        obj3.newDeleteButton.setOnAction(event2 ->
                        {
                            mainVbox.getChildren().remove(obj3.newToolBar);
                            //obj3=null;
                            obj3.newSubject.setValue(null);
                            obj3.newScore.setText(null);
                            numberOfSubject -= 1;
                            System.out.println("Убрался третий тулбар" + numberOfSubject);
                        });
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    if (listOfToolBars.get("Информатика") != null) {
                        obj4.newSubject.setValue("Информатика");
                        weNeedThisStepInCode = new BigDecimal(listOfToolBars.get("Информатика")).setScale(3, RoundingMode.DOWN).doubleValue();
                        neededScore = (int) weNeedThisStepInCode;
                        obj4.newScore.setText(String.valueOf(neededScore));
                        mainVbox.getChildren().add(obj4.newToolBar);

                        obj4.newMoreButton.setOnAction(this::ClickToAddSubject); //не забываем сказать что делать кнопкам!
                        obj4.newDeleteButton.setOnAction(event2 ->
                        {
                            mainVbox.getChildren().remove(obj4.newToolBar);
                            obj4.newSubject.setValue(null);
                            obj4.newScore.setText(null);
                            numberOfSubject -= 1;
                            System.out.println("Убрался четвёртый тулбар" + numberOfSubject);
                        });
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //а теперь

                //и запрещаем редактирование наших BTS


                deliteButton1.setDisable(true);//это всегда нельзя трогать, потому что русский язык нельзя убрать

                moreButton1.setDisable(true);
                subject1.setDisable(true);
                score1.setDisable(true);

                obj2.newSubject.setDisable(true);
                obj2.newMoreButton.setDisable(true);
                obj2.newDeleteButton.setDisable(true);
                obj2.newScore.setDisable(true);

                obj3.newSubject.setDisable(true);
                obj3.newMoreButton.setDisable(true);
                obj3.newDeleteButton.setDisable(true);
                obj3.newScore.setDisable(true);

                obj4.newSubject.setDisable(true);
                obj4.newMoreButton.setDisable(true);
                obj4.newDeleteButton.setDisable(true);
                obj4.newScore.setDisable(true);
            }

            editSaveSubjectButton.setDisable(true); //блокируем кнопку
        }



    }

    public boolean NullInExams() //смотрит пустая клеточка экзаменов в базе или нет
    {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT exams FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                foundNULL = resultSet.getString("exams");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут " + foundNULL);
        return Objects.equals(foundNULL, null);

    }

    public HashMap<String, Double> HashMapInExams() //жоска вытаскивает экзамены из базы и сразу преобразовывает в хэшмэп
    {
        Statement statement = null;
        String foundString = "";

        HashMap<String, Double> hashMapFromBase = new HashMap<>();

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT exams FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                foundString = resultSet.getString("exams");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот наши BTS из базы слева направо... " + foundString);
        //а так обратно в хешмэп

        Type type = hashMapFromBase.getClass();

        GsonBuilder gsonBuilder = new GsonBuilder(); //какие-то
        Gson gsonObj = gsonBuilder.create(); //это тоже

        hashMapFromBase = gsonObj.fromJson(foundString, type); //все супер все достали и оно норм хешмеп
        //System.out.println("new hashMap: "+ newHashMap);
        System.out.println("Вот они причёсанные " + hashMapFromBase);

        return hashMapFromBase;

    }

}


