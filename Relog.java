
import java.awt.Point;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Mouse;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.SceneObjects;

public class Relog implements Strategy {
	public boolean activate() {
		
		if (isLoggedIn()) {
			return false;
		}
		System.out.println("Logging In....");
		return true;
	}

	public void execute() {
		Point login = new Point(452,280);

		Mouse.getInstance().click(login);
		Time.sleep(new SleepCondition() {
			@Override
			public boolean isValid() {
				return isLoggedIn();
			}
		}, 6000);
	}
	
	public static boolean isLoggedIn() {
		try {
		return SceneObjects.getNearest().length > 0;
		} catch(IllegalArgumentException e) {
			// catch
		}
		return false;
		
	}
	
}