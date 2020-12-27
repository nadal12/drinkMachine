public class DrinkMachineMain {

    private static final int MACHINE_CAPACITY = 10;
    private static final int MAX_CLIENTS = 10;
    private static final int MAX_REPLENISHER = 5;

    public static void main(String[] args) {

        //Crear nombre de clients i reposadors aleatori.
        int clientsNumber = (int) (Math.random() * MAX_CLIENTS);
        int replenishersNumber = (int) (Math.random() * MAX_REPLENISHER);

        Thread[] molecules = new Thread[clientsNumber + replenishersNumber];

        System.out.println("COMENÇA LA SIMULACIÓ");
        System.out.println("Avui hi ha " + clientsNumber + " clients i " + replenishersNumber + " reposadors\n" +
                "La màquina de refrescs està buida, hi caben " + MACHINE_CAPACITY + " refrescs");

        for (int i = 0; i < clientsNumber; i++) {
            molecules[i] = new Thread(new Client(i + 1));
            molecules[i].start();
        }

        for (int i = clientsNumber; i < clientsNumber + replenishersNumber; i++) {
            molecules[i] = new Thread(new Replenisher(i - clientsNumber + 1));
            molecules[i].start();
        }

        for (Thread molecule : molecules) {
            join(molecule);
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
