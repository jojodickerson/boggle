/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V4
 * Joanna Dickerson
 *
 * Die Class
 ***************************************************************/

package p1;

import javafx.scene.control.Label;

public class Die extends Label{
	private Boolean visited = false;

	//V4
	public Boolean getVisited() { return visited; }
	public void setVisited(Boolean visited) { this.visited = visited; }

	public Die(String s){
		this.setText(s);
		this.getStyleClass().add("die");
	}
}
