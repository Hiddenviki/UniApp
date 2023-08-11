package com.example.screens.finalThings;
import com.example.screens.data.DatabaseHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.HashSet;

public class finalDocument extends DatabaseHandler
{
    private String budgetDocumentPath; //документик на бюджет
    private String paidDocumentPath; //документик на платку

    public finalDocument() {
        try
        {
            doHashMaps();
            Thread.sleep(500); //немного приостанавливаем
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Уведомления со списками отправлены абитуриентам");
            alert.setContentText("Сформированные списки отправлены всем абитуриентам для ознакомления.");
            alert.showAndWait();

        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }; //типа этот конструктор-лапочка запускает тот жоский конструктор

    private void doHashMaps() throws InterruptedException {
        ArrayList<enrolleeInList> dirtyList= new ArrayList<>(); //список ВООБЩЕ ВСЕХ кто 'в конкурсе'

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {

            ps = getDbConection().prepareStatement("SELECT firstname, lastname, exams, directions FROM enrollees WHERE status = 'В конкурсе'");

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String fio1="";
                String strExams="";
                HashMap<String,Double> exams1 = new HashMap<>();
                String strDirections="";
                ArrayList<String> directions1=new ArrayList<>();

                //вытаскиваем экзамены
                Type type1 = exams1.getClass();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gsonObj = gsonBuilder.create();
                strExams = resultSet.getString("exams");
                exams1 = gsonObj.fromJson(strExams, type1); //все супер все достали и оно норм хешмеп

                //вытаскиваем направления
                Type type2 = directions1.getClass();
                GsonBuilder gsonBuilder2 = new GsonBuilder();
                Gson gsonObj2 = gsonBuilder2.create();
                strDirections = resultSet.getString("directions");
                directions1 = gsonObj2.fromJson(strDirections, type2); //все супер все достали и оно норм хешмеп

                //делаем фио в одну строку
                fio1+=resultSet.getString("firstname");
                fio1+=" ";
                fio1+=resultSet.getString("lastname");

                //создаем такого человечка
                enrolleeInList oneMoreEnrollee= new enrolleeInList(fio1,exams1,directions1);
                System.out.println("78 "+oneMoreEnrollee.toString()); //все нормально
                //ну и добавляем в наш общий список тех кто в конкурсе
                dirtyList.add(oneMoreEnrollee);

            }
            System.out.println("Все в конкурсе были распределены по спискам!");
//            System.out.println("71 "+dirtyList); //все на месте спротсмены

        } catch (Exception e){
            System.out.println(e.getMessage());
        } //тут все норм

        //создаю 12 массивов)))
        ArrayList<String> matlabBudget = new ArrayList<>();
        ArrayList<String> matlabPaid = new ArrayList<>();

        ArrayList<String> linalBudget = new ArrayList<>();
        ArrayList<String> linalPaid = new ArrayList<>();

        ArrayList<String> kibersportBudget = new ArrayList<>();
        ArrayList<String> kibersportPaid = new ArrayList<>();

        ArrayList<String> happyLifeBudget = new ArrayList<>();
        ArrayList<String> happyLifePaid = new ArrayList<>();

        ArrayList<String> ufoBudget= new ArrayList<>();
        ArrayList<String> ufoPaid = new ArrayList<>();

        ArrayList<String> successfulSuccessBudget = new ArrayList<>();
        ArrayList<String> successfulSuccessPaid = new ArrayList<>();

        //создаем объект вспомогательного класса чтоб понимать че там по баллам
        DirectionsInfo directionsInfo = new DirectionsInfo();

        System.out.println("вот что в грязном списке ");
        System.out.println(dirtyList);
        // и начинаем смотреть какие направления у нашх чувачков в грязном списке и смотреть на сумму буллов зв экзамены у чувачков
        for (enrolleeInList e : dirtyList)
        {
            System.out.println("-----------"+e.getFio()+"-----------");


            if (e.getDirections().contains("Прикладной матлаб")) //если человечек выбрал матлаб
            {
                System.out.println("106 строка: " + e.getFio() + " Выбирал(а) прикладной матлаб");

                //смотрим скока баллов в сумме и распределяем куда он попадает в бюджет или в плаьку
                if (e.getScoreSumm() >= directionsInfo.matlabBugetSumm &&
                        e.getExams().keySet().containsAll(directionsInfo.matlab)) //если проходит на бюджет
                {
                    System.out.println(e.getFio()+" идет на бюджет");
                    matlabBudget.add(e.getFio());
                } else //иначе на платку
                {
                    System.out.println(e.getFio()+" идет на платку");
                    matlabPaid.add(e.getFio());
                }

                System.out.println("117 платный матлаб " + matlabPaid);
                System.out.println("118 бесплатный матлаб " + matlabBudget);

            }

            if (e.getDirections().contains("Любимый Линал")  &&
                    e.getExams().keySet().containsAll(directionsInfo.linal)) //если человечек выбрал матлаб
            {
                System.out.println("121 строка: " + e.getFio()+ " Выбирал(а) Любимый линал");
                //смотрим скока баллов в сумме и распределяем куда он попадает в бюджет или в плаьку
                if (e.getScoreSumm() >= directionsInfo.linalBugetSumm) //если проходит на бюджет
                {
                    System.out.println(e.getFio()+" идет на бюджет");
                    linalBudget.add(e.getFio());
                } else //иначе на платку
                {
                    System.out.println(e.getFio()+" идет на платку");
                    linalPaid.add(e.getFio());
                }
                System.out.println("130 платный линал " + linalPaid);
                System.out.println("131 бесплатный линал " + linalBudget);

            }

            if (e.getDirections().contains("Киберспорт " + e.getFio())  &&
                    e.getExams().keySet().containsAll(directionsInfo.kibersport)) //если человечек выбрал матлаб
            {
                System.out.println("135 строка: " + e.getFio()+" выбрал(а) киберсопрт");
                //смотрим скока баллов в сумме и распределяем куда он попадает в бюджет или в плаьку
                if (e.getScoreSumm() >= directionsInfo.kibersportBugetSumm) //если проходит на бюджет
                {
                    System.out.println(e.getFio()+" идет на бюджет");
                    kibersportBudget.add(e.getFio());
                } else //иначе на платку
                {
                    System.out.println(e.getFio()+" идет на платку");
                    kibersportPaid.add(e.getFio());
                }
                System.out.println("146 платный киберспорт " + kibersportPaid);
                System.out.println("147 бесплатный киберспорт " + kibersportBudget);

            }

            if (e.getDirections().contains("Счастливая жизнь")  &&
                    e.getExams().keySet().containsAll(directionsInfo.happyLife)) //если человечек выбрал матлаб
            {
                System.out.println("149 строка: " + e.getFio()+" выбрал(а) счастливую жизнь");
                //смотрим скока баллов в сумме и распределяем куда он попадает в бюджет или в плаьку
                if (e.getScoreSumm() >= directionsInfo.happyLifeBugetSumm) //если проходит на бюджет
                {
                    System.out.println(e.getFio()+" идет на бюджет");
                    happyLifeBudget.add(e.getFio());
                } else //иначе на платку
                {
                    System.out.println(e.getFio()+" идет на платку");
                    happyLifePaid.add(e.getFio());
                }
                System.out.println("158 платный happy life " + happyLifePaid);
                System.out.println("159 бесплатный happy life " + happyLifeBudget);

            }

            if (e.getDirections().contains("Исследование НЛО и ракет") &&
                    e.getExams().keySet().containsAll(directionsInfo.ufo)) //если человечек выбрал матлаб
            {
                System.out.println("163 строка: " + e.getFio() +" выбрал(а) НЛО");
                //смотрим скока баллов в сумме и распределяем куда он попадает в бюджет или в плаьку
                if (e.getScoreSumm() >= directionsInfo.ufoBugetSumm) //если проходит на бюджет
                {
                    System.out.println(e.getFio()+" идет на бюджет");
                    ufoBudget.add(e.getFio());
                } else //иначе на платку
                {
                    System.out.println(e.getFio()+" идет на платку");
                    ufoPaid.add(e.getFio());
                }
                System.out.println("172 платный UFO " + ufoPaid);
                System.out.println("173 бесплатный UFO " + ufoBudget);

            }

            if (e.getDirections().contains("Успешный успех") &&
                    e.getExams().keySet().containsAll(directionsInfo.successfulSuccess)) //если человечек выбрал матлаб
            {
                System.out.println("177 строка " + e.getFio()+" выбрал(а) успешный успех");
                //смотрим скока баллов в сумме и распределяем куда он попадает в бюджет или в плаьку
                if (e.getScoreSumm() >= directionsInfo.successfulSuccessBugetSumm) //если проходит на бюджет
                {
                    System.out.println(e.getFio()+" идет на бюджет");
                    successfulSuccessBudget.add(e.getFio());
                } else //иначе на платку
                {
                    System.out.println(e.getFio()+" идет на платку");
                    successfulSuccessPaid.add(e.getFio());
                }
                System.out.println("186 платный успех " + successfulSuccessPaid);
                System.out.println("187 бесплатный успех " + successfulSuccessBudget);

            }



        }


        try {
            doFinalDocuments(matlabBudget, matlabPaid, linalBudget, linalPaid,
                    kibersportBudget, kibersportPaid, happyLifeBudget, happyLifePaid,
                    ufoBudget, ufoPaid, successfulSuccessBudget,successfulSuccessPaid);
            System.out.println("Списки составлены успешно");

            //и сразу кидаем в базу
            try {
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE notificationsforall SET finalList=" + "'" + 1 + "'");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
//e.getExams().keySet().stream().toList().containsAll(directionsInfo.successfulSuccess)

    }


    private void doFinalDocuments(ArrayList<String> matlabBudget, ArrayList<String> matlabPaid,
                                  ArrayList<String> linalBudget, ArrayList<String> linalPaid,
                                  ArrayList<String> kibersportBudget, ArrayList<String> kibersportPaid,
                                  ArrayList<String> happyLifeBudget, ArrayList<String> happyLifePaid,
                                  ArrayList<String> ufoBudget, ArrayList<String> ufoPaid,
                                  ArrayList<String> successfulSuccessBudget, ArrayList<String> successfulSuccessPaid) throws IOException, InterruptedException {

        //создаем два файлика
        File budget = new File("src/main/java/com/example/screens/finalThings/Budget.txt");
        File paid = new File("src/main/java/com/example/screens/finalThings/Paid.txt");

        //и заполняем аоао ммм))

        //сначала бюджетные списки
        FileWriter writer = new FileWriter(budget);
        writer.write("----Прикладной матлаб----"+"\n");
        for(int i=0;i<matlabBudget.size();i++) {writer.write(i+1+") "+matlabBudget.get(i)+"\n");}
        writer.write("----Любимый Линал----"+"\n");
        for(int i=0;i<linalBudget.size();i++) {writer.write(i+1+") "+linalBudget.get(i)+"\n");}
        writer.write("----Киберспорт----"+"\n");
        for(int i=0;i<kibersportBudget.size();i++) {writer.write(i+1+") "+kibersportBudget.get(i)+"\n");}
        writer.write("----Счастливая жизнь----"+"\n");
        for(int i=0;i<happyLifeBudget.size();i++) {writer.write(i+1+") "+happyLifeBudget.get(i)+"\n");}
        writer.write("----Исследование НЛО и ракет----"+"\n");
        for(int i=0;i<ufoBudget.size();i++) {writer.write(i+1+") "+ufoBudget.get(i)+"\n");}
        writer.write("----Успешный успех----"+"\n");
        for(int i=0;i<successfulSuccessBudget.size();i++) {writer.write(i+1+") "+successfulSuccessBudget.get(i)+"\n");}

        Thread.sleep(200); //немного приостанавливаем
        writer.close();

        //сначала платные списки
        writer = new FileWriter(paid);
        writer.write("----Прикладной матлаб----"+"\n");
        for(int i=0;i<matlabPaid.size();i++) {writer.write(i+1+") "+matlabPaid.get(i)+"\n");}
        writer.write("----Любимый Линал----"+"\n");
        for(int i=0;i<linalPaid.size();i++) {writer.write(i+1+") "+linalPaid.get(i)+"\n");}
        writer.write("----Киберспорт----"+"\n");
        for(int i=0;i<kibersportPaid.size();i++) {writer.write(i+1+") "+kibersportPaid.get(i)+"\n");}
        writer.write("----Счастливая жизнь----"+"\n");
        for(int i=0;i<happyLifePaid.size();i++) {writer.write(i+1+") "+happyLifePaid.get(i)+"\n");}
        writer.write("----Исследование НЛО и ракет----"+"\n");
        for(int i=0;i<ufoPaid.size();i++) {writer.write(i+1+") "+ufoPaid.get(i)+"\n");}
        writer.write("----Успешный успех----");
        for(int i=0;i<successfulSuccessPaid.size();i++) {writer.write(i+1+") "+successfulSuccessPaid.get(i)+"\n");}

        System.out.println("Списки сформированы!");

        //ну допустим
        this.budgetDocumentPath = "src/main/java/com/example/screens/finalThings/Budget.txt";
        this.paidDocumentPath = "src/main/java/com/example/screens/finalThings/Paid.txt";


        writer.close();


    }

    public String getBudgetDocumentPath() {
        return budgetDocumentPath;
    }

    public void setBudgetDocumentPath(String budgetDocumentPath) {
        this.budgetDocumentPath = budgetDocumentPath;
    }

    public String getPaidDocumentPath() {
        return paidDocumentPath;
    }

    public void setPaidDocumentPath(String paidDocumentPath) {
        this.paidDocumentPath = paidDocumentPath;
    }





}
