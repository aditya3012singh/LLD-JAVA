import java.util.ArrayList;
import java.util.List;

class Student {
    String name;
    Teacher teacher;

    Student(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    void displayTeacher() {
        if (teacher != null) {
            System.out.println("Teacher Name: " + teacher.name);
        } else {
            System.out.println("No teacher assigned to student " + name);
        }
    }
}

class Teacher {
    String name;
    Teacher(String name) {
        this.name = name;
    }
}

class Department{
    String name;
    List<Teacher> teachers;

    Department(String name, List<Teacher> teachers) {
        this.name = name;
        this.teachers = teachers;
    }

    void displayTeachers() {
        System.out.println("Teachers in " + name + " Department:");
        for (Teacher teacher : teachers) {
            System.out.println("- " + teacher.name);
        }
    }
}

class Order{
    String orderId;
    List<OrderItem> items = new ArrayList<>();
    Order(String orderId) {
        this.orderId = orderId;
    }

    void addItem(String itemName, int quantity, double price) {
        items.add(new OrderItem(itemName, quantity, price));
    }

    void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Items in the order:");
        for (OrderItem item : items) {
            System.out.println("- " + item.itemName + " (Quantity: " + item.quantity + ", Price: $" + item.price + ")");
        }
    }
}

class OrderItem{
    String itemName;
    int quantity;
    double price;

    OrderItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}