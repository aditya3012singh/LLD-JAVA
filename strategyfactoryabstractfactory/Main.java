package strategyfactoryabstractfactory;


import java.util.Locale;

// ==================================================
// 1. STRATEGY PATTERN
// Problem: interchangeable delivery pricing rules
// ==================================================

interface DeliveryStrategy {
    double calculateCharge();
}

class StandardDelivery implements DeliveryStrategy {
    @Override
    public double calculateCharge() {
        return 50.0;
    }
}

class ExpressDelivery implements DeliveryStrategy {
    @Override
    public double calculateCharge() {
        return 100.0;
    }
}

class SameDayDelivery implements DeliveryStrategy {
    @Override
    public double calculateCharge() {
        return 200.0;
    }
}

class DeliveryService {
    private final DeliveryStrategy strategy;

    DeliveryService(DeliveryStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException(
                "Strategy cannot be null"
            );
        }
        this.strategy = strategy;
    }

    public double calculateBill() {
        return strategy.calculateCharge();
    }
}


// ==================================================
// 2. SIMPLE FACTORY
// Problem: centralize strategy object creation
// ==================================================

class DeliveryFactory {
    public static DeliveryStrategy create(String type) {
        if (type == null) {
            throw new IllegalArgumentException(
                "Delivery type cannot be null"
            );
        }

        switch (type.trim().toUpperCase(Locale.ROOT)) {
            case "STANDARD":
                return new StandardDelivery();
            case "EXPRESS":
                return new ExpressDelivery();
            case "SAME_DAY":
                return new SameDayDelivery();
            default:
                throw new IllegalArgumentException(
                    "Unknown delivery type: " + type
                );
        }
    }
}


// ==================================================
// 3. FACTORY METHOD
// Problem: subclasses decide which sender to create
// ==================================================

interface MessageSender {
    void send(String message);
}

class EmailMessageSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SmsMessageSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

abstract class Notification {
    protected abstract MessageSender createSender();

    public void notifyUser(String message) {
        MessageSender sender = createSender();
        sender.send(message);
    }
}

class EmailNotification extends Notification {
    @Override
    protected MessageSender createSender() {
        return new EmailMessageSender();
    }
}

class SmsNotification extends Notification {
    @Override
    protected MessageSender createSender() {
        return new SmsMessageSender();
    }
}


// ==================================================
// 4. ABSTRACT FACTORY
// Problem: create related families of products
// ==================================================

interface NotificationSender {
    void send(String message);
}

interface MessageFormatter {
    String format(String message);
}

// Email product family

class EmailSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

class HtmlFormatter implements MessageFormatter {
    @Override
    public String format(String message) {
        return "<p>" + message + "</p>";
    }
}

// SMS product family

class SmsSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class PlainTextFormatter implements MessageFormatter {
    @Override
    public String format(String message) {
        return message;
    }
}

// Abstract factory contract

interface NotificationFactory {
    NotificationSender getSender();
    MessageFormatter getFormatter();
}

// Concrete factories

class EmailNotificationFactory implements NotificationFactory {
    @Override
    public NotificationSender getSender() {
        return new EmailSender();
    }

    @Override
    public MessageFormatter getFormatter() {
        return new HtmlFormatter();
    }
}

class SmsNotificationFactory implements NotificationFactory {
    @Override
    public NotificationSender getSender() {
        return new SmsSender();
    }

    @Override
    public MessageFormatter getFormatter() {
        return new PlainTextFormatter();
    }
}

// Client uses the factory abstraction

class NotificationClient {
    public static void send(
        NotificationFactory factory,
        String message
    ) {
        NotificationSender sender = factory.getSender();
        MessageFormatter formatter = factory.getFormatter();

        sender.send(formatter.format(message));
    }
}


// ==================================================
// MAIN: Demonstrate all four concepts
// ==================================================

public class Main {
    public static void main(String[] args) {

        // 1. Strategy: choose behavior
        DeliveryService delivery =
            new DeliveryService(new ExpressDelivery());

        System.out.println(
            "Strategy charge: " + delivery.calculateBill()
        );

        // 2. Simple Factory: choose and create an object
        DeliveryStrategy strategy =
            DeliveryFactory.create("STANDARD");

        DeliveryService standard =
            new DeliveryService(strategy);

        System.out.println(
            "Factory charge: " + standard.calculateBill()
        );

        // 3. Factory Method: subclass selects sender
        Notification emailNotification =
            new EmailNotification();

        emailNotification.notifyUser("Order confirmed");

        // 4. Abstract Factory: create compatible product family
        NotificationClient.send(
            new EmailNotificationFactory(),
            "Order confirmed"
        );

        NotificationClient.send(
            new SmsNotificationFactory(),
            "Order confirmed"
        );
    }
}
