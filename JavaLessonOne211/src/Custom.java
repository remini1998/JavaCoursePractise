import java.util.ArrayList;

public class Custom {
	public String name = "¹Ë¿Í";
	public ArrayList<Item> items;
	Custom(){
		items = new ArrayList<Item>();
	}
	
	public float getTotalPrice() {
		float all = 0;
		for (Item item : items) {
			all += item.getTotalPrice();
		}
		return all;
	}
	
	public float getTotalKinds() {
		return items.size();
	}
	
	public float getTotalAmount() {
		float all = 0;
		for (Item item : items) {
			all += item.amount;
		}
		return all;
	}
}
