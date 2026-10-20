interface DeliveryStrategy{
    double billpay();
}

class StandardDelivery implements DeliveryStrategy{
    @Override 
    public double billpay(){
        return 50.0;
    }
}

class ExpressDelivery implements DeliveryStrategy{
    @Override 
    public double billpay(){
        return 100.0;
    }
}

class SameDayDelivery implements DeliveryStrategy{
    @Override 
    public double billpay(){
        return 200.0;
    }
}

class DeliveryService{
    private final DeliveryStrategy deliveryStrategy;

    DeliveryService(DeliveryStrategy deliveryStrategy){
        if(deliveryStrategy == null){
            throw new IllegalArgumentException("Delivery strategy cannot be null");
        }
        this.deliveryStrategy = deliveryStrategy;
    }

    public double calculateBill(){
        return deliveryStrategy.billpay();
    }
}

class DeliveryFactory{
    public static DeliveryService createStrategy(String type){
        if(type == null || type.isEmpty()){
            throw new IllegalArgumentException("Delivery type cannot be null or empty");
        }
        switch(type.toLowerCase()){
            case "standard":
                return new DeliveryService(new StandardDelivery());
            case "express":
                return new DeliveryService(new ExpressDelivery());
            case "sameday":
                return new DeliveryService(new SameDayDelivery());
            default:
                throw new IllegalArgumentException("Unknown delivery type: " + type);
        }
    }
}

class StrategyPattern{
    public static void main(String[] args) {
        DeliveryService standard= new DeliveryService(new StandardDelivery());
        DeliveryService express= new DeliveryService(new ExpressDelivery());
        DeliveryService sameDay= new DeliveryService(new SameDayDelivery());

        System.out.println("Standard Delivery Bill: " + standard.calculateBill());
        System.out.println("Express Delivery Bill: " + express.calculateBill());
        System.out.println("Same Day Delivery Bill: " + sameDay.calculateBill());

        try {
            DeliveryService nullDelivery = new DeliveryService(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // DeliveryStrategy strategy= DeliveryFactory.createStrategy("standard");
        // DeliveryService deliveryService= new DeliveryService(strategy);
        
    }
}