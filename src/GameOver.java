import java.io.FileInputStream;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOver extends Application{
	
	Text text = new Text(60,140,"");
	@Override
	//Restarts the game
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		
		Image image1 = new Image(new FileInputStream("start.jpg"));
		ImageView image= new ImageView(image1);
		image.setFitWidth(400); image.setFitHeight(600);
		Scene scene = new Scene(root,400,600);

		root.getChildren().add(image);
		root.getChildren().add(text);
		
		stage.setScene(scene);
		scene.setOnKeyPressed(event -> {
			String keyboardCode = event.getCode().toString();

			if (keyboardCode == "ENTER") {
				
				Action newgame = new Action();
				try {
					newgame.start(stage);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		
		});
		stage.show();
		
	
	}
	//Displays game over scene and calls the start method which restarts the game
	public void gameOverScene(Stage stage, String score, int level) {
		text.setText( "GAME OVER!"+ System.lineSeparator() + "Your SCORE : "+score + System.lineSeparator() +  System.lineSeparator() +"Press ENTER to restart" );
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		text.setFill(Color.MAROON);
		text.setFont(Font.font("",FontWeight.BOLD, 30));
		text.setEffect(ds); text.setCache(true);

		try {
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
