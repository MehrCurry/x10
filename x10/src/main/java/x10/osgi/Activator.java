package x10.osgi;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ManagedServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	static BundleContext bc;
	static final Logger logger=LoggerFactory.getLogger(Activator.class);

	Config config;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(BundleContext bc) {
		this.bc = bc;

		config = new Config();

		Hashtable props = new Hashtable();
		props.put("service.pid", "x10.controllers");
		bc.registerService(ManagedServiceFactory.class.getName(), config, props);
	}

	public void stop(BundleContext bc) {
		config.stop();
		this.bc=null;
	}
}