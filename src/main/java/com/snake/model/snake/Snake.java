package com.snake.model.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final List<SnakePart> parts = new ArrayList<>(20);
    private final Head head;
    private SnakeDirection direction;
    private boolean appleIsEaten = false;

    private static Snake snake;

    private Snake (int x, int y, SnakeDirection direction) {
        head = new Head(x , y);
        parts.add(head);
        this.direction = direction;

        addPart();
        addPart();
    }

    public void eat() {
        appleIsEaten = true;
    }

    private void addPart() {
        SnakePart lastPart = parts.get(parts.size() - 1);
        int x = lastPart.getX();
        int y = lastPart.getY();

        switch (direction) {
            case UP:
                parts.add( new SnakePart(x, y + 1) );
                break;
            case DOWN:
                parts.add(new SnakePart(x, y - 1));
                break;
            case LEFT:
                parts.add( new SnakePart(x + 1, y) );
                break;
            case RIGHT:
                parts.add( new SnakePart(x - 1, y) );
                break;
        }
    }


    public static Snake getInstance(int x, int y, SnakeDirection direction) {
        if (snake == null) {
            snake = new Snake(x, y, direction);
        }
        return snake;
    }

    public static Snake getInstance() {
        return snake;
    }

    public void doMove() {
        SnakePart lastPart = parts.get(parts.size() - 1);
        int lastPartX = lastPart.getX();
        int lastPartY = lastPart.getY();

        //move all parts in the direction of the head

        int x = head.getX();
        int y = head.getY();

        for (int i = 1; i < parts.size(); i++) {
            SnakePart part = parts.get(i);
            int oldX = part.getX();
            int oldY = part.getY();

            part.setX(x);
            part.setY(y);

            x = oldX;
            y = oldY;
        }

        //add +1 part after eating apple
        if (appleIsEaten) {
            parts.add(new SnakePart(lastPartX, lastPartY));
            appleIsEaten = false;
        }
        //todo update cellslist in doMove()
        //move head part
        int headX = head.getX();
        int headY = head.getY();

        switch (direction) {
            case UP:
                head.setY(headY - 1);
                break;
            case DOWN:
                head.setY(headY + 1);
                break;
            case LEFT:
                head.setX(headX - 1);
                break;
            case RIGHT:
                head.setX(headX + 1);
                break;
        }
    }

    public List<SnakePart> getParts() {
        return parts;
    }

    public Head getHead() {
        return head;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }
}

