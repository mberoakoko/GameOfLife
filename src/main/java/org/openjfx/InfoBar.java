package org.openjfx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import static org.openjfx.MainView.*;

public class InfoBar extends HBox {
    private final Label cursor;
    private final Label editingTool;
    private final String DRAW_MODE_FORMAT = "DrawMode : %s";
    private final String CURSOR_POSITION_FORMAT = "Cursor <%3d,%3d>";
    public InfoBar(){
        cursor = new Label("Cursor <0, 0>");
        editingTool = new Label("DrawMode : DrawMode");

        Pane horizontalSpace = new Pane();
        horizontalSpace.setMinSize(0, 0);
        horizontalSpace.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(horizontalSpace, Priority.ALWAYS);

        this.getChildren().addAll(cursor, horizontalSpace, editingTool);
    }
    public void setDrawMode(DRAW_MODE drawMode){
        this.editingTool.setText(String.format(DRAW_MODE_FORMAT, drawMode.toString()));
    }
    public void setCursorPosition(int x, int y){
        this.cursor.setText(String.format(CURSOR_POSITION_FORMAT, x, y));
    }
}
