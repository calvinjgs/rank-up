package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class RequirementsArea extends JPanel implements ActionListener{

	private RUGUI ui;

	private JLabel heading;//selected detachment's name
	private JLabel troops, monsters, heroes, warEngines, wildCards;
	private JLabel alliedPoints, totalPoints;
	private JTextField pointsField;
	private JPanel pointsFieldArea;

	private int sdIndex;//selected detachment index

	public RequirementsArea(RUGUI ui) {
		//super(new GridLayout(1, 8));
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.ui = ui;
		this.sdIndex = -1;

		//build JLabels
		heading = new JLabel("", SwingConstants.CENTER);
		this.add(heading);
		troops = new JLabel("", SwingConstants.CENTER);
		this.add(troops);
		monsters = new JLabel("", SwingConstants.CENTER);
		this.add(monsters);
		heroes = new JLabel("", SwingConstants.CENTER);
		this.add(heroes);
		warEngines = new JLabel("", SwingConstants.CENTER);
		this.add(warEngines);
		wildCards = new JLabel("", SwingConstants.CENTER);
		this.add(wildCards);


		alliedPoints = new JLabel("", SwingConstants.CENTER);
		this.add(alliedPoints);
		totalPoints = new JLabel("", SwingConstants.CENTER);
		this.add(totalPoints);

		pointsFieldArea = new JPanel(new GridLayout(2, 1));
		pointsFieldArea.add(new JLabel("Set Points:"));
		pointsField = new JTextField("", 4);
		this.pointsField.setFont(new Font(null, Font.PLAIN, RUData.titleSize));
		pointsField.addActionListener(this);
		pointsFieldArea.add(pointsField);
		this.add(pointsFieldArea);
		pointsFieldArea.setVisible(false);
	}

	public void displayRequirements() {
		if (sdIndex != -1) {
			//get current detachment
			ArmyRequirements ar = this.ui.armyReqs();
			//heading
			heading.setText(this.ui.army().forces(sdIndex).name() + ": ");
			//troops
			troops.setText(RUData.html("Troops: " + ar.troopCurrent(sdIndex) + " / " + ar.troopMax(sdIndex)));
			troops.setOpaque(true);
			if (ar.troopCurrent(sdIndex) < ar.troopMax(sdIndex)) {
				troops.setBackground(Color.GREEN);
			} else if (ar.troopCurrent(sdIndex) > ar.troopMax(sdIndex)) {
				troops.setBackground(Color.RED);
			} else {//if equal
				troops.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			troops.setMaximumSize(new Dimension(troops.getMaximumSize().width, Integer.MAX_VALUE));

			//monsters
			monsters.setText(RUData.html("Monsters: " + ar.monCurrent(sdIndex) + " / " + ar.monMax(sdIndex)));
			monsters.setOpaque(true);
			if (ar.monCurrent(sdIndex) < ar.monMax(sdIndex)) {
				monsters.setBackground(Color.GREEN);
			} else if (ar.monCurrent(sdIndex) > ar.monMax(sdIndex)) {
				monsters.setBackground(Color.RED);
			} else {//if equal
				monsters.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			monsters.setMaximumSize(new Dimension(monsters.getMaximumSize().width, Integer.MAX_VALUE));

			//heroes
			heroes.setText(RUData.html("Heroes: " + ar.heroCurrent(sdIndex) + " / " + ar.heroMax(sdIndex)));
			heroes.setOpaque(true);
			if (ar.heroCurrent(sdIndex) < ar.heroMax(sdIndex)) {
				heroes.setBackground(Color.GREEN);
			} else if (ar.heroCurrent(sdIndex) > ar.heroMax(sdIndex)) {
				heroes.setBackground(Color.RED);
			} else {//if equal
				heroes.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			heroes.setMaximumSize(new Dimension(heroes.getMaximumSize().width, Integer.MAX_VALUE));

			//warEngines
			warEngines.setText(RUData.html("War Engines: " + ar.warEngCurrent(sdIndex) + " / " + ar.warEngMax(sdIndex)));
			warEngines.setOpaque(true);
			if (ar.warEngCurrent(sdIndex) < ar.warEngMax(sdIndex)) {
				warEngines.setBackground(Color.GREEN);
			} else if (ar.warEngCurrent(sdIndex) > ar.warEngMax(sdIndex)) {
				warEngines.setBackground(Color.RED);
			} else {//if equal
				warEngines.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			warEngines.setMaximumSize(new Dimension(warEngines.getMaximumSize().width, Integer.MAX_VALUE));

			//wildCards
			wildCards.setText(RUData.html("Wild Cards: " + ar.wildCurrent(sdIndex) + " / " + ar.wildMax(sdIndex)));
			wildCards.setOpaque(true);
			if (ar.wildCurrent(sdIndex) < ar.wildMax(sdIndex)) {
				wildCards.setBackground(Color.GREEN);
			} else if (ar.wildCurrent(sdIndex) > ar.wildMax(sdIndex)) {
				wildCards.setBackground(Color.RED);
			} else {//if equal
				wildCards.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			wildCards.setMaximumSize(new Dimension(wildCards.getMaximumSize().width, Integer.MAX_VALUE));

			//alliedPoints
			String ap = "Allied Points: " + ar.alliedPointsCurrent() + " / " + ar.alliedPointsMax() + "<br>";
			ap += "Remaining: " + (ar.alliedPointsMax() - ar.alliedPointsCurrent());
			alliedPoints.setText(RUData.html(ap));
			alliedPoints.setOpaque(true);
			if (ar.alliedPointsCurrent() < ar.alliedPointsMax()) {
				alliedPoints.setBackground(Color.GREEN);
			} else if (ar.alliedPointsCurrent() > ar.alliedPointsMax()) {
				alliedPoints.setBackground(Color.RED);
			} else {//if equal
				alliedPoints.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			alliedPoints.setMaximumSize(new Dimension(alliedPoints.getMaximumSize().width, Integer.MAX_VALUE));

			//totalPoints
			String tp = "Total Points: " + ar.pointsCurrent() + " / " + ar.pointsMax() + "<br>";
			tp += "Remaining: " + (ar.pointsMax() - ar.pointsCurrent());
			totalPoints.setText(RUData.html(tp));
			totalPoints.setOpaque(true);
			if (ar.pointsCurrent() < ar.pointsMax()) {
				totalPoints.setBackground(Color.GREEN);
			} else if (ar.pointsCurrent() > ar.pointsMax()) {
				totalPoints.setBackground(Color.RED);
			} else {//if equal
				totalPoints.setBackground(new Color(215, 215, 0));
			}
			//take up as much vertical space as possible
			totalPoints.setMaximumSize(new Dimension(totalPoints.getMaximumSize().width, Integer.MAX_VALUE));

			pointsFieldArea.setVisible(true);
		} else {
			//set up as blank labels
			heading.setText("");
			//troops
			troops.setText("");
			troops.setOpaque(false);
			//monsters
			monsters.setText("");
			monsters.setOpaque(false);
			//heroes
			heroes.setText("");
			heroes.setOpaque(false);
			//warEngines
			warEngines.setText("");
			warEngines.setOpaque(false);
			//wildCards
			wildCards.setText("");
			wildCards.setOpaque(false);
			//alliedPoints
			alliedPoints.setText("");
			alliedPoints.setOpaque(false);
			//totalPoints
			totalPoints.setText("");
			totalPoints.setOpaque(false);

			pointsFieldArea.setVisible(false);
		}

		this.revalidate();
		this.repaint();

	}

	public void actionPerformed(ActionEvent e) {
		String pts = pointsField.getText();
		if (pts.matches("\\d+$")) this.ui.armyReqs().setPointsMax(Integer.parseInt(pts));
		displayRequirements();
	}

	//set things
	public void setSdIndex(int sdi) {
		this.sdIndex = sdi;
	}

	//return things
	public int sdIndex() {
		return this.sdIndex;
	}
}