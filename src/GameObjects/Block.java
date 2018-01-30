package GameObjects;

import java.awt.geom.Point2D;

public abstract class Block extends GameObject {

	public Block(int x, int y, int fps, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, fps, map, bombMap);
	}
	
	@Override
	public abstract void collide(GameObject obj);
	
	@Override
	public void collideWithPlayer(Player player) {
		int direction = player.getDirection();
		double speed = player.getSpeed();
		switch(direction){
			case 1: player.moveX((int) speed);
			break;
			case 2: player.moveX((int) -speed);
			break;
			case 3: player.moveY((int) speed);
			break;
			case 4: player.moveY((int) -speed);
			break;
			default:
			break;
		}
		player.stopMoving();		
	}
	
	public void collideWithPowerup(Powerup p){
		//do nothing
	}
	public void collideWithDoor(Door d){
		//do nothing
	}
	
	public void collideWithFireball(Fireball fireball){
		fireball.blownUp = true;
		fireball.map = null;
		Point2D grid = fireball.convertToGrid();
		this.map[(int) grid.getY()][(int) grid.getX()] = null;
		
	}

}
