package gui;

import java.util.HashMap;

import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Screen {
    private Canvas _canvas;
    private GraphicsContext _ctx;

    private HashMap<String, Image> _imageCache;

    private double _width;
    private double _height;

    public Screen(Stage stage, final Mouse mouse, final Keyboard keyboard, String title) {
        _imageCache = new HashMap<String, Image>();

        Pane root = new Pane();

        _width = 400.0;
        _height = 400.0;
        _canvas = new Canvas(_width, _height);
        _ctx = _canvas.getGraphicsContext2D();
        root.getChildren().add(_canvas);

        Scene scene = new Scene(root, _width, _height, Color.WHITE);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        EventHandler<MouseEvent> mouseMotionHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.x = event.getSceneX();
                mouse.y = event.getSceneY();
            }
        };
        root.setOnMouseDragged(mouseMotionHandler);
        root.setOnMouseMoved(mouseMotionHandler);
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.lastClickTime = System.currentTimeMillis();
            }
        });
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.down = true;
            }
        });
        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.down = false;
            }
        });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyboard.setKeyPressed(event.getCode(), true);
            }
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyboard.setKeyPressed(event.getCode(), false);
            }
        });
    }

    public void setColor(Color color) {
        _ctx.setFill(color);
    }

    public void clearWith(Color color) {
        _ctx.save();
        setColor(color);
        rect(0, 0, _width, _height);
        _ctx.restore();
    }

    public void clear() {
        clearWith(Color.WHITE);
    }

    public void circle(double x, double y, double radius) {
        ellipse(x, y, radius * 2, radius * 2);
    }

    public void ellipse(double x, double y, double width, double height) {
        _ctx.fillOval(x - width / 2, y - height / 2, width, height);
    }

    public void rect(double x, double y, double width, double height) {
        _ctx.fillRect(x, y, width, height);
    }

    private Image imageAtPath(String path) {
        Image img = _imageCache.get(path);
        if (img == null) {
            img = new Image(path);
            _imageCache.put(path, img);
        }
        return img;
    }

    public void image(String path, double x, double y) {
        _ctx.drawImage(imageAtPath(path), x, y);
    }

    public void image(String path, double x, double y, double w, double h) {
        _ctx.drawImage(imageAtPath(path), x, y, w, h);
    }

    public double height() {
        return _height;
    }

    public double width() {
        return _width;
    }
}
