import java.util.Random;
import java.lang.Math;

class CellularAutomaton {

	boolean containsMicroOrganism;
	double chemoAttract;
	double chemoRepel;
	double newChemoAttract;
	double newChemoRepel;
	CellularAutomaton N,S,E,W,NE,SE,SW,NW;

	CellularAutomaton() {
	  chemoAttract = chemoRepel = 0;
	  N=S=E=W=NE=SE=SW=NW=null;
	  containsMicroOrganism = false;
	}
	CellularAutomaton(CellularAutomaton N, CellularAutomaton S, CellularAutomaton E, CellularAutomaton W, CellularAutomaton NE, CellularAutomaton SE, CellularAutomaton SW, CellularAutomaton NW) {
	  this.N=N; this.NE=NE;
	  this.S=S; this.SE=SE;
	  this.E=E; this.NW=NW;
	  this.W=W; this.SW=SW;
	}

	private void calcNewChemoAttract() { newChemoAttract = (chemoAttract+N.chemoAttract+S.chemoAttract+E.chemoAttract+W.chemoAttract+NE.chemoAttract+NW.chemoAttract+SE.chemoAttract+SW.chemoAttract)/9; }
	private void calcNewChemoRepel() { newChemoRepel = (chemoRepel+N.chemoRepel+S.chemoRepel+E.chemoRepel+W.chemoRepel+NE.chemoRepel+NW.chemoRepel+SE.chemoRepel+SW.chemoRepel)/9; }
	public void calcNewChemicals() { calcNewChemoAttract(); calcNewChemoRepel(); }
	public void setNewChemicals() { chemoAttract = newChemoAttract; chemoRepel = newChemoRepel; }

}

/*
class CellularAutomatonBorder extends CellularAutomaton {

	CellularAutomatonBorder() {
	  N=S=E=W=NW=NE=SE=SW=null;
	}
	CellularAutomatonBorder(CellularAutomaton N, CellularAutomaton S, CellularAutomaton E, CellularAutomaton W, CellularAutomaton NE, CellularAutomaton SE, CellularAutomaton SW, CellularAutomaton NW) {
	  this.N=N; this.NE=NE;
	  this.S=S; this.SE=SE;
	  this.E=E; this.NW=NW;
	  this.W=W; this.SW=SW;
	}
	public void calcNewChemicals() {}
	public void setNewChemicals() { chemoRepel = 0; chemoAttract = 0; }
} */

class CellularAutomataGrid {
	CellularAutomaton[][] grid;
	int size = 100;

	CellularAutomataGrid(int size) { 
	  if (size<10) {
	    System.out.println("A grid of size " + size + " is not big enough!");
	    System.exit(-1);
	  }
	  else {
	    this.size = size;
	    grid = new CellularAutomaton[size][size]; 
	    createGridInner();
	    //createGridBorder();
	    linkGrid();
	  }
	}

	void createGridBorder() {
	  for (int i=0;i<size;i++) {
	    grid[0][i] = new CellularAutomatonBorder();
	    grid[size-1][i] = new CellularAutomatonBorder();
	    grid[i][0] = new CellularAutomatonBorder();
	    grid[i][size-1] = new CellularAutomatonBorder();
	  }
	}
	/*
	void createGridInnerBak() {
	  for (int i=1;i<(size-1);i++) {
	    for (int j=1;j<(size-1);j++) {
	      grid[i][j] = new CellularAutomaton();
	    }
	  }
	} */
	void createGridInner() {
	  for (int i=0;i<size;i++) {
	    for (int j=0;j<size;j++) {
	      grid[i][j] = new CellularAutomaton();
	    }
	  }
	}
/*
	void linkGridBak() {
	  for (int i=1;i<(size-1);i++) {
	    for (int j=1;j<(size-1);j++) {
	      CellularAutomaton tmpCA = grid[i][j];
	      tmpCA.N = grid[i-1][j];
	      tmpCA.NE = grid[i-1][j+1];
	      tmpCA.E = grid[i][j+1];
	      tmpCA.SE = grid[i+1][j+1];
	      tmpCA.S = grid[i+1][j];
	      tmpCA.SW = grid[i+1][j-1];
	      tmpCA.W = grid[i][j-1];
	      tmpCA.NW = grid[i-1][j-1];
	    }
	  }
	} */
	void linkGrid() {
	  for (int i=0;i<size;i++) {
	    for (int j=0;j<size;j++) {
	      CellularAutomaton tmpCA = grid[i][j];
	      tmpCA.N = grid[(i+size-1)%size][(j+size)%size];
	      tmpCA.NE = grid[(i+size-1)%size][(j+size+1)%size];
	      tmpCA.E = grid[(i+size)%size][(j+size+1)%size];
	      tmpCA.SE = grid[(i+size+1)%size][(j+size+1)%size];
	      tmpCA.S = grid[(i+size+1)%size][(j+size)%size];
	      tmpCA.SW = grid[(i+size+1)%size][(j+size-1)%size];
	      tmpCA.W = grid[(i+size)%size][(j+size-1)%size];
	      tmpCA.NW = grid[(i+size-1)%size][(j+size-1)%size];
	    }
	  }
	}

	void printGrid() {
	  for (int i=0;i<size*5;i++) { System.out.print("-"); }
	  System.out.println();
	  for (int i=0;i<size;i++) {
	    for (int j=0;j<size;j++) {
	      if (grid[i][j].containsMicroOrganism)
	        System.out.print("\033[0;36m ####\033[0m");
	      else
	        System.out.printf("%5.0f",grid[i][j].chemoAttract);
	    }
	    System.out.println();
	  }
	  for (int i=0;i<size*5;i++) { System.out.print("-"); }
	  System.out.println();
	}

	void diffuseChemicals() {
	  for (int i=0;i<size;i++) {
	    for (int j=0;j<size;j++) {
	      grid[i][j].calcNewChemicals();
	    }
	  }
	  for (int i=0;i<size;i++) {
	    for (int j=0;j<size;j++) {
	      grid[i][j].setNewChemicals();
	    }
	  }
	}
	CellularAutomaton getRandomCA() {
	  Random randomGen = new Random();
	  int x = java.lang.Math.abs(randomGen.nextInt()%size);
	  int y = java.lang.Math.abs(randomGen.nextInt()%size);
	  return grid[x][y];
	}
	
}

class testCA {
	public static void main(String[] args) {
	  EColi ecoli1,ecoli2;
	  Random randomGen= new Random();
	  CellularAutomataGrid myGrid = new CellularAutomataGrid(42);
	  myGrid.getRandomCA().chemoAttract = 50000.0;
	  myGrid.getRandomCA().chemoAttract = 50000.0;
	  ecoli1 = new EColi(myGrid.getRandomCA());
	  ecoli2 = new EColi(myGrid.getRandomCA());
	  while (true) {
	    System.out.println(((char) 27)+"[2J");
	    myGrid.printGrid();
	    if (randomGen.nextDouble() < 0.6)
	      myGrid.diffuseChemicals();
	    ecoli1.move();
	    ecoli2.move();
	    try { Thread.currentThread().sleep(800); } catch (InterruptedException ie) { System.err.println("ERROR, Interrupted"); System.exit(-1); }

	  }
	}
}


