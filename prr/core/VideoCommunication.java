package prr.core;

public class VideoCommunication extends Communication{
	private int _duration;

	public VideoCommunication(int id, Terminal from, Terminal to){
		super(id, from, to, "VIDEO", 0);
	}

	public boolean equals(VideoCommunication c1){
		return this.getId() == c1.getId();
	}

	@Override
	protected double computeCost(String plan, int duration){
		double price = 10 * duration;
		if (plan.equals("NORMAL"))
			price = 30 * duration;

		if (plan.equals("GOLD"))
			price = 20 * duration;

		setCost(Math.round(price));
		return price;
	}

	public void setDuration(int duration){
		_duration = duration;
	}

	public int getDuration(){
		return _duration;
	}

	protected int getSize(){
		return 0;
	}
}