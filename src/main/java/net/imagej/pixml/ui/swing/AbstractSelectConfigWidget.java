package net.imagej.pixml.ui.swing;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.scijava.Context;
import org.scijava.command.Command;
import org.scijava.command.CommandModule;
import org.scijava.command.CommandService;
import org.scijava.plugin.PluginService;
import org.scijava.thread.ThreadService;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.WidgetModel;

/**
 * TODO: make selection persistent!
 * 
 * @author Martin Horn
 *
 */
public abstract class AbstractSelectConfigWidget<C extends Command> extends SwingInputWidget<C> {

	private JComboBox<Wrapper<C>> comboBox;

	@Override
	public C getValue() {
		return getSelectedItem().get();
	}

	abstract Class<C> getObjectClass();

	@Override
	public void set(WidgetModel model) {
		super.set(model);
		PluginService pluginService = model.getContext().getService(PluginService.class);
		List<Wrapper<C>> classifiers = pluginService.createInstancesOfType(getObjectClass()).stream()
				.map(o -> new Wrapper<C>(o)).collect(Collectors.toList());
		comboBox = new JComboBox(classifiers.toArray(new Object[classifiers.size()]));

		JPanel p = new JPanel();
		p.add(comboBox);
		JButton config = new JButton("configure");
		comboBox.addActionListener(l -> {
			updateModel();
		});
		config.addActionListener(createConfigAction(() -> getSelectedItem().get(), c -> {
			getSelectedItem().set(c);
			updateModel();
		}, getContext()));
		p.add(config);

		getComponent().add(p);
	}

	@Override
	protected void doRefresh() {
		comboBox.setSelectedItem(new Wrapper(get().getValue()));
	}

	@Override
	public boolean supports(WidgetModel model) {
		return super.supports(model) && model.isType(getObjectClass());
	}

	public static <C extends Command> ActionListener createConfigAction(Supplier<C> get, Consumer<C> set, Context c) {
		CommandService commandService = c.getService(CommandService.class);
		ThreadService threadService = c.getService(ThreadService.class);
		return l -> {
			Future<CommandModule> f = commandService.run(get.get().getClass(), true);
			// ugly code here -> cleaner solution required
			// reason to run it in an extra thread: action
			// listener event runs in the Event Dispatcher
			// Thread and Future.get() would block it -> config
			// dialog won't open
			threadService.run(() -> {
				try {
					set.accept((C) f.get().getCommand());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
	}

	private Wrapper<C> getSelectedItem() {
		return (Wrapper<C>) comboBox.getSelectedItem();
	}

	private class Wrapper<O> {

		O obj;

		public Wrapper(O obj) {
			this.obj = obj;
		}

		O get() {
			return obj;
		}

		void set(O obj) {
			this.obj = obj;
		}
	}

}
