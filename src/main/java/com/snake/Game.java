package com.snake;

import com.snake.model.*;
import com.snake.model.snake.Head;
import com.snake.model.snake.Snake;
import com.snake.model.snake.SnakeDirection;
import com.snake.model.snake.SnakePart;

import java.util.*;

public class Game {

    private Snake snake;
    private Apple apple;
    private final Head head;

    private final Random random = new Random();

    public static final int GAME_SIZE = 20;

    private final Cell[][] cellsMatrix = new Cell[GAME_SIZE][GAME_SIZE];
    private final List<Cell> cellsForApple = new ArrayList<>(GAME_SIZE * GAME_SIZE);

    private boolean snakeCanChangeDirection;
    private boolean gameOver;
    private boolean appleIsEaten = false;


    public Game() {
        gameOver = false;
        createCellsList();

        createSnake();
        head = snake.getHead();
        generateApple();
    }

    public void checkHeadAndAppleContact() {
        //todo win/loose
        int headX = head.getX();
        int headY = head.getY();
        int appleX = apple.getX();
        int appleY = apple.getY();

        if (headX == appleX && headY == appleY) {
            snake.eat();
            appleIsEaten = true;
//            generateApple();
        }
    }

    public void updateField() {

        //add free cell to the list
        List<SnakePart> parts = snake.getParts();
        SnakePart lastPart = parts.get(parts.size() - 1);
        int lastPartX = lastPart.getX();
        int lastPartY = lastPart.getY();
        Cell vacatedCell = cellsMatrix[lastPartX][lastPartY];
        cellsForApple.add(vacatedCell);

        snake.doMove();
        checkHeadAndAppleContact();

        if (!isAlive()) { //skip further steps if game is ended
            return;
        }

        //remove the cell where the head moved
        Cell occupiedCell = cellsMatrix[head.getX()][head.getY()];
        cellsForApple.remove(occupiedCell);

        if (appleIsEaten) {
            cellsForApple.remove(vacatedCell);
            generateApple();
            appleIsEaten = false;
        }

        setSnakeCanChangeDirection(true);
    }

    public boolean isAlive() {
        int headX = head.getX();
        int headY = head.getY();

        if (headX < 0 || headX >= GAME_SIZE || headY < 0 || headY >= GAME_SIZE) {
            return false;
        }

        List<SnakePart> parts = snake.getParts();
        for (int i = 1; i < parts.size(); i++) {
            int x = parts.get(i).getX();
            int y = parts.get(i).getY();
            if (x == headX && y == headY) {
                return false;
            }
        }

        return true;
    }

    private void createSnake() {
        int x = random.nextInt(10) + 5;
        int y = random.nextInt(10) + 5;

        SnakeDirection direction = null;
        int dir = random.nextInt(4);
        switch (dir) {
            case 0:
                direction = SnakeDirection.UP;
                break;
            case 1:
                direction = SnakeDirection.DOWN;
                break;
            case 2:
                direction = SnakeDirection.LEFT;
                break;
            case 3:
                direction = SnakeDirection.RIGHT;
                break;
        }

        snake = Snake.getInstance(x, y, direction);


        //remove snake parts from cells where apple can be generated
        for (SnakePart part : snake.getParts()) {
            int partX = part.getX();
            int partY = part.getY();
            Cell cell = cellsMatrix[partX][partY];
            cellsForApple.remove(cell);
        }
    }

    public void generateApple() {
        int randomNumber = random.nextInt(cellsForApple.size());

        //choose random cell among cells where apple can be generated
        Cell cell = cellsForApple.get(randomNumber);
        int appleX = cell.getX();
        int appleY = cell.getY();

        if (apple == null) {
            apple = new Apple(appleX, appleY);
            return;
        }
        apple.setX(appleX);
        apple.setY(appleY);
    }

    private void createCellsList() {
        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                Cell cell = new Cell(i, j);
                cellsMatrix[i][j] = cell;
                cellsForApple.add(cell);
            }
        }
    }

    public Apple getApple() {
        return apple;
    }

    public boolean isSnakeCanChangeDirection() {
        return snakeCanChangeDirection;
    }

    public void setSnakeCanChangeDirection(boolean snakeCanChangeDirection) {
        this.snakeCanChangeDirection = snakeCanChangeDirection;
    }
}
