import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class StatsPanel extends JComponent{
	
	public final static int HEIGHT = 64;
	public static final int WIDTH = 932;
	
	private boolean isPaused;
	private File skeleton;
	private BufferedImage frame,hundImg, tensImg, onesImg, lives;
	private int time;
	private long systemTime;
	private int hundreds;
	private int tens;
	private int ones;
	private File[] numbers = new File[10];
	private GameFrame level;
	
	public StatsPanel(GameFrame g) {
		this.isPaused = false;
		this.skeleton = new File("Sprites/statspanel.png");
		this.time = 500;
		this.systemTime = System.currentTimeMillis();
		this.hundreds = 5;
		this.tens = 0;
		this.ones = 0;
		
		this.level = g;
		
		for(int i = 0; i < 10; i++){
			this.numbers[i] = new File("Sprites/timer-" + i + ".png");
		}
		
		try {
			this.frame = ImageIO.read(this.skeleton);
			this.lives = ImageIO.read(this.numbers[this.level.level.getPlayer().getLives()]);
			this.hundImg = ImageIO.read(this.numbers[this.hundreds]);
			this.tensImg = ImageIO.read(this.numbers[this.tens]);
			this.onesImg = ImageIO.read(this.numbers[this.ones]);
		} catch (IOException exception) {
			// do nothing
		}
		
		
	}

	@Override
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		
		g2.drawImage(this.frame, null, 0, 0);
		g2.drawImage(this.hundImg, null, 96, 24);
		g2.drawImage(this.tensImg, null, 112, 24);
		g2.drawImage(this.onesImg, null, 128, 24);
		g2.drawImage(this.lives, null, 900, 18);
		
		if(this.systemTime + 980 < System.currentTimeMillis()){
			this.systemTime = System.currentTimeMillis();
			this.time--;
			
			this.hundreds = this.time/100;
			this.tens = (this.time/10)%10;
			this.ones = this.time %10;
		}
		
		try {
			this.lives = ImageIO.read(this.numbers[this.level.level.getPlayer().getLives()]);
			this.hundImg = ImageIO.read(this.numbers[this.hundreds]);
			this.tensImg = ImageIO.read(this.numbers[this.tens]);
			this.onesImg = ImageIO.read(this.numbers[this.ones]);
		} catch (IOException exception) {
			// Lord help us if an integer value between 0 and 9 isn't 1, 2, 3, 4, 5, 6, 7, 8, 9, or 0.
		} catch (NullPointerException e) {
			//this is so when the player dies it doesn't freak
		}
	}
	
	public void pause(){
		this.isPaused = !this.isPaused;
	}

}
