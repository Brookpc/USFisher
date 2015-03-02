import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.TilePath;

public class Walk implements Strategy {



	@Override
	public boolean activate() {

		SceneObject booth = null;

		if (USFisher.useDock == 3) {
			booth = SceneObjects.getClosest(5276);
		} else {
			booth = SceneObjects.getClosest(2213);
		}

		Npc spot = null;
		spot = Npcs.getClosest(USFisher.spotID);

		if (!Inventory.isFull() && (spot == null || spot.distanceTo() > 6)) {
			System.out.println("Walking...");
			return true;
		} else if (Inventory.isFull() && (booth == null || booth.distanceTo() > 4)) {
			System.out.println("Walking...");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void execute() {
		if (Inventory.isFull()) {
			
			if (USFisher.useDock == 1) {
				TilePath path = new TilePath(USFisher.NORTH_WALK);
				if (path != null) {
					path.traverse();
					Time.sleep(1000);
				}
			} else if (USFisher.useDock == 2) {
				TilePath path = new TilePath(USFisher.SOUTH_WALK);
				if (path != null) {
					path.traverse();
					Time.sleep(1000);
				}
			} else if (USFisher.useDock == 3) {
				TilePath path = new TilePath(USFisher.DONOR_WALK);
				if (path != null) {
					path.traverse();
					Time.sleep(1000);
				}
			}
		} else if (!Inventory.isFull()) {
			if (USFisher.useDock == 1) {
				TilePath path = new TilePath(USFisher.NORTH_WALK).reverse();
				if (path != null) {
					path.traverse();
					Time.sleep(1000);
				}
			} else if (USFisher.useDock == 2) {
				TilePath path = new TilePath(USFisher.SOUTH_WALK).reverse();
				if (path != null) {
					path.traverse();
					Time.sleep(1000);
				}
			} else if (USFisher.useDock == 3) {
				TilePath path = new TilePath(USFisher.DONOR_WALK).reverse();
				if (path != null) {
					path.traverse();
					Time.sleep(1000);
				}
			}
		}
	}
}