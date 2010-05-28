package br.compnatural.coordinate;

public class RealCoordinate extends Coordinate {
	public double min;
	public double max;
	public double delta;
	public double value;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public RealCoordinate(String name, double min, double max){
		this.name = name;
		this.min = min;
		this.max = max;
		delta = (double)(Math.abs(min) + Math.abs(max)) * 10d / 100d;
	}
	
	public String toString(){
		return "name=("+name +") min=("+min +") max=("+max+") value=("+value+")" ; 
	}
}
