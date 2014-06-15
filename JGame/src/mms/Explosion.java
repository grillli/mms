package mms;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
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

public class Explosion extends AbstractMoveableEntity {
	private Texture expl1 = null;
	private Texture expl2 = null;
	private Texture expl3 = null;
	private Texture expl4 = null;
	private Texture expl5 = null;
	private Texture expl6 = null;
	private Texture expl7 = null;
	private Texture expl8 = null;
	private Texture expl9 = null;
	private Texture expl10 = null;
	private Texture expl11 = null;
	private Texture expl12 = null;
	private Texture expl13 = null;
	private Texture expl14 = null;

	private boolean check = true; 
	public static final int countExplosion = 13; 
	private int i;
	
	public Explosion(double x, double y, double width, double height) {
		super(x, y, width, height);
		try {
			this.expl1 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion1.png")));
			this.expl2 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion2.png")));
			this.expl3 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion3.png")));
			this.expl4 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion4.png")));
			this.expl5 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion5.png")));
			this.expl6 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion6.png")));
			this.expl7 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion7.png")));
			this.expl8 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion8.png")));
			this.expl9 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion9.png")));
			this.expl10 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion10.png")));
			this.expl11 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion11.png")));
			this.expl12 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion12.png")));
			this.expl13 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion13.png")));
			this.expl14 = TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/explosion14.png")));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		expl1.bind();
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
		
			if (i < 3) {
				expl1.bind();
			} else if (i < 6) {
				expl2.bind();
			} else if (i < 9) {
				expl3.bind();
			} else if (i < 12) {
				expl4.bind();
			} else if (i < 15) {
				expl5.bind();
			} else if (i <18) {
				expl6.bind();
			} else if (i < 21) {
				expl7.bind();
			} else if(i < 24){
				expl8.bind();
					
			}else if (i < 26){
				expl9.bind();
			}else if(i < 28){
				expl10.bind();
			}else if(i < 30){
				expl11.bind();
			}else if(i< 32){
				expl12.bind();
			}else if (i < 34){
				expl13.bind();
			}else{
				expl14.bind(); 
				check = false; 

			}
			
		
		if(check){
			i++;

		}
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