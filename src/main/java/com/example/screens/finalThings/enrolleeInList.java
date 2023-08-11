package com.example.screens.finalThings;

import java.util.ArrayList;
import java.util.HashMap;

public class enrolleeInList //Да, это еще один класс абитуриента, я тут решаю как я буду писать
{
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public HashMap<String, Double> getExams() {
        return exams;
    }

    public void setExams(HashMap<String, Double> exams) {
        this.exams = exams;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public Integer getScoreSumm() {
        return scoreSumm;
    }

    public void setScoreSumm(Integer scoreSumm) {
        this.scoreSumm = scoreSumm;
    }

    private String fio;
    private HashMap<String,Double> exams;
    private ArrayList<String> directions;

    private Integer scoreSumm;


    public enrolleeInList(String fio, HashMap<String, Double> exams, ArrayList<String> directions) {
        this.fio = fio;
        this.exams = exams;
        this.directions = directions;
        this.scoreSumm=Integer.parseInt(String.valueOf(exams.values().stream().mapToInt(Double::intValue).sum()));

    }


    @Override
    public String toString() {
        return this.getFio() + " " + this.getDirections() + " "+this.getExams()+" "+this.getScoreSumm();
    }
}
