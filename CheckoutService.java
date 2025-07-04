
import java.util.Map;

public class CheckoutService {

    private Cart cart;
    private Customer customer;
    private ShippingService shippingService;

    public CheckoutService(Cart cart, Customer customer, ShippingService shippingService) {
        if (!setCart(cart)) {
            throw new IllegalArgumentException("Cart cannot be empty");
        }
        if (!setCustomer(customer)) {
            throw new IllegalArgumentException("Invalid customer");
        }
        if (!setShippingService(shippingService)) {
            throw new IllegalArgumentException("Invalid shipping service");
        }
        if (!isSufficientBalance()) {
            throw new IllegalArgumentException("Insufficient customer balance for this purchase");
        }
    }

    private boolean setCart(Cart cart) {
        if (cart != null && !cart.isEmpty()) {
            this.cart = cart;
            return true;
        }
        return false;
    }

    private boolean setCustomer(Customer customer) {
        if (customer != null) {
            this.customer = customer;
            return true;
        }
        return false;
    }

    private boolean setShippingService(ShippingService shippingService) {
        if (shippingService != null) {
            this.shippingService = shippingService;
            return true;
        }
        return false;
    }

    private boolean isSufficientBalance() {
        return this.customer.getBalance() >= this.cart.getTotalPrice() + this.shippingService.getTotalShippingFees();
    }

    public void generateReceipt() {
        StringBuilder receipt = new StringBuilder();

        // Generate shipment notice
        receipt.append(generateShipmentNotice());
        receipt.append("\n\n");

        // Generate checkout receipt
        receipt.append(generateCheckoutReceipt());

        // Print the receipt to console
        System.out.println(receipt.toString() + "\n");
    }

    /**
     * Generates shipment notice for shippable items only
     *
     * @return formatted shipment notice string
     */
    private String generateShipmentNotice() {
        StringBuilder notice = new StringBuilder();
        notice.append("** Shipment notice **\n");

        // Get cart items and iterate through them
        Map<Product, Integer> cartItems = cart.getItems();

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            // Only include shippable items in shipment notice
            if (product instanceof Shippable shippableProduct) {
                double itemWeight = shippableProduct.getWeight() * quantity;

                // Format: "1x Cheese    200g"
                notice.append(String.format("%dx %-12s %.0fg%n",
                        quantity,
                        product.getName(),
                        itemWeight * 1000)); // Convert kg to grams
            }
        }

        // Add total package weight
        double totalWeightKg = shippingService.getTotalWeight();
        notice.append(String.format("Total package weight %.1fkg", totalWeightKg));

        return notice.toString();
    }

    /**
     * Generates checkout receipt for all items
     *
     * @return formatted checkout receipt string
     */
    private String generateCheckoutReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("** Checkout receipt **\n");

        // Get cart items and iterate through them
        Map<Product, Integer> cartItems = cart.getItems();

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;

            // Format: "2x Cheese    200"
            receipt.append(String.format("%dx %-12s %.0f%n",
                    quantity,
                    product.getName(),
                    itemTotal));
        }

        // Add separator line
        receipt.append("----------------------\n");

        // Add totals
        double subtotal = cart.getTotalPrice();
        double shipping = shippingService.getTotalShippingFees();
        double total = subtotal + shipping;

        receipt.append(String.format("%-12s %.0f%n", "Subtotal", subtotal));
        receipt.append(String.format("%-12s %.0f%n", "Shipping", shipping));
        receipt.append(String.format("%-12s %.0f", "Amount", total));

        return receipt.toString();
    }
}
