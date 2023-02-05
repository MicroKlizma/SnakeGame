package com.snake.controllers;

import com.snake.Game;
import com.snake.model.Apple;
import com.snake.model.snake.Snake;
import com.snake.model.snake.SnakeDirection;
import com.snake.model.snake.SnakePart;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML
    private AnchorPane gameField;

    private long lastTick = 0;

    private final int SPEED = 4; //fps
    private final int CELL_SIZE = 40;

    private Snake snake;
    private Apple apple;
    private final Game game = new Game();

    public void start() {
        gameField.requestFocus();
        snake = Snake.getInstance();
        apple = game.getApple();

        setEventHandler();
        startTimer();
        draw();
    }

    private void update() {
//        game.checkHeadAndAppleContact();
//        game.updateField();
//        draw();
//        game.setSnakeCanChangeDirection(true);
        if (!game.isAlive()) {
            return;
        }
        game.updateField();
        draw();
    }

    private void draw() {
        gameField.getChildren().clear();
        //draw snake
        for (SnakePart part : snake.getParts()) {
            Rectangle rectanglePart = new Rectangle(part.getX() * CELL_SIZE, part.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            gameField.getChildren().add(rectanglePart);
            rectanglePart.setViewOrder(5.0);
        }

        //draw apple
        Rectangle rectangleApple = new Rectangle(apple.getX() * CELL_SIZE, apple.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        rectangleApple.setViewOrder(10.0);
        rectangleApple.setFill(Color.RED);
        gameField.getChildren().add(rectangleApple);
    }

    private void setEventHandler() {
        gameField.setOnKeyPressed(event -> {
            boolean snakeCanChangeDirection = game.isSnakeCanChangeDirection();

            if (!snakeCanChangeDirection) {
                return;
            }

            SnakeDirection currentDirection = snake.getDirection();
            SnakeDirection newDirection = currentDirection;
            switch (event.getCode()) {
                case UP:
                case W:
                    if (currentDirection != SnakeDirection.DOWN) {
                        newDirection = SnakeDirection.UP;
                    }
                    break;
                case DOWN:
                case S:
                    if (currentDirection != SnakeDirection.UP) {
                        newDirection = SnakeDirection.DOWN;
                    }
                    break;
                case LEFT:
                case A:
                    if (currentDirection != SnakeDirection.RIGHT) {
                        newDirection = SnakeDirection.LEFT;
                    }
                    break;
                case RIGHT:
                case D:
                    if (currentDirection != SnakeDirection.LEFT) {
                        newDirection = SnakeDirection.RIGHT;
                    }
                    break;
            }

            if (currentDirection == newDirection) {
                return;
            }

            snake.setDirection(newDirection);
            game.setSnakeCanChangeDirection(false);
        });

    }

    private void startTimer() {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastTick == 0) {
                    lastTick = l;
                }
                if (l - lastTick > 1_000_000_000 / SPEED) {
                    lastTick = l;
                    update();
                }
            }
        }.start();
    }

}
