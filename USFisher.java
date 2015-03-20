import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.Area;
import org.rev317.min.api.wrappers.Tile;

@ScriptManifest(author = "Ark",
category = Category.FISHING, 
description = "Ultimate Scape 2 - AIOFisher",
name = "USFisher by Ark",
servers = { "Ultimate Scape" },
version = 2.2)

public class USFisher extends Script implements Paintable {

	//Strategies List
	private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();

	//Donator Areas and Tile arrays
	public final static Tile[] DONOR_WALK = {new Tile (2560,3891,0), new Tile(2571,3895,0)};
	
	//Fishing Guild areas and tile arrays
	public final static Tile[] SOUTH_WALK = {new Tile(2608, 3415, 0), new Tile (2604, 3412, 0), new Tile(2600, 3406, 0), new Tile(2594, 3414, 0), new Tile(2586, 3420, 0)};
	public final static Tile[] NORTH_WALK = {new Tile (2599, 3422, 0), new Tile(2594, 3417), new Tile(2586, 3420, 0)};
	public final static Area northDock = new Area (new Tile (2596, 3420, 0), new Tile(2604, 3420, 0), new Tile(2604, 3425, 0), new Tile(2596, 3425, 0));
	public final static Area southDock = new Area (new Tile (2603, 3416, 0), new Tile(2611, 3416), new Tile(2603, 3407, 0), new Tile(2611, 3407, 0));
	public final static Area bank = new Area (new Tile(2584, 3417, 0), new Tile(2590, 3417, 0), new Tile(2584, 3423, 0), new Tile(2590, 3423, 0));

	//General Variables
	public static int spotID;
	public static int useDock;
	public static int spotInteractCode;

	//Paint Variables
	private final Color textColor = new Color(255, 255, 255);
	private final Font textFont = new Font("Arial", 0, 14);
	private final Timer RUNTIME = new Timer();
	private static Image img;
	public static int fishCount;
	public static int expCount;
	private static int curExp;
	private static int startExp;
	public static boolean isRunning = true;

	//GUI variables
	Gui x = new Gui();
	public boolean guiWait = true;

	/***************************************************************************************************************************************/

	public boolean onExecute() {
		x.setVisible(true);
		while (x.guiIsRunning && guiWait) {
			Time.sleep(200);
		}

		strategies.add(new Relog());
		strategies.add(new Walk());
		strategies.add(new Fish());
		strategies.add(new Banks());
		

		img = getImage("http://i.imgur.com/fRVqz8M.png");

		startExp = Skill.FISHING.getExperience();
		curExp = Skill.FISHING.getExperience();

		provide(strategies);

		return true;
		
	}
	
	/***************************************************************************************************************************************/
	
	public static Image getImage(String url) {
		
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image not found for paint...");
			return null;
		}
	}
	
	/***************************************************************************************************************************************/
	
	public static int getExpCount() {
		curExp = Skill.FISHING.getExperience();
		expCount = ( curExp - startExp );
		return expCount;
	}
	
	/***************************************************************************************************************************************/
	
	public static void caughtCounter() {
		if (Skill.FISHING.getExperience() - startExp != expCount) {
			fishCount++;
		}
	}
	
	/***************************************************************************************************************************************/	
	
	public String addDecimals(int i)
	{
		DecimalFormat x = new DecimalFormat("#,###");
		return "" + x.format(i);
	}
	
	/***************************************************************************************************************************************/

	@Override
	public void paint(Graphics arg0) {

		Graphics2D g = (Graphics2D) arg0;
		if (guiWait) {
			Time.sleep(200);
		}else {

			g.drawImage(img, 4, 23, null);
			g.setFont(textFont);
			g.setColor(textColor);
			g.drawString(addDecimals(fishCount), 82, 57);
			g.drawString(addDecimals(expCount), 82, 70);
			g.drawString("" + RUNTIME, 82, 83);

		}
	}
	
	/***************************************************************************************************************************************/

	public class Gui extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6241803601296202605L;
		public boolean guiIsRunning = true;
		private JPanel contentPane;

		public void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Gui frame = new Gui();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Line 186");
					}
				}
			});
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Gui() {
			initComponents();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 150, 180);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			//Title Label
			lblUSFisher = new JLabel("USFisher");
			lblUSFisher.setFont(new Font("Arial", Font.PLAIN, 20));
			lblUSFisher.setBounds(15, 11, 101, 16);
			contentPane.add(lblUSFisher);

			//Ready Label
			lblReady = new JLabel("Ready?");
			lblReady.setBounds(17, 49, 82, 14);
			contentPane.add(lblReady);

			//Combo Box
			JComboBox fishList = new JComboBox();
			fishList.setModel(new DefaultComboBoxModel(fishStrings));
			fishList.setSelectedIndex(0);
			fishList.setBounds(10, 75, 95, 23);
			contentPane.add(fishList);

			//Start Button
			btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(fishList.getSelectedIndex() == 0) {
						spotID = 320;
						useDock = 2;
						spotInteractCode = 0;
					} else if (fishList.getSelectedIndex() == 1) {
						spotID = 328;
						useDock = 1;
						spotInteractCode = 0;
					} else if (fishList.getSelectedIndex() == 2) {
						spotID = 321;
						useDock = 2;
						spotInteractCode = 0;
					} else if (fishList.getSelectedIndex() == 3) {
						spotID = 321;
						useDock = 2;
						spotInteractCode = 2;
					} else if (fishList.getSelectedIndex() == 4) {
						spotID = 322;
						useDock = 2;
						spotInteractCode = 2;
					} else if (fishList.getSelectedIndex()== 5) {
						spotID = 233;
						useDock = 3;
						spotInteractCode = 0;
					}
					guiWait = false;
					isRunning = false;
					x.dispose();
				}

			});
			btnStart.setBounds(10, 112, 95, 23);
			contentPane.add(btnStart);

		}

		private void initComponents() {
			lblUSFisher= new JLabel();
			lblReady = new JLabel();
		}
		
		public String[] fishStrings = { "Shrimp", "Trout", "Lobster", "Tuna/SwordFish", "Shark", "RockTail" };
		private JLabel lblUSFisher;
		private JButton btnStart;
		@SuppressWarnings({ "rawtypes", "unused"})
		private JComboBox fishList;
		private JLabel lblReady;
	}
}
