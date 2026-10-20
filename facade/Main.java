package facade;

public class Main {
    public static void main(String[] args) {
        // OrderFacade orderFacade= new OrderFacade(new PaymentService(), new InventoryService(),new NotificationService());
        // orderFacade.placeOrder();

        OrderCancellationFacade cancellationFacade =
        new OrderCancellationFacade(
            new RefundService(),
            new InventoryRestoreService(),
            new NotificationService()
        );

        cancellationFacade.cancelOrder();
    }
}


class PaymentService {
    public void pay() {
        System.out.println("Payment completed");
    }
}

class InventoryService {
    public void updateStock() {
        System.out.println("Inventory updated");
    }
}

class NotificationService {
    public void sendConfirmation() {
        System.out.println("Confirmation sent");
    }
}

class OrderFacade {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;

    public OrderFacade(PaymentService paymentService, InventoryService inventoryService, NotificationService notificationService){
        this.paymentService=paymentService;
        this.inventoryService= inventoryService;
        this.notificationService= notificationService;
    }

    public void placeOrder(){
        paymentService.pay();
        inventoryService.updateStock();
        notificationService.sendConfirmation();
    }
}


class RefundService {
    public void refund() {
        System.out.println("Refund completed");
    }
}

class InventoryRestoreService {
    public void restoreStock() {
        System.out.println("Inventory restored");
    }
}

class OrderCancellationFacade{
    private final RefundService refundService;
    private final InventoryRestoreService inventoryRestoreService;
    private final NotificationService notificationService;

    public OrderCancellationFacade(RefundService refundService, InventoryRestoreService inventoryRestoreService, NotificationService notificationService){
        this.refundService=refundService;
        this.inventoryRestoreService=inventoryRestoreService;
        this.notificationService= notificationService;
    }

    public void cancelOrder(){
        refundService.refund();
        inventoryRestoreService.restoreStock();
        notificationService.sendConfirmation();
    }
}