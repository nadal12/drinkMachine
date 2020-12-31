public class DrinkMachineMonitor {

    private static final int MAX_TIME_TO_TAKE_PRODUCT = 5000;
    private static final int MAX_TIME_TO_REFILL_MACHINE = 5000;
    private final int machineCapacity;
    private int productsQuantity = 0;
    private static int clientsQuantity;
    private static int replenishersQuantity;

    public DrinkMachineMonitor(int machineCapacity, int clientsQuantity, int replenishersQuantity) {
        this.machineCapacity = machineCapacity;
        DrinkMachineMonitor.clientsQuantity = clientsQuantity;
        DrinkMachineMonitor.replenishersQuantity = replenishersQuantity;
    }

    synchronized public int refillMachine() {

        while (productsQuantity == 10 && hasClients()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        sleep((long) (Math.random() * MAX_TIME_TO_REFILL_MACHINE));
        int refilledQuantity = machineCapacity - productsQuantity;
        productsQuantity = machineCapacity;
        notifyAll();
        return refilledQuantity;
    }

    synchronized public void takeProduct() {
        while (productsQuantity < 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        sleep((long) (Math.random() * MAX_TIME_TO_TAKE_PRODUCT));
        productsQuantity--;
        notify();
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

    public boolean hasReplenishers() {
        return replenishersQuantity > 0;
    }

    synchronized public void clientLeaves() {
        clientsQuantity--;
        notify();
    }

    public int getClientsQuantity() {
        return clientsQuantity;
    }

    public int getMachineCapacity() {
        return machineCapacity;
    }
}
