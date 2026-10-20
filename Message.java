interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender{
    @Override
    public void send(String message) {
        System.out.println("Sending email with message: " + message);
    }
}

class OrderService{
    private final MessageSender messageSender;
    OrderService(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    void placeOrder(String orderDetails){
        System.out.println("Placing order: " + orderDetails);
        messageSender.send("Order placed successfully!");
    }
}

class Main{
    public static void main(String[] args) {
        MessageSender emailSender = new EmailSender();
        OrderService orderService = new OrderService(emailSender);
        orderService.placeOrder("Order #12345");
    }
}