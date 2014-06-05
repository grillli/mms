package examples;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureDemo {

	private Texture wood;

	public TextureDemo() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Seas de Wadln");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		wood = loadTexture("wood");

		// try {
		// Texture texture = TextureLoader.getTexture("PNG",
		// new FileInputStream(new File("res/wood.png")));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);

		while (!Display.isCloseRequested()) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			wood.bind();

			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2i(400, 400);// Upper left
			glTexCoord2f(1, 0);
			glVertex2i(450, 400);// Upper right
			glTexCoord2f(1, 1);
			glVertex2i(450, 450);// Bottom right
			glTexCoord2f(0, 1);
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

	private Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/" + key + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new TextureDemo();

	}
}
