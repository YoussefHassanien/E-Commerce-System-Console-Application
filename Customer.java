
public class Customer {

    private String name;
    private double balance;

    public Customer(String name, double balance, Cart cart) {
        if (!setName(name)) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (!setBalance(balance)) {
            throw new IllegalArgumentException("Invalid balance");
        }
    }

    private boolean setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
            return true;
        }
        return false;
    }

    private boolean setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance;
            return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }
}
