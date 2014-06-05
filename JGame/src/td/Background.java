package td;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Background {
	Texture texture = null;

	public Background() {
		try {
			this.texture = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/background2.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		// float x = 640 * 1.6f;
		// float y = 480 * 1.5f;
//		float x = 800*1.28f;
//		float y = 600*1.2f;
//		float x = 800f;
//		float y = 600f;
		float x = 800*1.6f;
		float y = 600*1.2f;
		texture.bind();
		glLoadIdentity();
		glTranslatef(0, 0, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(x, 0);
		glTexCoord2f(1, 1);
		glVertex2f(x, y);
		glTexCoord2f(0, 1);
		glVertex2f(0, y);
		glEnd();
		glLoadIdentity();
	}
}
