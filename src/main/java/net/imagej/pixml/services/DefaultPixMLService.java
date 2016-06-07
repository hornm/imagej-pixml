package net.imagej.pixml.services;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.scijava.command.CommandService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.ClassifierConfig;
import net.imagej.pixml.FeatureSet;

@Plugin(type = PixMLService.class)
public class DefaultPixMLService extends AbstractService implements PixMLService {

	@Parameter
	private PluginService pluginService;

	@Parameter
	private CommandService commandService;

	@Override
	public List<Classifier> getClassifiers() {
		return pluginService.createInstancesOfType(Classifier.class);
	}

	@Override
	public List<FeatureSet> getFeatures() {
		return pluginService.createInstancesOfType(FeatureSet.class);
	}

	@Override
	public ClassifiersConfig getClassifiersConfig() {
		return new ClassifiersConfig() {

			@Override
			public Classifier getSelectedClassifier() {
				return null;
			}

			@Override
			public JPanel getConfigPanel() {
				return new JPanel() {
					{
						List<Classifier> classifiers = getClassifiers();
						JComboBox<Classifier> comboBox = new JComboBox<>(
								classifiers.toArray(new Classifier[classifiers.size()]));
						add(comboBox);

						JButton config = new JButton("configure");
						config.addActionListener(l -> {
							Classifier c = (Classifier) comboBox.getSelectedItem();
							try {
								commandService.run(c.getClassifierConfigClass(), true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
						add(config);
					}
				};
			}
		};
	}

	@Override
	public FeatureSetsConfig getFeaturesConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
