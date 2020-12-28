public class DrinkMachineMonitor {

    private static final int MAX_TIME_TO_TAKE_PRODUCT = 5000;
    private static final int MAX_TIME_TO_REFILL_MACHINE = 5000;
    private int machineCapacity;
    private int productsQuantity = 0;
    private static int clientsQuantity;

    public DrinkMachineMonitor(int machineCapacity, int clientsQuantity) {
        this.machineCapacity = machineCapacity;
        this.clientsQuantity = clientsQuantity;
    }

    public void refillMachine() {
        sleep((long) (Math.random() * MAX_TIME_TO_REFILL_MACHINE));
        productsQuantity = machineCapacity;
    }

    public void takeProduct() {
        sleep((long) (Math.random() * MAX_TIME_TO_TAKE_PRODUCT));
        productsQuantity--;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasClients() {
        return clientsQuantity > 0;
    }

    public void clientLeaves() {
        clientsQuantity--;
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }
}
