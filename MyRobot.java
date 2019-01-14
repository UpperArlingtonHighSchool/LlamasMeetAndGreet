package bc19;

import java.util.ArrayList;

public class MyRobot extends BCAbstractRobot {
	public int turn;
	public int cx1 = 0, cx2 = 0, cx3 = 0, cy1 = 0, cy2 = 0, cy3 = 0;
	public int maplength = getFuelMap()[0].length;
	public int castles = 0;
	ArrayList<Integer> sequenceLocation = new ArrayList<Integer>();
	int[][] robotMap;
	int[] radiusX1 = { 1, -1, 1, -1, 1, -1, 0, 0 };
	int[] radiusY1 = { 0, 0, -1, 1, 1, -1, 1, -1 };
	int[] radiusX2 = { 2, -2, 0, 0, 1, 1, -1, -1 };
	int[] radiusY2 = { 0, 0, 2, -2, 1, -1, 1, -1 };
	int[] radiusX3 = { 4, -4, 3, -3, 3, -3, 3, -3, 3, -3, 2, -2, 2, -2, 1, -1, 1, -1, 0, 0 };
	int[] radiusY3 = { 0, 0, 1, -1, -1, 1, 2, -2, -2, 2, 3, -3, -3, 3, 3, -3, -3, 3, 4, -4 };

	public Action turn() {
		turn++;
		if (turn / 10 == 0) {
			robotMap = this.getVisibleRobotMap();
		}
		if (me.unit == SPECS.CASTLE) {
			if (turn == 1) {
				castles++;
				if (castles == 1) {
					cx1 = me.x;
					cy1 = me.y;
					sequenceLocation.add(0);
				}
				if (castles == 2) {
					cx2 = me.x;
					cy2 = me.y;
					sequenceLocation.add(0);
				}
				if (castles == 3) {
					cx3 = me.x;
					cy3 = me.y;
					sequenceLocation.add(0);
				}
			}
			int castleNumber = identifyCastle();
			vCastle castle = new vCastle();
			return castle.vCastle(castleNumber);
		}
		if (me.unit == SPECS.PILGRIM) {
			vPilgrim pilgrim = new vPilgrim();
			return pilgrim.randomM();
		}
		if (me.unit == SPECS.CRUSADER) {
			vCrusader crusader = new vCrusader();
			return crusader.randomM();
		}
		if (me.unit == SPECS.PROPHET) {
			vProphet prophet = new vProphet();
			return prophet.randomM();
		}
		if (me.unit == SPECS.PREACHER) {
			vPreacher preacher = new vPreacher();
			return preacher.randomM();
		}
		return null;
	}

	private int identifyCastle() {
		int castleNumber = 0;
		if (me.x == cx1 && me.y == cy1)
			castleNumber = 1;
		else if (me.x == cx2 && me.y == cy2)
			castleNumber = 2;
		else if (me.x == cx3 && me.y == cy3)
			castleNumber = 3;
		return castleNumber;
	}

	private class vCastle {
		public vCastle() {
		}

		public Action vCastle(int castleIdentifier) {
			int[] specsProductionSequence = new int[] { SPECS.PILGRIM, SPECS.PROPHET, SPECS.PILGRIM, SPECS.PROPHET,
					SPECS.PREACHER };
			if (sequenceLocation.get(castleIdentifier - 1) < 5) {
				log("" + specsProductionSequence[sequenceLocation.get(castleIdentifier - 1)]);
				sequenceLocation.set(castleIdentifier - 1, sequenceLocation.get(castleIdentifier - 1) + 1);
				return randomB(specsProductionSequence[sequenceLocation.get(castleIdentifier - 1)]);
			}
			return null;
		}

		public Action randomB(int unit) {
			int raXY = (int) (Math.random() * 9);
			int randomx = radiusX1[raXY];
			int randomy = radiusY1[raXY];
			while (!isPassable(randomx, randomy)) {
				if (raXY != 9) {
					raXY++;
				} else {
					raXY = 0;
				}
				randomx = radiusX1[raXY];
				randomy = radiusY1[raXY];
			}
			return buildUnit(unit, randomx, randomy);
		}
		
		private boolean isPassable(int randomx, int randomy) {
			log("map length is" + maplength);
			if((me.x+randomx) > maplength-1 || (me.x+randomx) < 0 || (me.y+randomy) > maplength-1 || (me.y+randomy) < 0){
				return false;
			}
			return map[me.x + randomx ][me.y + randomy];
		}
	}

	private class vPilgrim {
		public int xcoord = me.x;
		public int ycoord = me.y;
		public int ccx = 0;
		public int ccy = 0;

		public vPilgrim() {
			xcoord = me.x;
			ycoord = me.y;
		}

		public Action randomM() {
			int raXY = (int) (Math.random() * 8);
			int randomx = radiusX2[raXY];
			int randomy = radiusY2[raXY];
			while (!isPassable(randomx, randomy)) {
				if (raXY != 8) {
					raXY++;
				} else {
					raXY = 0;
				}
				randomx = radiusX2[raXY];
				randomy = radiusY2[raXY];
			}
			return move(randomx, randomy);
		}

		private boolean isPassable(int randomx, int randomy) {
			log("map length is" + maplength);
			if((me.x+randomx) > maplength-1 || (me.x+randomx) < 0 || (me.y+randomy) > maplength-1 || (me.y+randomy) < 0){
				return false;
			}
			return map[me.x + randomx ][me.y + randomy];
		}	}

	private class vCrusader {
		public vCrusader() {
		}

		public Action randomM() {
			int raXY = (int) (Math.random() * 20);
			int randomx = radiusX2[raXY];
			int randomy = radiusY2[raXY];
			while (!isPassable(randomx, randomy)) {
				if (raXY != 20) {
					raXY++;
				} else {
					raXY = 0;
				}
				randomx = radiusX2[raXY];
				randomy = radiusY2[raXY];
			}
			return move(randomx, randomy);
		}

		private boolean isPassable(int randomx, int randomy) {
			log("map length is" + maplength);
			if((me.x+randomx) > maplength-1 || (me.x+randomx) < 0 || (me.y+randomy) > maplength-1 || (me.y+randomy) < 0){
				return false;
			}
			return map[me.x + randomx ][me.y + randomy];
		}
	}

	private class vProphet {
		public vProphet() {
		}

		public Action randomM() {
			int raXY = (int) (Math.random() * 8);
			int randomx = radiusX2[raXY];
			int randomy = radiusY2[raXY];
			while (!isPassable(randomx, randomy)) {
				if (raXY != 8) {
					raXY++;
				} else {
					raXY = 0;
				}
				randomx = radiusX2[raXY];
				randomy = radiusY2[raXY];
			}
			return move(randomx, randomy);
		}

		private boolean isPassable(int randomx, int randomy) {
			log("map length is" + maplength);
			if((me.x+randomx) > maplength-1 || (me.x+randomx) < 0 || (me.y+randomy) > maplength-1 || (me.y+randomy) < 0){
				return false;
			}
			return map[me.x + randomx ][me.y + randomy];
		}
	}

	private class vPreacher {
		public vPreacher() {
		}

		public Action randomM() {
			int raXY = (int) (Math.random() * 8);
			int randomx = radiusX2[raXY];
			int randomy = radiusY2[raXY];
			while (!isPassable(randomx, randomy)) {
				if (raXY != 8) {
					raXY++;
				} else {
					raXY = 0;
				}
				randomx = radiusX2[raXY];
				randomy = radiusY2[raXY];
			}
			return move(randomx, randomy);
		}

		private boolean isPassable(int randomx, int randomy) {
			log("map length is" + maplength);
			if((me.x+randomx) > maplength-1 || (me.x+randomx) < 0 || (me.y+randomy) > maplength-1 || (me.y+randomy) < 0){
				return false;
			}
			return map[me.x + randomx ][me.y + randomy];
		}
	}
}
