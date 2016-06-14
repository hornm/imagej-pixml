package net.imagej.pixml.service;

import java.util.List;

import org.scijava.service.Service;

import net.imagej.pixml.Builder;
import net.imagej.pixml.FeatureSet;
import net.imagej.pixml.Predictor;

public interface PixMLService extends Service {

	List<Builder> getBuilders();
	
	List<Predictor> getPredictors();
	
	List<FeatureSet> getFeatureSets();

	//TODO convenient methods to calculate features, training, and prediction
	
}
