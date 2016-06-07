package net.imagej.pixml.services;

import java.util.List;

import org.scijava.service.Service;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.FeatureSet;

public interface PixMLService extends Service {

	List<Classifier> getClassifiers();
	
	List<FeatureSet> getFeatures();

	ClassifiersConfig getClassifiersConfig();
	
	FeatureSetsConfig getFeaturesConfig();
	
	//TODO convenient methods to calculate features, training, and prediction
	
}
