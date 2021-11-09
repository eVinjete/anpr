package si.evinjete.anpr;

import javaanpr.gui.ReportGenerator;
import java.io.IOException;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;


public class Main {
    public static ReportGenerator rg = new ReportGenerator();
    public static Intelligence systemLogic;
    
    public static void main(String[] args) throws Exception {
        
        if (args.length==3 && args[0].equals("-recognize") && args[1].equals("-i")) {



            try {
                Main.systemLogic = new Intelligence(false);
                System.out.println(systemLogic.recognize(new CarSnapshot(args[2])));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }



        } else {
            System.out.println("End");
        }
    }
}
