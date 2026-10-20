class Order{
    String orderId;
    double totalAmount;

    Order(String orderId, Double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Total Amount: $" + totalAmount);
    }
}

interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender{
    @Override 
    public void send(String message) {
        System.out.println("Sending email with message: " + message);
    }
}

class SmsSender implements MessageSender{
    @Override 
    public void send(String message) {
        System.out.println("Sending SMS with message: " + message);
    }
}

class OrderService{
    private final MessageSender messageSender;

    OrderService(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    void placeOrder(Order order){
        System.out.println("Placing order: " + order.orderId);
        messageSender.send("Order placed successfully!");
    }

    void displayOrderDetails(Order order){
        order.displayOrder();
    }
}

class Main{
    public static void main(String[] args) {
        Order order = new Order("12345", 250.0);

        MessageSender emailSender = new EmailSender();
        OrderService orderServiceWithEmail = new OrderService(emailSender);
        orderServiceWithEmail.placeOrder(order);
        orderServiceWithEmail.displayOrderDetails(order);

        MessageSender smsSender = new SmsSender();
        OrderService orderServiceWithSms = new OrderService(smsSender);
        orderServiceWithSms.placeOrder(order);
        orderServiceWithSms.displayOrderDetails(order);
    }
}