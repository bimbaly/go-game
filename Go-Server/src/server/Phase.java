package server;

import java.io.IOException;

public interface Phase {
	public boolean process(String input) throws IOException;
}
