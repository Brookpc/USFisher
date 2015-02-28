import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.Tile;

public class Fish implements Strategy {
	Tile startDock1 = new Tile(2601,3421);
	Tile startDock2 = new Tile(2608,3415);
		
		@Override
		public boolean activate() {
			
			return (!Inventory.isFull());
		}

		@Override
		public void execute() {
			final Npc fishSpot[] = Npcs.getNearest(USFisher.spotID);
			final Npc fish = fishSpot[0];
			
			if (fish != null && Players.getMyPlayer().getAnimation() == -1 && !Inventory.isFull() && Relog.isLoggedIn()) {
				try {
					USFisher.getExpCount();
					System.out.println("Fishing...");
					Npcs.getNearest(USFisher.spotID)[0].interact(USFisher.spotInteractCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Time.sleep(2000);
			}
//			} else if (fish != null  && Players.getMyPlayer().getAnimation() == -1) {
//				fish.getLocation().walkTo();
//				Time.sleep(500);
//			}
			while (Players.getMyPlayer().getAnimation() != -1 && Relog.isLoggedIn()) {
				USFisher.getExpCount();
				Time.sleep(200);
			}
		}
	}