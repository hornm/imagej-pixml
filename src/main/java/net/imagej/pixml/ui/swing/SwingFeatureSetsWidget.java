package net.imagej.pixml.ui.swing;

import java.awt.GridLayout;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.scijava.Context;
import org.scijava.convert.Converter;
import org.scijava.plugin.Plugin;
import org.scijava.prefs.PrefService;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import net.imagej.pixml.FeatureSet;
import net.imagej.pixml.FeatureSets;
import net.imagej.pixml.service.PixMLService;

/**
 * TODO: make the selection persistent, e.g. by using (and modifing) the
 * {@link PrefService} and providing the right to/from string {@link Converter}
 * s.
 * 
 * @author Martin Horn
 */
@Plugin(type = InputWidget.class)
public class SwingFeatureSetsWidget extends SwingInputWidget<FeatureSets> {

	private List<FeatureSetPanel> featureSetPanels;

	@Override
	public FeatureSets getValue() {
		List<FeatureSet> list = featureSetPanels.stream().filter(fsp -> fsp.isSelected())
				.map(fsp -> fsp.getFeatureSet()).collect(Collectors.toList());
		return new FeatureSetsListWrapper(list);
	}

	@Override
	public void set(WidgetModel model) {
		super.set(model);
		PixMLService pixmlService = model.getContext().getService(PixMLService.class);
		featureSetPanels = new ArrayList<>();
		JPanel p = new JPanel();
		// create a checkbox, a label and a config-button for
		// each
		// feature set
		List<FeatureSet> featureSets = pixmlService.getFeatureSets();
		p.setLayout(new GridLayout(featureSets.size(), 1));
		featureSets.forEach(fs -> {
			FeatureSetPanel fsp = new FeatureSetPanel(fs, model.getContext());
			featureSetPanels.add(fsp);
			p.add(fsp);
		});
		getComponent().add(p);
	}

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supports(final WidgetModel model) {
		return super.supports(model) && model.isType(FeatureSets.class);
	}

	/**
	 * Panel for a feature set with a checkbox (to activate/inactivate it), a
	 * label and a config-button.
	 * 
	 */
	private class FeatureSetPanel extends JPanel {

		private FeatureSet fs;

		private JCheckBox checkbox;

		public FeatureSetPanel(FeatureSet fs, Context c) {
			this.fs = fs;
			checkbox = new JCheckBox(fs.toString());
			checkbox.addActionListener(l -> {
				updateModel();
			});
			add(checkbox);

			JButton config = new JButton("Configure");
			// config.setEnabled(fs instanceof Configurable);
			// config.addActionListener(
			// SwingClassifierWidget.createConfigAction(() ->
			// (Configurable<Command>) fs, c));
			add(config);
		}

		boolean isSelected() {
			return checkbox.isSelected();
		}

		FeatureSet getFeatureSet() {
			return fs;
		}

	}

	private class FeatureSetsListWrapper extends AbstractList<FeatureSet> implements FeatureSets {

		private List<FeatureSet> list;

		public FeatureSetsListWrapper(List<FeatureSet> list) {
			this.list = list;
		}

		@Override
		public FeatureSet get(int index) {
			return list.get(index);
		}

		@Override
		public int size() {
			return list.size();
		}

	}

}
