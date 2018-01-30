import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import GameObjects.SoundPlayer;

public class GameFrame extends JFrame implements Runnable{
	
	public LevelPanel level;
	public StatsPanel stats;
	public KeyboardListener keys;
	private SoundPlayer normal, boss;

	public GameFrame() throws HeadlessException {
		LevelPanel l = new LevelPanel();
		Dimension d = new Dimension(LevelPanel.HEIGHT, LevelPanel.WIDTH);//LSD
		l.setSize(d);
		this.level = l;
		
		StatsPanel s = new StatsPanel(this);
		d.setSize(StatsPanel.HEIGHT, StatsPanel.WIDTH);
		s.setSize(d);
		this.stats = s;
		
		this.normal = new SoundPlayer("Sounds/normalMusic.wav");
		this.normal.loop();
		this.boss = new SoundPlayer("Sounds/boss.wav");
		
		this.keys = new KeyboardListener(this.level, this.normal, this.boss);
		this.addKeyListener(this.keys);
		
		new Thread(this).start();
	}

	public GameFrame(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub.
	}

	public GameFrame(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub.
	}

	public GameFrame(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub.
	}
	

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000 / 30);
				this.level.repaint();
				this.stats.repaint();
				if(this.level.gameOver()){
					if(this.level.getPlayer().getLives() > 0){
						if(this.level.getLevelNum() == 4){
							this.normal.kill();
							this.boss.loop();
						}
						this.level.loadLevel(this.level.getLevelNum());
						this.keys.setPlayer();
					} else {
						this.normal.kill();
						this.boss.kill();
						this.normal.loop();
						this.level.loadLevel(1);
						this.keys.setPlayer();
					}
				}
			}
		} catch (InterruptedException e) {
			// nothing to do
		}
	}
}
