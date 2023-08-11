package com.example.screens.data.enrolleeControllers;


import com.example.screens.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DocumentsController  extends HelloController {
    //    private Enrollee enrollee = new Enrollee();
    @FXML
    private Button addGraduationButton = new Button();
    @FXML
    private Text pathDiploma = new Text("");
    @FXML
    private Text pathGraduation = new Text("");
    @FXML
    private Button addDiplomaButton = new Button();

    @FXML
    private Button ButtonToOpenDiploma = new Button();

    @FXML
    private Button ButtonToOpenGraduation = new Button();

    @FXML
    private Button editSaveDocButton = new Button();

    private String nameOfDiploma;
    private String nameOfGraduation;
    public void clickToAddGraduation(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Выберите ИД");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {

            try {
                nameOfGraduation = selectedFile.getName();
                String name = enrollee.getIdEnrollee().toString();
                Files.copy(selectedFile.toPath(), Path.of("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf"));
                pathGraduation.setText("Выбранный файл:\t"+selectedFile.getName());
            }
            catch (Exception e) {
                System.out.println("Ошибка, бывает");
            }

            ButtonToOpenGraduation.setDisable(false);
            clickToAddGraduationVer2();
        }
    }

    private void clickToAddGraduationVer2() {
        addGraduationButton.setText("Удалить");

        addGraduationButton.setOnAction(actionEvent3 -> {
            ButtonToOpenGraduation.setDisable(true);
            String name = enrollee.getIdEnrollee().toString();;
            if (new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf").delete()) {
                System.out.println("Файл успешно удалён");
                pathGraduation.setText("Файл удален");
            } else {
                System.out.println("Файл не удален");
            }

            addGraduationButton.setText("Добавить");

            addGraduationButton.setOnAction(this::clickToAddGraduation);//все поехали заново

        });
    }

    public void ClickToAddDiploma(ActionEvent event)  {
        FileChooser fc = new FileChooser();
        fc.setTitle("Выберите Аттестат");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(".pdf", "*.pdf"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
//            FileChannel sourceChannel = null;
            try {
                String name = enrollee.getIdEnrollee().toString();
                nameOfDiploma = selectedFile.getName();
                Files.copy(selectedFile.toPath(), Path.of("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf"));
                pathDiploma.setText("Выбранный файл:\t"+selectedFile.getName());
//                upload(getDbConection(), selectedFile);
            }
            catch (Exception e){
                System.out.println("Ошибка, бывает");
//                System.out.println(e.getCause());
            }
            ButtonToOpenDiploma.setDisable(false);
            ButtonToOpenDiploma.setText("Открыть");
            ButtonToOpenDiploma.setDisable(false);
            ClickToAddDiplomaVer2();
        }

    }

    private void ClickToAddDiplomaVer2() {
        addDiplomaButton.setText("Удалить");

        addDiplomaButton.setOnAction(actionEvent3 -> {
            ButtonToOpenDiploma.setDisable(true);
            String name = enrollee.getIdEnrollee().toString();
            File file = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
            if (file.delete()) {
                System.out.println("Файл успешно удалён");

                pathDiploma.setText("Файл удален");
            } else {
                System.out.println("Файл не удален");
            }

            addDiplomaButton.setText("Добавить");

            addDiplomaButton.setOnAction(this::ClickToAddDiploma);//все поехали заново

        });
    }


    public void clickToEditSaveDocuments(ActionEvent event) {
        addGraduationButton.setDisable(false);
        addDiplomaButton.setDisable(false);
        editSaveDocButton.setText("Сохранить");
        editSaveDocButton.setOnAction(event2 ->
        {
            editSaveDocButton.setText("Редактировать");
            addGraduationButton.setDisable(true);
            addDiplomaButton.setDisable(true);
            editSaveDocButton.setOnAction(this::clickToEditSaveDocuments);//все поехали заново


        });





    }
    @FXML
    public void ClickToButtonToOpenDiploma() throws IOException {
        String name = enrollee.getIdEnrollee().toString();
        File selectedFile = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
        Desktop desktop =Desktop.getDesktop();
        desktop.open(selectedFile);
    }

    public void ClickToButtonToOpenGraduation() throws IOException {
        String name = enrollee.getIdEnrollee().toString();
        File selectedFile = new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf");
        Desktop desktop =Desktop.getDesktop();
        desktop.open(selectedFile);
    }
    public void initialize()
    {
        if(Date.valueOf(String.valueOf(enrollee.dateNow)).before(Date.valueOf(String.valueOf(enrollee.lastDate))))
        {

            String name = enrollee.getIdEnrollee().toString();
            File file = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
            if (file.exists()) {
                ButtonToOpenDiploma.setText("Открыть");
                ButtonToOpenDiploma.setDisable(false);
                pathDiploma.setText("Файл загружен");
                ClickToAddDiplomaVer2();
            } else {
                ButtonToOpenDiploma.setDisable(true);

            }
            file = new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf");
            if (file.exists()) {
                ButtonToOpenGraduation.setDisable(false);
                pathGraduation.setText("Файл загружен");
                clickToAddGraduationVer2();
            } else {
                ButtonToOpenGraduation.setDisable(true);
            }

            addDiplomaButton.setDisable(true);
            addGraduationButton.setDisable(true);

        }
        else
        {

            String name = enrollee.getIdEnrollee().toString();
            File file = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
            if (file.exists()) {
                ButtonToOpenDiploma.setText("Открыть");
                ButtonToOpenDiploma.setDisable(false);
                pathDiploma.setText("Файл загружен");
                ClickToAddDiplomaVer2();
            } else {
                ButtonToOpenDiploma.setDisable(true);

            }
            file = new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf");
            if (file.exists()) {
                ButtonToOpenGraduation.setDisable(false);
                pathGraduation.setText("Файл загружен");
                clickToAddGraduationVer2();
            } else {
                ButtonToOpenGraduation.setDisable(true);
            }

            addDiplomaButton.setDisable(true);
            addGraduationButton.setDisable(true);

            editSaveDocButton.setDisable(true);//выключаем кнопку

        }


    }
}

