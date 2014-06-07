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
	Player player;
	Enemy enemy;
	Laser laser;
	// TrueTypeFont font;

	int backgroundY = 0;

	private Audio audioEffect;

	private static UnicodeFont font;
	private static DecimalFormat formatter = new DecimalFormat("#.##");

	private Background[] backgroundArray = new Background[2];
	private Background thisBG;

	List<Laser> laserShots = new LinkedList<Laser>();
	List<Background> backgroundLoop = new LinkedList<Background>();
	int x = 0;

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
		thisBG=new Background(0, -1000, 1800, 3000);
		backgroundLoop.add(thisBG);
		
		enemy = new Enemy(10, 10, 50, 50);
		enemy.setDY(0.03);
		enemy.setDX(0.05);

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
			for (Background bg : backgroundLoop) {
				bg.draw();
				bg.update(delta);
				bg.setDY(0.1);
			}

			player.draw();
			player.update(delta);

			fonts();

			enemy.draw();
			enemy.update(delta);
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
			 System.out.println(backgroundY);

			if (backgroundY > x) {
//				backgroundLoop.add(new Background(0, -1000, 1800, 3000));
				thisBG=new Background(0, -2200, 1800, 3000);
				backgroundLoop.add(thisBG);
//				x += 1000;
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

			input();
			input2();

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
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			player.setY(player.getY() - 6);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			player.setY(player.getY() + 6);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			player.setX(player.getX() - 6);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			player.setX(player.getX() + 6);
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
			if (Keyboard.getEventKey() == Keyboard.KEY_SPACE
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
		font.drawString(10, 10, "TEXT TEST");
	}

	public static void main(String[] args) {
		new Main();
	}
}
