package org.jcjxb.wsn.web.bean;

public class Position {

	private int x;
	
	private int y;

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return true;
		}
		if(obj instanceof Position) {
			Position pos = (Position)obj;
			return this.x == pos.x && this.y == pos.y;
		}
		return false;
	}
}
