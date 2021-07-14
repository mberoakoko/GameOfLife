package org.openjfx;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import static org.openjfx.MainView.*;

public class AppToolBar extends ToolBar {
    Button draw, erase , run;
    private MainView view;

    AppToolBar(MainView view){
        this.view = view;
        draw = new Button("draw");
        draw.setOnAction(this::handleDraw);
        erase = new Button("erase");
        erase.setOnAction(this::handleEraseEvent);
        run = new Button("run/step");
        run.setOnAction(this::runSimulation);

        this.getItems().addAll(draw, erase, run);
    }

    private void runSimulation(ActionEvent actionEvent) {
        System.out.println("Running Simulation... ");
        this.view.getSimulation().step();
        this.view.draw();
    }

    private void handleEraseEvent(ActionEvent actionEvent) {
        System.out.println("Handling Draw event... ");
        this.view.setDrawMode(DRAW_MODE.ERASE);
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Handling Draw Event...");
        this.view.setDrawMode(DRAW_MODE.DRAW);
    }
}
