
public class Item {
	public String name = "ŒÔ∆∑";
	public float price = 0;
	public int amount = 0;
	
	Item(){
		
	}
	
	Item(String name, float price, int amount){
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	
	public float getTotalPrice() {
		return price * amount;
	}
	
	
}
