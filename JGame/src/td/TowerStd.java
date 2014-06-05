package td;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRectd;
import entities.AbstractEntity;

public class TowerStd extends AbstractEntity {

	private int countdown = 100;

	public TowerStd(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public void draw() {
		glColor3f(1f, 0, 0);
		glRectd(x, y, x + width, y + height);
		glColor3f(1f, 1f, 1f);
	}

	@Override
	public void update(int delta) {
	}

	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}

	public int getCountdown() {
		return countdown;
	}

}
