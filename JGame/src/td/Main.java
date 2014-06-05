package td;

import static org.lwjgl.opengl.GL11.*;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Main {

	private int selector_x = 0, selector_y = 0;
	// private Enemy enemy;
	private long lastFrame;
	// private LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	private LinkedList<Enemy> enemies0 = new LinkedList<Enemy>();
	private LinkedList<Enemy> enemies1 = new LinkedList<Enemy>();
	private LinkedList<Enemy> enemies2 = new LinkedList<Enemy>();
	private LinkedList<Enemy> enemies3 = new LinkedList<Enemy>();
	private LinkedList<Enemy> enemies4 = new LinkedList<Enemy>();

	private LinkedList<Bullet> bullets = new LinkedList<Bullet>();

	// private LinkedList<TowerStd> towerStds = new LinkedList<TowerStd>();
	private LinkedList<TowerStd> towerStds0 = new LinkedList<TowerStd>();
	private LinkedList<TowerStd> towerStds1 = new LinkedList<TowerStd>();
	private LinkedList<TowerStd> towerStds2 = new LinkedList<TowerStd>();
	private LinkedList<TowerStd> towerStds3 = new LinkedList<TowerStd>();
	private LinkedList<TowerStd> towerStds4 = new LinkedList<TowerStd>();

	// private LinkedList<TowerIncome> towerInc = new LinkedList<TowerIncome>();
	private LinkedList<TowerIncome> towerInc0 = new LinkedList<TowerIncome>();
	private LinkedList<TowerIncome> towerInc1 = new LinkedList<TowerIncome>();
	private LinkedList<TowerIncome> towerInc2 = new LinkedList<TowerIncome>();
	private LinkedList<TowerIncome> towerInc3 = new LinkedList<TowerIncome>();
	private LinkedList<TowerIncome> towerInc4 = new LinkedList<TowerIncome>();

	private static int enemyCounter = 0;
	private static int timer = 720;

	// private int squareX;
	// private int squareY;
	// private int realTowerPlaceX;
	// private int realTowerPlaceY;

	// private int money = 100;
	private int money = 10000;

	private boolean[][] towerThere = new boolean[6][5];

	private long lastFPS = getTime();
	private int fps;

	private static UnicodeFont font;
	private static UnicodeFont font2;
	private static DecimalFormat formatter = new DecimalFormat("#.##");

	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			// Display.setDisplayMode(new DisplayMode(1024, 720));
			Display.setTitle("td");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		// glOrtho(0, 640, 480, 0, 1, -1);
		glOrtho(0, 800, 600, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		Background bg = new Background();
		TowerBar towerBar = new TowerBar();
		setUpFonts();
		setUpTimer();

		while (!Display.isCloseRequested()) {
			// Render
			updateFPS();
			Random rng = new Random();
			setUpEnemies(rng.nextFloat());

			glClear(GL_COLOR_BUFFER_BIT);
			bg.draw();
			towerBar.draw();
			logic(getDelta());

			drawSelectionBox();
			input();

			towerShots();

			fonts();

			// checkCollisions();
			// checkCollEnemies();

			incomeTimer();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private void fonts() {
		font.drawString(10, 10, String.valueOf(money));
		font2.drawString(5, 503, "\"I\": 50");
		font2.drawString(100, 503, "\"T\": 100");
	}

	private static void setUpFonts() {
		// java.awt.Font awtFont = new java.awt.Font("Times New Roman",
		// java.awt.Font.BOLD, 36);
		java.awt.Font awtFont = new java.awt.Font("Comic Sans MS",
				java.awt.Font.BOLD, 36);
		java.awt.Font awtFont2 = new java.awt.Font("Comic Sans MS",
				java.awt.Font.BOLD, 18);
		// java.awt.Font awtFont = new java.awt.Font("Segoe Script",
		// java.awt.Font.BOLD, 36);
		font = new UnicodeFont(awtFont);
		font2 = new UnicodeFont(awtFont2);
		font.getEffects().add(new ColorEffect(java.awt.Color.YELLOW));
		font2.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
		font.addAsciiGlyphs();
		font2.addAsciiGlyphs();
		try {
			font.loadGlyphs();
			font2.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps + towerInc0.size()
					+ towerInc1.size() + towerInc2.size() + towerInc3.size()
					+ towerInc4.size());
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	private void incomeTimer() {
		setIncomeTimer(towerInc0);
		setIncomeTimer(towerInc1);
		setIncomeTimer(towerInc2);
		setIncomeTimer(towerInc3);
		setIncomeTimer(towerInc4);
	}

	private void setIncomeTimer(LinkedList<TowerIncome> towerIncList) {
		for (TowerIncome ti : towerIncList) {
			if (ti.getCountdown() <= 0) {
				money += 25;
				ti.setCountdown(500);
			}
			ti.setCountdown(ti.getCountdown() - 1);
		}
	}

	private void towerShots() {
		setTowerShots(towerStds0);
		setTowerShots(towerStds1);
		setTowerShots(towerStds2);
		setTowerShots(towerStds3);
		setTowerShots(towerStds4);
	}

	// private void checkCollEnemies() {
	// for (Enemy e : enemies) {
	// if (e.getX() < 400) {
	// for (TowerIncome ti : towerInc) {
	// if (e.intersects(ti)) {
	// towerThere[getTowerPlaceX((int) ti.getX())][getTowerPlaceY((int) ti
	// .getY())] = false;
	// towerInc.remove(ti);
	// }
	// }
	// for (TowerStd t : towerStds) {
	// if (e.intersects(t)) {
	// towerThere[getTowerPlaceX((int) t.getX())][getTowerPlaceY((int) t
	// .getY())] = false;
	// towerStds.remove(t);
	// }
	// }
	// }
	// }
	// }

	// private void checkCollisions() {
	// one: for (Enemy e : enemies0) {
	// for (Bullet b : bullets) {
	// if (b.getX() > 630) {
	// bullets.remove(b);
	// // System.out.println("ausser range");
	// break;
	// } else if (e.intersects(b)) {
	// enemies.remove(e);
	// bullets.remove(b);
	// // System.out.println("down");
	// break one;
	// }
	// }
	// }
	// }

	private void setTowerShots(LinkedList<TowerStd> towerStdList) {
		int bulletSize = 10;
		for (TowerStd t : towerStdList) {
			if (t.getCountdown() <= 0) {
				Bullet bullet = new Bullet(t.getX(), t.getY() + (t.getHeight())
						/ 2 - bulletSize / 2, bulletSize, bulletSize);
				bullet.setDX(0.4);
				bullets.add(bullet);
				t.setCountdown(100);
			}
			t.setCountdown(t.getCountdown() - 1);
		}
	}

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private void logic(int delta) {
		drawBullets(delta);
		drawTowers();
		drawEnemies(delta);
	}

	private void drawEnemies(int delta) {
		drawEnemyList(delta, enemies0);
		drawEnemyList(delta, enemies1);
		drawEnemyList(delta, enemies2);
		drawEnemyList(delta, enemies3);
		drawEnemyList(delta, enemies4);
	}

	private void drawEnemyList(int delta, LinkedList<Enemy> enemyList) {
		for (Enemy e : enemyList) {
			e.update(delta);
			e.draw();
		}
	}

	private void drawTowers() {
		drawTowerStd();
		drawTowerInc();
	}

	private void drawTowerInc() {
		drawTowerIncList(towerInc0);
		drawTowerIncList(towerInc1);
		drawTowerIncList(towerInc2);
		drawTowerIncList(towerInc3);
		drawTowerIncList(towerInc4);
	}

	private void drawTowerIncList(LinkedList<TowerIncome> towerIncList) {
		for (TowerIncome ti : towerIncList) {
			ti.draw();
		}
	}

	private void drawTowerStd() {
		drawTowerStdList(towerStds0);
		drawTowerStdList(towerStds1);
		drawTowerStdList(towerStds2);
		drawTowerStdList(towerStds3);
		drawTowerStdList(towerStds4);
	}

	private void drawTowerStdList(LinkedList<TowerStd> towerStdList) {
		for (TowerStd t : towerStdList) {
			t.draw();
		}
	}

	private void drawBullets(int delta) {
		for (Bullet b : bullets) {
			b.update(delta);
			b.draw();
		}
	}

	private long getTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}

	private void setUpEnemies(float rnd) {

		// if (enemyCounter < 1) {
		// if (enemyCounter < 6) {
		if (enemyCounter < 20) {
			int yAxe = 0;
			if (rnd < 0.2) {
				yAxe = 20;
				Enemy enemy = new Enemy(timer, yAxe, 64, 64);
				enemy.setDX(-.03);
				enemies0.add(enemy);
			} else if (rnd < 0.4) {
				yAxe = 120;
				Enemy enemy = new Enemy(timer, yAxe, 64, 64);
				enemy.setDX(-.03);
				enemies1.add(enemy);
			} else if (rnd < 0.6) {
				yAxe = 230;
				Enemy enemy = new Enemy(timer, yAxe, 64, 64);
				enemy.setDX(-.03);
				enemies2.add(enemy);
			} else if (rnd < 0.8) {
				yAxe = 330;
				Enemy enemy = new Enemy(timer, yAxe, 64, 64);
				enemy.setDX(-.03);
				enemies3.add(enemy);
			} else {
				yAxe = 420;
				Enemy enemy = new Enemy(timer, yAxe, 64, 64);
				enemy.setDX(-.03);
				enemies4.add(enemy);
			}
			// timer += 180;
			timer += 45;
			enemyCounter++;
		}
	}

	private void drawSelectionBox() {
		// int x = selector_x;
		// int y = selector_y;
		// int x2 = x + 64;
		// int y2 = y + 64;
		// // glBindTexture(GL_TEXTURE_2D, 0);
		// glColor4f(1f, 1f, 1f, 0.5f);
		// glBegin(GL_QUADS);
		// glVertex2i(x, y);
		// glVertex2i(x2, y);
		// glVertex2i(x2, y2);
		// glVertex2i(x, y2);
		// glEnd();
		// glColor4f(1f, 1f, 1f, 1f);

		int x = getXSquare(selector_x);
		int y = getYSquare(selector_y);
		int x2 = x + 64;
		int y2 = y + 64;
		// glBindTexture(GL_TEXTURE_2D, 0);
		glColor4f(1f, 1f, 1f, 0.5f);
		glBegin(GL_QUADS);
		glVertex2i(x, y);
		glVertex2i(x2, y);
		glVertex2i(x2, y2);
		glVertex2i(x, y2);
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
	}

	private void input() {
		int mouseX = Mouse.getX();
		int mouseY = 600 - Mouse.getY() - 1;
		boolean mouseClicked = Mouse.isButtonDown(0);
		selector_x = Math.round(mouseX / 1.3f);
		selector_y = Math.round(mouseY);// / 1.3f);
		// if (mouseClicked) {
		while (Keyboard.next()) {
			// if (Keyboard.getEventKey() == Keyboard.KEY_SPACE
			// && Keyboard.getEventKeyState()) {
			// Bullet bullet = new Bullet(selector_x, selector_y, 10, 10);
			// bullet.setDX(0.7);
			// bullets.add(bullet);
			// // bullet.update(17);
			// // bullet.draw();
			// }
			if (Keyboard.getEventKey() == Keyboard.KEY_T
					&& Keyboard.getEventKeyState()) {
				createStdTower(selector_x, selector_y, 100);
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_I
					&& Keyboard.getEventKeyState()) {
				createIncTower(selector_x, selector_y, 50);
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	private void createIncTower(int selector_x2, int selector_y2, int cost) {
		int squareX = getXSquare(selector_x2);
		int squareY = getYSquare(selector_y2);
		int realTowerPlaceX = getTowerPlaceX(squareX);
		int realTowerPlaceY = getTowerPlaceY(squareY);
		if (!towerThere[realTowerPlaceX][realTowerPlaceY] && money >= cost) {
			TowerIncome tower = new TowerIncome(getXSquare(selector_x),
					getYSquare(selector_y), 64, 64);
			switch (realTowerPlaceY) {
			case 0:
				towerInc0.add(tower);
				break;
			case 1:
				towerInc1.add(tower);
				break;
			case 2:
				towerInc2.add(tower);
				break;
			case 3:
				towerInc3.add(tower);
				break;
			case 4:
				towerInc4.add(tower);
				break;
			}
			towerThere[realTowerPlaceX][realTowerPlaceY] = true;
			money -= cost;
		} else if (towerThere[realTowerPlaceX][realTowerPlaceY]) {
			System.out.println("do steht scho a turm");

		} else {
			System.out.println("zu wenig money");
		}
	}

	private void createStdTower(int selector_x2, int selector_y2, int cost) {
		int squareX = getXSquare(selector_x2);
		int squareY = getYSquare(selector_y2);
		int realTowerPlaceX = getTowerPlaceX(squareX);
		int realTowerPlaceY = getTowerPlaceY(squareY);
		if (!towerThere[realTowerPlaceX][realTowerPlaceY] && money >= cost) {
			TowerStd tower = new TowerStd(getXSquare(selector_x),
					getYSquare(selector_y), 64, 64);
			switch (realTowerPlaceY) {
			case 0:
				towerStds0.add(tower);
				break;
			case 1:
				towerStds1.add(tower);
				break;
			case 2:
				towerStds2.add(tower);
				break;
			case 3:
				towerStds3.add(tower);
				break;
			case 4:
				towerStds4.add(tower);
				break;
			}
			towerThere[realTowerPlaceX][realTowerPlaceY] = true;
			money -= cost;
		} else if (towerThere[realTowerPlaceX][realTowerPlaceY]) {
			System.out.println("do steht scho a turm");

		} else {
			System.out.println("zu wenig money");
		}
	}

	private int getTowerPlaceX(int squareX) {
		switch (squareX) {
		case 5:
			return 0;
		case 85:
			return 1;
		case 170:
			return 2;
		case 260:
			return 3;
		case 345:
			return 4;
		case 430:
			return 5;
		default:
			System.out.println("do hods wos");
		}
		return 0;
	}

	private int getTowerPlaceY(int squareY) {
		switch (squareY) {
		case 20:
			return 0;
		case 120:
			return 1;
		case 230:
			return 2;
		case 330:
			return 3;
		case 420:
			return 4;
		default:
			System.out.println("do hods wos");
		}
		return 0;
	}

	private int getXSquare(int MouseCoordX) {
		if (MouseCoordX < 64) {
			return 5;
		} else if (MouseCoordX < 123) {
			return 85;
		} else if (MouseCoordX < 192) {
			return 170;
		} else if (MouseCoordX < 258) {
			return 260;
		} else if (MouseCoordX < 330) {
			return 345;
		} else {
			return 430;
		}
	}

	private int getYSquare(int MouseCoordY) {
		if (MouseCoordY < 100) {
			return 20;
		} else if (MouseCoordY < 200) {
			return 120;
		} else if (MouseCoordY < 306) {
			return 230;
		} else if (MouseCoordY < 395) {
			return 330;
		} else {
			return 420;
		}
	}

	public static void main(String[] args) {
		new Main();

	}

}