package mms;


import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.AbstractMoveableEntity;
import entities.Entity;
	
public class EnemyLaser extends AbstractMoveableEntity{
	private Texture texture = null; 
	
	public EnemyLaser(double x, double y, double width, double height) {
		super(x, y, width, height);
		try{
			this.texture = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/laser2.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean intersects(Entity other) {
		hitbox.setBounds((int)x, (int)y , (int) width, (int) height);
		return hitbox.intersects(other.getX(), other.getY(), (x > other.getWidtH()) ? other.getWidtH() - other.getWidtH()/2 : other.getWidtH(),
				other.getHeight());
	}
	
	@Override
	public void draw() {
		texture.bind();
		glLoadIdentity();
//		glRotatef(90, 0, 1, 0);
		glTranslated(x, y, 0);
		glBegin(GL_QUADS);
//		glBegin(G_)
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f((float)width, 0);
		glTexCoord2f(1, 1);
		glVertex2f((float)width, (float)height);
		glTexCoord2f(0, 1);
		glVertex2f(0, (float)height);
		glEnd();
		glLoadIdentity();	
		
//		glColor3f(0, 1, 0);
//		glBegin(GL_QUADS);
//		glVertex2d(x, y);
//		glVertex2d(x + width, y);
//		glVertex2d(x + width, y + height);
//		glVertex2d(x, y + height);
//		glEnd();
	}

}

