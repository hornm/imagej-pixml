package net.imagej.pixml.services;

import java.io.Serializable;
import java.util.List;

import javax.swing.JPanel;

import net.imagej.pixml.Feature;

public interface FeaturesConfig extends Serializable{
	
	JPanel getConfigPanel();
	
	List<Feature> getSelectedFeatures();

}
