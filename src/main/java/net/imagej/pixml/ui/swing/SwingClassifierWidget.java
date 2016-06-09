package net.imagej.pixml.ui.swing;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.scijava.Context;
import org.scijava.command.Command;
import org.scijava.command.CommandModule;
import org.scijava.command.CommandService;
import org.scijava.plugin.Plugin;
import org.scijava.thread.ThreadService;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.Configurable;
import net.imagej.pixml.service.PixMLService;

/**
 * TODO: make selection persistent!
 * 
 * @author Martin Horn
 *
 * @param <M>
 */
@Plugin(type = InputWidget.class)
public class SwingClassifierWidget<M extends Serializable> extends SwingInputWidget<Classifier<M>> {

	private JComboBox<Classifier> comboBox;

	@Override
	public Classifier<M> getValue() {
		return (Classifier<M>) comboBox.getSelectedItem();
	}

	@Override
	public void set(WidgetModel model) {
		super.set(model);
		PixMLService pixmlService = model.getContext().getService(PixMLService.class);
		List<Classifier> classifiers = pixmlService.getClassifiers();
		comboBox = new JComboBox<>(classifiers.toArray(new Classifier[classifiers.size()]));

		JPanel p = new JPanel();
		p.add(comboBox);
		JButton config = new JButton("configure");
		comboBox.addActionListener(l -> {
			config.setEnabled(comboBox.getSelectedItem() instanceof Configurable);
		});
		config.addActionListener(
				createConfigAction(() -> (Configurable<Command>) comboBox.getSelectedItem(), model.getContext()));
		p.add(config);
		config.setEnabled(comboBox.getSelectedItem() instanceof Configurable);

		getComponent().add(p);
	}

	@Override
	protected void doRefresh() {
		comboBox.setSelectedItem((Classifier<M>) get().getValue());
	}

	@Override
	public boolean supports(WidgetModel model) {
		return super.supports(model) && model.isType(Classifier.class);
	}

	public static ActionListener createConfigAction(Supplier<Configurable<Command>> s, Context c) {
		CommandService commandService = c.getService(CommandService.class);
		ThreadService threadService = c.getService(ThreadService.class);
		return l -> {
			Future<CommandModule> f = commandService.run((Class<? extends Command>) s.get().getConfigCommandClass(),
					true);
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
