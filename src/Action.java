
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Action extends Application{
	
	private final double limXR=260, limXL=82;
	private final double limLayoutY = 600;
	private double speed = 100;
	private HashMap<String,Boolean> keys = new HashMap<String,Boolean>();
	private ArrayList<ImageView> otherCarList = new ArrayList<ImageView>();
	private Image greenCar; private Image yellowCar;
	private Image blackCar;private ImageView car;
	private int carCounter = 0;
	private double level = 1;
	//Level increases each 5 overtaken cars
	private Text text = new Text(10,18,"");
	private boolean isStop = false;
	Scene scene; Stage stage; double elapsedTime;
	LongValue lastCurrentTime = new LongValue(System.nanoTime());
	private long currentNanoTime;
	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		this.stage = stage;
		StartGame start = new StartGame();
		start.createRoad();
		ImageView road = start.images[0];
		car = start.createCar();
		ImageView road1 = start.images[1];
		scene = new Scene(root,400,600);
		
		 text.setText("Score : " + carCounter + System.lineSeparator() + "Level : "+ (int) level);
		 text.setFill(Color.GHOSTWHITE);
		 text.setFont(Font.font("",FontWeight.SEMI_BOLD,20));
		 
		 scene.setOnKeyPressed(event -> {
				String keyboardCode = event.getCode().toString();

				if (!keys.containsKey(keyboardCode))
					keys.put(keyboardCode, true);
			
			});
		
			scene.setOnKeyReleased(event ->{
				keys.remove(event.getCode().toString());
		
			});
			
		RacingCars race = new RacingCars();
		greenCar = race.greenCar();
		yellowCar = race.yellowCar();
		blackCar = race.blackCar();
		race.addCarsLeftRandom(car, 1);
		race.addCarsRightRandom(car, 2);
		otherCarList.addAll(race.getCars());
		root.getChildren().addAll(road,road1);
		root.getChildren().addAll(otherCarList);
		root.getChildren().addAll( car, text);
		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
			
				elapsedTime = (currentNanoTime - lastCurrentTime.value) / 1000000000.0;
		        lastCurrentTime.value = currentNanoTime;
		        	//restart the game 
					if (isStop ) {
	                	stage.show();
	                	this.stop();
	                	stage.show();
	                	stage.show();
	                	
	                	GameOver newgame= new GameOver();
						try {
							newgame.gameOverScene(stage,String.valueOf(carCounter), (int)(level));
						} catch (Exception e) {
							
							e.printStackTrace();
						}
	                }
				 	if (activeKey("LEFT")) {
	                    slideCarLeft(car,elapsedTime,50);
	                    
	                }

	                if (activeKey("RIGHT")) {
	                	slideCarRight(car,elapsedTime,50);
	                }
	                if (activeKey("UP")) {
	                	speedChecker();
	                	slideCarUp(root,speed,elapsedTime,road,road1);
	                }
	                
	                else if (!keys.keySet().contains("UP")) {
	                	speedD(root,road,road1);
	                	
	                }
			}
		}.start();
		stage.setTitle("HUBBM-Race");
		stage.setScene(scene);
		stage.show();
		
	}
	//This method returns true if the key pressed
	private boolean activeKey(String keyCode) {
		Boolean active = keys.get(keyCode);
		
		if (active != null && active == true) {
			return true;
		}
		else 
			return false;
	}
	
	//This method provides shift our car to right direction.
	private void slideCarRight(ImageView car, double time, double velocity) {
		
		for (int i = 0;i<otherCarList.size();i++) {
			if (crashCar(car,otherCarList.get(i)) == false) {
				if ( ((velocity * time) + car.getLayoutX() ) < limXR ) {
					car.setLayoutX(car.getLayoutX() + (velocity * time));
				}
			}
			else {
				changeColorBlack(otherCarList.get(i));
				isStop = true;
			}
		}
		
		
	}
	//This method provides shift our car to left direction.
	private void slideCarLeft(ImageView car, double time , double velocity) {
		for (int i = 0;i<otherCarList.size();i++) {
			if (crashCar(car,otherCarList.get(i)) == false) {
				if ( (car.getLayoutX() - (velocity * time)) > limXL ) {
					
					car.setLayoutX(car.getLayoutX() - (velocity * time));
				}
			}
			
			else {
				changeColorBlack(otherCarList.get(i));
				isStop = true;
			}
		}
		
	}
	
	//Car shifted to up by this method
	private void slideCarUp(Group root,double velocity,double time,ImageView firstRoad,ImageView secondRoad) {
		firstRoad.setLayoutY(firstRoad.getLayoutY() + time * velocity);
		secondRoad.setLayoutY(secondRoad.getLayoutY() + time * velocity);
		roadSetter(root,firstRoad,secondRoad); 
		controlOtherCars(velocity,time);
		
	}
	//This method provides to put image to the scene again after the image become invisible. 
	private void roadSetter(Group root,ImageView firstRoad, ImageView secondRoad) {
		if (firstRoad.getLayoutY()>= 600) {
			firstRoad.setLayoutY(secondRoad.getLayoutY()-600);
		}
		
		if (secondRoad.getLayoutY()>= 600) {
			secondRoad.setLayoutY(firstRoad.getLayoutY()-600);
		}
		
	}
	//Rival cars will be shifted by this method 
	private void controlOtherCars (double velocity,double time) {
		for (int i = 0;i<otherCarList.size();i++) {
				otherCarList.get(i).setLayoutY(otherCarList.get(i).getLayoutY() + time * velocity );
				
				if (crashCar(car,otherCarList.get(i)) == true) {
					changeColorBlack(otherCarList.get(i));
				
					isStop = true;
				}
				
				else {
					if ((otherCarList.get(i).getLayoutY() - car.getLayoutY()) >=0) {
						changeColorGreen(otherCarList.get(i));
					
					}
					
					if (otherCarList.get(i).getLayoutY() >= 600) {
						otherCarList.get(i).setLayoutY(otherCarList.get(i).getLayoutY() -650);
						Random rand = new Random();
						int add = rand.nextInt(254-85)+85;
						otherCarList.get(i).setLayoutX(add);
						changeColorYellow(otherCarList.get(i));
						
					}
				}
					
		}
	}
	//This method changes the color of overtaken cars
	//Level increases each 5 overtaken cars.
	private void changeColorGreen(ImageView otherCar)  {
		if (otherCar.getImage() != greenCar) {
			otherCar.setImage(greenCar);
			carCounter += (int)level* 1;
			level = level + 0.2; 
			text.setText("Score : " + carCounter + System.lineSeparator() + "Level : "+ (int) level);
			speedUp();
		}
		
		
	}
	//This method changes the color of car
	private void changeColorYellow(ImageView otherCar) {
		otherCar.setImage(yellowCar);
	}
	//This method changes the color of collided cars
	private void changeColorBlack(ImageView otherCar) {
		otherCar.setImage(blackCar);
		car.setImage(blackCar);
	}
	
	//increases the speed
	private void speedUp() {
		if (carCounter % 5==0) {
			speed += 3;

		}
			
	}
	//decreases the speed
	private void speedD(Group root,ImageView road, ImageView road1) {
		if (speed > 0) {
			speed -= 5;
			if (speed <= 20) {
				speed -= 2;
				if (speed <= 0) {
					speed = 0;
				}
			}
		}
		slideCarUp(root,speed,elapsedTime,road,road1);
		
	}
	
	//Checks whether or not car hits the other one
	//ourCar.getBoundsInParent().intersects(otherCar.getBoundsInParent())
	private boolean crashCar(ImageView ourCar , ImageView otherCar) {
		if (ourCar.getBoundsInParent().intersects(otherCar.getLayoutX(),otherCar.getLayoutY(),42,82)) {
			return true;
		}
		return false;
	}
	
	private void speedChecker() {
		
		if (speed <= 300) {
			speed += 2.5;
		}
		
		if (speed >= 300) {
			speed += 1;
		}
	}
	
}
