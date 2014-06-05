package pong;

import java.util.Random;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import entities.AbstractMoveableEntity;
import static org.lwjgl.opengl.GL11.*;

public class PongGame {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	private boolean isRunning = true;
	private Ball ball;
	private Bat bat;

	public PongGame() {
		setUpDisplay();
		setUpOpenGL();
		setUpEntities();
		setUpTimer();
		while (isRunning) {
			render();
			logic(getDelta());
			input();
			Display.update();
			Display.sync(60);
			checkEND();
			checkTopBot();
			if (Display.isCloseRequested()) {
				isRunning = false;
			}
		}
		Display.destroy();
	}

	private void checkTopBot() {
		if (ball.getY() <= 0) {
			ball.setDY(.3);
		}
		if (ball.getY() >= HEIGHT) {
			ball.setDY(-.3);
		}
	}

	private void checkEND() {
		if (ball.getX() >= WIDTH) {
			ball.setDX(-.3);
			Random randomGenerator = new Random();
			// float des = randomGenerator.nextFloat();
			float plusMinus = randomGenerator.nextFloat();
			// float min = 0.1f;
			// float max = 0.3f;
			// des = min + des * ((max - min));

			if (plusMinus >= 0.5) {
				// ball.setDY(des);
				ball.setDY(.3);
			} else {
				// ball.setDY(-des);
				ball.setDY(-.3);
			}
		}
	}

	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			bat.setDY(-.5);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			bat.setDY(.5);
		} else {
			bat.setDY(0);
		}
	}

	private long lastFrame;

	private long getTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}

	private void logic(int delta) {
		ball.update(delta);
		bat.update(delta);
		if (ball.getX() <= bat.getX() + bat.getWidtH()
				&& ball.getX() >= bat.getX() && ball.getY() >= bat.getY()
				&& ball.getY() <= bat.getY() + bat.getHeight()) {
			ball.setDX(0.3);
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		ball.draw();
		bat.draw();
	}

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private void setUpEntities() {
		bat = new Bat(10, HEIGHT / 2 - 80 / 2, 10, 80);
		ball = new Ball(WIDTH / 2 - 10 / 2, HEIGHT / 2 - 10 / 2, 10, 10);
		ball.setDX(-.1);
	}

	private void setUpOpenGL() {
		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Pong");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private static class Bat extends AbstractMoveableEntity {

		public Bat(double x, double y, double width, double height) {
			super(x, y, width, height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void draw() {
			glRectd(x, y, x + width, y + height);
		}
	}

	private static class Ball extends AbstractMoveableEntity {

		public Ball(double x, double y, double width, double height) {
			super(x, y, width, height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void draw() {
			glRectd(x, y, x + width, y + height);
		}
	}

	public static void main(String[] args) {
		new PongGame();
	}
}
