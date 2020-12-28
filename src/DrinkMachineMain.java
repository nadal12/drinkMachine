public class DrinkMachineMain {

    private static final int MACHINE_CAPACITY = 10;
    private static final int MAX_CLIENTS = 10;
    private static final int MAX_REPLENISHERS = 5;
    private static final String [] names = {"Ivan", "Àlvar", "Narcís", "Virgínia", "Jessica", "Jonàs", "Pere", "Teix", "Aloma", "Remei"};

    public static void main(String[] args) {

        //Crear nombre de clients i reposadors aleatori.
        int clientsNumber = (int) (Math.random() * MAX_CLIENTS);
        int replenishersNumber = (int) (Math.random() * MAX_REPLENISHERS);

        //Monitor
        DrinkMachineMonitor monitor = new DrinkMachineMonitor(MACHINE_CAPACITY, clientsNumber);

        Thread[] molecules = new Thread[clientsNumber + replenishersNumber];

        System.out.println("COMENÇA LA SIMULACIÓ");
        System.out.println("Avui hi ha " + clientsNumber + " clients i " + replenishersNumber + " reposadors\n" +
                "La màquina de refrescs està buida, hi caben " + MACHINE_CAPACITY + " refrescs");

        for (int i = 0; i < clientsNumber; i++) {
            molecules[i] = new Thread(new Client(i + 1, names[i], monitor));
            molecules[i].start();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = clientsNumber; i < clientsNumber + replenishersNumber; i++) {
            molecules[i] = new Thread(new Replenisher(i - clientsNumber + 1, monitor));
            molecules[i].start();
        }

        for (Thread molecule : molecules) {
            join(molecule);
        }

        System.out.println("ACABA LA SIMULACÍO");
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
