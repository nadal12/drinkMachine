/*
Nom i llintages: Nadal Llabrés Belmar
Enllaç al vídeo: https://youtu.be/53h863nLFY4
 */

public class DrinkMachineMonitor {

    private static final int MAX_TIME_TO_TAKE_PRODUCT = 5000;   //Interval de temps per agafar el producte [0, 5000] ms.
    private static final int MAX_TIME_TO_REFILL_MACHINE = 5000; //Interval de temps per reposar la màquina [0, 5000] ms.
    public static final int MACHINE_CAPACITY = 10;
    private int productsQuantity = 0;   //Quantitat de productes a un moment donat.
    private static int clientsQuantity; //Quantitat de clients a un moment donat.
    private static int replenishersQuantity; //Quantitat de reposadors.

    public DrinkMachineMonitor(int clientsQuantity, int replenishersQuantity) {
        DrinkMachineMonitor.clientsQuantity = clientsQuantity;
        DrinkMachineMonitor.replenishersQuantity = replenishersQuantity;
    }

    /**
     * Funció que omple la màquina fins a la seva capacitat màxima. Important que sigui
     * del tipus synchronized per asegurar l'exclusió mutua.
     *
     * @return Retorna la quantitat d'elements que ha afegit.
     */
    synchronized public int refillMachine() {

        //Mentre la màquina estigui plena i hi hagi clients es queda esperant al wait.
        while (productsQuantity == MACHINE_CAPACITY && hasClients()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int refilledQuantity = 0;

        /*Quan s'arriba aqui es perque la màquina no està plena o perque no hi ha clients. Si no hi ha clients no
          omple la màquina i si n'hi ha l'omple fins a la seva capacitat màxima.*/
        if (hasClients()) {
            sleep((long) (Math.random() * MAX_TIME_TO_REFILL_MACHINE));
            refilledQuantity = MACHINE_CAPACITY - productsQuantity;
            productsQuantity = MACHINE_CAPACITY;
        }

        //Notificació per a tots els clients. Indica que la màquina ha estat reposada.
        notifyAll();
        return refilledQuantity;
    }

    /**
     * Mètode per agafar un producte de la màquina. Important que sigui
     * del tipus synchronized per asegurar l'exclusió mutua.
     */
    synchronized public void takeProduct() {

        // Metre no hi hagui productes a la màquina esperam.
        while (productsQuantity < 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Una vegada aqui, vol dir que la màquina té productes i n'agafem un.
        sleep((long) (Math.random() * MAX_TIME_TO_TAKE_PRODUCT));
        productsQuantity--;

        /*Notificar de que s'ha agafat un producte perque els reposadors comprovin si
        n'han de afegir o no*/
        notifyAll();
    }

    /**
     * Mètode per fer esperes.
     *
     * @param ms Milisegons que ha de durar l'espera.
     */
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funció per saber si queden clients o no.
     *
     * @return True -> hi ha clients. False -> no hi ha clients.
     */
    synchronized public boolean hasClients() {
        return clientsQuantity > 0;
    }

    /**
     * Funció per saber si queden reposadors o no.
     *
     * @return True -> hi ha reposadors. False -> no hi ha reposadors.
     */
    synchronized public boolean hasReplenishers() {
        return replenishersQuantity > 0;
    }

    /**
     * Mètode per indicar que un client se'n va.
     */
    synchronized public void clientLeaves() {
        clientsQuantity--;

        //Avisa de que sen va perque es comprovi si queden clients o no.
        notifyAll();
    }

    /**
     * Funció que retorna la quantitat de clients.
     *
     * @return
     */
    synchronized public int getClientsQuantity() {
        return clientsQuantity;
    }

    /**
     * Funció que retorna la capacitat de la màquina.
     *
     * @return
     */
    synchronized public int getMachineCapacity() {
        return MACHINE_CAPACITY;
    }
}
