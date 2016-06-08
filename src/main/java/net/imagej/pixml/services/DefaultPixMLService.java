package net.imagej.pixml.services;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.scijava.command.Command;
import org.scijava.command.CommandModule;
import org.scijava.command.CommandService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.prefs.PrefService;
import org.scijava.service.AbstractService;
import org.scijava.thread.ThreadService;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.Configurable;
import net.imagej.pixml.FeatureSet;

@Plugin(type = PixMLService.class)
public class DefaultPixMLService extends AbstractService implements PixMLService {

	@Parameter
	private PluginService pluginService;

	@Parameter
	private CommandService commandService;

	@Parameter
	private ThreadService threadService;

	@Override
	public List<Classifier> getClassifiers() {
		return pluginService.createInstancesOfType(Classifier.class);
	}

	@Override
	public List<FeatureSet> getFeatureSets() {
		return pluginService.createInstancesOfType(FeatureSet.class);
	}

	@Override
	public ClassifiersConfig getClassifiersConfig() {
		return new ClassifiersConfig() {

			private List<Classifier> classifiers = getClassifiers();
			private JComboBox<Classifier> comboBox = new JComboBox<>(
					classifiers.toArray(new Classifier[classifiers.size()]));

			@Override
			public Classifier getSelectedClassifier() {
				return (Classifier) comboBox.getSelectedItem();
			}

			@Override
			public JPanel getConfigPanel() {
				return new JPanel() {
					{
						add(comboBox);
						JButton config = new JButton("configure");
						comboBox.addActionListener(l -> {
							config.setEnabled(comboBox.getSelectedItem() instanceof Configurable);
						});
						config.addActionListener(
								createConfigAction(() -> (Configurable<Command>) comboBox.getSelectedItem()));
						add(config);
						config.setEnabled(comboBox.getSelectedItem() instanceof Configurable);
					}
				};
			}
		};
	}

	@Override
	public FeatureSetsConfig getFeaturesConfig() {
		return new FeatureSetsConfig() {

			private List<FeatureSetPanel> featureSetPanels;

			@Override
			public List<FeatureSet> getSelectedFeatureSets() {
				// return only the selected feature sets
				return featureSetPanels.stream().filter(fsp -> fsp.isSelected()).map(fsp -> fsp.getFeatureSet())
						.collect(Collectors.toList());
			}

			@Override
			public JPanel getConfigPanel() {
				return new JPanel() {
					{
						featureSetPanels = new ArrayList<>();
						// create a checkbox, a label and a config-button for
						// each
						// feature set
						List<FeatureSet> featureSets = getFeatureSets();
						setLayout(new GridLayout(featureSets.size(), 1));
						featureSets.forEach(fs -> {
							FeatureSetPanel fsp = new FeatureSetPanel(fs);
							featureSetPanels.add(fsp);
							add(fsp);
						});
					}
				};
			}
		};
	}

	/**
	 * Panel for a feature set with a checkbox (to activate/inactivate it), a
	 * label and a config-button.
	 * 
	 * TODO: persist the feature set selections somehow (e.g. by using the
	 * {@link PrefService})
	 */
	private class FeatureSetPanel extends JPanel {

		private FeatureSet fs;

		private JCheckBox checkbox;

		public FeatureSetPanel(FeatureSet fs) {
			this.fs = fs;
			checkbox = new JCheckBox(fs.toString());
			add(checkbox);

			JButton config = new JButton("Configure");
			config.setEnabled(fs instanceof Configurable);
			config.addActionListener(createConfigAction(() -> (Configurable<Command>) fs));
			add(config);
		}

		boolean isSelected() {
			return checkbox.isSelected();
		}

		FeatureSet getFeatureSet() {
			return fs;
		}

	}

	private ActionListener createConfigAction(Supplier<Configurable<Command>> s) {
		return l -> {
			Future<CommandModule> f = commandService
					.run((Class<? extends Command>) s.get().getConfigCommandClass(), true);
			// ugly code here -> cleaner solution required
			// reason to run it in an extra thread: action
			// listener event runs in the Event Dispatcher
			// Thread and Future.get() would block it -> config
			// dialog won't open
			threadService.run(() -> {
				try {
					s.get().configure((Command) f.get().getDelegateObject());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
	}

}
