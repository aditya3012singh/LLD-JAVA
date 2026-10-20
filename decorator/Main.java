package decorator;

public class Main {
    public static void main(String[] args) {
        // Notification notification =
        // new LoggingDecorator(new BasicNotification());

        // notification.send("Order placed");


        Notification notification =
        new RetryDecorator(
            new LoggingDecorator(new BasicNotification())
        );

        notification.send("OrderPlaced");
    }
}


interface Notification {
    void send(String message);
}

class BasicNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending notification: " + message);
    }
}

class LoggingDecorator implements Notification{
    private final Notification notification;
    public LoggingDecorator(Notification notification){
        this.notification= notification;
    }


    @Override 
    public void send(String message){
        System.out.println("Logging : "+ message);
        notification.send(message);
    }

}

class RetryDecorator implements Notification{
    private final Notification notification;

    RetryDecorator(Notification notification){
        this.notification= notification;
    }

    
    @Override
    public void send(String message) {
        for (int i = 0; i < 3; i++) {
            try {
                notification.send(message);
                System.out.println("Attempt " + (i + 1) + " succeeded");
                return;
            } catch (RuntimeException e) {
                if (i == 2) {
                    throw e;
                }
                System.out.println("Attempt " + (i + 1) + " failed; retrying...");
            }
        }
    }

}