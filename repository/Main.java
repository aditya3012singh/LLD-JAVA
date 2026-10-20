
package repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);

        service.createOrder("ORD-101", 1500.0);
        service.createOrder("ORD-102", 2500.0);

        service.getOrder("ORD-101")
                .ifPresent(System.out::println);

        service.getOrder("ORD-999")
                .ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Order not found")
                );
    }
}

class Order {
    private final String id;
    private final double amount;

    public Order(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', amount=" + amount + "}";
    }
}

interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(String id);
}

class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }
}

class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void createOrder(String id, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Order amount must be positive"
            );
        }

        repository.save(new Order(id, amount));
    }

    public Optional<Order> getOrder(String id) {
        return repository.findById(id);
    }
}
