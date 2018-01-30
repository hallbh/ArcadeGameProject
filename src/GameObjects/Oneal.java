package GameObjects;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Oneal extends Enemy {
	
	private Point2D target;
	private Point2D gridPos;
	private File[] resting = new File[]{new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-2.png"),
										new File("Sprites/Enemy2-2.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2-2.png"),
										new File("Sprites/Enemy2-2.png")};
	
	private File[] standing = new File[]{new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-2.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2-2.png"),
										new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-2.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2-2.png")};
	
	private File[] jumping = new File[]{new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-3.png"),
										new File("Sprites/Enemy2.png"),
										new File("Sprites/Enemy2-3.png")};
	private int spriteCount = 0;
	private long systemTime;
	private Random rand;

	public Oneal(int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, map, bombMap);
		this.map = map;
		this.sprite = this.resting;
		this.systemTime = System.currentTimeMillis();
		this.rand = new Random();
		this.gridPos = this.convertToGrid();
	}

	@Override
	public void run() {
		try{
			while(this.map != null && !this.blownUp){
				if(!this.isPaused){
					if(this.systemTime + 150 < System.currentTimeMillis()) {
						this.systemTime = System.currentTimeMillis();
						if(this.spriteCount == 1){
							this.sprite = this.resting;
						}
						else if(this.spriteCount == 24){
							this.sprite = this.standing;
						}
						else if(this.spriteCount == 36){
							this.sprite = this.jumping;
						} 
						else if(this.spriteCount == 48){
							ArrayList<Point2D> landingZones = nullMapLocations();
							int landingZone = this.rand.nextInt(landingZones.size());
							this.target = landingZones.get(landingZone);
							Point2D grid = this.convertToGrid();
							this.map[(int) target.getY()][(int) target.getX()] = this;
							this.map[(int) grid.getY()][(int) grid.getX()] = null;
							this.x = 32*this.target.getX();
							this.y = 32*this.target.getY();
							this.spriteCount = 0;
						}
						
						this.spriteCount++;
						if(this.spriteCount > 48){
							this.spriteCount = 1;
						}
					}
					
					Thread.sleep(this.frameTime);
				}
				this.update();
			}
		}
		catch(InterruptedException e) {
			//do nothing
		}
	}
	
	private ArrayList<Point2D> nullMapLocations(){
		ArrayList<Point2D> returnMe = new ArrayList<Point2D>();
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 29; j++) {
				if(this.map[i][j] == null && this.bombMap[i][j] == null){
					returnMe.add(new Point2D.Double(j,i));
				}
			}
		}
		return returnMe;
	}
	
	@Override
	public void update(){
		this.center = new Point2D.Double(this.x + 16, this.y + 16);
		this.hitbox = new Rectangle2D.Double(this.x, this.y, 32, 32);
		this.area = new Area(this.hitbox);
		Point2D grid = this.convertToGrid();
		if(!grid.equals(this.gridPos) && !this.blownUp){
			try{
				this.map[(int) grid.getY()][(int) grid.getX()] = this;
				this.map[(int) this.gridPos.getY()][(int) this.gridPos.getX()] = null;
				this.gridPos = grid;
			} catch (NullPointerException e){
				this.map = null;
				this.bombMap = null;
			}
		}
	}
	

	@Override
	public void collide(GameObject obj) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithBrick(Brick brick) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithWall(Wall wall) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithBomb(Bomb bomb) {
		// TODO Auto-generated method stub.

	}

}
