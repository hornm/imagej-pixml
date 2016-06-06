package net.imagej.pixml.services;

import java.util.List;

import org.scijava.service.Service;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.Feature;

public interface PixMLService extends Service {

	List<Classifier> getClassifiers();
	
	List<Feature> getFeatures();

	ClassifiersConfig getClassifiersConfig();
	
	FeaturesConfig getFeaturesConfig();
	
	//TODO convenient methods to calculate features, training, and prediction
	
}
