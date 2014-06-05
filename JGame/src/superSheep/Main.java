package superSheep;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

	private Player player;
	private int delta;
	private long lastFrame;
	private int xVec = 0;
	private int yVec = 0;

	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(1024, 720));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glLoadIdentity();
		glOrtho(0, 1024, 720, 0, 1, -1);

		player = new Player(250, 250, 15, 15);
		player.setDX(.1);
		player.setDY(0);

		lastFrame = getTime();

		xVec = (int) (player.getDX() * 100);
		yVec = (int) (player.getDY() * 100);

		delta = getDelta();
		while (!Display.isCloseRequested()) {
//			glClear(GL_COLOR_BUFFER_BIT);
			delta = getDelta();
			player.draw();
			player.update(delta);

			// System.out.println(des);
			// des++;
			// if(des==1200){
			// Display.destroy();
			// System.exit(1);
			// }
			//

			input();
			
			exit();
			//
			// player.setDX(.2);
			// player.setDY(.05);

			// if (player.getDY() >= 0.45 && player.getDY() <= 0.55) {
			// player.setDX(0);
			// }
			// System.out.println(player.getDX() + " == " + player.getDY());
			System.out.println(xVec + ", " + yVec);

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private void exit() {
		while(Keyboard.next()){
			if(Keyboard.getEventKey()==Keyboard.KEY_ESCAPE){
				Display.destroy();
				System.exit(0);
			}
		}
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
		// if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (xVec <= 10 && xVec > 0 && yVec <= 0 && yVec > -10) {
				xVec--;
				yVec--;
				player.setDX(player.getDX() - .01);
				player.setDY(player.getDY() - .01);
			}
			if (xVec <= 0 && xVec > -10 && yVec >= -10 && yVec < 0) {
				xVec--;
				yVec++;
				player.setDX(player.getDX() - .01);
				player.setDY(player.getDY() + .01);
			}
			if (xVec >= -10 && xVec < 0 && yVec >= 0 && yVec < 10) {
				xVec++;
				yVec++;
				player.setDX(player.getDX() + .01);
				player.setDY(player.getDY() + .01);
			}
			if (xVec >= 0 && xVec < 10 && yVec <= 10 && yVec > 0) {
				xVec++;
				yVec--;
				player.setDX(player.getDX() + .01);
				player.setDY(player.getDY() - .01);
			}
		}

		// if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (xVec <= 10 && xVec > 0 && yVec >= 0 && yVec < 10) {
				xVec--;
				yVec++;
				player.setDX(player.getDX() - .01);
				player.setDY(player.getDY() + .01);
			}
			if (xVec <= 0 && xVec > -10 && yVec <= 10 && yVec > 0) {
				xVec--;
				yVec--;
				player.setDX(player.getDX() - .01);
				player.setDY(player.getDY() - .01);
			}
			if (xVec >= -10 && xVec < 0 && yVec <= 0 && yVec > -10) {
				xVec++;
				yVec--;
				player.setDX(player.getDX() + .01);
				player.setDY(player.getDY() - .01);
			}
			if (xVec >= 0 && xVec < 10 && yVec >= -10 && yVec < 0) {
				xVec++;
				yVec++;
				player.setDX(player.getDX() + .01);
				player.setDY(player.getDY() + .01);
			}
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
			Display.destroy();
			System.exit(0);
		}
	}

	// }

	public static void main(String[] args) {
		new Main();
	}
}
