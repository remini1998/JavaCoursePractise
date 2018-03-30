
public class Item {
	public String name = "ŒÔ∆∑";
	public float price = 0;
	public int amount = 0;
	
	Item(){
		
	}
	
	public static Item ItemFactory(String name, float price, int amount){
		Item i = new Item();
		i.name = name;
		i.price = price;
		i.amount = amount;
		return i;
	}
	
	public float getTotalPrice() {
		return price * amount;
	}
	
	
}
