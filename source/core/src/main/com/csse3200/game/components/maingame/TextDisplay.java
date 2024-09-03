package com.csse3200.game.components.maingame;

public class TextDisplay extends UIComponent {
    private boolean isVisible;
    private final MainGameScreen game;
    private Image displayBox;
    private String text;
    public TextDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}