package x10;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockController implements Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(MockController.class);

	private boolean onOff = false;
	private List<UnitListener> listeners = new ArrayList<UnitListener>();
	private Timer timer = new Timer();

	public MockController() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				updateAll();
			}
		}, 0, 5000);
	}

	protected void updateAll() {
		Command cmd = new Command("A1", onOff ? Command.ON : Command.OFF);
		UnitEvent event = new UnitEvent(cmd);
		for (UnitListener l : listeners) {
			if (onOff)
				l.unitOn(event);
			else
				l.unitOff(event);
		}
		onOff = !onOff;
	}

	@Override
	public void addUnitListener(UnitListener listener) {
		logger.debug("addUnitListener(" + listener + ")");
		listeners.add(listener);
	}

	@Override
	public void removeUnitListener(UnitListener listener) {
		logger.debug("removeUnitListener(" + listener + ")");
		listeners.remove(listener);
	}

	@Override
	public void addCommand(Command command) {
		logger.debug("addCommand(" + command + ")");
	}
}
