module com.snake.snakegame {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.snake to javafx.fxml;
    opens com.snake.controllers to javafx.fxml, javafx.controls;
    exports com.snake;
}