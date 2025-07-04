import java.time.LocalDate;

public class ExpirableShippableProduct extends Product implements Expirable, Shippable {
    private LocalDate expiryDate;
    private double weight;
    private double shippingFees;

    public ExpirableShippableProduct(String name, int quantity, double price, LocalDate expiryDate, double weight, double shippingFees){
        super(name, quantity, price);
        if(!setExpiryDate(expiryDate)) {
            throw new IllegalArgumentException("Invalid expiry date");
        }
        if(!setWeight(weight)) {
            throw new IllegalArgumentException("Invalid weight");
        }
        if(!setShippingFees(shippingFees)) {
            throw new IllegalArgumentException("Invalid shipping fees");
        }
    }

    @Override
    public boolean setExpiryDate(LocalDate expiryDate){
        if(expiryDate != null && expiryDate.isAfter(LocalDate.now())){
            this.expiryDate = expiryDate;
            return true;
        }
        return false;
    }

    @Override
    public boolean isExpired(){
        return this.expiryDate.isBefore(LocalDate.now());
    }

        @Override
    public boolean setWeight(double weight){
        if(weight > 0){
            this.weight = weight;
            return true;
        }
        return false;
    }

    @Override
    public boolean setShippingFees(double shippingFees){
        if(shippingFees > 0){
            this.shippingFees = shippingFees;
            return true;
        }
        return false;
    }

    @Override
    public double getShippingFees() {
        return this.shippingFees;
    }

    @Override
    public double getWeight(){
        return this.weight;
    }
}
