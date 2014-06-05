package flappyBird;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Display;

public class Main {
	private long lastFrame;
	Bird bird;
	Obstacle obst1, obst2, obst3, obst4, obst5, obst6,obst7,obst8,obst9,obst10;
	int delta;
	int birdInt = 0;

	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(1024, 720));
			Display.setTitle("Flappy Bird");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		// glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1024, 720, 0, 1, -1);
		// glMatrixMode(GL_MODELVIEW);
		// glEnable(GL_TEXTURE_2D);
		// glEnable(GL_BLEND);
		// glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		bird = new Bird(50, 50, 50, 50);
		setUpTimer();
		obst1 = new Obstacle(1000, 0, 50, 400);
		obst2 = new Obstacle(1000, 400 + 50 * 4, 50, 400);
		obst1.setDX(-.2);
		obst2.setDX(-.2);
		obst3 = new Obstacle(1400, 0, 50, 200);
		obst4 = new Obstacle(1400, 200 + 50 * 4, 50, 400);
		obst3.setDX(-.2);
		obst4.setDX(-.2);
		obst5 = new Obstacle(1800, 0, 50, 300);
		obst6 = new Obstacle(1800, 300 + 50 * 4, 50, 400);
		obst5.setDX(-.2);
		obst6.setDX(-.2);
		obst7 = new Obstacle(2200, 0, 50, 100);
		obst8 = new Obstacle(2200, 100 + 50 * 4, 50, 600);
		obst7.setDX(-.2);
		obst8.setDX(-.2);
		obst9 = new Obstacle(2600, 0, 50, 500);
		obst10 = new Obstacle(2600, 500 + 50 * 4, 50, 400);
		obst9.setDX(-.2);
		obst10.setDX(-.2);

		while (!Display.isCloseRequested()) {
			delta = getDelta();
			glClear(GL_COLOR_BUFFER_BIT);
			bird.update(delta);
			if (birdInt == 20) {
				birdInt = 0;
			}
			if (birdInt < 5) {
				bird.draw();
				birdInt++;
			} else if (birdInt < 10) {
				bird.draw2();
				birdInt++;
			} else if (birdInt < 15) {
				bird.draw3();
				birdInt++;
			} else if (birdInt >= 15) {
				bird.draw4();
				birdInt++;
			}

			obst1.update(delta);
			obst1.draw();
			obst2.update(delta);
			obst2.draw();
			obst3.update(delta);
			obst3.draw();
			obst4.update(delta);
			obst4.draw();
			obst5.update(delta);
			obst5.draw();
			obst6.update(delta);
			obst6.draw();
			obst7.update(delta);
			obst7.draw();
			obst8.update(delta);
			obst8.draw();
			obst9.update(delta);
			obst9.draw();
			obst10.update(delta);
			obst10.draw();
			Display.update();
			Display.sync(60);
			checkDown();
			input();
		}
		Display.destroy();
		System.exit(0);
	}

	private void input() {
		while (Keyboard.next()) {

			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				Display.destroy();
				System.exit(0);
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_UP
					&& Keyboard.getEventKeyState()) {
				bird.setY(bird.getY() - 50 * 2);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_DOWN
					&& Keyboard.getEventKeyState()) {
				bird.setY(bird.getY() + 50/2);
			}
		}
	}

	private void checkDown() {
		if (bird.getY() >= 650) {
			bird.setDY(0);
		} else {
			bird.setDY(.1);
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

	public static void main(String[] args) {
		new Main();
	}
}
