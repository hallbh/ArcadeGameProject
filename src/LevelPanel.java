import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

import GameObjects.Ballom;
import GameObjects.Bomb;
import GameObjects.Brick;
import GameObjects.Enemy;
import GameObjects.FinalBoss;
import GameObjects.GameObject;
import GameObjects.Oneal;
import GameObjects.Player;
import GameObjects.Wall;

public class LevelPanel extends JComponent {
	
	public static final int WIDTH = 932;
	public static final int HEIGHT = 448;
	private File level;
	private GameObject[][] map;
	private Bomb[][] bombMap;
	private int levelNum;
	private final Color BACKGROUND_COLOR = new Color(16,120,48);
	public Thread repainterThread;
	public Repainter repainter;
	private boolean isPaused;
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	//public Enemy numberOne		
			
	public LevelPanel() {
		this.levelNum = 1;
		this.level = new File("Levels/level1.txt");
		this.map = new GameObject[13][29];
		this.bombMap = new Bomb[13][29];
		this.isPaused = false;
		
		this.generateLevel();
		
		this.repainter = new Repainter(30);
		this.repainterThread = new Thread(this.repainter);
		this.repainterThread.start();
	}
	
	private void generateLevel(){
		int x = 0;
		int y = 0;
		Scanner scan = null;
		try {
			scan = new Scanner(this.level);
			for(int i = 0; i < 13; i++){
				x = 0;
				String temp = scan.nextLine();
				for(int j = 0; j < 29; j++){
					char curr = temp.charAt(j);
					switch(curr){
						case 'f': this.map[i][j] = null;
								  x += 32;
							break;
						case '#': this.map[i][j] = new Wall(x, y, this.map, this.bombMap);
								  x += 32;
							break;
						case '@': this.map[i][j] = new Player(x, y, this.map, this.bombMap);
								  x += 32;
							break;
						case 'b': this.map[i][j] = new Brick(false, 0, x, y, this.map, this.bombMap);
								  x += 32;
							break;
						case 'd': this.map[i][j] = new Brick(true, 0, x, y, this.map, this.bombMap);
								  x += 32;
							break;
						case 'c': this.map[i][j] = new Brick(false, 1, x, y, this.map, this.bombMap);
						  		  x += 32;
							break;
						case 's': this.map[i][j] = new Brick(false, 2, x, y, this.map, this.bombMap);
				  		  		  x += 32;
				  		  	break;
						case 't': this.map[i][j] = new Brick(false, 3, x, y, this.map, this.bombMap);
								  x += 32;
							break;
						case 'e': this.map[i][j] = new Brick(false, 4, x, y, this.map, this.bombMap);
						  		  x += 32;
						  	break;
						case 'p': this.map[i][j] = new Brick(false, 5, x, y, this.map, this.bombMap);
				  		  		  x += 32;
				  		  	break;
						case 'w': this.map[i][j] = new FinalBoss(x, y, this.map, this.bombMap);
		  		  		  		  x += 32;
		  		  		    break;
						case '1': this.map[i][j] = new Ballom(x, y, this.map, this.bombMap);
								  this.enemies.add((Enemy) this.map[i][j]);
								  x += 32;
							break;
						case '2': this.map[i][j] = new Oneal(x, y, this.map, this.bombMap);
								  this.enemies.add((Enemy) this.map[i][j]);
								  x += 32;
						  	break;
						default:  this.map[i][j] = null;
								  x += 32;
							break;
					}
					if(this.map[i][j] != null){
						new Thread(this.map[i][j]).start();
					}
				}
				y += 32;
			}
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} finally {
			scan.close();
		}
	}
	
	public void resetMaps(){
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				//tell all the old game objects to stop fiddling with the maps
				try{
					this.map[i][j].map = null;
					this.map[i][j].bombMap = null;
				} catch(NullPointerException e){
					//do nothing
				}
				try{
					this.bombMap[i][j].map = null;
					this.bombMap[i][j].bombMap = null;
				} catch(NullPointerException e){
					//do nothing
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.BACKGROUND_COLOR);	
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.map[i][j] != null){
					Point2D point = this.map[i][j].convertToGrid();
					if((int)point.getX() != j && (int)point.getY() != i){
						this.map[(int) point.getY()][(int) point.getX()] = this.map[i][j];
					}
					this.map[i][j].drawOn(g2);
				}
				if(this.bombMap[i][j] != null){
					this.bombMap[i][j].drawOn(g2);
				}
			}
		}
	}
	
	public void pause(){
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				try{
					this.map[i][j].pause();
				} catch (NullPointerException e) {
					//do nothing
				}
				try{
					this.bombMap[i][j].pause();
				} catch (NullPointerException e) {
					//do no thing
				}
			}
		}
		this.isPaused = ! this.isPaused;
	}
	
	public boolean getPaused(){
		return this.isPaused;
	}
	
	public void addBomb(){
		Bomb bomb = this.getPlayer().placeBomb();
		if(bomb != null){
			Point2D bombGrid = bomb.convertToGrid();
			this.bombMap[(int)bombGrid.getY()][(int)bombGrid.getX()] = bomb;
			new Thread(bomb).start();
		}		
	}
	
	public void addGameObject(GameObject obj, int x, int y){
		this.map[y][x] = obj;
	}
	
	public Player getPlayer(){
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.map[i][j] instanceof Player){
					return (Player)this.map[i][j];
				}
			}
		}
		return null;
	}
	
	class Repainter implements Runnable {
		private final int fps;
		
		public Repainter(int fps) {
			this.fps = fps;
		}
		
		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000 / this.fps);
					LevelPanel.this.repaint();
				}
			} catch (InterruptedException e) {
				// nothing to do
			}
		}
	}
	
	public int getLevelNum() {
		return this.levelNum;
	}
	
	public void loadLevel(int l){
		if(this.getPlayer().getLives() > 0){
			Player dead = this.getPlayer();
			this.resetMaps();
			this.levelNum = l;
			this.level = new File("Levels/level" + l + ".txt");
			this.generateLevel();
			Player alive = this.getPlayer();
			dead.transferPowerups(alive);
		} else {
			Player old = this.getPlayer();
			this.resetMaps();
			this.levelNum = l;
			this.level = new File("Levels/level" + l + ".txt");
			this.generateLevel();
			Player curr = this.getPlayer();
			old.transferPowerups(curr);
		}
		
	}
	
	public boolean gameOver(){
		if(this.map[0][0] == this.getPlayer()){
			this.getPlayer().loseLife();
			return true;
		}
		return false;
	}
}
