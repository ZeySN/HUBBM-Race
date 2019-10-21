import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RacingCars {
	private ArrayList<ImageView> cars = new ArrayList<ImageView>(5);
	private boolean intersect = false;
	private int upY = 10;
	
	
	//puts yellow cars randomly to the left side of road for the first step of game, 
	//after that the game program puts yellow cars randomly without any limitation in the interval of road
	public void addCarsLeftRandom(ImageView ourCar, int n) throws FileNotFoundException {
		Image othercar = new Image(new FileInputStream("a3.png"));
		
		for (int i = 0;i<n;i++) {
			ImageView otherCar= new ImageView(othercar);
			otherCar.setFitHeight(85);
			otherCar.setFitWidth(45);
			Random r = new Random();
			double resultX = r.nextInt(120-85)+85;
			otherCar.setLayoutX(resultX);
			otherCar.setLayoutY(upY);
			cars.add(otherCar);
			upY += 200;
		}
		
	}
	
	public void addCarsRightRandom(ImageView ourCar, int n) throws FileNotFoundException {
		Image othercar = new Image(new FileInputStream("a3.png"));
		
		for (int i = 0;i<n;i++) {
			ImageView otherCar= new ImageView(othercar);
			otherCar.setFitHeight(90);
			otherCar.setFitWidth(50);
			Random r = new Random();
			double resultX = r.nextInt(252-230)+230;
			otherCar.setLayoutX(resultX);
			otherCar.setLayoutY(upY);
			cars.add(otherCar);
			upY += 200;
		}
	}
	
	public Image greenCar () throws FileNotFoundException {
		Image newcolor = new Image(new FileInputStream("a4.png"));
		
		return newcolor;
	}
	
	public Image yellowCar () throws FileNotFoundException {
		Image newcolor = new Image(new FileInputStream("a3.png"));
		
		return newcolor;
	}
	
	public Image blackCar () throws FileNotFoundException {
		Image newcolor = new Image(new FileInputStream("a2.png"));
		return newcolor;
	}
	
	public ArrayList<ImageView> getCars() {
		return cars;
	}
	
	public void setCars(ArrayList<ImageView> cars) {
		this.cars = cars;
	}
	
}
