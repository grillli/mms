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
import entities.Entity;

public class Player extends AbstractMoveableEntity {

	private Texture texture = null;

	public Player(double x, double y, double width, double height) {
		super(x, y, width, height);
		try {
			this.texture = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/raumschiff.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean intersects(Entity other) {
		if (other instanceof Boss) {
			hitbox.setBounds((int) (x < other.getX() ? x - width / 2 : x),
					(int) (y), (int) width, (int) height);
			return hitbox.intersects(other.getX(), other.getY(),
					other.getWidtH() - 400, other.getHeight() - 100);
		} else if (other instanceof HomingMissile) {

			hitbox.setBounds((int) ((x < other.getX()) ? x - width / 2 : x
					+ width), (int) (y + height), (int) width,
					(int) (height - height / 2));
			return hitbox.intersects(other.getX(), other.getY(),
					other.getWidtH(), other.getHeight());
		} else {
			hitbox.setBounds((int) ((x < other.getX()) ? x - width / 2 : x
					+ width / 3), (int) (y + height / 5), (int) width,
					(int) (height - height / 2));
			return hitbox.intersects(other.getX(), other.getY(),
					other.getWidtH(), other.getHeight());
		}
	}

	@Override
	public void draw() {
		texture.bind();
		glLoadIdentity();
		glTranslated(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f((float) this.width, 0);
		glTexCoord2f(1, 1);
		glVertex2f((float) this.width, (float) this.height);
		glTexCoord2f(0, 1);
		glVertex2f(0, (float) this.height);
		glEnd();
		glLoadIdentity();
	}
}