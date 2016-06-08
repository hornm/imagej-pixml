package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.command.Command;

public interface FeatureSetConfig extends Command, Serializable {
	
	public static final class EmptyFeatureSetConfig implements FeatureSetConfig {

		@Override
		public void run() {
			//			
		}
		
	}

}
