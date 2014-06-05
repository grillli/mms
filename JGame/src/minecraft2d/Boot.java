package minecraft2d;

import static org.lwjgl.opengl.GL11.*;

import java.awt.RenderingHints.Key;
import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class Boot {
	private BlockGrid grid;
	private BlockType selection = BlockType.STONE;
	private int selector_x = 0, selector_y = 0;
	private boolean mouseEnabled = true;
	private Player player;
	private boolean jump = false;

	public Boot() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Minecraft2d");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// Block grass = new Block(BlockType.GRASS, 10, 10);
		grid = new BlockGrid();
		for (int i = 0; i < 20; i++) {
			for (int j = 7; j < 15; j++) {
				if (j == 7) {
					grid.setAt(i, j, BlockType.GRASS);
				} else {
					grid.setAt(i, j, BlockType.DIRT);
				}
			}
		}

		player = new Player(60, 190);

		// Initializing code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested()) {
			// Render

			glClear(GL_COLOR_BUFFER_BIT);

			input();
			// grass.draw();
			grid.draw();
			// player.draw();
			drawPlayer();
			drawSelectionBox();

			playerFall();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private void drawPlayer() {

		player.draw();
	}

	private void playerFall() {
		if (grid.getAt((int) player.getX() / World.BLOCK_SIZE,
				(int) player.getY() / World.BLOCK_SIZE + 2).getType() == BlockType.AIR
				&& !jump) {
			player.setY((int) player.getY() + World.BLOCK_SIZE);
		}
		// while (grid.getAt((int) player.getX() / World.BLOCK_SIZE,
		// (int) player.getY() / World.BLOCK_SIZE + 2).getType() ==
		// BlockType.AIR) {
		// player.setY((int) player.getY() + World.BLOCK_SIZE);
		// }
	}

	private void drawSelectionBox() {
		int x = selector_x * World.BLOCK_SIZE;
		int y = selector_y * World.BLOCK_SIZE;
		int x2 = x + World.BLOCK_SIZE;
		int y2 = y + World.BLOCK_SIZE;
		if (grid.getAt(selector_x, selector_y).getType() != BlockType.AIR
				|| selection == BlockType.AIR) {
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1f, 1f, 1f, 0.5f);
			glBegin(GL_QUADS);
			glVertex2i(x, y);
			glVertex2i(x2, y);
			glVertex2i(x2, y2);
			glVertex2i(x, y2);
			glEnd();
			glColor4f(1f, 1f, 1f, 1f);

		} else {
			glColor4f(1f, 1f, 1f, 0.5f);
			new Block(selection, selector_x * World.BLOCK_SIZE, selector_y
					* World.BLOCK_SIZE).draw();
			glColor4f(1f, 1f, 1f, 1f);
		}
	}

	private void input() {
		if (mouseEnabled || Mouse.isButtonDown(0)) {
			mouseEnabled = true;
			int mouseX = Mouse.getX();
			int mouseY = 480 - Mouse.getY() - 1;
			boolean mouseClicked = Mouse.isButtonDown(0);
			selector_x = Math.round(mouseX / World.BLOCK_SIZE);
			selector_y = Math.round(mouseY / World.BLOCK_SIZE);
			if (mouseClicked) {
				// int grid_x = Math.round(mouseX / World.BLOCK_SIZE);
				// int grid_y = Math.round(mouseY / World.BLOCK_SIZE);
				grid.setAt(selector_x, selector_y, selection);
			}
		}
		while (Keyboard.next()) {

			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT
					&& Keyboard.getEventKeyState()) {
				mouseEnabled = false;
				// if (!(selector_x + 1 > World.BLOCKS_WIDTH - 1)) {
				// selector_x += 1;
				// }
				// selector_x++;
				// player.setX(selector_x * World.BLOCK_SIZE);
				if (!(player.getX() > 640 - 2 * World.BLOCK_SIZE)) {
					player.setX(player.getX() + World.BLOCK_SIZE);
					jump = false;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT
					&& Keyboard.getEventKeyState()) {
				mouseEnabled = false;
				// if (!(selector_x - 1 < 0)) {
				// selector_x -= 1;
				// }
				// selector_x--;
				// player.setX(selector_x * World.BLOCK_SIZE);
				if (!(player.getX() < 0)) {
					player.setX(player.getX() - World.BLOCK_SIZE);
					jump = false;
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_UP
					&& Keyboard.getEventKeyState()) {
				mouseEnabled = false;
				// if (!(selector_y - 1 < 0)) {
				// selector_y -= 1;
				// }
				// selector_y--;
				// player.setY(selector_y * World.BLOCK_SIZE);
				player.setY(player.getY() - World.BLOCK_SIZE);
				jump = true;
				// }
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_DOWN
					&& Keyboard.getEventKeyState()) {
				mouseEnabled = false;
				if (!(selector_y + 1 > World.BLOCKS_HEIGHT - 1)) {
					// selector_y += 1;
					// }
					// selector_x--;
					// player.setX(selector_x * World.BLOCK_SIZE);
				}
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_S) {
				grid.save(new File("save.xml"));
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_L) {
				grid.load(new File("save.xml"));
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_1) {
				selection = BlockType.STONE;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2) {
				selection = BlockType.DIRT;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3) {
				selection = BlockType.GRASS;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_4) {
				selection = BlockType.AIR;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_C) {
				grid.clear();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		new Boot();

	}
}
