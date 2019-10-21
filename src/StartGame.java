import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StartGame {
	ImageView[] images = new ImageView[2];
	
	//creates the road at the beginning of the game
	public void createRoad() throws FileNotFoundException {
		Image image1 = new Image(new FileInputStream("yol1.png"));
		ImageView road1= new ImageView(image1);
		road1.setFitHeight(600); road1.setFitWidth(400);
		images[0] = road1;
		
		Image image2 = new Image(new FileInputStream("yol2.png"));
		ImageView road2 = new ImageView(image2);
		road2.setFitHeight(600); road2.setFitWidth(400);
		road2.setLayoutY(-600);
		images[1] = road2;
		
	}
	//creates our car at the beginning of the game
	public ImageView createCar() throws FileNotFoundException {
		Image newcar = new Image(new FileInputStream("a1.png"));
		ImageView car = new ImageView(newcar);
		car.setLayoutX(170); car.setLayoutY(495);
		car.setFitHeight(85);
		car.setFitWidth(45);
		return car;
	}
	
	public ImageView imageSender() {
		Random random = new Random();
		int r = random.nextInt(2);
		return images[r];
	}
	
	

}
