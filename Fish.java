import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.wrappers.Npc;

public class Fish implements Strategy {

	@Override
	public boolean activate() {
		return (!Inventory.isFull());
	}

	@Override
	public void execute() {
		final Npc fishSpot[] = Npcs.getNearest(USFisher.spotID);
		final Npc fish = fishSpot[0];

		//Checks then fishes
		System.out.println("Waiting...");
		if (fish != null && Players.getMyPlayer().getAnimation() == -1 && !Inventory.isFull() && Relog.isLoggedIn()) {
			USFisher.getExpCount();
			System.out.println("Fishing...");
			try {
				Npcs.getNearest(USFisher.spotID)[0].interact(USFisher.spotInteractCode);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Time.sleep(2000);
		}

		//Track EXP and caught fish as fishing animation loops
		while (Players.getMyPlayer().getAnimation() != -1 && Relog.isLoggedIn()) {
			USFisher.caughtCounter();
			USFisher.getExpCount();	
			Time.sleep(200);
		}
	}
}