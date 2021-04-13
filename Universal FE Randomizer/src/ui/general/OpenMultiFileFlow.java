package ui.general;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OpenMultiFileFlow implements Listener {

    Shell parent;
    MultiFileFlowDelegate delegate;
    List<String> filterExtensions = new LinkedList<>();
    List<String> filterNames = new LinkedList<>();

    public OpenMultiFileFlow(Shell parent, MultiFileFlowDelegate delegate) {
        super();
        this.parent = parent;
        this.delegate = delegate;
        filterExtensions.add("*");
        filterNames.add("All Files (*.*)");
    }

    public void addFilterFileTypes(String... fileTypes)
    {
        filterExtensions.add(0, String.join(";", fileTypes));
        filterNames.add(0, String.join(",", fileTypes));
    }

    @Override
    public void handleEvent(Event event) {
        // TODO Auto-generated method stub
        FileDialog openDialog = new FileDialog(parent, SWT.OPEN | SWT.MULTI);
        openDialog.setFilterExtensions(filterExtensions.toArray(new String[0]));
        openDialog.setFilterNames(filterNames.toArray(new String[0]));
        openDialog.open();
        delegate.onSelectedFiles(Arrays.stream(openDialog.getFileNames())
                .map(x -> openDialog.getFilterPath() + File.separator + x)
                .collect(Collectors.toList())
                .toArray(new String[0]));
    }

}
