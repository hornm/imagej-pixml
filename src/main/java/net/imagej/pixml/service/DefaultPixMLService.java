package net.imagej.pixml.service;

import java.util.List;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;

import net.imagej.pixml.Builder;
import net.imagej.pixml.FeatureSet;
import net.imagej.pixml.Predictor;

@Plugin(type = PixMLService.class)
public class DefaultPixMLService extends AbstractService implements PixMLService {

	@Parameter
	private PluginService pluginService;

	@Override
	public List<Builder> getBuilders() {
		return pluginService.createInstancesOfType(Builder.class);
	}
	
	@Override
	public List<Predictor> getPredictors() {
		return pluginService.createInstancesOfType(Predictor.class);
	}

	@Override
	public List<FeatureSet> getFeatureSets() {
		return pluginService.createInstancesOfType(FeatureSet.class);
	}
}
