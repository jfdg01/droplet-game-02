package com.kandclay.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button {
    TextButton button;

    public Button(float width, float height) {
        BitmapFont font = new BitmapFont();
        Texture buttonImg = new Texture(Constants.BUTTON_IMAGE_PATH);
        Drawable drawable = new TextureRegionDrawable(buttonImg);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = drawable; // Use the image as the button's appearance

        button = new TextButton("START", textButtonStyle);
        button.setSize(width, height); // Set the size of the button
    }

    public TextButton getButton() {
        return button;
    }
}
