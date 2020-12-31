public class Client implements Runnable {

    private static final int MAX_DRINKS = 5;
    private final int id;
    private final String name;
    private int drinkNumber;

    private final DrinkMachineMonitor monitor;

    public Client(int id, String name, DrinkMachineMonitor monitor) {
        this.id = id;
        this.name = name;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        int totalDrinks = (int) (Math.random() * MAX_DRINKS);
        System.out.println("\t\t" + name + " arriba i farà " + totalDrinks + " consumicions");

        if (totalDrinks > 0) {
            if (monitor.hasReplenishers()) {
                for (int i = 0; i < totalDrinks; i++) {
                    monitor.takeProduct();
                    System.out.println(name + " agafa un refresc - consumició: " + ++drinkNumber);
                }
            } else {
                System.out.println(name + ": Aquí no hi ha nigú per a la màquina!!!!");
            }
        }
        monitor.clientLeaves();
        System.out.println("------> " + name + " se'n va, queden " + monitor.getClientsQuantity() + " clients");
    }
}
