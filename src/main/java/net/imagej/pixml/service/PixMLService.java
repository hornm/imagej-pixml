package net.imagej.pixml.service;

import java.util.List;

import org.scijava.service.Service;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.ClassifierFactory;
import net.imagej.pixml.FeatureSet;

public interface PixMLService extends Service {

	List<ClassifierFactory> getClassifierFactories();

	List<FeatureSet> getFeatureSets();

	// TODO convenient methods to calculate features, training, and prediction

}
