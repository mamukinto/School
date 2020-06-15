package gui.student;

import gui.common.LabelUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Event;

import java.time.format.DateTimeFormatter;

public class EventContainer extends VBox {
    public EventContainer(Event event) {
        Label eventText = new Label();
        eventText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        eventText.setMinWidth(Region.USE_PREF_SIZE);
        eventText.setText(event.getContent());
        eventText.setPadding(new Insets(10,50,0,10));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm-ss");
        String date = event.getDate().format(dateFormat);

        this.getChildren().addAll(eventText);

        Label dateLabel = LabelUtil.getLabel(date);

        HBox dateBox = new HBox();
        dateBox.getChildren().add(dateLabel);
        dateBox.setAlignment(Pos.BOTTOM_RIGHT);
        dateBox.setPadding(new Insets(0,10,10,0));
        dateLabel.setMinWidth(Region.USE_PREF_SIZE);
        this.getChildren().add(dateBox);
        this.setStyle("-fx-border-color: black; -fx-border-radius: 10px;");
        this.setSpacing(5);
    }
}
