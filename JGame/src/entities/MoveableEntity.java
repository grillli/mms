package entities;

public interface MoveableEntity extends Entity {
	public double getDX();

	public double getDY();

	//Geschwindigkeit auf X-Achse
	public void setDX(double dx);

	public void setDY(double dy);
}
