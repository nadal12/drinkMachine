public class Replenisher implements Runnable {

    private final int id;
    private final DrinkMachineMonitor monitor;

    public Replenisher(int id, DrinkMachineMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        System.out.println("Reposador " + id + " arriba");

        while(monitor.hasClients()) {
            int refilledQuantity = monitor.refillMachine();
            if (refilledQuantity > 0) {
                System.out.println("Reposador " + id + " reposa la màquina, hi ha " + (monitor.getMachineCapacity() - refilledQuantity) + " refrescs i en posa " + refilledQuantity);
            }
        }

        System.out.println("Reposador " + id + " se'n va");
    }
}
