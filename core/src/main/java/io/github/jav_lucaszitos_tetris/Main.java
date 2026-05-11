package io.github.jav_lucaszitos_tetris;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    FitViewport viewport;
    ShapeRenderer shapeRenderer;
    int[][] background;
    int[][] piece;
    float pieceX = 0;
    float pieceY = 0;



    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        shapeRenderer = new ShapeRenderer();
        background = new int[20][10];
        piece = new int[0][0];

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        scenario();

        iTetris(zBlock, pieceX, pieceY);
        shapeRenderer.end();


    }

    public void scenario(){
        int block = 25;
        float offsetX = 250;
        float offsetY = 50;

        for(int i = 0; i < background.length; i++) {
            for(int j = 0; j < background[i].length; j++) {

                float x = j * block;
                float y = i * block;

                shapeRenderer.setColor(Color.GRAY);
                shapeRenderer.rect(offsetX + x, offsetY + y, block - 1, block - 1);
            }
        }
    }

    public void iTetris(int[][] piece, float pX, float pY) {
        int block = 25;
        for(int i = 0; i < piece.length; i++) {
            for(int j = 0; j < piece[i].length; j++) {
                if(piece[i][j] == 1) {
                    float x = j * block;
                    float y = i * block;
                    float offsetX = 250;
                    float offsetY = 50;
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(offsetX+x, offsetY+y, block - 1, block - 1);
                }
            }
        }
    }

    int[][] iBlock = {
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0}
    };

    int[][] tBlock = {
        {0, 0, 0},
        {1, 1, 1},
        {0, 1, 0}
    };

    int[][] oBlock = {
        {1, 1},
        {1, 1}
    };

    int[][] lBlock = {
        {0, 1, 0},
        {0, 1, 0},
        {0, 1, 1}
    };

    int[][] jBlock = {
        {0, 1, 0},
        {0, 1, 0},
        {1, 1, 0}
    };

    int[][] sBlock = {
        {0, 1, 1},
        {1, 1, 0},
        {0, 0, 0}
    };

    int[][] zBlock = {
        {1, 1, 0},
        {0, 1, 1},
        {0, 0, 0}
    };


}
