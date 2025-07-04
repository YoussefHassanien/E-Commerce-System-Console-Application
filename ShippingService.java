
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {

    private Map<Product, Integer> items = new LinkedHashMap<>();
    private List<Shippable> shippableItems = new ArrayList<>();
    private double totalShippingFees;
    private double totalWeight;

    public ShippingService(Map<Product, Integer> items) {
        if (!setItems(items)) {
            throw new IllegalArgumentException("Invalid items");
        }
        this.totalShippingFees = 0.0;
        this.totalWeight = 0.0;
        calculateShippingFees();
    }

    private void calculateShippingFees() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            if (product instanceof Shippable shippableProduct) {
                this.shippableItems.add(shippableProduct);
                totalShippingFees += shippableProduct.getShippingFees() * quantity;
                totalWeight += shippableProduct.getWeight() * quantity;
            }
        }
    }

    private boolean setItems(Map<Product, Integer> items) {
        if (!items.isEmpty()) {
            this.items = items;
            return true;
        }
        return false;
    }

    public double getTotalShippingFees() {
        return totalShippingFees;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public List<Shippable> getShippableItems() {
        return Collections.unmodifiableList(this.shippableItems);
    }
}
