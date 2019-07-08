package ngp.data.encryption.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InputFileReader extends Closeable {
    public List<String> nextLine();
    public Map<String, String> nextRecord();
    public boolean hasNext();
    public List<String> getHeaderAsList ();
}

