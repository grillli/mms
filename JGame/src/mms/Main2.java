//////////////////////////////////////////////////////////
//
// boolean alive;
// if(!alive) GameOverScreen.draw (Klasse GameOverScreen)
// alle anderen inputs auer ESC deaktiviern + sounds;
//
// abfragen if(player.getX<0||player.getX>bildschirmbreite und Y dann geht A bzw D tasten nimchtmehr
// + W und S fr getY
//
// laserabfrage, damit nicht unendlich laser geschossen werden, wenn laser.getY>bildschirmhhe, dann lasershots.delete(laser)
//
// Hautpmen bzw. Startmen fehlt auch noch
//
// Highscore mach ich noch
//
// und fr des komplette level brauchen wir dann zb noch einen Time-Manager der nach einer bestimmten Zeit einen Gegner erstellt
//
// wir knnen auch noch den speedBoost vom Stern auf den Hintergrund hinzufgen, dass es aussieht als ob man schneller fliegt
/////////////////////////

package mms;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

public class Main2 {

	private long lastFrame;
	private int delta;
	private double turbo = 6;
	private int steps = 0;
	private boolean gimColl = false;
	private boolean explosionColl = false;
	private boolean bossDeath = false;
	private Player player;
	private Explosion expl;

	// Enemy enemy;
	private Laser laser;
	private EnemyLaser enemylaser;
	private Boss boss;
	private boolean bossThere = false;
	// TrueTypeFont font;

	private int backgroundY = 0;

	private int leftMidRight = 1;

	private Audio audioEffect;
	private Audio audioEffect2;

	private static int PREF_DISPLAY_WIDTH = 1024;
	private static int PREF_DISPLAY_HEIGHT = 720;
	private static UnicodeFont font;
	private static UnicodeFont font2;
	private static UnicodeFont font3;
	private static UnicodeFont fontGameOver;
	private static DecimalFormat formatter = new DecimalFormat("#.##");
	private int actualScore = 0;
	private Highscore highscore;
	private int highscoreInt = 0;
	private int bossLive = 50;

	// private Background[] backgroundArray = new Background[2];
	private Background thisBG;
	private List<Background> backgroundLoop = new LinkedList<Background>();
	private int x = 0;

	private List<Laser> laserShots = new LinkedList<Laser>();
	private List<Enemy> enemies = new LinkedList<Enemy>();
	private List<Gimmick> gimmicks = new LinkedList<Gimmick>();
	private List<Obstacle> obstacles = new LinkedList<Obstacle>();
	private List<Explosion> explosions = new LinkedList<Explosion>();
	private List<HomingMissile> homingMissiles = new LinkedList<HomingMissile>();
	private List<EnemyLaser> enemyLaserShots = new LinkedList<EnemyLaser>();

	private boolean running = false;
	private boolean enemyLaserBoolean = false;
	private boolean enemyWaveBoolean = false;

	private boolean playerAlive = true;
	private Live liveImage;
	private int liveCounter = 3;
	private int i = 20;
	private int counterGameOver = 100;
	private Explosion explosionLast;
	private Explosion explosionLast2 = null;
	private Explosion explosionLast3 = null;

	public Main2() {
		try {
			Display.setDisplayMode(new DisplayMode(PREF_DISPLAY_WIDTH,
					PREF_DISPLAY_HEIGHT));
			Display.setTitle("2dGame");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		player = new Player(PREF_DISPLAY_WIDTH / 2 - 50,
				PREF_DISPLAY_HEIGHT - 100, 100, 100);
		// Background background = new Background(0, -5000, 1800, 5000);
		// Background background2 = new Background(0, -500, 1800, 5000);
		// background.setDY(1); //0.05
		// background2.setDY(.05);
		// backgroundArray[0]=background;
		// backgroundArray[1]=background2;
		thisBG = new Background(0, -1000, 1800, 3000);
		backgroundLoop.add(thisBG);

		// Enemy enemy1 = new Enemy(10, 10, 50, 50, 0);
		// enemy1.setDY(0.03);
		// enemy1.setDX(0.05);
		// enemies.add(enemy1);

		Enemy enemy2 = new Enemy(800, 10, 80, 80, 1);
		enemy2.setDY(0.01);
		enemies.add(enemy2);

		Enemy enemy3 = new Enemy(500, 100, 80, 80, 2);
		enemy3.setDY(0.03);
		enemies.add(enemy3);

		Enemy enemy4 = new Enemy(430, 100, 80, 80, 3);
		enemy4.setDY(0.09);
		enemies.add(enemy4);

		Enemy enemy5 = new Enemy(730, 100, 80, 80, 4);
		enemy5.setDY(0.06);
		enemies.add(enemy5);

		Enemy enemy6 = new Enemy(650, 100, 80, 80, 5);
		enemy6.setDY(0.05);
		enemies.add(enemy6);

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

		expl = new Explosion(player.getX(), player.getY(), 100, 100);

		// homingMissiles.add(new HomingMissile(100, 100, 150, 150));
		// homingMissiles.add(new HomingMissile(800, 100, 150, 150));
		// homingMissiles.add(new HomingMissile(599, 100, 150, 150));

		boss = new Boss(300, -1000, 800, 400);
		boss.setDY(0.05);

		try {
			audioEffect = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("res/laser_Shoot.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// try{
		// audioEffect = AudioLoader.getAudio("WAV",
		// ResourceLoader.getResourceAsStream("res/background_musik.wav"));
		// }catch(IOException e){
		// e.printStackTrace();
		// }

		// laser = new Laser(player.getX()+player.getWidtH()/2 - 10/2,
		// player.getY(), 10, 10);

		setUpTimer();
		setUpFonts();

		// methode
		highscore = new Highscore();
		// highscore.load(new File("save.xml"));
		highscoreInt = highscore.getHighscore();

		liveImage = new Live(50, PREF_DISPLAY_HEIGHT - 50, 50, 50);

		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, PREF_DISPLAY_WIDTH, PREF_DISPLAY_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested()) {
			delta = getDelta();
			glClear(GL_COLOR_BUFFER_BIT);

			// background.update(delta);
			// background.draw();

			if (!running) {
				if (!playerAlive) {
					// System.out.println("game over");
					drawGameOverScreen();
					while (Keyboard.next()) {
						Display.destroy();
						AL.destroy();
						System.exit(0);
					}

				} else {
					drawMenuScreen();
					if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
						Display.destroy();
						AL.destroy();
						System.exit(0);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
						running = true;
						TimerTask enemyWave = new TimerTask() {
							public void run() {
								enemyWaveBoolean = true;
							}
						};
						Timer TimerEnemyWaves = new Timer();
						TimerEnemyWaves.schedule(enemyWave, 5000, 5000);
						// 2ter parameter is wann des startet, 3ter is wann des
						// wiederholt wird
						// wenn man nur zb enemyWave und 5000 angibt wird es net
						// wiederholt sondern nur einmal nach 5000millisek, also
						// 5sek gestartet

						// try {
						// Thread.sleep(2000);
						// } catch (InterruptedException e) {
						// e.printStackTrace();
						// }
					}
				}
			}

			if (running) {

				if (enemyWaveBoolean) {
					int xE = 50, yE = 0;
					for (int i = 0; i < 5; i++) {
						if (!explosionColl) {
							enemies.add(new Enemy(xE, yE, 80, 80, 3));

							xE += 200;
						}
					}

					for (Enemy enemy : enemies) {
						enemyLaserShots.add(new EnemyLaser(enemy.getX()
								+ enemy.getWidtH() / 2 - 11, enemy.getY(), 30,
								40));
					}
					enemyWaveBoolean = false;
				}

				// methode auslagern
				if (boss.getY() > 0) {
					if (!bossThere) {
						boss.setDY(0);
						boss.setDX(-0.1);
						if (!explosionColl) {
							homingMissiles.add(new HomingMissile(boss.getX()
									+ boss.getWidtH() / 4, boss.getY()
									+ boss.getHeight() / 4, 150, 150));
							homingMissiles.add(new HomingMissile(boss.getX()
									+ boss.getWidtH() / 4 + 135, boss.getY()
									+ boss.getHeight() / 4, 150, 150));
						}

						bossThere = true;
					}

					if (boss.getX() < 0) {
						boss.setDX(0.1);
					} else if (boss.getX() > 600) {
						boss.setDX(-0.1);
					}

				}

				for (Background bg : backgroundLoop) {
					bg.draw();
					bg.update(delta);
					bg.setDY(0.1 + turbo / 100 - 0.06);
				}

				// enemy.draw();
				// enemy.update(delta);
				if (!bossDeath) {
					boss.draw();
					boss.update(delta);
				} else {
					boss.setY(-1000);
					boss.setX(-1000);

				}

				for (HomingMissile hm : homingMissiles) {
					hm.draw();
					hm.update(delta);
				}

				for (Enemy enemy : enemies) {
					if (enemy.getDX() == 0 && enemy.getDY() == 0) {
						enemy.setDY(0.05);
					}
					enemy.draw();
					enemy.update(delta);

				}

				for (Gimmick gimmick : gimmicks) {
					gimmick.draw2();
					gimmick.update(delta);
				}

				for (Obstacle obstacle : obstacles) {
					obstacle.draw2();
					obstacle.update(delta);
				}

				if (!explosionColl && playerAlive) {
					// player.draw2(leftMidRight);
					player.draw();
					player.update(delta);

				} else {

					expl.setX(player.getX());
					expl.setY(player.getY());
					expl.draw2();
					i--;
					if (i == 0) {
						explosionColl = false;
					}

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

				for (EnemyLaser enemyLaser : enemyLaserShots) {
					enemyLaser.draw();
					enemyLaser.update(delta);
					enemyLaser.setDY(0.2);
				}

				leftMidRight = 1;
				input();
				input2();
				checkLaserOutOfScreen();
				checkEnemyLaserOutOfScreen();
				checkColl();

				fonts();

				homing();

				liveImage.draw();
				liveImage.update(delta);

				checkAlive();

				// if (!playerAlive && counterGameOver == 100) {
				//
				// }
				if (!playerAlive) {
					if (counterGameOver == 100) {
						explosionLast = new Explosion(player.getX(),
								player.getY(), 500, 500);
					}
					if (counterGameOver == 80) {
						explosionLast2 = new Explosion(player.getX() - 200,
								player.getY(), 500, 500);
					}
					if (counterGameOver == 50) {
						explosionLast2 = new Explosion(player.getX() - 100,
								player.getY() - 100, 500, 500);
					}

					counterGameOver--;

					explosionLast.draw2();
					explosionLast.update(delta);
					if (explosionLast2 != null) {
						explosionLast2.draw2();
						explosionLast2.update(delta);
					}
					if (explosionLast3 != null) {
						explosionLast3.draw2();
						explosionLast3.update(delta);
					}
				}
				if (counterGameOver == 0) {
					running = false;
				}

			}
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

	private void checkLaserOutOfScreen() {
		for (Laser laser : laserShots) {
			if (laser.getY() < 0) {
				laserShots.remove(laser);
				break;
			}
		}
	}

	private void checkEnemyLaserOutOfScreen() {
		for (EnemyLaser enemyLaser : enemyLaserShots) {
			if (enemyLaser.getY() > 720) {
				laserShots.remove(enemyLaser);
				break;
			}
		}
	}

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
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			// das is GAME OVER nicht keyboard P
			highscore.setHighscore(actualScore);

		}
		// if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			highscore.setHighscore(actualScore);
			Display.destroy();
			AL.destroy();
			System.exit(0);
		}

		// if (checkFrame()) {

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
			if (!(player.getY() < 0)) {
				player.setY(player.getY() - turbo);
			}
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

			if (player.getY() < 635) {
				player.setY(player.getY() + turbo);
			}

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
			if (!(player.getX() < 0)) {
				player.setX(player.getX() - turbo);
				leftMidRight = 0;
			}

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
			if (player.getX() < 960) {
				player.setX(player.getX() + turbo);
				leftMidRight = 2;
			}
		}
		// if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.next()) {
		// laserShots.add(new Laser(
		// player.getX() + player.getWidtH() / 2 - 23, player.getY(),
		// 10, 20));
		// audioEffect.playAsSoundEffect(1f, 1f, false);
		// }
		// }
		// SoundStore.get().poll(0);

		// }

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
		java.awt.Font awtFont2 = new java.awt.Font("Comic Sans MS",
				java.awt.Font.BOLD, 50);
		java.awt.Font awtFont3 = new java.awt.Font("Comic Sans MS",
				java.awt.Font.BOLD, 50);
		java.awt.Font awtfontGameOver = new java.awt.Font("Comic Sans MS",
				java.awt.Font.BOLD, 50);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.YELLOW));
		font.addAsciiGlyphs();
		font2 = new UnicodeFont(awtFont2);
		font2.getEffects().add(new ColorEffect(java.awt.Color.BLUE));
		font2.addAsciiGlyphs();
		font3 = new UnicodeFont(awtFont3);
		font3.getEffects().add(new ColorEffect(java.awt.Color.RED));
		font3.addAsciiGlyphs();
		fontGameOver = new UnicodeFont(awtFont3);
		fontGameOver.getEffects().add(new ColorEffect(java.awt.Color.RED));
		fontGameOver.addAsciiGlyphs();
		try {
			font.loadGlyphs();
			font2.loadGlyphs();
			font3.loadGlyphs();
			fontGameOver.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void fonts() {
		font.drawString(10, 10, Integer.toString(actualScore));
		font.drawString(750, 10, "Highscore: " + Integer.toString(highscoreInt));
		font3.drawString(5, PREF_DISPLAY_HEIGHT - 65,
				Integer.toString(liveCounter));
	}

	private void drawMenuScreen() {
		font2.drawString(PREF_DISPLAY_WIDTH / 6, PREF_DISPLAY_HEIGHT / 2 - 100,
				"Press RETURN to start game");
		font2.drawString(PREF_DISPLAY_WIDTH / 6, PREF_DISPLAY_HEIGHT / 2,
				"Zum Starten ENTER druecken");
	}

	private void drawGameOverScreen() {
		fontGameOver.drawString(PREF_DISPLAY_WIDTH / 6,
				PREF_DISPLAY_HEIGHT / 2 - 100, "GAME OVER");
		fontGameOver.drawString(PREF_DISPLAY_WIDTH / 6,
				PREF_DISPLAY_HEIGHT / 2, "Your Score: " + actualScore);
		fontGameOver.drawString(PREF_DISPLAY_WIDTH / 6,
				PREF_DISPLAY_HEIGHT / 2 + 100,
				"Highscore: " + highscore.getHighscore());
	}

	private void checkColl() {
		boolean collDetected = false;
		outerLoop: for (Enemy enemy : enemies) {
			for (Laser laser : laserShots) {
				if (laser.intersects(enemy)) {

					expl.setX(enemy.getX());
					expl.setY(enemy.getY());
					expl.draw();

					laserShots.remove(laser);
					enemies.remove(enemy);
					actualScore += 10;
					collDetected = true;
					break outerLoop;
				}
			}
		}
		if (collDetected) {
			checkColl();
		}

		for (Laser laser : laserShots) {
			if (laser.intersects(boss)) {
				if (!bossDeath) {
					expl.setX(laser.getX() - 50);
					expl.setY(laser.getY() - 50);
					expl.draw();
				}

				laserShots.remove(laser);
				bossLive--;
				collDetected = true;
				break;
			}
			if (bossLive == 0) {
				bossDeath = true;
			}
		}

		boolean collDetected2 = false;
		outerLoop: for (HomingMissile homingM : homingMissiles) {
			for (Laser laser : laserShots) {
				if (laser.intersects(homingM)) {
					expl.setX(homingM.getX());
					expl.setY(homingM.getY());
					expl.draw();
					laserShots.remove(laser);
					homingMissiles.remove(homingM);
					actualScore += 10;
					collDetected2 = true;
					break outerLoop;
				}
			}
		}
		if (collDetected2) {
			checkColl();
		}

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
				decreaseLive();
			}
		}

		// die schleife ist schneller als die draw2 methode, daher werden die
		// explosionen ganz kurz angezeigt
		boolean collDetected4 = false;
		outerLoop: for (Enemy enemy : enemies) {
			if (player.intersects(enemy)) {
				expl.setX(player.getX());
				expl.setY(player.getY());
				expl.draw2();

				enemies.remove(enemy);

				explosionColl = true;
				i = 20;
				collDetected4 = true;
				decreaseLive();
				break outerLoop;

			}
		}
		if (collDetected4) {
			checkColl();
		}

		for (HomingMissile homingM : homingMissiles) {
			if (player.intersects(homingM)) {
				homingMissiles.remove(homingM);
				explosionColl = true;
				decreaseLive();
			}
		}

		if (player.intersects(boss)) {
			// for (Explosion expl1 : explosions) {
			expl.setX(player.getX());
			expl.setY(player.getY());
			expl.draw2();
			// }
			explosionColl = true;
			decreaseLive();

		}

		boolean collDetected3 = false;
		outerLoop: for (EnemyLaser enemyLaser : enemyLaserShots) {

			if (enemyLaser.intersects(player)) {

				// for (Explosion expl1 : explosions) {
				expl.setX(player.getX());
				expl.setY(player.getY());
				expl.draw2();

				// }
				enemyLaserShots.remove(enemyLaser);
				explosionColl = true;
				collDetected3 = true;
				decreaseLive();
				break outerLoop;
			}

		}
		if (collDetected3) {
			checkColl();
		}

	}

	private void decreaseLive() {
		liveCounter--;
		i = 20;
	}

	private void checkAlive() {
		if (liveCounter <= 0) {
			playerAlive = false;
			// running = false;
			// counterGameOver = 100;
		}
	}

	public void homing() {
		if (!explosionColl) {
			int threshold = 1;
			for (HomingMissile homingM : homingMissiles) {
				// System.out.println(player.getX()-homingM.getX());
				if (homingM.getX() > player.getX() + threshold) {
					// System.out.println("test");
					homingM.setDX(-0.1);
				} else if (homingM.getX() < player.getX() - threshold) {
					// System.out.println("test");
					homingM.setDX(+0.1);
				} else {
					homingM.setDX(0);
				}
				if (homingM.getY() > player.getY() + threshold) {
					// System.out.println("test");
					homingM.setDY(-0.1);
				} else if (homingM.getY() < player.getY() - threshold) {
					// System.out.println("test");
					homingM.setDY(+0.1);
				} else {
					homingM.setDY(0);
				}
				// homingM.setX(player.getX() - homingM.getX());
				// homingM.setY(player.getY() - homingM.getY());
			}
		}
	}

	public static void main(String[] args) {
		new Main2();
	}
}