package org.openjfx;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import static javafx.scene.paint.Color.*;

public class MainView extends VBox {
    public static enum DRAW_MODE{DRAW, ERASE}
    private Button runButton;
    private final Canvas canvas;
    private final int WIDTH;
    private final int HEIGHT;
    private Simulation simulation;
    private final Affine affine;
    private int drawMode = 1;
    private DRAW_MODE draw_mode = DRAW_MODE.DRAW;

    InfoBar infoBar;
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
        canvas.setOnMouseMoved(this::handleMouseMovement);
        this.setOnKeyPressed(this::keyIsPressed);
        AppToolBar toolBar = new AppToolBar(this);
        infoBar = new InfoBar();
        infoBar.setDrawMode(draw_mode);
        Pane verticalSpacer = new Pane();
        verticalSpacer.setMinSize(0, 0);
        verticalSpacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(verticalSpacer, Priority.ALWAYS);
        this.getChildren().addAll(toolBar,  canvas, verticalSpacer, infoBar);
        affine = new Affine();
        affine.appendScale(450f/WIDTH, 450f/WIDTH);
        simulation = new Simulation(WIDTH, HEIGHT);


    }

    private void handleMouseMovement(MouseEvent event)  {
        try {
            Point2D mouseCoordinate = getMouseCoordinate(event);
            int mouseX = (int) mouseCoordinate.getX();
            int mouseY = (int) mouseCoordinate.getY();
            infoBar.setCursorPosition(mouseX, mouseY);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }

    }

    private void keyIsPressed(KeyEvent event) {
        switch (event.getCode()){
            case D: this.drawMode = 1; infoBar.setDrawMode(DRAW_MODE.DRAW); break;
            case E: this.drawMode = 0; infoBar.setDrawMode(DRAW_MODE.ERASE);break;
        }
    }

    private void handleDraw(MouseEvent event)  {
        try {
            Point2D point2D = getMouseCoordinate(event);
            int simX = (int) point2D.getX();
            int simY = (int) point2D.getY();
            simulation.setState(simX, simY, drawMode);
            this.draw();
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }

    }
    private Point2D getMouseCoordinate(MouseEvent event) throws NonInvertibleTransformException{
        double mouseX = event.getX();
        double mouseY = event.getY();
        return this.affine.inverseTransform(mouseX, mouseY);
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

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setDrawMode(DRAW_MODE drawModeState) {
        this.draw_mode = drawModeState;
        this.infoBar.setDrawMode(drawModeState);
    }
}
