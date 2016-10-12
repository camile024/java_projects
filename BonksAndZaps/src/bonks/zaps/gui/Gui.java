package bonks.zaps.gui;

import java.util.ArrayList;

import bonks.zaps.game.Application;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Base class for creating UI elements
 * @author Kamil
 *
 */
public class Gui {
	protected Application app;
	
	/**
	 * Not currently used to create instances, only inherited, but can be used to make creating Panes easier
	 * @param app
	 */
	public Gui(Application app){
		this.app = app;
	}
	
	/**
	 * Used for creating VBoxes easily and quickly
	 * @param spacing
	 * @param padding
	 * @param nodes elements inside the box
	 * @return VBox with nodes elements
	 */
	protected VBox addVBox(double spacing, Insets padding, ArrayList<Node> nodes){
		VBox result = new VBox();
		result.setPadding(padding);
		result.setSpacing(spacing);
		for (Node n : nodes){
			result.getChildren().add(n);
		}
		return result;
	}
	
	/**
	 * Same as addVBox but horizontal
	 * @param spacing
	 * @param padding
	 * @param nodes
	 * @return
	 */
	protected HBox addHBox(double spacing, Insets padding, ArrayList<Node> nodes){
		HBox result = new HBox();
		result.setPadding(padding);
		result.setSpacing(spacing);
		for (Node n : nodes){
			result.getChildren().add(n);
		}
		return result;
	}
	
	/**
	 * Same as VBox but wraps nicely
	 * @param spacingH
	 * @param spacingV
	 * @param padding
	 * @param nodes
	 * @return
	 */
	protected FlowPane addFlowPane(double spacingH, double spacingV, Insets padding, ArrayList<Node> nodes){
		FlowPane result = new FlowPane();
		result.setPadding(padding);
		result.setVgap(spacingV);
		result.setHgap(spacingH);
		result.setPrefWrapLength(300);
		for (Node n : nodes){
			result.getChildren().add(n);
		}
		return result;
	}
	
	/**
	 * To quickly show error messages
	 * @param err
	 * @param caller
	 */
	protected void showError(String err, Stage caller){
		Label error = new Label(err);
		Button ok = new Button("Ok");
		ok.setMinWidth(80);
		error.setId("errormsg");
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(error);
		nodes.add(ok);
		
		VBox root = addVBox(5, new Insets(5), nodes);
		root.setAlignment(Pos.TOP_CENTER);
		Stage stage = new Stage();
		Scene scene = new Scene(root, 300, 80);
		ok.setOnAction(e -> stage.close());
		scene.getStylesheets().add("styles/menu.css");
		stage.setTitle("Error!");
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(caller);
		stage.show();
	}
	

	
}