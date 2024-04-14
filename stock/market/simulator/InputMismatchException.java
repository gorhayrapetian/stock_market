package stock.market.simulator;

public class InputMismatchException extends Exception {
    public InputMismatchException(){
        super();
    }

    public InputMismatchException(String message){
        super(message);
    }

}
