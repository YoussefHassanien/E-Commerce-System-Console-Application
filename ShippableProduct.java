public class ShippableProduct extends Product implements Shippable {
    private double weight;
    private double shippingFees;

    public ShippableProduct(String name, int quantity, double price, double weight, double shippingFees){
        super(name, quantity, price);
        if(!setWeight(weight)) {
            throw new IllegalArgumentException("Invalid weight");
        }
        if(!setShippingFees(shippingFees)) {
            throw new IllegalArgumentException("Invalid shipping fees");
        }
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
