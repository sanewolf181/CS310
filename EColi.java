/* MicroOrganism class

	class members
	======================
	
	instance members
	=====================
	direction
	speed
	
Ecoli class
===========

states
--------
set of CA
direction
speed

methods
----------
calculate new direction (probabalistic)
calculate new speed
move
	
*/
import java.util.Random;

class EColi {
	Random randomGenerator = new Random();
	Direction direction;
	int speed = 10;
	int memory = 5;
	CellularAutomaton head,middle1,middle2,middle3,tail;

	EColi(CellularAutomaton head) {
	  this.head = head;
	  middle1 = head.N;
	  middle2 = middle1.N;
	  middle3 = middle2.N;
	  tail = middle3.N;
	void tumble() { 
	  int r = randomGenerator.nextInt()%(Direction.values().length);
	  switch (r) {
	    case 0: direction = Direction.N; break;
	    case 1: direction = Direction.NE; break;
	    case 2: direction = Direction.E; break;
	    case 3: direction = Direction.SE; break;
	    case 4: direction = Direction.S; break;
	    case 5: direction = Direction.SW; break;
	    case 6: direction = Direction.W; break;
	    case 7: direction = Direction.NW; break;
	    default: direction = Direction.S; break;
	  }
	}
}



enum Direction { N,NE,E,SE,S,SW,W,NW }
