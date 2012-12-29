package x10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockController implements Controller {
	private static final Logger logger=LoggerFactory.getLogger(MockController.class);
	@Override
	public void addUnitListener(UnitListener listener) {
		logger.debug("addUnitListener(" + listener + ")");
	}

	@Override
	public void removeUnitListener(UnitListener listener) {
		logger.debug("removeUnitListener(" + listener + ")");
	}

	@Override
	public void addCommand(Command command) {
		logger.debug("addCommand(" + command + ")");
	}
}
