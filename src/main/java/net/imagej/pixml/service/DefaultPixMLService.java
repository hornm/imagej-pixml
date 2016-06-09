package net.imagej.pixml.service;

import java.util.List;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.FeatureSet;

@Plugin(type = PixMLService.class)
public class DefaultPixMLService extends AbstractService implements PixMLService {

	@Parameter
	private PluginService pluginService;

	@Override
	public List<Classifier> getClassifiers() {
		return pluginService.createInstancesOfType(Classifier.class);
	}

	@Override
	public List<FeatureSet> getFeatureSets() {
		return pluginService.createInstancesOfType(FeatureSet.class);
	}
}
