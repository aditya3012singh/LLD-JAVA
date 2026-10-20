interface PaymentMethod {
    boolean pay(double amount);
}

interface MessageSender {
    void send(String message);
}

class CardPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing card payment of $" + amount);
        if(amount>0 && amount<=10000)
            return true;
        else
            return false;
    }
}

class UpiPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing UPI payment of $" + amount);
        if(amount>0 && amount<=5000)
            return true;
        else
            return false;
    }
}

class EmailSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Sending email with message: " + message);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS with message: " + message);
    }
}

class Order{
    private final String orderId;
    final double totalAmount;

    Order(String orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Total Amount: $" + totalAmount);
    }
}

class OrderService{
    private final MessageSender messageSender;
    private final PaymentMethod paymentMethod;

    OrderService(MessageSender messageSender, PaymentMethod paymentMethod){
        this.messageSender = messageSender;
        this.paymentMethod = paymentMethod;
    }

    boolean placeOrder(Order order){
        if(order.totalAmount <= 0){
            System.out.println("Invalid order amount.");
            return false;
        }

        boolean paymentSuccess = paymentMethod.pay(order.totalAmount);
        if(paymentSuccess){
            messageSender.send("Order placed successfully!");
            return true;
        } else {
            System.out.println("Failed to place order.");
            return false;
        }
    }

}

class Main{
    public static void main(String[] args) {
        Order order1= new Order("1", 2000);
        MessageSender emailSender = new EmailSender();
        PaymentMethod cardPayment = new CardPayment();
        OrderService orderService = new OrderService(emailSender, cardPayment);
        orderService.placeOrder(order1);
        order1.displayOrder();


        Order order2= new Order("2", 12000);
        MessageSender smsSender = new SmsSender();
        PaymentMethod upiPayment = new UpiPayment();
        OrderService orderService2 = new OrderService(smsSender, upiPayment);
        orderService2.placeOrder(order2);
        order2.displayOrder();
    }
}