package flappyBird;

import entities.AbstractMoveableEntity;
import static org.lwjgl.opengl.GL11.*;

public class Obstacle extends AbstractMoveableEntity {

	public Obstacle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public void draw() {
		glColor3f(1f, 0, 0);
		glRectd(x, y, x + width, y + height);
	}

}
