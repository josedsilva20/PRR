package prr.core;

public class VoiceCommunication extends Communication{
	private int _duration;

	public VoiceCommunication(int id, Terminal from, Terminal to){
		super(id, from, to, "VOICE", 0);
	}
	public VoiceCommunication(){

	}

	public boolean equals(VoiceCommunication c1){
		return this.getId() == c1.getId();
	}

	@Override
	protected double computeCost(String plan, int duration){
		double price = 10 * duration;
		if (plan.equals("NORMAL")){
			price = 20 * duration;
		}
		setCost(Math.round(price));
		return price;
	}

	public boolean areFriends(Terminal from, Terminal to){
		return from.getOrderedFriends().contains(to);
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