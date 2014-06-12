
//////////////////////////////////////////////////////////
//
//			boolean alive;
//			if(!alive) GameOverScreen.draw (Klasse GameOverScreen)
//			alle anderen inputs außer ESC deaktiviern + sounds;
//
//			abfragen if(player.getX<0||player.getX>bildschirmbreite und Y dann geht A bzw D tasten nimchtmehr 
//			+ W und S für getY
//
//			laserabfrage, damit nicht unendlich laser geschossen werden, wenn laser.getY>bildschirmhöhe, dann lasershots.delete(laser)
//
//			Hautpmenü bzw. Startmenü fehlt auch noch
//
//			Highscore mach ich noch
//
//			und für des komplette level brauchen wir dann zb noch einen Time-Manager der nach einer bestimmten Zeit einen Gegner erstellt
//
//			wir können auch noch den speedBoost vom Stern auf den Hintergrund hinzufügen, dass es aussieht als ob man schneller fliegt
/////////////////////////










package mms;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class Main {

	private long lastFrame;
	private int delta;
	private double turbo = 6;
	private int steps = 0;
	private boolean gimColl = false;
	private boolean explosionColl = false;
	private Player player;
	private Explosion explosion;
	// Enemy enemy;
	private Laser laser;
	private EnemyLaser enemylaser;
	private Boss boss;
	private boolean bossThere=false;
	// TrueTypeFont font;

	private int backgroundY = 0;

	private int leftMidRight = 1;

	private Audio audioEffect;

	private static UnicodeFont font;
	private static DecimalFormat formatter = new DecimalFormat("#.##");
	private int highscore = 0;

	private Background[] backgroundArray = new Background[2];
	private Background thisBG;

	private List<Laser> laserShots = new LinkedList<Laser>();
	private List<Enemy> enemies = new LinkedList<Enemy>();
	private List<Gimmick> gimmicks = new LinkedList<Gimmick>();
	private List<Obstacle> obstacles = new LinkedList<Obstacle>();

	private List<HomingMissile> homingMissiles = new LinkedList<HomingMissile>();

	private List<Background> backgroundLoop = new LinkedList<Background>();
	private int x = 0;

	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(1024, 720));
			Display.setTitle("2dGame");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		player = new Player(200, 300, 100, 100);
		// Background background = new Background(0, -5000, 1800, 5000);
		// Background background2 = new Background(0, -500, 1800, 5000);
		// background.setDY(1); //0.05
		// background2.setDY(.05);
		// backgroundArray[0]=background;
		// backgroundArray[1]=background2;
		thisBG = new Background(0, -1000, 1800, 3000);
		backgroundLoop.add(thisBG);

		

		Enemy enemy1 = new Enemy(10, 10, 50, 50, 0);
		enemy1.setDY(0.03);
		enemy1.setDX(0.05);
		enemies.add(enemy1);

		Enemy enemy2 = new Enemy(800, 10, 50, 50, 1);
		enemy2.setDY(0.01);
		enemies.add(enemy2);

		Enemy enemy3 = new Enemy(500, 100, 50, 50, 2);
		enemy3.setDY(0.03);
		enemy3.setDX(-0.01);
		enemies.add(enemy3);

		Gimmick gim1 = new Gimmick(50, 10, 50, 50);
		gim1.setDY(0.3);
		gimmicks.add(gim1);

		Gimmick gim2 = new Gimmick(210, 10, 50, 50);
		gim2.setDY(0.09);
		gimmicks.add(gim2);

		Gimmick gim3 = new Gimmick(550, 10, 50, 50);
		gim3.setDY(0.02);
		gimmicks.add(gim3);

		Gimmick gim4 = new Gimmick(750, 10, 50, 50);
		gim4.setDY(0.04);
		gimmicks.add(gim4);

		Obstacle obst1 = new Obstacle(680, 10, 70, 70);
		obst1.setDY(0.04);
		obstacles.add(obst1);

		Obstacle obst2 = new Obstacle(480, 10, 70, 70);
		obst2.setDY(0.02);
		obstacles.add(obst2);
		
		homingMissiles.add(new HomingMissile(100, 100, 150, 150));
		homingMissiles.add(new HomingMissile(800, 100, 150, 150));
		homingMissiles.add(new HomingMissile(599, 100, 150, 150));
		
		boss = new Boss(200, -1000, 800, 800);
		boss.setDY(0.05);

		try {
			audioEffect = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("res/laser_Shoot.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// laser = new Laser(player.getX()+player.getWidtH()/2 - 10/2,
		// player.getY(), 10, 10);

		setUpTimer();
		setUpFonts();

		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1024, 720, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested()) {
			delta = getDelta();
			glClear(GL_COLOR_BUFFER_BIT);

			// background.update(delta);
			// background.draw();
			
			
			//methode auslagern
			if(boss.getY()>0){
				if(!bossThere){
				boss.setDY(0);
				boss.setDX(-0.1);
				bossThere=true;
				}
				System.out.println(boss.getX());
				if(boss.getX()<0){
					boss.setDX(0.1);
				}
				else if(boss.getX()>420){
					boss.setDX(-0.1);
				}
//				boss.s
			}
			
			for (Background bg : backgroundLoop) {
				bg.draw();
				bg.update(delta);
				bg.setDY(0.1);
			}



			fonts();

			// enemy.draw();
			// enemy.update(delta);

			boss.draw();
			boss.update(delta);
			
			for (HomingMissile hm : homingMissiles) {
				hm.draw();
				hm.update(delta);
			}

			for (Enemy enemy : enemies) {
				enemy.draw();
				enemy.update(delta);
			}

			for (Gimmick gimmick : gimmicks) {
				gimmick.draw2();
				gimmick.update(delta);
			}

			for (Obstacle obstacle : obstacles) {
				obstacle.draw();
				obstacle.update(delta);
			}
			
			if (!explosionColl) {
				player.draw2(leftMidRight);
				player.update(delta);

			} else {
				explosion = new Explosion(player.getX(), player.getY(), 100,
						100);
				explosion.draw();
			}

			// System.out.println(background.getY());
			// if (background.getY() > 100) {
			// background.setY(-500);
			// }

			// if((int)background.getY()+1%500==0){
			// System.out.println("jz");
			// }
			// if(background.getY()>background.getHeight()){
			// System.out.println("test");
			// }
			backgroundY = (int) thisBG.getY();
			// System.out.println(backgroundY);

			if (backgroundY > x) {
				// backgroundLoop.add(new Background(0, -1000, 1800, 3000));
				thisBG = new Background(0, -2200, 1800, 3000);
				backgroundLoop.add(thisBG);
				// x += 1000;
			}
			// System.out.println(backgroundLoop.size());
			if (backgroundLoop.size() > 2) {
				backgroundLoop.remove(0);
			}

			// laserMovement(laser);

			for (Laser laser : laserShots) {
				laser.draw();
				laser.update(delta);
				laser.setDY(-0.2);
			}
			


			leftMidRight = 1;
			input();
			input2();

			checkColl();
			
			homing();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}

	// private void laserMovement(Laser laser) {
	// laser.setX(player.getX());
	// laser.setY(player.getY());
	//
	// }

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}

	private long getTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

	private void input() {
		// while (Keyboard.next()) {

		// if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			AL.destroy();
			System.exit(0);
		}

		// if (Keyboard.getEventKey() == Keyboard.KEY_W
		// && Keyboard.getEventKeyState()) {
		if (!explosionColl && Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (gimColl) {
				steps++;
			}

			if (gimColl && steps == 200) {
				turbo = 6;
				steps = 0;
				gimColl = false;
			}
			player.setY(player.getY() - turbo);

		}
		if (!explosionColl && Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (gimColl) {
				steps++;
			}

			if (gimColl && steps == 200) {
				turbo = 6;
				steps = 0;
				gimColl = false;
			}
			player.setY(player.getY() + turbo);

		}
		if (!explosionColl && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (gimColl) {
				steps++;
			}
			if (gimColl && steps == 200) {
				turbo = 6;
				steps = 0;
				gimColl = false;

			}
			player.setX(player.getX() - turbo);
			leftMidRight = 0;

		}
		if (!explosionColl && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (gimColl) {
				steps++;
			}
			if (gimColl && steps == 200) {
				turbo = 6;
				steps = 0;
				gimColl = false;
			}
			player.setX(player.getX() + turbo);
			leftMidRight = 2;
		}
		// if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.next()) {
		// laserShots.add(new Laser(
		// player.getX() + player.getWidtH() / 2 - 23, player.getY(),
		// 10, 20));
		// audioEffect.playAsSoundEffect(1f, 1f, false);
		// }
		// }
		// SoundStore.get().poll(0);
	}

	private void input2() {
		while (Keyboard.next()) {
			if (!explosionColl && Keyboard.getEventKey() == Keyboard.KEY_SPACE
					&& Keyboard.getEventKeyState()) {
				laserShots.add(new Laser(player.getX() + player.getWidtH() / 2
						- 23, player.getY(), 10, 20));
				audioEffect.playAsSoundEffect(1f, 1f, false);
			}
			SoundStore.get().poll(0);
		}
	}

	private static void setUpFonts() {
		java.awt.Font awtFont = new java.awt.Font("Comic Sans MS",
				java.awt.Font.BOLD, 36);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.YELLOW));
		font.addAsciiGlyphs();
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void fonts() {
		font.drawString(10, 10, Integer.toString(highscore));
	}

	private void checkColl() {
		boolean collDetected = false;
		outerLoop: for (Enemy enemy : enemies) {
			for (Laser laser : laserShots) {
				if (laser.intersects(enemy)) {
					laserShots.remove(laser);
					enemies.remove(enemy);
					highscore += 10;
					collDetected = true;
					break outerLoop;
				}
			}
		}
		if (collDetected) {
			checkColl();
		}
		//
		// for(Enemy enemy : enemies){
		// if(player.intersects(enemy)){
		//
		// }
		// }

		for (Gimmick gimmick : gimmicks) {
			if (player.intersects(gimmick)) {
				gimColl = true;
				gimmicks.remove(gimmick);
				turbo = 10;
				break;
			}
		}

		for (Obstacle obstacle : obstacles) {
			if (player.intersects(obstacle)) {
				obstacles.remove(obstacle);
				explosionColl = true;
			}
		}

	}

	public void homing() {
		int threshold=1;
		for (HomingMissile homingM : homingMissiles) {
//			System.out.println(player.getX()-homingM.getX());
			if(homingM.getX()>player.getX()+threshold){
//				System.out.println("test");
				homingM.setDX(-0.1);
			}
			else if(homingM.getX()<player.getX()-threshold){
//				System.out.println("test");
				homingM.setDX(+0.1);
			}
			else{
				homingM.setDX(0);
			}
			if(homingM.getY()>player.getY()+threshold){
//				System.out.println("test");
				homingM.setDY(-0.1);
			}
			else if(homingM.getY()<player.getY()-threshold){
//				System.out.println("test");
				homingM.setDY(+0.1);
			}
			else{
				homingM.setDY(0);
			}
//			homingM.setX(player.getX() - homingM.getX());
//			homingM.setY(player.getY() - homingM.getY());
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
