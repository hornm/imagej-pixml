package net.imagej.pixml.services;

import java.io.Serializable;
import java.util.List;

import javax.swing.JPanel;

import net.imagej.pixml.FeatureSet;

public interface FeatureSetsConfig extends Serializable{
	
	JPanel getConfigPanel();
	
	List<FeatureSet> getSelectedFeatureSets();

}
