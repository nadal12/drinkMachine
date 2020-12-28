public class Client implements Runnable {

    private static final int MAX_DRINKS = 5;
    private final int id;
    private final String name;
    private int totalDrinks;
    private int drinkNumber;

    private DrinkMachineMonitor monitor;

    public Client(int id, String name, DrinkMachineMonitor monitor) {
        this.id = id;
        this.name = name;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        totalDrinks =  (int) (Math.random() * MAX_DRINKS);
        System.out.println("\t\t" + name + " arriba i farà " + totalDrinks + " consumicions");

        for (int i = 0; i < totalDrinks; i++) {
            System.out.println(name + " agafa un refresc - consumició: " + ++drinkNumber);
            monitor.takeProduct();
        }

        monitor.clientLeaves();
        System.out.println("------> " + name + " se'n va, queden " + 999 + " clients");
    }
}
