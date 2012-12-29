package x10.osgi;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import x10.CM11ASerialController;
import x10.CM17ASerialController;
import x10.Controller;
import x10.net.SocketController;

public class Activator implements BundleActivator, ManagedServiceFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(Activator.class);
	private BundleContext ctx;
	private ServiceRegistration<ManagedServiceFactory> myReg;
	private HashMap<String, ServiceRegistration<Controller>> services;

	@Override
	public String getName() {
		return "X10 Bundle";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties)
			throws ConfigurationException {
		logger.debug("configuration updated:" + properties);
		ServiceRegistration<Controller> reg = services.get(pid);
		if (reg == null) {
			try {
				Controller c;
				String module = (String) properties.get("module");
				String port = (String) properties.get("Port");
				if ("cm11a".equalsIgnoreCase(module)) {
					c=new CM11ASerialController(port);
				} else if (("cm17a".equalsIgnoreCase(module) || "firecracker".equalsIgnoreCase(module))) {
					c=new CM17ASerialController(port);
				} else if ("socket".equalsIgnoreCase(module)) {
					String[] parts=port.split(":");
					c=new SocketController(parts[0], Integer.parseInt(parts[1]));
				} else {
					throw new UnsupportedOperationException("Unknown controller type: " + module);
				}

				services.put(pid, ctx.registerService(Controller.class,
						c, properties));
			} catch (UnsatisfiedLinkError e) {
				String libraryPath = System.getProperty("java.library.path");
				logger.error("Please copy rxtx native libraries (.dll/.so) to " + libraryPath, e);
			}
			catch (Exception e) {
				logger.warn("Exception while creating X10 controller:", e);
			}
		} else {
			// i should do some update here
		}

	}

	@Override
	public void deleted(String pid) {
		ServiceRegistration<Controller> reg = services.get(pid);
		if (reg != null) {
			reg.unregister();
		}
	}

	@Override
	public void start(BundleContext context) throws Exception {
		logger.debug("starting factory...");
		this.ctx = context;
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Constants.SERVICE_PID, "x10.servicefactory");
		myReg = context.registerService(ManagedServiceFactory.class, this,
				properties);
		System.out.println("registered as ManagedServiceFactory");
		services = new HashMap<String, ServiceRegistration<Controller>>();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		for (ServiceRegistration<?> reg : services.values()) {
			logger.debug("deregister " + reg);
			reg.unregister();
		}
		if (myReg != null) {
			myReg.unregister();
		} else {
			logger.debug("my service registration as already null "
					+ "(although it shouldn't)!");
		}
	}

}
