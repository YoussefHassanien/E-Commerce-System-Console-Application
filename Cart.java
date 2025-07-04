import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> items = new LinkedHashMap<>();
    private double totalPrice;

    public boolean addItem(Product item, int quantity){
        if(item != null && quantity > 0 && quantity <= item.getQuantity() && item.reduceQuantity(quantity)){
            this.items.merge(item, quantity, Integer::sum);
            updateTotalPrice(item.getPrice() * quantity);
            return true;
        }
        return false;
    }

    private void updateTotalPrice(double price){
        this.totalPrice += price;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }

    public Map<Product, Integer> getItems(){
        return Collections.unmodifiableMap(items);
    }

    public boolean isCartEmpty(){
        return this.items.isEmpty();
    }

}
