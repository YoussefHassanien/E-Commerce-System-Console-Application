
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== E-Commerce System Test Cases ===\n");

        // Test Case 1: Successful checkout with mixed products
        testCase1_SuccessfulMixedProductsCheckout();
        // Test Case 2: Only regular products (no shipping)
        testCase2_OnlyRegularProducts();
        // Test Case 3: Only shippable products
        testCase3_OnlyShippableProducts();
        // Test Case 4: Expirable products
        testCase4_ExpirableProducts();
        // Test Case 5: Expirable + Shippable products
        testCase5_ExpirableShippableProducts();
        // Test Case 6: Insufficient customer balance
        testCase6_InsufficientBalance();
        // Test Case 7: Empty cart scenario
        testCase7_EmptyCart();
        // Test Case 8: Large order with multiple quantities
        testCase8_LargeOrder();
        // Test Case 9: Edge case - zero balance customer
        testCase9_ZeroBalanceCustomer();
        // Test Case 10: Exact balance scenario
        testCase10_ExactBalance();
    }

    private static void testCase1_SuccessfulMixedProductsCheckout() {
        System.out.println("--- Test Case 1: Successful Mixed Products Checkout ---");
        System.out.println();
        try {
            // Create different types of products
            Product digitalProduct = new Product("E-Book", 100, 25.0);
            ShippableProduct physicalProduct = new ShippableProduct("Laptop", 5, 999.99, 2.5, 50.0);
            ExpirableProduct food = new ExpirableProduct("Milk", 50, 5.99, LocalDate.now().plusDays(7));

            // Create cart and add items
            Cart cart = new Cart();
            cart.addItem(digitalProduct, 2);
            cart.addItem(physicalProduct, 1);
            cart.addItem(food, 3);

            // Create customer with sufficient balance
            Customer customer = new Customer("John Doe", 1200.0, cart);

            // Create shipping service and checkout
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testCase2_OnlyRegularProducts() {
        System.out.println("--- Test Case 2: Only Regular Products (No Shipping) ---");
        System.out.println();
        try {
            // Create only digital/regular products
            Product ebook = new Product("Programming Guide", 200, 39.99);
            Product software = new Product("Photo Editor License", 50, 129.99);
            Product course = new Product("Online Course", 1000, 199.99);

            Cart cart = new Cart();
            cart.addItem(ebook, 1);
            cart.addItem(software, 1);
            cart.addItem(course, 1);

            Customer customer = new Customer("Alice Smith", 500.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testCase3_OnlyShippableProducts() {
        System.out.println("--- Test Case 3: Only Shippable Products ---");
        System.out.println();
        try {
            ShippableProduct book = new ShippableProduct("Physical Book", 30, 29.99, 0.5, 5.99);
            ShippableProduct electronics = new ShippableProduct("Headphones", 15, 199.99, 0.3, 9.99);
            ShippableProduct clothing = new ShippableProduct("T-Shirt", 100, 24.99, 0.2, 7.99);

            Cart cart = new Cart();
            cart.addItem(book, 2);
            cart.addItem(electronics, 1);
            cart.addItem(clothing, 3);

            Customer customer = new Customer("Bob Johnson", 400.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testCase4_ExpirableProducts() {
        System.out.println("--- Test Case 4: Expirable Products ---");
        System.out.println();
        try {
            ExpirableProduct freshMilk = new ExpirableProduct("Fresh Milk", 20, 4.99, LocalDate.now().plusDays(3));
            ExpirableProduct bread = new ExpirableProduct("Whole Wheat Bread", 15, 3.49, LocalDate.now().plusDays(5));
            ExpirableProduct yogurt = new ExpirableProduct("Greek Yogurt", 25, 6.99, LocalDate.now().plusDays(10));

            Cart cart = new Cart();
            cart.addItem(freshMilk, 2);
            cart.addItem(bread, 1);
            cart.addItem(yogurt, 3);

            Customer customer = new Customer("Carol Wilson", 100.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testCase5_ExpirableShippableProducts() {
        System.out.println("--- Test Case 5: Expirable + Shippable Products ---");
        System.out.println();
        try {
            ExpirableShippableProduct cheese = new ExpirableShippableProduct(
                    "Aged Cheese", 10, 15.99, LocalDate.now().plusDays(30), 0.5, 8.99);
            ExpirableShippableProduct meat = new ExpirableShippableProduct(
                    "Premium Beef", 5, 45.99, LocalDate.now().plusDays(7), 1.2, 12.99);

            Cart cart = new Cart();
            cart.addItem(cheese, 2);
            cart.addItem(meat, 1);

            Customer customer = new Customer("David Brown", 150.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

            System.out.println("✅ Expirable + Shippable products checkout successful!");
            System.out.println("Cheese expiry status: " + (cheese.isExpired() ? "Expired" : "Fresh"));
            System.out.println("Meat expiry status: " + (meat.isExpired() ? "Expired" : "Fresh"));

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testCase6_InsufficientBalance() {
        System.out.println("--- Test Case 6: Insufficient Customer Balance ---");
        System.out.println();
        try {
            ShippableProduct expensiveItem = new ShippableProduct("Gaming Console", 3, 599.99, 3.0, 25.99);
            Product accessory = new Product("Controller", 10, 79.99);

            Cart cart = new Cart();
            cart.addItem(expensiveItem, 1);
            cart.addItem(accessory, 2);

            // Customer with insufficient balance
            Customer customer = new Customer("Poor Pete", 100.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (IllegalArgumentException e) {
            System.out.println("✅ Expected failure: " + e.getMessage());
            System.out.println("Customer needs more funds for this purchase.");
        }
        System.out.println();
    }

    private static void testCase7_EmptyCart() {
        System.out.println("--- Test Case 7: Empty Cart Scenario ---");
        System.out.println();
        try {
            Cart emptyCart = new Cart();
            Customer customer = new Customer("Empty Buyer", 1000.0, emptyCart);
            ShippingService shippingService = new ShippingService(emptyCart.getItems());
            CheckoutService checkout = new CheckoutService(emptyCart, customer, shippingService);
            checkout.generateReceipt();

        } catch (IllegalArgumentException e) {
            System.out.println("✅ Expected failure: " + e.getMessage());
            System.out.println("Cannot checkout with empty cart.");
        }
        System.out.println();
    }

    private static void testCase8_LargeOrder() {
        System.out.println("--- Test Case 8: Large Order with Multiple Quantities ---");
        System.out.println();
        try {
            Product pen = new Product("Ballpoint Pen", 1000, 1.99);
            ShippableProduct notebook = new ShippableProduct("Spiral Notebook", 500, 4.99, 0.3, 2.99);
            ExpirableProduct snack = new ExpirableProduct("Energy Bar", 200, 2.49, LocalDate.now().plusDays(180));

            Cart cart = new Cart();
            cart.addItem(pen, 50);           // 50 pens
            cart.addItem(notebook, 20);      // 20 notebooks
            cart.addItem(snack, 15);         // 15 energy bars

            Customer customer = new Customer("Bulk Buyer", 500.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testCase9_ZeroBalanceCustomer() {
        System.out.println("--- Test Case 9: Zero Balance Customer ---");
        System.out.println();
        try {
            Product freeItem = new Product("Free Sample", 100, 0.01);
            Cart cart = new Cart();
            cart.addItem(freeItem, 1);

            // This should fail during customer creation
            Customer customer = new Customer("Zero Balance", 0.0, cart);

        } catch (IllegalArgumentException e) {
            System.out.println("✅ Expected failure: " + e.getMessage());
            System.out.println("Cannot create customer with zero balance.");
        }
        System.out.println();
    }

    private static void testCase10_ExactBalance() {
        System.out.println("--- Test Case 10: Exact Balance Scenario ---");
        System.out.println();
        try {
            Product item = new Product("Exact Price Item", 5, 45.0);
            ShippableProduct shippableItem = new ShippableProduct("Shipping Item", 3, 30.0, 1.0, 5.0);

            Cart cart = new Cart();
            cart.addItem(item, 1);           // $45
            cart.addItem(shippableItem, 1);  // $30 + $5 shipping = $35
            // Total: $45 + $35 = $80

            Customer customer = new Customer("Exact Balance", 80.0, cart);
            ShippingService shippingService = new ShippingService(cart.getItems());
            CheckoutService checkout = new CheckoutService(cart, customer, shippingService);
            checkout.generateReceipt();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        System.out.println();
    }
}
