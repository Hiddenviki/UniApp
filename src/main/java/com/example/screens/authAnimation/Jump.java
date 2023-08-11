package com.example.screens.authAnimation;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene. Node;


public class Jump {
    private TranslateTransition tt;
    public Jump(Node node){
        tt = new TranslateTransition(Duration.millis(70),  node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
    }
    public void play(){
        tt.playFromStart();
    }
}
