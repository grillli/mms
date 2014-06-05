package td;

import entities.AbstractMoveableEntity;
import static org.lwjgl.opengl.GL11.*;

public class Bullet extends AbstractMoveableEntity {

	public Bullet(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public void draw() {
		glColor3f(1f, 1f, 0);
		glRectd(x, y, x + width, y + height);
		glColor3f(1f, 1f, 1f);
	}

}