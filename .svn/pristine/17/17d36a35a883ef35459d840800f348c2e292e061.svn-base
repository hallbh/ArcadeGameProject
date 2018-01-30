package GameObjects;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public abstract class GameObject extends JComponent implements Runnable{
	
	protected File[] sprite;
	private int spriteNum;
	protected double x;
	protected double y;
	protected Point2D center;
	protected Rectangle.Double hitbox;
	protected int framesPerSecond;
	protected long frameTime;
	protected Area area;
	private long systemTime;
	public GameObject[][] map;
	public Bomb[][] bombMap;
	protected boolean isPaused;
	
	
	
	public GameObject(int x, int y, int framesPerSecond, GameObject[][] map, Bomb[][] bombMap) {
		this.x = x;
		this.y = y;
		this.center = new Point2D.Double(this.x + 16, this.y + 16);
		this.hitbox = new Rectangle2D.Double(this.x, this.y, 32.0, 32.0);
		this.framesPerSecond = framesPerSecond;
		this.frameTime = 1000/this.framesPerSecond;
		this.area = new Area(this.hitbox);
		this.spriteNum = 1;
		this.map = map;
		this.bombMap = bombMap;
		this.isPaused = false;
	}
	
	public Point2D getCenterPoint(){
		return this.center;
	}
	public Rectangle2D getHitBox(){
		return this.hitbox;
	}
	public abstract void collide(GameObject obj);
	
	public void drawOn(Graphics2D g2){
		BufferedImage img = null;
		if(this.sprite.length == 1){
			try{
				img = ImageIO.read(this.sprite[0]);
			} catch (IOException e) {
				System.out.println("Sprite not found");
			}
			g2.drawImage(img, null, (int)this.x, (int)this.y);
		} else {
			try{//system in milliseconds
				img = ImageIO.read(this.sprite[this.spriteNum]);
			} catch (IOException e) {
				System.out.println("Sprite not found");
			}
			g2.drawImage(img, null, (int)this.x, (int)this.y);
			if(this.systemTime + 150 < System.currentTimeMillis()) {
				this.systemTime = System.currentTimeMillis();
				if(this.spriteNum < this.sprite.length - 1){
					this.spriteNum += 1;
				} else {
					this.spriteNum = 0;
				}
			} 
		}
	}
	
	public void pause(){
		this.isPaused = !this.isPaused;
	}
	
	public void update(){
		this.center = new Point2D.Double(this.x + 16, this.y + 16);
		this.hitbox = new Rectangle2D.Double(this.x, this.y, 32, 32);
		this.area = new Area(this.hitbox);
	}
	/**
	 * This method converts the current center point of the object to the grid position in LevelPanel
	 * 
	 * @return Point2D x grid and y grid
	 */
	public Point2D convertToGrid(){
		int centerX = (int)this.center.getX();
		int centerY = (int)this.center.getY();
		Point2D toReturn = new Point2D.Double(centerX / 32, centerY / 32);
		return toReturn;
	}
	public Point2D convertToGrid(int x, int y){
		Point2D toReturn = new Point2D.Double((x + 16)/32, (y + 16)/32);
		return toReturn;
	}
	
	protected GameObject nearestObject(){
		GameObject closest = null;
		double nearestDistance = Double.MAX_VALUE;
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.map[i][j] != null && this.map[i][j] != this){
					double distance = this.center.distance(this.map[i][j].getCenterPoint());
					if(distance < nearestDistance){
						nearestDistance = distance;
						closest = this.map[i][j];
					}
				}	
			}
		}
		return closest;
	}
	
	public Bomb nearestBomb(){
		Bomb closest = null;
		double nearestDistance = Double.MAX_VALUE;
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.bombMap[i][j] != null){
					double distance = this.center.distance(this.bombMap[i][j].getCenterPoint());
					if(distance < nearestDistance){
						nearestDistance = distance;
						closest = this.bombMap[i][j];
					}
				}
			}
		}
		return closest;
	}
	
	public void die(){
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.map[i][j] == this){
					this.map[i][j] = null;
				}
			}
		}
	}
	
	public abstract void collideWithDoor(Door door);
	
	public abstract void collideWithPowerup(Powerup powerup);
	
	public abstract void collideWithBrick(Brick brick);

	public abstract void collideWithWall(Wall wall);

	public abstract void collideWithPlayer(Player player);

	public abstract void collideWithBomb(Bomb bomb);
	
	public abstract void collideWithExplosion(Explosion explosion);
	
	public abstract void collideWithEnemy(Enemy enemy);

	public abstract void collideWithFireball(Fireball fireball);

}
