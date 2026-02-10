package registration;
import java.util.Scanner;

public class Frontend {
    final String ADD_CMD = "A";
    final String REMOVE_CMD = "R";
    final String OFFER_CMD = "O";
    final String CLOSE_CMD = "C";
    final String ENROLL_CMD = "E";
    final String DROP_CMD = "D";
    final String STOP_CMD = "Q";

    // keep under 40 lines
    public void run(){
        String onRunMsg = "Registration System is Running";
        System.out.println(onRunMsg);
        Scanner sc = new Scanner(System.in);
        String input,inputCmd;
        //loop forever unless stopped
        while (true) {
            if (sc.hasNextLine()) {
                input = sc.nextLine();
                inputCmd = input.substring(0, 1); //get first letter
                input = input.substring(1);
                if (inputCmd.equals(ADD_CMD)) {
                    setADD_CMD(input);
                } else if (inputCmd.equals(REMOVE_CMD)) {
                    setREMOVE_CMD(input);
                } else if (inputCmd.equals(OFFER_CMD)) {
                    setOFFER_CMD(input);
                } else if (inputCmd.equals(CLOSE_CMD)) {
                    setCLOSE_CMD(input);
                } else if (inputCmd.equals(ENROLL_CMD)) {
                    setENROLL_CMD(input);
                } else if (inputCmd.equals(DROP_CMD)) {
                    setDROP_CMD(input);
                } else if (inputCmd.equals(STOP_CMD)) {
                    break;
                }
                //ERROR
                else{
                    System.out.println(inputCmd + " is an invalid command!");
                }
            }
        }
        System.out.println("Registration System is Terminated.");
    }

    private void setADD_CMD(String input){
        System.out.println("running add cmd");
    }
    private void setREMOVE_CMD(String input){
        System.out.println("running remove cmd");
    }
    private void setOFFER_CMD(String input){
        System.out.println("running offer cmd");
    }
    private void setCLOSE_CMD(String input){
        System.out.println("running close cmd");
    }
    private void setENROLL_CMD(String input){
        System.out.println("running enroll cmd");
    }
    private void setDROP_CMD(String input){
        System.out.println("running drop cmd");
    }

}
