package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.GdxGame;
import com.csse3200.game.entities.factories.RenderFactory;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.screens.text.AnimatedText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;

/**
 * The game screen where you can choose a planet to play on.
 */
public class LevelSelectScreen extends ScreenAdapter {
    Logger logger = LoggerFactory.getLogger(LevelSelectScreen.class);
    private final GdxGame game;
    private SpriteBatch batch;

    private Sprite background;

    float timeCounter = 0;

    private static final String BG_PATH = "planets/background.png";

    public LevelSelectScreen(GdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Sprite(new Texture(BG_PATH));
    }

    /**
     * Spawns the planets on the screen by doing contionous calls to spawnPlanet().
     * The rotation speed of a planet is determined by the frame variable, this
     * function can be modified.
     */
    private void spawnPlanets() {
        // Spawn desert planet
        spawnPlanet(150, 150, Planets.DESERT[0], Planets.DESERT[1], "Desert", 1, (int) (timeCounter * 60) % 60 + 1);
        // Spawn ice planet
        spawnPlanet(150, 150, Planets.ICE[0], Planets.ICE[1],"Barren_or_Moon", 2, (int) (timeCounter * 60) % 60 + 1);
        // Spawn lava planet
        spawnPlanet(200, 200, Planets.LAVA[0], Planets.LAVA[1],"Lava", 1, (int) (timeCounter * 60) % 60 + 1);

        spawnPlanetBorders();
    }

    private void spawnPlanet(int width, int height, int posx, int posy, String planetName, int version, int frame) {
        Texture planet = new Texture(String.format("planets/%s/%d/%d.png", planetName, version, frame));
        Sprite planetSprite = new Sprite(planet);
        planetSprite.setSize(width, height);
        batch.draw(planetSprite, posx, posy, width, height);
    }

    private void spawnPlanetBorders() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        // Iterates through the planets checking for the bounding box
        for (int[] planet : Planets.PLANETS) {
            Rectangle planetRect = new Rectangle(planet[0], planet[1], planet[2], planet[3]);
            if (planetRect.contains(mousePos.x, Gdx.graphics.getHeight() - mousePos.y)) {
                // If a planet is clicked it will load the level based on the planet
                if (Gdx.input.justTouched()) {
                    dispose();
                    game.setScreen(new MainGameScreen(game));
                } else {
                    Sprite planetBorder = new Sprite(new Texture("planets/planetBorder.png"));
                    batch.draw(planetBorder, planet[0] - 2, planet[1] - 2, planet[2] + 3, planet[3] + 3);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        timeCounter += delta;

        // Gets position of cursor
        batch.begin();

        // Draws the background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Draws the planets on top of the background
        spawnPlanets();


        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
