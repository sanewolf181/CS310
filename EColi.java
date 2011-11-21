import java.util.Random;
import java.lang.Math;

class EColi {
	Random randomGenerator = new Random();
	Direction direction;
	int speed = 10;
	CellularAutomaton head;
	int prevConcAttract, prevConcRepel, newConcAttract, newConcRepel;

	EColi(CellularAutomaton head) {
	  this.head = head;
	  head.containsMicroOrganism = true;
	  prevConcAttract = (int)head.chemoAttract;
	  prevConcRepel = (int)head.chemoRepel;
	  tumble();
	}

	void tumble() { 
	  int r = randomGenerator.nextInt()%(Direction.values().length);
	  r = java.lang.Math.abs(r);
	  switch (r) {
	    case 0: direction = Direction.N; break;
	    case 1: direction = Direction.NE; break;
	    case 2: direction = Direction.E; break;
	    case 3: direction = Direction.SE; break;
	    case 4: direction = Direction.S; break;
	    case 5: direction = Direction.SW; break;
	    case 6: direction = Direction.W; break;
	    case 7: direction = Direction.NW; break;
	    default : System.err.println("Invalid Direction: r is " + r) ; System.exit(-1); break;
	  }
	}

	void move() {
	  double p = 0.4;
	  double increment=0.2;
	  tumble();
	  do {
	    prevConcAttract = (int)head.chemoAttract;
	    prevConcRepel = (int)head.chemoRepel;
	    goStraight(1);
	    newConcAttract = (int)head.chemoAttract;
	    newConcRepel = (int)head.chemoRepel;
	    if (newConcRepel > prevConcRepel)
	      p += increment;
	      //p *= (1.0 + increment);
	    else if (newConcRepel < prevConcRepel)
	      p -= increment;
	      //p *= (1.0 - increment);
	    else if (newConcAttract > prevConcAttract)
	      p -= increment;
	      //p *= (1.0 - increment);
	    else if (newConcAttract < prevConcAttract)
	      p += increment;
	      //p *= (1.0 + increment);
	    p = java.lang.Math.abs(p);
	  } while (p < randomGenerator.nextDouble());
	}
	
	void goStraight(int steps) {
	  head.containsMicroOrganism = false;
	  for(int i=0;i<steps;i++) {
	    switch(direction) {
	      case N : head = head.N; break;
	      case NE : head = head.NE; break;
	      case E : head = head.E; break;
	      case SE : head = head.SE; break;
	      case S : head = head.S; break;
	      case SW : head = head.SW; break;
	      case W : head = head.W; break;
	      case NW : head = head.NW; break;
	      default : System.err.println("Invalid Direction") ; System.exit(-1); break;
	    }
	  }
	  head.containsMicroOrganism = true;
	}
}

enum Direction { N,NE,E,SE,S,SW,W,NW }
