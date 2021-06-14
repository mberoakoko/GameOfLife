package org.openjfx;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import static javafx.scene.paint.Color.*;

public class MainView extends VBox {
    private Button runButton;
    private Canvas canvas;
    private final int WIDTH;
    private final int HEIGHT;
    private Simulation simulation;
    private final Affine affine;
    MainView(){
        runButton = new Button("run/Step");
        runButton.setOnAction(actionEvent -> {
            simulation.step();
            draw();
        });

        WIDTH = 20;
        HEIGHT = 20;
        canvas = new Canvas(450, 450);
        canvas.setOnMousePressed(this::handleDraw);
        canvas.setOnMouseDragged(this::handleDraw);
        this.getChildren().addAll(runButton, canvas);

        affine = new Affine();
        affine.appendScale(450f/WIDTH, 450f/WIDTH);
        simulation = new Simulation(WIDTH, HEIGHT);



    }
    private void handleDraw(MouseEvent event)  {
        double mouseX = event.getX();
        double mouseY = event.getY();
        Point2D point2D = null;
        try {
            point2D = this.affine.inverseTransform(mouseX, mouseY);
            int simX = (int) point2D.getX();
            int simY = (int) point2D.getY();
            simulation.setAlive(simX, simY);
            this.draw();
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
    }
    public void draw(){
        GraphicsContext ctx = this.canvas.getGraphicsContext2D();
        ctx.setTransform(this.affine);
        ctx.setFill(LIGHTGREY);
        ctx.fillRect(0, 0, 450, 450);

        ctx.setFill(BLACK);
        for (int x = 0; x < simulation.getWidth(); x++) {
            for (int y = 0; y < simulation.getHeight(); y++) {
                if(simulation.getState(x, y) == 1) ctx.fillRect(x, y, 1, 1);
            }
        }
        ctx.setStroke(WHITE);
        ctx.setLineWidth(0.02);
        for (int x = 0; x < simulation.getWidth() ; x++) {
            ctx.strokeLine(x, 0, x, WIDTH);
        }
        for (int y = 0; y < simulation.getHeight(); y++) {
            ctx.strokeLine(0, y, HEIGHT, y);
        }
    }
}
