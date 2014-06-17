package mms;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.AbstractMoveableEntity;

public class Gimmick extends AbstractMoveableEntity {
	private Texture star1 = null;
	private Texture star2 = null;
	private Texture star3 = null;
	private Texture star4 = null;
	private int i;

	public Gimmick(double x, double y, double width, double height) {
		super(x, y, width, height);
		i = 0;
		try {
			this.star1 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/star1.png")));
			this.star2 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/star2.png")));
			this.star3 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/star3.png")));
			this.star4 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/star4.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		if (i < 8) {
			star1.bind();
		} else if (i < 16) {
			star2.bind();
		} else if (i < 24) {
			star3.bind();
		} else {
			star4.bind();
			if (i == 32) {
				i = -1;
			}
		}
		i++;
		glLoadIdentity();
		glTranslated(x, y, 0);
		glBegin(GL_QUADS);
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

	}
}
