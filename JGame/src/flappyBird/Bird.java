package flappyBird;

import entities.AbstractMoveableEntity;
import static org.lwjgl.opengl.GL11.*;

public class Bird extends AbstractMoveableEntity {
	// private double x, y, width, height;

	public Bird(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public void draw() {
		glColor3f(0, 0, 1f);
		// glRectd(x, y, x + width, y + height);
		glBegin(GL_QUADS);
		glVertex2d(x, y);
		glVertex2d(x + width, y);
		glVertex2d(x + width, y + height);
		glVertex2d(x, y + height);
		glEnd();
	}

	public void draw2() {
		glColor3f(0, 0, 1f);
		// glRectd(x, y, x + width, y + height);
		glBegin(GL_QUADS);
		glVertex2d(x + width / 3, y - height / 4);
		glVertex2d(x + width + width / 4, y + height / 3);
		glVertex2d(x + width - width / 3, y + height + height / 4);
		glVertex2d(x - width / 4, y + height - height / 3);
		glEnd();
	}

	public void draw3() {
		glColor3f(0, 0, 1f);
		// glRectd(x, y, x + width, y + height);
		glBegin(GL_QUADS);
		glVertex2d(x + width / 2, y - height / 3);
		glVertex2d(x + width + width / 3, y + height / 2);
		glVertex2d(x + width / 2, y + height + height / 3);
		glVertex2d(x - width / 3, y + height / 2);
		glEnd();
	}

	public void draw4() {
		glColor3f(0, 0, 1f);
		// glRectd(x, y, x + width, y + height);
		glBegin(GL_QUADS);
		glVertex2d(x + ((3 * width) / 4), y - ((height) / 4));
		glVertex2d(x + width + ((width) / 4), y + ((3 * height) / 4));
		glVertex2d(x + ((width) / 4), y + height + ((height) / 4));
		glVertex2d(x - ((width) / 4), y + ((height) / 4));
		glEnd();
	}

}
