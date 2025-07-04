public interface Shippable {
    boolean setWeight(double weight);
    boolean setShippingFees(double shippingFees);
    double getWeight();
    double getShippingFees();
}