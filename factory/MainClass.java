package factory;

interface NotificationSender {
    void send(String message);
}

class EmailSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

class SmsSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending Sms: " + message);
    }
}



// Factory Method example 
// abstract class Notification {

//     protected abstract NotificationSender createSender();

//     public void notify(String message) {
//         NotificationSender sender = createSender();
//         sender.send(message);
//     }
// }

// class EmailNotification extends Notification {

//     @Override 
//     protected NotificationSender createSender() {
//         return new EmailSender();
//     }
// }

// class SmsNotification extends Notification {

//     @Override 
//     protected NotificationSender createSender() {
//         return new SmsSender();
//     }
// }



// Abstract Factory Method

interface MessageFormatter {
    public String format(String message);
}

class HtmlFormatter implements MessageFormatter{
    @Override 
    public String format(String message){
        String html= "<p>"+message+ "</p>";
        return html;
    }
}

class PlainTextFormatter implements MessageFormatter{
    @Override 
    public String format(String message){
        return message;
    }
}

interface NotificationFactory{
    NotificationSender getSender();
    MessageFormatter getFormatter();
}

class EmailNotificationFactory implements NotificationFactory{
    @Override 
    public NotificationSender getSender(){
        return new EmailSender();
    }

    @Override 
    public MessageFormatter getFormatter(){
        return new HtmlFormatter();
    }
}

class SmsNotificationFactory implements NotificationFactory{
    @Override 
    public NotificationSender getSender(){
        return new SmsSender();
    }

    @Override 
    public MessageFormatter getFormatter(){
        return new PlainTextFormatter();
    }
}

public class MainClass {
    public static void main(String[] args) {
        // Notification notification = new EmailNotification();
        // notification.notify("Your order has been confirmed!");



        sendNotification(
        new EmailNotificationFactory(),
            "Order confirmed"
        );

        sendNotification(
            new SmsNotificationFactory(),
            "Order confirmed"
        );

    }

    static void sendNotification(NotificationFactory factory, String message) {

        NotificationSender sender = factory.getSender();
        MessageFormatter formatter = factory.getFormatter();

        String formattedMessage = formatter.format(message);
        sender.send(formattedMessage);
    }

}