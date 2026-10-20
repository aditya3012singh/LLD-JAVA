package state;

public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        order.ship(); // Should reject
        order.pay();  // Should succeed
        order.pay();  // Should reject duplicate payment
        order.ship(); // Should succeed
        order.ship(); // Should reject duplicate shipping
    }
}


interface OrderState {
    void pay(Order order);
    void ship(Order order);
}

class Order{
    private OrderState state;
    Order(){
        this.state= new CreatedState();
    }

    public void setState(OrderState state){
        this.state= state;
    }

    public void pay() {
        state.pay(this);
    }

    public void ship(){
        state.ship(this);
    }
}

class CreatedState implements OrderState {
    @Override 
    public void pay(Order order){
        System.out.println("Payment Successful");
        order.setState(new PaidState());
    }

    @Override 
    public void ship(Order order){
        System.out.println("Cannot ship an order before payment");
    }
}

class PaidState implements OrderState{
    @Override
    public void pay(Order order) {
        System.out.println("Order is already paid");
    }

    @Override
    public void ship(Order order) {
        System.out.println("Order shipped successfully");
        order.setState(new ShippedState());
    }
}

class ShippedState implements OrderState{
    @Override
    public void pay(Order order) {
        System.out.println("Cannot pay an order that is already shipped");
    }

    @Override
    public void ship(Order order) {
        System.out.println("Order is already shipped");
    }
}