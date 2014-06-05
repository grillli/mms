package td;

import static org.lwjgl.opengl.GL11.*;

public class TowerBar {

	public TowerBar() {

	}

	public void draw() {
		int numberTowers = 2;
		float x = 0;
		float y = 507;
		for (int i = 0; i < numberTowers; i++) {
			glBegin(GL_QUADS);
			if (i == 0) {
				glColor3f(0, 1f, 0);
			} else if (i == 1) {
				glColor3f(1f, 0, 0);
			}
			glVertex2f(x, y);
			x += 93;
			glVertex2f(x, y);
			y += 93;
			glVertex2f(x, y);
			x -= 93;
			glVertex2f(x, y);
			glEnd();
			x += 93;
			y -= 93;
		}
	}
}
