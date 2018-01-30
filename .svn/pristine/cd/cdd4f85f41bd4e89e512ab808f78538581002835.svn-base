package GameObjects;

import java.awt.geom.Point2D;

import javax.swing.JOptionPane;

public abstract class Enemy extends GameObject {
	
	protected int direction;
	protected boolean blownUp;
	private SoundPlayer deathNoise;
	
	public Enemy(int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, 30, map, bombMap);
		this.direction = (int)(Math.random() * 4);
		this.blownUp = false;
		this.deathNoise = new SoundPlayer("Sounds/monstersDie.wav");
	}

	@Override
	public abstract void collide(GameObject obj);
	
	@Override
	public void collideWithExplosion(Explosion explosion){
		//the sound file is quiet so we ran it 3 times
		for(int i = 0; i < 3; i++){
			this.deathNoise.play();
		}
		
		Point2D grid = this.convertToGrid();
		try{
			this.map[(int) grid.getY()][(int) grid.getX()] = explosion;
		} catch (NullPointerException e) {
			System.out.println("Failed to remove enemy from map");
		}
		new Thread(explosion).start();
		this.blownUp = true;
		this.map = null;
	}
	
	@Override
	public void collideWithEnemy(Enemy enemy){
		int direction = enemy.getDirection();
		Point2D grid = enemy.convertToGrid();
		int tempDirection = (int)(Math.random() * 4);
		while(tempDirection == direction){				
			tempDirection = (int)(Math.random() * 4);
		}
		enemy.setDirection(tempDirection);
		enemy.setY((int)(grid.getY() * 32));
		enemy.setX((int)(grid.getX() * 32));
	}
	
	public void collideWithPowerup(Powerup p){
		//TODO, make turn around
	}
	public void collideWithDoor(Door d){
		//TODO, make turn around
	}
	
	public boolean isExploded(){
		return this.blownUp;
	}
	
	@Override
	public void collideWithPlayer(Player player){
		player.map = null;
		if(player.getLives() > 1){
			JOptionPane.showMessageDialog(null, "You have " + (player.getLives() - 1) + " lives left!");
			this.map[0][0] = player;
		} else {
			int restart = JOptionPane.showConfirmDialog(null, "You Lose! Try again?");
			if(restart == JOptionPane.OK_OPTION){
				this.map[0][0] = player;
			} else {
				System.exit(0);
			}
		}
	}
	public int getDirection(){
		return this.direction;
	}
	public void setDirection(int direction){
		this.direction = direction;
	}
	public void moveX(double x){
		this.x += x;
	}
	public void moveY(double y){
		this.y += y;
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void collideWithFireball(Fireball fireball){
		//fireballs transcend collisions with each other
	}
}
