package net.imagej.pixml;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.widget.InputHarvester;

import net.imagej.pixml.service.PixMLService;
import net.imagej.pixml.ui.swing.SwingClassifierWidget;


/**
 * A class that is Configurable is associated with a 'config' command C. After
 * instantiating, populating the command's C {@link Parameter}s (potentially by opening a
 * configure dialog, see {@link InputHarvester}) and running it, the config object is
 * passed to the {@link Configurable#configure(Command)}-method. All this
 * takes, e.g., place in {@link SwingClassifierWidget}.
 * 
 * @author Martin Horn
 */
public interface Configurable<C extends Command> {
	
	Class<C> getConfigCommandClass();
	
	void configure(C config);

}
