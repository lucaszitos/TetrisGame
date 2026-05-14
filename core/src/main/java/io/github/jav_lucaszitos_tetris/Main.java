package io.github.jav_lucaszitos_tetris;

import com.badlogic.gdx.ApplicationAdapter;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    FitViewport viewport;
    ShapeRenderer shapeRenderer;

    int[][] background;
    int[][] piece;
    int BLOCK_SIZE = 25;
    int OFFSET_X = 300;
    int OFFSET_Y = 75;

    float timer = 0;
    float fallSpeed = 0.5f;
    float fastSpeed = 0.05f;
    float currentSpeed;
    float pieceX = 0;
    float pieceY = 18;

    int[][] iBlock = {
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0}
    };

    int[][] tBlock = {
        {0, 2, 0},
        {2, 2, 2},
        {0, 0, 0}
    };

    int[][] oBlock = {
        {3, 3},
        {3, 3}
    };

    int[][] lBlock = {
        {0, 4, 0},
        {0, 4, 0},
        {0, 4, 4}
    };

    int[][] jBlock = {
        {0, 5, 0},
        {0, 5, 0},
        {5, 5, 0}
    };

    int[][] sBlock = {
        {0, 6, 6},
        {6, 6, 0},
        {0, 0, 0}
    };

    int[][] zBlock = {
        {7, 7, 0},
        {0, 7, 7},
        {0, 0, 0}
    };

    int [][][] allBlocks = {iBlock, tBlock, oBlock, lBlock, jBlock, sBlock, zBlock};

    public void generator() {
        int idx = (int)(Math.random() * allBlocks.length);
        piece = allBlocks[idx];
        pieceX = 5 - (piece[0].length / 2);
        pieceY = 18;

        if (!canMove((int)pieceX, (int)pieceY, piece)) {
            background = new int[20][10];
        }
    }

    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        shapeRenderer = new ShapeRenderer();
        background = new int[20][10];
        piece = new int[0][0];
        generator();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            currentSpeed = fastSpeed;
        } else {
            currentSpeed = fallSpeed;
        }

        timer += dt;

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            if (canMove((int) pieceX - 1, (int) pieceY, piece)) {
                pieceX--;
            }
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            if (canMove((int)pieceX + 1, (int)pieceY, piece)) {
                pieceX++;
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            int[][] nextRotation = rotate(piece);
            if (canMove((int)pieceX, (int)pieceY, nextRotation)) {
                piece = nextRotation;
            }
        }

        if (timer >= currentSpeed) {
            if (canMove((int)pieceX, (int)pieceY - 1, piece)) {
                pieceY--;
            } else {
                colision();
                generator();
            }
            timer = 0;
        }
    }

    public boolean canMove(int nextX, int nextY, int[][] currentPiece){
        for(int row = 0; row < currentPiece.length; row++){
            for(int col = 0; col < currentPiece[row].length; col++){
                if(currentPiece[row][col] != 0){
                    int boardX = nextX + col;
                    int boardY = nextY + row;

                    if(boardX < 0 || boardX >= 10){
                        return false;
                    }
                    if(boardY < 0){
                        return false;
                    }
                    if(boardY < 20 && background[boardY][boardX] != 0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int[][] rotate(int[][] current) {
        int rows = current.length;
        int cols = current[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = current[i][j];
            }
        }
        return rotated;
    }

    public void drawBlock(int column, int line, Color color) {
        float pixelX = OFFSET_X + (BLOCK_SIZE * column);
        float pixelY = OFFSET_Y + (BLOCK_SIZE * line);

        shapeRenderer.setColor(color);
        shapeRenderer.rect(pixelX, pixelY, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
    }

    public void scenario() {
        for(int row = 0; row < background.length; row++) {
            for(int col = 0; col < background[row].length; col++) {
                int value = background[row][col];
                if (value != 0) {
                    drawBlock(col, row, getColor(value));
                } else {
                    drawBlock(col, row, Color.GRAY);
                }
            }
        }
    }

    public void iTetris(int[][] piece) {
        shapeRenderer.setColor(Color.WHITE);
        for(int row = 0; row < piece.length; row++) {
            for(int col = 0; col < piece[row].length; col++) {
                if(piece[row][col] != 0) {
                    int value = piece[row][col];
                    if(value != 0) {
                    drawBlock((int)pieceX + col, (int)pieceY + row, getColor(value));
                    }

                }
            }
        }
    }

    private Color getColor(int type) {
        switch (type) {
            case 1: return Color.CYAN;
            case 2: return Color.PURPLE;
            case 3: return Color.YELLOW;
            case 4: return Color.ORANGE;
            case 5: return Color.BLUE;
            case 6: return Color.GREEN;
            case 7: return Color.RED;
            default: return Color.GRAY;
        }
    }

    public void colision(){
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != 0) {
                    int gridX = (int) pieceX + j;
                    int gridY = (int) pieceY + i;
                    if (gridY >= 0 && gridY < 20 && gridX >= 0 && gridX < 10) {
                        background[gridY][gridX] = piece[i][j];
                    }
                }
            }
        }
        checkLines();
    }

    public void checkLines() {
        for (int row = 0; row < background.length; row++) {
            boolean full = true;
            for (int col = 0; col < background[row].length; col++) {
                if (background[row][col] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) {
                removeLine(row);
                row--;
            }
        }
    }

    private void removeLine(int rowToDelete) {
        for (int i = rowToDelete; i < background.length - 1; i++) {
            background[i] = background[i + 1];
        }
        background[background.length - 1] = new int[10];
    }

    @Override
    public void render() {
        update();

        ScreenUtils.clear(Color.BLACK);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        scenario();
        if (piece.length > 0) {
            iTetris(piece);
        }


        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

}

