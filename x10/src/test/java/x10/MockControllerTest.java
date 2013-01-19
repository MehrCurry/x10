package x10;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

public class MockControllerTest {

	@Test
	public void testAddUnitListener() throws InterruptedException {
		Controller ctrl=new MockController();
		UnitListener listener=Mockito.mock(UnitListener.class);
		ctrl.addUnitListener(listener);
		Thread.sleep(6000);
		verify(listener).unitOn(any(UnitEvent.class));
	}

}
