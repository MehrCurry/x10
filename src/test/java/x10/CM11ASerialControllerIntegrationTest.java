package x10;

import static org.junit.Assert.*;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CM11ASerialControllerIntegrationTest {
	private static final Logger logger=LoggerFactory.getLogger(CM11ASerialControllerIntegrationTest.class);
	@Test
	public void test() throws IOException {
		HashSet<CommPortIdentifier> ports = getAvailableSerialPorts();
		if (ports.isEmpty())
			fail("No serial ports availible on your system. Test not run");
		for (CommPortIdentifier ci : ports) {
			logger.debug("Trying: " + ci.getName());
			CM11ASerialController ctrl = new CM11ASerialController(ci.getName());
			assertNotNull(ctrl);
			ctrl.shutdownNow();
		}
	}

	/**
	 * @return A HashSet containing the CommPortIdentifier for all serial ports
	 *         that are not currently being used.
	 */
	public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
		HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
		Enumeration<?> thePorts = CommPortIdentifier.getPortIdentifiers();
		while (thePorts.hasMoreElements()) {
			CommPortIdentifier com = (CommPortIdentifier) thePorts
					.nextElement();
			switch (com.getPortType()) {
			case CommPortIdentifier.PORT_SERIAL:
				try {
					CommPort thePort = com.open("CommUtil", 50);
					thePort.close();
					h.add(com);
				} catch (PortInUseException e) {
					System.out.println("Port, " + com.getName()
							+ ", is in use.");
				} catch (Exception e) {
					System.err.println("Failed to open port " + com.getName());
					e.printStackTrace();
				}
			}
		}
		return h;
	}
}
