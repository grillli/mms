package examples;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class TimerDemo {

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

	public TimerDemo() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Seas de Wadln");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		int x = 100;
		int y = 100;
		int dx = 1;
		int dy = 1;

		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		lastFrame = getTime();

		while (!Display.isCloseRequested()) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			int delta = getDelta();
			x += delta * dx * 0.1;
			y += delta * dy * 0.1;

			glRecti(x, y, x + 30, y + 30);

			glBegin(GL_QUADS);
			glVertex2i(400, 400);// Upper left
			glVertex2i(450, 400);// Upper right
			glVertex2i(450, 450);// Bottom right
			glVertex2i(400, 450);// Bottom left
			glEnd();

			glBegin(GL_LINES);
			glVertex2i(100, 100);
			glVertex2i(200, 200);
			glEnd();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

	public static void main(String[] args) {
		new TimerDemo();

	}
}
