package com.example.screens.finalThings;

import java.util.ArrayList;

public class DirectionsInfo //это вспомогательный класс с информацией о проходных баллах и направлениях
{
    ///прикладной матлаб///
    public ArrayList<String> matlab; //"Математика","Русский язык","Информатика"
    public Integer matlabBugetSumm;
    public Integer matlabPaidSumm;

    ///Любимый Линал///
    public ArrayList<String> linal; //"Математика","Русский язык"
    public Integer linalBugetSumm;
    public Integer linalPaidSumm;

    ///Киберспорт///
    public ArrayList<String> kibersport; //"Русский язык"
    public Integer kibersportBugetSumm;
    public Integer kibersportPaidSumm;

    ///Счастливая жизнь///
    public ArrayList<String> happyLife; //"Русский язык"
    public Integer happyLifeBugetSumm;
    public Integer happyLifePaidSumm;

    ///исследование НЛО и ракет///
    public ArrayList<String> ufo; //"Математика","Русский язык","Физика"
    public Integer ufoBugetSumm;
    public Integer ufoPaidSumm;

    ///успешный успех///
    public ArrayList<String> successfulSuccess; //"Математика","Русский язык","Физика","Информатика"
    public Integer successfulSuccessBugetSumm;
    public Integer successfulSuccessPaidSumm;

    DirectionsInfo()
    {
        ///прикладной матлаб///
        this.matlab = new ArrayList<>(); //"Математика","Русский язык","Информатика"
        this.matlab.add("Математика");
        this.matlab.add("Русский язык");
        this.matlab.add("Информатика");
        this.matlabBugetSumm = 290;
        this.matlabPaidSumm = 180;

        ///Любимый Линал///
        this.linal = new ArrayList<>(); //"Математика","Русский язык"
        this.linal.add("Математика");
        this.linal.add("Русский язык");
        this.linalBugetSumm = 210;
        this.linalPaidSumm = 120;

        ///Киберспорт///
        this.kibersport = new ArrayList<>(); //"Русский язык"
        this.kibersport.add("Русский язык");
        this.kibersportBugetSumm = 80;
        this.kibersportPaidSumm = 60;

        ///Счастливая жизнь///
        this.happyLife = new ArrayList<>(); //"Русский язык"
        this.happyLife.add("Русский язык");
        this.happyLifeBugetSumm = 80;
        this.happyLifePaidSumm = 60;

        ///исследование НЛО и ракет///
        this.ufo = new ArrayList<>(); //"Математика","Русский язык","Физика"
        this.ufo.add("Математика");
        this.ufo.add("Русский язык");
        this.ufo.add("Физика");
        this.ufoBugetSumm = 295;
        this.ufoPaidSumm = 180;

        ///успешный успех///
        this.successfulSuccess = new ArrayList<>(); //"Математика","Русский язык","Физика","Информатика"
        this.successfulSuccess.add("Математика");
        this.successfulSuccess.add("Русский язык");
        this.successfulSuccess.add("Физика");
        this.successfulSuccess.add("Информатика");
        this.successfulSuccessBugetSumm = 390;
        this.successfulSuccessPaidSumm = 240;
    }






}
