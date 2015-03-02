import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;


public class Banks implements Strategy{

	public static final int[] FISH_IDS = {335, 331, 349, 317, 327, 353, 377, 383, 359, 371,15271};

	@Override
	public boolean activate() {
		if (Inventory.isFull()) {
			return true;
		}
		return false;
	}

	@Override
	public void execute() {

		SceneObject[] booth = null;

		if (USFisher.useDock == 3) {
			booth = SceneObjects.getNearest(5276);
		} else if (USFisher.useDock == 2 || USFisher.useDock == 1) {
			booth = SceneObjects.getNearest(2213);
		}

		if (Game.getOpenInterfaceId() != 5292 && Inventory.isFull()) {
			if (booth != null) {
				System.out.println("Banking...");
				if (booth[0].getId() == 2213) {
					booth[0].interact(0);
				} else if (booth[0].getId() == 5276) {
					booth[0].interact(1);
				}
				Time.sleep(2000);
				Time.sleep(new SleepCondition() {
					@Override
					public boolean isValid() {                       
						return Game.getOpenInterfaceId() == 5292;
					}

				}, 2000);
			}
		}
		if (Game.getOpenInterfaceId() == 5292 && Inventory.isFull()) {
			if(FISH_IDS != null) {
				try {
					Bank.depositAllExcept(302,310,314,306,304,312,315);
				} catch(Exception e) {
					e.printStackTrace();
				}
				Time.sleep(200);
			}
		}
	}
}

