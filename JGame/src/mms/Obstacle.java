package mms;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.AbstractMoveableEntity;

public class Obstacle extends AbstractMoveableEntity {
	
	private Texture obst1 = null;
	private Texture obst2 = null;
	private Texture obst3 = null;
	private Texture obst4 = null;
	private Texture obst5 = null;
	private Texture obst6 = null;
	private Texture obst7 = null;
	private Texture obst8 = null;
	private int i;

	public Obstacle(double x, double y, double width, double height) {
		super(x, y, width, height);
		i = 0;
		try {
			this.obst1 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle.png")));
			this.obst2 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle2.png")));
			this.obst3 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle3.png")));
			this.obst4 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle4.png")));
			this.obst5 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle5.png")));
			this.obst6 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle6.png")));
			this.obst7 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle7.png")));
			this.obst8 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/obstacle8.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		obst1.bind();
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
	}

	public void draw2() {
		if (i < 8) {
			obst1.bind();
		} else if (i < 16) {
			obst2.bind();
		} else if (i < 24) {
			obst3.bind();
		} else if (i < 40) {
			obst4.bind();
		} else if (i < 48) {
			obst5.bind();
		} else if (i < 56) {
			obst6.bind();
		} else if (i < 64) {
			obst7.bind();
		} else{
			obst8.bind();
	
			if (i == 72) {
				i = -1;
			}
		}
		i++;
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

	}

}