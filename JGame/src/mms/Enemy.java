package mms;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.AbstractMoveableEntity;

public class Enemy extends AbstractMoveableEntity {
	private Texture texture = null;

	public Enemy(double x, double y, double width, double height, int textureID) {
		super(x, y, width, height);
		try {
			switch (textureID) {
			case 0:
				this.texture = TextureLoader.getTexture("PNG",
						new FileInputStream(new File("res/enemy.png")));
				break;
			case 1:
				this.texture = TextureLoader.getTexture("PNG",
						new FileInputStream(new File("res/enemy1.png")));
				break;
			case 2:
				this.texture = TextureLoader.getTexture("PNG",
						new FileInputStream(new File("res/enemy2.png")));
				break;
			case 3:
				this.texture = TextureLoader.getTexture("PNG",
						new FileInputStream(new File("res/enemy3.png")));
				break;
			case 4:
				this.texture = TextureLoader.getTexture("PNG",
						new FileInputStream(new File("res/enemy4.png")));
				break;
			case 5:
				this.texture = TextureLoader.getTexture("PNG",
						new FileInputStream(new File("res/enemy5.png")));
				break;
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		texture.bind();
		glLoadIdentity();
		glTranslated(x, y, 0);
		glBegin(GL_QUADS);
		// glBegin(G_)
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f((float) width, 0);
		glTexCoord2f(1, 1);
		glVertex2f((float) width, (float) height);
		glTexCoord2f(0, 1);
		glVertex2f(0, (float) height);
		glEnd();
		glLoadIdentity();

		// glColor3f(1, 0, 0);
		// glBegin(GL_QUADS);
		// glVertex2d(x, y);
		// glVertex2d(x + width, y);
		// glVertex2d(x + width, y + height);
		// glVertex2d(x, y + height);
		// glEnd();
	}
}