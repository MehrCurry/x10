import x10.*;
import x10.net.*;

public class X10SerialServer
{
    public static void main(String[] args) throws Exception
    {
        Controller controller = new CM11ASerialController(args[0]);
        ControllerServer cs = new ControllerServer(controller, 2400);
        cs.start();
        System.out.println("Threads Started...");
        System.in.read();
    }
}