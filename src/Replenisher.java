public class Replenisher implements Runnable {

    private final int id;
    private DrinkMachineMonitor monitor;

    public Replenisher(int id, DrinkMachineMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        System.out.println("Reposador " + id + " arriba");

        while(monitor.hasClients()) {
            System.out.println("Reposador " + id + " reposa la màquina, hi ha " + monitor.getProductsQuantity() + " i en posa ");
            monitor.refillMachine();
        }

        System.out.println("Reposador " + id + " se'n va");
    }
}
