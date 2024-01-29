package com.kandclay.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Raindrop {
    Rectangle a;
    Texture texture;
    int textureId;

    public Raindrop(Rectangle a, Texture texture, int textureId) {
        this.a = a;
        this.texture = texture;
        this.textureId = textureId;
    }
}
