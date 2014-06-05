package minecraft2d;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Player {
	private Texture texture = null;
	private float x;
	private float y;
	private int PLAYER_SIZE = 32;

	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		try {
			this.texture = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/player.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		texture.bind();
		glLoadIdentity();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(PLAYER_SIZE, 0);
		glTexCoord2f(1, 1);
		glVertex2f(PLAYER_SIZE, PLAYER_SIZE);
		glTexCoord2f(0, 1);
		glVertex2f(0, PLAYER_SIZE);
		glEnd();
		glLoadIdentity();
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
