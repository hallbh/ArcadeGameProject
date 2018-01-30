package GameObjects;


import java.awt.geom.Point2D;
import java.io.File;

public class Bomb extends GameObject {
	boolean hasMovedOut;
	private Player player;
	private boolean isExploded;
	private boolean detonator;
	private int bombSize;
	private SoundPlayer boom;

	public Bomb(int x, int y, Player player, GameObject[][] map, Bomb[][] bombMap, boolean detonator, int bombSize) {
		super(x, y, 30, map, bombMap);
		this.sprite = new File[]{new File("Sprites/bomb.png")};
		this.hasMovedOut = false;
		this.player = player;
		this.map = map;
		this.bombMap = bombMap;
		this.isExploded = false;
		this.detonator = detonator;
		this.bombSize = bombSize;
		this.boom = new SoundPlayer("Sounds/boom.wav");
	}

	@Override
	public void collide(GameObject obj) {
		obj.collideWithBomb(this);
	}

	@Override
	public void collideWithBrick(Brick brick) {
		// bombs will never collide with bricks
	}

	@Override
	public void collideWithWall(Wall wall) {
		// bombs will never collide with walls
	}

	@Override
	public void collideWithPlayer(Player player) {
		if(this.hasMovedOut){
			int direction = player.getDirection();
			switch(direction){
				case 1: player.moveX(2);
				break;
				case 2: player.moveX(-2);
				break;
				case 3: player.moveY(2);
				break;
				case 4: player.moveY(-2);
				break;
				default:
				break;
			}
			player.stopMoving();
		}
		
	}

	@Override
	public void collideWithBomb(Bomb bomb) {
		// bombs will never collide with bombs
		
	}
	
	public boolean hasExploded(){
		return this.isExploded;
	}

	@Override
	public void run() {		
		try {
			double gridx = this.center.getX();
			double gridy = this.center.getY();
			double playerx = this.player.center.getX();
			double playery = this.player.center.getY();
			double xDist = Math.abs(playerx - gridx);
			double yDist = Math.abs(playery - gridy);
			double timer = 0;
			
			while(this.map != null){
				if(!this.isPaused) {
					Thread.sleep(this.frameTime);
					timer += 1;
					if(!this.hasMovedOut){
						gridx = this.center.getX();
						gridy = this.center.getY();
						playerx = this.player.center.getX();
						playery = this.player.center.getY();
						xDist = Math.abs(playerx - gridx);
						yDist = Math.abs(playery - gridy);
						if(xDist > 32 || yDist > 32){
							this.hasMovedOut = true;
						}
					}
					if(timer >= 90 && !this.detonator){
						this.explode();
					}
					if(this.hasExploded()){
						break;
					}
				}
				if(timer >= 90 && !this.detonator){
					this.explode();
				}
				if(this.hasExploded()){
					break;
				}
			}
		} 
		catch (InterruptedException exception) {
			exception.printStackTrace();
		} 
		return;
	}
	
	public void explode(){
		this.boom.play();
		this.boom.setFramePosition(40000);
		//TODO change once explosion size is implemented
		this.isExploded = true;
		int horizX = 32;
		int horizNegX = 32;
		int vertY = 32;
		int vertNegY = 32;
		boolean[] stopped = new boolean[]{false,false,false,false};//array to find out where fire needs to stop, {x,-x,y,-y}
		Point2D grid = this.convertToGrid();
		
		Explosion middle = new Explosion((int)this.x, (int)this.y, 30, 1, this.map, this.bombMap);
		this.map[(int)grid.getY()][(int)grid.getX()] = middle;
		new Thread(this.map[(int)grid.getY()][(int)grid.getX()]).start();
		
		for(int i = 0; i < this.bombSize - 1; i ++){
			double xPlus = this.x + horizX;
			double yPlus = this.y + vertY;
			double xMinus = this.x - horizNegX;
			double yMinus = this.y - vertNegY;
			Point2D up = this.convertToGrid((int)this.x, (int)yMinus);
			Point2D down = this.convertToGrid((int)this.x, (int)yPlus);
			Point2D right = this.convertToGrid((int)xPlus, (int)this.y);
			Point2D left = this.convertToGrid((int)xMinus, (int)this.y);
			if(this.map[(int) up.getY()][(int) up.getX()] != null){
				stopped[2] = true;
			}
			if(this.map[(int) down.getY()][(int) down.getX()] != null){
				stopped[3] = true;
			}
			if(this.map[(int) right.getY()][(int) right.getX()] != null){
				stopped[0] = true;
			}
			if(this.map[(int) left.getY()][(int) left.getX()] != null){
				stopped[1] = true;
			}
			Explosion explodeUp = null;
			Explosion explodeDown = null;
			Explosion explodeRight = null;
			Explosion explodeLeft = null;
			
			if(!stopped[2]){
				explodeUp = new Explosion((int)this.x, (int)yMinus, 30, 7, this.map, this.bombMap);
				this.map[(int) up.getY()][(int) up.getX()] = explodeUp;
				new Thread(explodeUp).start();
				vertNegY += 32;
			}
			if(!stopped[3]){
				explodeDown = new Explosion((int)this.x, (int)yPlus, 30, 7, this.map, this.bombMap);
				this.map[(int) down.getY()][(int) down.getX()] = explodeDown;
				new Thread(explodeDown).start();
				vertY += 32;
			}
			if(!stopped[0]){
				explodeRight = new Explosion((int)xPlus, (int)this.y, 30, 6, this.map, this.bombMap);
				this.map[(int) right.getY()][(int) right.getX()] = explodeRight;
				new Thread(explodeRight).start();
				horizX += 32;
			}
			if(!stopped[1]){
				explodeLeft = new Explosion((int)xMinus, (int)this.y, 30, 6, this.map, this.bombMap);
				this.map[(int) left.getY()][(int) left.getX()] = explodeLeft;
				new Thread(explodeLeft).start();
				horizNegX += 32;
			}
		}	
		
		Explosion top = new Explosion((int)this.x, (int)this.y - vertNegY, 30,  2, this.map, this.bombMap);
		Explosion bottom = new Explosion((int)this.x, (int)this.y + vertY, 30, 3, this.map, this.bombMap);
		Explosion right = new Explosion((int)this.x + horizX, (int)this.y, 30, 4, this.map, this.bombMap);
		Explosion left = new Explosion((int)this.x - horizNegX, (int) this.y, 30, 5, this.map, this.bombMap);		
		
		if(this.map[(int)grid.getY() - (vertNegY / 32)][(int)grid.getX()] != null){
			top.collide(this.map[(int)grid.getY() - (vertNegY / 32)][(int)grid.getX()]);
		}
		else{
			this.map[(int)grid.getY() - (vertNegY / 32)][(int)grid.getX()] = top;
			new Thread(this.map[(int)grid.getY() - (vertNegY / 32)][(int)grid.getX()]).start();
		}
		if(this.map[(int)grid.getY() + (vertY / 32)][(int)grid.getX()] != null){
			bottom.collide(this.map[(int)grid.getY() + (vertY / 32)][(int)grid.getX()]);
		}
		else{
			this.map[(int)grid.getY() + (vertY / 32)][(int)grid.getX()] = bottom;
			new Thread(this.map[(int)grid.getY() + (vertY / 32)][(int)grid.getX()]).start();
		}
		if(this.map[(int)grid.getY()][(int)grid.getX() + (horizX / 32)] != null){
			right.collide(this.map[(int)grid.getY()][(int)grid.getX() + (horizX / 32)]);
		}
		else{
			this.map[(int)grid.getY()][(int)grid.getX() + (horizX / 32)] = right;
			new Thread(this.map[(int)grid.getY()][(int)grid.getX() + (horizX / 32)]).start();
		}
		if(this.map[(int)grid.getY()][(int)grid.getX() - (horizNegX / 32)] != null){
			left.collide(this.map[(int)grid.getY()][(int)grid.getX() - (horizNegX / 32)]);
		}
		else{
			this.map[(int)grid.getY()][(int)grid.getX() - (horizNegX / 32)] = left;
			new Thread(this.map[(int)grid.getY()][(int)grid.getX() - (horizNegX / 32)]).start();
		}
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.bombMap[i][j] == this){
					this.bombMap[i][j] = null;
				}
			}
		}
	}

	@Override
	public void collideWithExplosion(Explosion explosion) {
		this.explode();
	}


	@Override
	public void collideWithEnemy(Enemy enemy) {
		int direction = (enemy).getDirection();
		Point2D grid = enemy.convertToGrid();
		int tempDirection = (int)(Math.random() * 4);
		while(tempDirection == direction){				
			tempDirection = (int)(Math.random() * 4);
		}
		(enemy).setDirection(tempDirection);
		enemy.setY((int)(grid.getY() * 32));
		enemy.setX((int)(grid.getX() * 32));
	}

	@Override
	public void collideWithDoor(Door door) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithPowerup(Powerup powerup) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithFireball(Fireball fireball) {
		// TODO Auto-generated method stub.
		this.explode();
	}
}
