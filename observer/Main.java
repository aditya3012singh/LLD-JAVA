package observer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderEventManager manager = new OrderEventManager();
        EmailObserver email= new EmailObserver();
        manager.subscribe(email);
        manager.subscribe(email);
        manager.subscribe(new WarehouseObserver());

        manager.notifyObservers("ORD-101");
    }
}



interface OrderObserver {
    void onOrderPlaced(String orderId);
}

class EmailObserver implements OrderObserver {
    @Override
    public void onOrderPlaced(String orderId) {
        System.out.println("Email sent for order: " + orderId);
    }
}

class WarehouseObserver implements OrderObserver {
    @Override
    public void onOrderPlaced(String orderId) {
        System.out.println("Warehouse notified for order: " + orderId);
    }
}

class OrderEventManager{
    private final List<OrderObserver> observer = new ArrayList<>();

    // OrderEventManager(List<OrderObserver> observer){
    //     this.observer= new ArrayList<>();
    // }

    public void subscribe(OrderObserver orderObserver){
        if(!observer.contains(orderObserver)){
            observer.add(orderObserver);
        }   
    }

    public void notifyObservers(String orderId){
        for(OrderObserver ord: observer){
            ord.onOrderPlaced(orderId);
        }
    }
}
